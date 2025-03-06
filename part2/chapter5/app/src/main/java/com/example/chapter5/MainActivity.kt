package com.example.chapter5

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chapter5.databinding.ActivityMainBinding
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var newsAdapter: NewsAdapter
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://news.google.com/")
        .addConverterFactory(
            TikXmlConverterFactory.create(
                TikXml.Builder()
                    .exceptionOnUnreadXml(false)
                    .build()
            )
        ).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        newsAdapter = NewsAdapter { url ->
            startActivity(
                Intent(this, WebViewActivity::class.java).apply {
                    putExtra("url", url)
                }
            )
        }
        val newsService = retrofit.create(NewsService::class.java)

        binding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = newsAdapter
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.feedChip.setOnClickListener {
            binding.chipGroup.clearCheck()
            binding.feedChip.isChecked = true
            newsService.mainFeed().submitList()
            //todo api 호출, 리스트를 변경
        }
        binding.politicsChip.setOnClickListener {
            binding.chipGroup.clearCheck()
            newsService.politicsNews().submitList()
            binding.politicsChip.isChecked = true
        }
        binding.economyChip.setOnClickListener {
            binding.chipGroup.clearCheck()
            newsService.economyNews().submitList()
            binding.economyChip.isChecked = true
        }
        binding.societyChip.setOnClickListener {
            binding.chipGroup.clearCheck()
            newsService.societyNews().submitList()
            binding.societyChip.isChecked = true
        }
        binding.itChip.setOnClickListener {
            binding.chipGroup.clearCheck()
            newsService.itNews().submitList()
            binding.itChip.isChecked = true
        }
        binding.sportChip.setOnClickListener {
            binding.chipGroup.clearCheck()
            newsService.sportsNews().submitList()
            binding.sportChip.isChecked = true
        }

        binding.searchTextInputEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.chipGroup.clearCheck()

                binding.searchTextInputEditText.clearFocus()

                val ims = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                ims.hideSoftInputFromWindow(v.windowToken, 0)

                newsService.search(binding.searchTextInputEditText.text.toString()).submitList()


                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false

        }



        newsService.mainFeed().submitList()


    }

    private fun Call<NewsRss>.submitList() {
        enqueue(object : Callback<NewsRss> {
            override fun onResponse(call: Call<NewsRss>, response: Response<NewsRss>) {
                Log.e("MainActivity", "${response.body()?.channel?.items}")

                val list = response.body()?.channel?.items.orEmpty().transform()
                newsAdapter.submitList(list)

                binding.notFoundView.isVisible = list.isEmpty()

                list.forEachIndexed { index, news ->
                    Thread {
                        try {
                            val jsoup = Jsoup.connect(news.link).get()
                            val elements = jsoup.select("meta[property^=og:]")
                            val ogImageNode = elements.find { node ->
                                node.attr("property") == "og:image"
                            }
                            news.imageUrl = ogImageNode?.attr("content")
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        runOnUiThread {
                            newsAdapter.notifyItemChanged(index)
                        }
                    }.start()
                }


            }

            override fun onFailure(call: Call<NewsRss>, t: Throwable) {
                t.printStackTrace();
            }
        })
    }

}