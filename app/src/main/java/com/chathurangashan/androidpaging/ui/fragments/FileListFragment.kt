package com.chathurangashan.androidpaging.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
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

    @Inject
    override lateinit var navigationController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding = FragmentFileListBinding.bind(view)

        initialization()
    }

    private fun initialization() {

        fragmentSubComponent = injector.fragmentComponent().create(requireView())
        fragmentSubComponent.inject(this)

        val fileListAdapter = FileListAdapter { onClickProfile(it) }
        viewBinding.filesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.filesRecyclerView.adapter = fileListAdapter.withLoadStateFooter(
            FileLoadStateAdapter { fileListAdapter.retry() }
        )

        lifecycleScope.launch {
            viewModel.getFileDataStream().collectLatest{
                fileListAdapter.submitData(it)
            }
        }

        super.initialization(null,null)
    }

    /**
     * Method Responsible for redirecting to profile view on user click on list item profile area.
     *
     * @param item: Selected post object
     */
    private fun onClickProfile(fileInformation: FileInformation?){}
}