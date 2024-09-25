package com.example.instagram_clone

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class InstaMainActivity : AppCompatActivity() {

    val instaPostFragment = InstaPostFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_insta_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tabs = findViewById<TabLayout>(R.id.main_tab)
        tabs.addTab(tabs.newTab().setIcon(R.drawable.btn_outsta_home))
        tabs.addTab(tabs.newTab().setIcon(R.drawable.btn_outsta_post))
        tabs.addTab(tabs.newTab().setIcon(R.drawable.btn_outsta_my))

        val pager = findViewById<ViewPager2>(R.id.main_pager)
        pager.adapter = InstaMainPagerAdapter(this@InstaMainActivity, 3, instaPostFragment)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager.setCurrentItem(tab!!.position)
                if(tab!!.position == 1){
                    instaPostFragment.makePost()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })


    }
}

class InstaMainPagerAdapter(
    fragmentActivity: FragmentActivity,
    val tabCount: Int,
    val instaPostFragment: InstaPostFragment
):FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return InstaFeedFragment()
            1 -> return instaPostFragment
            else -> return InstaProfileFragment()
        }
    }
}