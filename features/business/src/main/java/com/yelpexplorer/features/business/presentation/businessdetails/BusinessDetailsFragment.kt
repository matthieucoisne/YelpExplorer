package com.yelpexplorer.features.business.presentation.businessdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.yelpexplorer.features.business.R
import com.yelpexplorer.features.business.databinding.FragmentBusinessDetailsBinding
import com.yelpexplorer.libraries.core.data.local.Const
import com.yelpexplorer.libraries.core.injection.scope.ApplicationScope
import com.yelpexplorer.libraries.core.utils.StarsProvider
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.smoothie.viewmodel.closeOnViewModelCleared
import toothpick.smoothie.viewmodel.installViewModelBinding
import javax.inject.Inject

class BusinessDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBusinessDetailsBinding
    private lateinit var openingHoursTextViews: List<TextView>

    @Inject lateinit var viewModel: BusinessDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBusinessDetailsBinding.inflate(inflater, container, false)
        binding.apply {
            openingHoursTextViews = listOf(tvMondayHours, tvTuesdayHours, tvWednesdayHours, tvThursdayHours, tvFridayHours, tvSaturdayHours, tvSundayHours)
        }

        KTP.openScopes(ApplicationScope::class)
            .openSubScope(BusinessDetailsViewModel::class) { scope: Scope ->
                scope.installViewModelBinding<BusinessDetailsViewModel>(this)
                    .closeOnViewModelCleared(this)
            }
            .inject(this)

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
                load(uiModel.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.placeholder_business_details)
                    .into(ivBusiness)
                load(StarsProvider.getDrawableId(uiModel.rating))
                    .into(ivRating)
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

            layoutOpeningHours.isVisible = true
            openingHoursTextViews.forEachIndexed { i, tv ->
                tv.text = uiModel.openingHours[i] ?: getString(R.string.closed)
            }

            layoutReviews.isVisible = true
            rvReviews.adapter = BusinessDetailsReviewListAdapter(uiModel.reviews)
        }
    }

    private fun showError(message: String) {
        showToast(message, Toast.LENGTH_LONG)
    }

    private fun showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireActivity(), message, length).show()
    }
}
