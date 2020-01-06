package com.yelpexplorer.features.business.presentation.businessdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    private lateinit var openingHoursTextViews: List<TextView>

    private lateinit var viewModel: BusinessDetailsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBusinessDetailsBinding.inflate(inflater, container, false)
        binding.apply {
            openingHoursTextViews = listOf(tvMondayHours, tvTuesdayHours, tvWednesdayHours, tvThursdayHours, tvFridayHours, tvSaturdayHours, tvSundayHours)
        }

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

    private fun showBusinessDetails(uiModel: BusinessDetailsUiModel) {
        binding.apply {
            Glide.with(requireContext()).apply {
                load(uiModel.photoUrl).into(ivBusiness)
                load(StarsProvider.getDrawableId(uiModel.rating)).into(ivRating)
            }

            tvName.text = uiModel.name
            tvReviewCount.text = resources.getQuantityString(
                R.plurals.business_reviews_count,
                uiModel.reviewCount,
                uiModel.reviewCount
            )
            tvPrice.text = if (uiModel.price.isNotBlank()) {
                getString(R.string.business_price, uiModel.price)
            } else {
                ""
            }
            tvCategories.text = uiModel.categories
            tvAddress.text = uiModel.address

            layoutOpeningHours.visibility = View.VISIBLE
            openingHoursTextViews.forEachIndexed { i, tv ->
                tv.text = uiModel.openingHours[i] ?: getString(R.string.closed)
            }
        }
    }

    private fun showError(message: String) {
        showToast(message, Toast.LENGTH_LONG)
    }

    private fun showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireActivity(), message, length).show()
    }
}
