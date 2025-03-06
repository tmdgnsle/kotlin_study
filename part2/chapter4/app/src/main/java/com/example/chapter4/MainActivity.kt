package com.example.chapter4

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chapter4.adapter.UserAdapter
import com.example.chapter4.databinding.ActivityMainBinding
import com.example.chapter4.model.Repo
import com.example.chapter4.model.UserDto
import com.example.chapter4.network.GithubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter



    private val handler = Handler(Looper.getMainLooper())
    private var searchFor = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        enableEdgeToEdge()
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }





        userAdapter = UserAdapter{
            val intent = Intent(this@MainActivity, RepoActivity::class.java)
            intent.putExtra("username", it.username)

            startActivity(intent)
        }

        binding.userRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }

        val runnable = Runnable {
            searchUser()
        }

        binding.searchEditText.addTextChangedListener {

            searchFor = it.toString()
            handler.removeCallbacks(runnable)
            handler.postDelayed(
                runnable,
                300,
            )

        }


    }


    private fun searchUser() {
        val githubService = APIClient.retrofit.create(GithubService::class.java)
        githubService.searchUsers(searchFor).enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                userAdapter.submitList(response.body()?.items)
            }

            override fun onFailure(p0: Call<UserDto>, p1: Throwable) {
                Toast.makeText(this@MainActivity, "에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
                p1.printStackTrace()
            }
        })
    }
}