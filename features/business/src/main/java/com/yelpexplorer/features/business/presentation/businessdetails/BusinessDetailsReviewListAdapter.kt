package com.yelpexplorer.features.business.presentation.businessdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.yelpexplorer.features.business.R
import com.yelpexplorer.features.business.databinding.FragmentBusinessDetailsReviewListItemBinding
import com.yelpexplorer.libraries.core.utils.StarsProvider

class BusinessDetailsReviewListAdapter(
    val data: List<ReviewUiModel>
) : RecyclerView.Adapter<BusinessDetailsReviewListAdapter.ViewHolder>() {

    private val crossFadeTransition = DrawableTransitionOptions.withCrossFade()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentBusinessDetailsReviewListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = data[position]

        holder.binding.apply {
            Glide.with(holder.itemView.context).apply {
                load(review.userImageUrl)
                    .transition(crossFadeTransition)
                    .error(R.drawable.placeholder_user)
                    .into(ivUser)
                load(StarsProvider.getDrawableId(review.rating))
                    .into(ivRating)
            }

            tvUserName.text = review.userName
            tvDate.text = review.timeCreated
            tvReview.text = review.text
        }
    }

    override fun getItemCount() = data.size

    class ViewHolder(
        val binding: FragmentBusinessDetailsReviewListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
