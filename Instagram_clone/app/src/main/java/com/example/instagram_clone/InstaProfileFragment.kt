package com.example.instagram_clone

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InstaProfileFragment : Fragment() {

    lateinit var userProfileImageView: ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.insta_profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userProfileImageView = view.findViewById(R.id.profile_img)

        view.findViewById<TextView>(R.id.change_img).setOnClickListener {
            startActivity(
                Intent(
                    activity as InstaMainActivity,
                    InstaChangeProfileActivity::class.java
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService = retrofit.create(RetrofitService::class.java)
        val header = HashMap<String, String>()
        val sp =
            (activity as InstaMainActivity).getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val token = sp.getString("token", "")
        header.put("Authorization", "token " + token!!)

        val glide = Glide.with(activity as InstaMainActivity)

        retrofitService.getUserInfo(header).enqueue(object : Callback<UserInfo> {
            override fun onResponse(p0: Call<UserInfo>, p1: Response<UserInfo>) {
                if (p1.isSuccessful) {
                    val userInfo: UserInfo = p1.body()!!
                    userInfo.profile.image?.let {
                        glide.load(it).into(userProfileImageView)
                    }
                }
            }

            override fun onFailure(p0: Call<UserInfo>, p1: Throwable) {
            }
        })
    }
}