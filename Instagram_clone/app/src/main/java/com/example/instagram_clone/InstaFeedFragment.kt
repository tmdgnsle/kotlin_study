package com.example.instagram_clone

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InstaFeedFragment : Fragment() {
    lateinit var retrofitService: RetrofitService
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.insta_feed_fragment, container, false)
    }

    fun postLike(post_id: Int) {
        retrofitService.postLike(post_id).enqueue(object : Callback<Any> {
            override fun onResponse(p0: Call<Any>, p1: Response<Any>) {
                Toast.makeText(activity, "좋아요!", Toast.LENGTH_SHORT).show()


            }

            override fun onFailure(p0: Call<Any>, p1: Throwable) {
                Toast.makeText(activity, "좋아요 실패", Toast.LENGTH_SHORT).show()

            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitService = retrofit.create(RetrofitService::class.java)

        retrofitService.getInstagramPosts().enqueue(object : Callback<ArrayList<InstaPost>> {
            override fun onResponse(
                p0: Call<ArrayList<InstaPost>>,
                p1: Response<ArrayList<InstaPost>>
            ) {
                val postList = p1.body()
                val postRecyclerView = view.findViewById<RecyclerView>(R.id.feed_list)
                postRecyclerView.adapter = PostRecyclerViewAdapter(
                    postList!!,
                    LayoutInflater.from(activity),
                    Glide.with(activity!!),
                    this@InstaFeedFragment,
                    activity as (InstaMainActivity)
                )
            }

            override fun onFailure(p0: Call<ArrayList<InstaPost>>, p1: Throwable) {
                Log.d("instaa", "error: $p1")
            }
        })
    }

    class PostRecyclerViewAdapter(
        val postList: ArrayList<InstaPost>,
        val inflater: LayoutInflater,
        val glide: RequestManager,
        val instaFeedFragment: InstaFeedFragment,
        val activity: InstaMainActivity
    ) : RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val ownerImg: ImageView
            val ownerUsername: TextView
            val postImg: ImageView
            val postContent: TextView
            val postLayer: ImageView
            val postHeart: ImageView

            init {
                ownerImg = itemView.findViewById(R.id.owner_img)
                ownerUsername = itemView.findViewById(R.id.owner_username)
                postImg = itemView.findViewById(R.id.post_img)
                postContent = itemView.findViewById(R.id.post_content)
                postLayer = itemView.findViewById(R.id.post_layer)
                postHeart = itemView.findViewById(R.id.post_heart)
                postImg.setOnClickListener {
                    instaFeedFragment.postLike(postList.get(adapterPosition).id)
                    Thread{
                        activity.runOnUiThread {
                            postLayer.visibility = VISIBLE
                            postHeart.visibility = VISIBLE
                        }
                        Thread.sleep(2000)
                        postLayer.visibility = INVISIBLE
                        postHeart.visibility = INVISIBLE

                    }.start()
                }
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val post = postList.get(position)

            post.owner_profile.image?.let {
                glide.load(it).centerCrop().circleCrop().into(holder.ownerImg)
            }
            post.image.let {
                glide.load(it).centerCrop().into(holder.postImg)
            }

            holder.ownerUsername.text = post.owner_profile.username
            holder.postContent.text = post.content
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(inflater.inflate(R.layout.post_item, parent, false))
        }

        override fun getItemCount(): Int {
            return postList.size
        }
    }
}