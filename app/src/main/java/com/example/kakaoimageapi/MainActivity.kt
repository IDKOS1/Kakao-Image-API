package com.example.kakaoimageapi

import SearchFragment
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.kakaoimageapi.ui.adapter.MainViewpagerAdapter
import com.example.kakaoimageapi.databinding.ActivityMainBinding
import com.example.kakaoimageapi.ui.bookmark.BookmarkFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val list = listOf(
            SearchFragment(),
            BookmarkFragment()
        )
        val adapter = MainViewpagerAdapter(list, this)

        binding.run {
            mainViewpager.adapter = adapter

            mainViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    // ViewPager의 페이지가 변경될 때 BottomNavigationView의 선택 상태를 변경
                    binding.mainBottomnavigation.menu.getItem(position).isChecked = true
                }
            })

            mainBottomnavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.search -> {
                        mainViewpager.currentItem = 0
                        true
                    }
                    R.id.folder -> {
                        mainViewpager.currentItem = 1
                        true
                    }
                    else -> false
                }
            }
        }
    }
}