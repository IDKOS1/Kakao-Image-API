import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kakaoimageapi.R
import com.example.kakaoimageapi.databinding.ItemImageBinding


class ImageAdapter(private var images: List<Document>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        private val thumbnailsImageView = binding.ivThumbnail
        private val siteNameTextView = binding.tvSiteName
        private val dateTimeTextView = binding.tvDatetime

        fun bind(image: Document) {
            Glide.with(thumbnailsImageView.context)
                .load(image.thumbnailUrl)
                .into(thumbnailsImageView)
            siteNameTextView.text = image.displaySitename
            dateTimeTextView.text = image.datetime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    fun updateImages(newImages: List<Document>) {
        images = newImages
        notifyDataSetChanged()
    }
}
