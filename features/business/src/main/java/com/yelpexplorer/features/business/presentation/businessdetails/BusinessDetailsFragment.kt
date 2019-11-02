package com.yelpexplorer.features.business.presentation.businessdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.yelpexplorer.features.business.databinding.FragmentBusinessDetailsBinding
import com.yelpexplorer.libraries.core.data.local.Const
import com.yelpexplorer.libraries.core.injection.ViewModelFactory
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
                showToast("Loading")
                viewState.businessDetails?.let { showBusinessDetails(it) }
            }
            is BusinessDetailsViewModel.ViewState.ShowBusinessDetails -> showBusinessDetails(viewState.businessDetails)
            is BusinessDetailsViewModel.ViewState.ShowError -> showError(getString(viewState.errorStringId))
        }
    }

    private fun showBusinessDetails(businessDetails: BusinessDetailsUiModel) {
        binding.tvName.text = businessDetails.name
        Glide.with(requireContext()).load(businessDetails.urlImage).into(binding.ivBusiness)
    }

    private fun showError(message: String) {
        showToast(message, Toast.LENGTH_LONG)
    }

    private fun showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireActivity(), message, length).show()
    }
}
