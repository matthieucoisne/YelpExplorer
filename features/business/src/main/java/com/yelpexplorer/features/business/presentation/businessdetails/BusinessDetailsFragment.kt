package com.yelpexplorer.features.business.presentation.businessdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.yelpexplorer.features.business.R
import com.yelpexplorer.features.business.databinding.FragmentBusinessDetailsBinding
import com.yelpexplorer.libraries.core.data.local.Const
import com.yelpexplorer.libraries.core.injection.ViewModelFactory
import com.yelpexplorer.libraries.core.utils.StarsProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BusinessDetailsFragment : DaggerFragment() {

    private lateinit var binding: FragmentBusinessDetailsBinding

    private lateinit var viewModel: BusinessDetailsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBusinessDetailsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, viewModelFactory).get(BusinessDetailsViewModel::class.java)

        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            render(it)
        })

        val businessId = requireArguments().getString(Const.KEY_BUSINESS_ID)
        viewModel.setBusinessId(businessId)

        return binding.root
    }

    private fun render(viewState: BusinessDetailsViewModel.ViewState) {
        when (viewState) {
            is BusinessDetailsViewModel.ViewState.ShowLoading -> {
                viewState.businessDetails?.let { showBusinessDetails(it) }
            }
            is BusinessDetailsViewModel.ViewState.ShowBusinessDetails -> showBusinessDetails(viewState.businessDetails)
            is BusinessDetailsViewModel.ViewState.ShowError -> showError(getString(viewState.errorStringId))
        }
    }

    private fun showBusinessDetails(businessDetailsUiModel: BusinessDetailsUiModel) {
        Glide.with(requireContext()).apply {
            load(businessDetailsUiModel.photoUrl).into(binding.ivBusiness)
            load(StarsProvider.getDrawableId(businessDetailsUiModel.rating)).into(binding.ivRating)
        }

        binding.tvName.text = businessDetailsUiModel.name
        binding.tvReviewCount.text = resources.getQuantityString(
            R.plurals.business_reviews_count,
            businessDetailsUiModel.reviewCount,
            businessDetailsUiModel.reviewCount
        )
        binding.tvPrice.text = if (businessDetailsUiModel.price.isNotBlank()) {
            resources.getString(R.string.business_price, businessDetailsUiModel.price)
        } else {
            ""
        }
        binding.tvCategories.text = businessDetailsUiModel.categories
        binding.tvAddress.text = businessDetailsUiModel.address
    }

    private fun showError(message: String) {
        showToast(message, Toast.LENGTH_LONG)
    }

    private fun showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireActivity(), message, length).show()
    }
}
