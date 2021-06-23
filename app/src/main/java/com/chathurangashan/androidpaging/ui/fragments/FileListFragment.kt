package com.chathurangashan.androidpaging.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.chathurangashan.androidpaging.R
import com.chathurangashan.androidpaging.adapters.files.FileListAdapter
import com.chathurangashan.androidpaging.adapters.files.FileLoadStateAdapter
import com.chathurangashan.androidpaging.data.general.FileInformation
import com.chathurangashan.androidpaging.databinding.FragmentFileListBinding
import com.chathurangashan.androidpaging.di.injector
import com.chathurangashan.androidpaging.di.subcomponents.FragmentSubComponent
import com.chathurangashan.androidpaging.di.viewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FileListFragment : BaseFragment(R.layout.fragment_file_list) {

    override val viewModel by viewModel{ fragmentSubComponent.fileListViewModel }

    private lateinit var fragmentSubComponent: FragmentSubComponent
    private lateinit var viewBinding: FragmentFileListBinding
    private lateinit var fileListAdapter: FileListAdapter

    @Inject
    override lateinit var navigationController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding = FragmentFileListBinding.bind(view)

        initialization()
        onClickRetry()
    }

    private fun initialization() {

        fragmentSubComponent = injector.fragmentComponent().create(requireView())
        fragmentSubComponent.inject(this)

        fileListAdapter = FileListAdapter { onClickProfile(it) }
        viewBinding.filesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.filesRecyclerView.adapter = fileListAdapter.withLoadStateFooter(
            FileLoadStateAdapter { fileListAdapter.retry() }
        )

        fileListAdapter.addLoadStateListener { loadState ->
            val isListEmpty = loadState.refresh is LoadState.NotLoading && fileListAdapter.itemCount == 0

            if(isListEmpty){
                viewBinding.retryLayoutContainer.visibility = View.VISIBLE
                viewBinding.errorMessage.visibility = View.VISIBLE
                viewBinding.retryButton.visibility = View.GONE
                viewBinding.errorMessage.text = getString(R.string.no_results)
            }else{
                viewBinding.retryLayoutContainer.visibility = View.GONE
                viewBinding.errorMessage.visibility = View.VISIBLE
                viewBinding.retryButton.visibility = View.VISIBLE
            }

            viewBinding.filesRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            viewBinding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            viewBinding.retryLayoutContainer.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                viewBinding.errorMessage.text = "\uD83D\uDE28 Wooops ${it.error}"
            }
        }

        lifecycleScope.launch {
            viewModel.getFileDataStream().collectLatest{
                fileListAdapter.submitData(it)
            }
        }

        super.initialization(null,null)
    }

    private fun onClickRetry(){

        viewBinding.retryButton.setOnClickListener {
            fileListAdapter.retry()
        }
    }

    /**
     * Method Responsible for redirecting to profile view on user click on list item profile area.
     *
     * @param item: Selected post object
     */
    private fun onClickProfile(fileInformation: FileInformation?){}
}