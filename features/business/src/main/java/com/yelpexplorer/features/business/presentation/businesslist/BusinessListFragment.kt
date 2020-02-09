package com.yelpexplorer.features.business.presentation.businesslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.yelpexplorer.features.business.R
import com.yelpexplorer.features.business.databinding.FragmentBusinessListBinding
import com.yelpexplorer.libraries.core.data.local.Const
import com.yelpexplorer.libraries.core.injection.scope.ApplicationScope
import com.yelpexplorer.libraries.core.utils.EventObserver
import com.yelpexplorer.libraries.core.utils.Router
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.smoothie.viewmodel.closeOnViewModelCleared
import toothpick.smoothie.viewmodel.installViewModelBinding
import javax.inject.Inject

class BusinessListFragment : Fragment() {

    private var _binding: FragmentBusinessListBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModel: BusinessListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBusinessListBinding.inflate(inflater)

        setHasOptionsMenu(true)

        KTP.openScopes(ApplicationScope::class)
            .openSubScope(BusinessListViewModel::class) { scope: Scope ->
                scope.installViewModelBinding<BusinessListViewModel>(this)
                    .closeOnViewModelCleared(this)
            }
            .inject(this)

        viewModel.viewAction.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is BusinessListViewModel.ViewAction.NavigateToDetails -> navigateToDetails(it.businessId)
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner) {
            render(it)
        }

        binding.rvBusinessList.adapter = BusinessListAdapter {
            viewModel.onBusinessClicked(it.id)
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                navigateToSettings()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun render(viewState: BusinessListViewModel.ViewState) {
        when (viewState) {
            is BusinessListViewModel.ViewState.ShowLoading -> {
                viewState.businessList?.let { showBusinessList(it) }
            }
            is BusinessListViewModel.ViewState.ShowBusinessList -> showBusinessList(viewState.businessList)
            is BusinessListViewModel.ViewState.ShowError -> showError(getString(viewState.errorStringId))
        }
    }

    private fun showBusinessList(uiModel: BusinessListUiModel) {
        (binding.rvBusinessList.adapter as BusinessListAdapter).setData(uiModel.businessList)
    }

    private fun navigateToDetails(businessId: String) {
        findNavController().navigate(
            R.id.action_businessListFragment_to_businessDetailsFragment,
            bundleOf(Const.KEY_BUSINESS_ID to businessId)
        )
    }

    private fun navigateToSettings() {
        startActivity(Router.getSettingsIntent(requireContext()))
    }

    private fun showError(message: String) {
        showToast(message, Toast.LENGTH_LONG)
    }

    private fun showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), message, length).show()
    }
}
