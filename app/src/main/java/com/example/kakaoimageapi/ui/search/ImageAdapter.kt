import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kakaoimageapi.R
import com.example.kakaoimageapi.databinding.ItemImageBinding
import com.example.kakaoimageapi.data.retrofit.Document
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID


class ImageAdapter(
    private var images: List<Document>,
    private val context: Context,
    private val clickListener: (Document) -> Unit
) : ListAdapter<Document, ImageViewHolder>(object : DiffUtil.ItemCallback<Document>() {
    override fun areItemsTheSame(oldItem: Document, newItem: Document): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(oldItem: Document, newItem: Document): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val document = getItem(position)
        holder.bind(document) { selectedItem ->
            clickListener(selectedItem)
        }
    }

    fun updateImages(newImages: List<Document>) {
        Log.d("test", "Updating images list: ${newImages.size} items")
        submitList(newImages)
    }
}

class ImageViewHolder(
    private val binding: ItemImageBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private val thumbnailsImageView = binding.ivThumbnail
    private val siteNameTextView = binding.tvSiteName
    private val dateTimeTextView = binding.tvDatetime
    private val bookmarkImageView = binding.ivBookmark

    private var isBookmarked: Boolean = false

    fun bind(image: Document, clickListener: (Document) -> Unit) {
        Glide.with(thumbnailsImageView.context)
            .load(image.thumbnailUrl)
            .into(thumbnailsImageView)
        siteNameTextView.text = image.displaySitename

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = inputFormat.parse(image.datetime)
        val dateTime = date?.let { outputFormat.format(it) }

        dateTimeTextView.text = dateTime

        // 북마크 아이콘 업데이트
        isBookmarked = image.isBookmarked
        updateBookmarkIcon(image.isBookmarked)

        itemView.setOnClickListener {
            isBookmarked = !isBookmarked
            val updatedImage = image.copy(
                uuid = image.uuid ?: UUID.randomUUID().toString(), // null 검사 및 기본값 설정
                isBookmarked = isBookmarked // 상태 토글
            )
            Log.i("test", "updatedImage bookmark: ${updatedImage.isBookmarked}")
            clickListener(updatedImage)
            updateBookmarkIcon(isBookmarked)
        }
    }

    private fun updateBookmarkIcon(isBookmarked: Boolean) {
        val bookmarkIconRes = if (isBookmarked) {
            R.drawable.ic_bookmark_full
        } else {
            R.drawable.ic_bookmark_empty
        }
        bookmarkImageView.setImageResource(bookmarkIconRes)
    }
}

