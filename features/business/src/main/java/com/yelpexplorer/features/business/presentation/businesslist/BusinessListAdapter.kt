package com.yelpexplorer.features.business.presentation.businesslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yelpexplorer.features.business.databinding.ActivityBusinessListItemBinding

class BusinessListAdapter(
    private val listener: (BusinessUiModel) -> Unit
) : RecyclerView.Adapter<BusinessListAdapter.ViewHolder>() {

    private var data = BusinessListUiModel(emptyList())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ActivityBusinessListItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val business = data.businessList[position]
        holder.binding.tvBusinessName.text = business.name
        Glide.with(holder.itemView.context).load(business.urlImage).into(holder.binding.ivBusiness)
        holder.binding.root.setOnClickListener {
            listener(business)
        }
    }

    override fun getItemCount() = data.businessList.size

    fun setData(data: BusinessListUiModel) {
        this.data = data
        notifyDataSetChanged()
    }

    class ViewHolder(
        val binding: ActivityBusinessListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
