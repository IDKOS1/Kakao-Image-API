import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kakaoimageapi.BuildConfig
import com.example.kakaoimageapi.data.retrofit.Document
import com.example.kakaoimageapi.databinding.FragmentSearchBinding
import com.example.kakaoimageapi.data.retrofit.KakaoImageResponse
import com.example.kakaoimageapi.data.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ImageAdapter
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())

        // RecyclerView 설정
        binding.let {
            it.rvSearchList.layoutManager = GridLayoutManager(requireContext(), 2)
            val clickListner: (Document) -> Unit = {document ->
                Log.d("test","Search Click Listener")
                // 북마크 토글 및 데이터 삭제 처리
                val updatedImage = document.copy()
                if (updatedImage.isBookmarked) {
                    sharedPreferencesHelper.saveSelectedItem("selected_image", updatedImage)
                } else {
                    sharedPreferencesHelper.removeSelectedItemByUuid("selected_image", updatedImage.uuid)
                }
                Log.i("test", "${sharedPreferencesHelper.getSelectedItems("selected_image")}")
            }


            adapter = ImageAdapter(emptyList(), requireContext(), clickListner)
            it.rvSearchList.adapter = adapter

            // 검색 버튼 클릭 이벤트
            it.searchButton.setOnClickListener {
                val query = binding.searchBar.text.toString()
                if (query.isNotEmpty()) {
                    searchImages(query)
                    val keyboard = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    keyboard.hideSoftInputFromWindow(binding.searchBar.windowToken, 0)
                } else {
                    Toast.makeText(requireContext(), "검색어를 입력하세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun searchImages(query: String) {
        val apiKey = "KakaoAK ${BuildConfig.KAKAO_API_KEY}"
        RetrofitClient.instance.searchImages(apiKey, query).enqueue(object : Callback<KakaoImageResponse> {
            override fun onResponse(call: Call<KakaoImageResponse>, response: Response<KakaoImageResponse>) {
                if (response.isSuccessful) {
                    Log.e("SearchImages", "Search Successful")
                    response.body()?.let {
                        adapter.updateImages(it.documents)
                        Log.i("document", "${it.documents}")
                    }
                } else {
                    val statusCode = response.code()
                    val message = response.message()
                    Log.e("SearchImages", "Error $statusCode: $message")
                    Toast.makeText(requireContext(), "검색 결과를 가져오지 못했습니다. 오류 코드: $statusCode", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<KakaoImageResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "오류가 발생했습니다: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }}
