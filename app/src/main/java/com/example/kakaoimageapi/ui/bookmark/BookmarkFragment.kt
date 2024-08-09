package com.example.kakaoimageapi.ui.bookmark

import ImageAdapter
import SharedPreferencesHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kakaoimageapi.R
import com.example.kakaoimageapi.data.retrofit.Document
import com.example.kakaoimageapi.databinding.FragmentBookmarkBinding


class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var adapter: ImageAdapter
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())

        val bookmarkedItems = sharedPreferencesHelper.getSelectedItems("selected_image")

        // RecyclerView 설정
        val clickListener: (Document) -> Unit = { document ->
            Log.d("test","클릭 리스너")
            // 북마크 토글 및 데이터 삭제 처리
            val updatedImage = document.copy()
            if (updatedImage.isBookmarked) {
                sharedPreferencesHelper.saveSelectedItem("selected_image", updatedImage)
            } else {
                sharedPreferencesHelper.removeSelectedItemByUuid("selected_image", updatedImage.uuid)
            }
            // UI 업데이트
            loadBookmarkedItems()
        }

        binding.rvBookmarkList.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = ImageAdapter(emptyList(), requireContext(), clickListener).apply {
            submitList(bookmarkedItems)
        }
        binding.rvBookmarkList.adapter = adapter

        // 북마크된 아이템들 로드
        loadBookmarkedItems()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadBookmarkedItems()
    }

    private fun loadBookmarkedItems() {
        // SharedPreferences에서 저장된 아이템 리스트 불러오기
        val bookmarkedItems = sharedPreferencesHelper.getSelectedItems("selected_image")
        // 어댑터에 아이템 리스트 업데이트
        adapter.submitList(bookmarkedItems)
    }
}
