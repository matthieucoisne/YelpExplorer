package com.yelpexplorer.features.business.presentation.businesslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yelpexplorer.features.business.R
import com.yelpexplorer.features.business.databinding.ActivityBusinessListItemBinding
import com.yelpexplorer.libraries.core.utils.StarsProvider

class BusinessListAdapter(
    private val listener: (BusinessUiModel) -> Unit
) : RecyclerView.Adapter<BusinessListAdapter.ViewHolder>() {

    private val data = mutableListOf<BusinessUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ActivityBusinessListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val business = data[position]

        holder.binding.apply {
            Glide.with(context).apply {
                load(business.photoUrl).into(ivBusiness)
                load(StarsProvider.getDrawableId(business.rating)).into(ivRating)
            }

            tvName.text = context.getString(R.string.business_name, position + 1, business.name)
            tvReviewCount.text = context.resources.getQuantityString(
                R.plurals.business_reviews_count,
                business.reviewCount,
                business.reviewCount
            )
            tvPrice.text = if (business.price.isNotBlank()) {
                context.resources.getString(R.string.business_price, business.price)
            } else {
                ""
            }
            tvCategories.text = business.categories
            tvAddress.text = business.address

            root.setOnClickListener {
                listener(business)
            }
        }
    }

    override fun getItemCount() = data.size

    fun setData(data: List<BusinessUiModel>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(
        val binding: ActivityBusinessListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
