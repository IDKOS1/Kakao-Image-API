package com.example.androidtask.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtask.presentation.ListItem
import com.example.kakaoimageapi.data.repository.SearchRepositoryImpl
import com.example.kakaoimageapi.ui.toImageListItem
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "SearchViewModel"

class SearchViewModel(
    private val repository: SearchRepositoryImpl
) : ViewModel() {
    private val _searchResult = MutableLiveData<List<ListItem>>()
    val searchResult: LiveData<List<ListItem>> = _searchResult

    fun fetchSearchResult(searchText: String, page: Int = 1) {
        viewModelScope.launch {
            runCatching {
                val imageResponse = repository.getImageList(searchText, page)

                val imageResult = imageResponse.documents?.toImageListItem() ?: listOf()

                _searchResult.value = imageResult
            }.onFailure {
                Log.e(TAG, "fetchSearchResult() 실패 : ${it.message}")
                handleException(it)
            }
        }
    }

    private fun handleException(e: Throwable) {
        when (e) {
            is HttpException -> {
                val errorJsonString = e.response()?.errorBody()?.string()
                Log.e(TAG, "HTTP error: $errorJsonString")
            }

            is IOException -> Log.e(TAG, "Network error: $e")
            else -> Log.e(TAG, "Unexpected error: $e")
        }
    }
}