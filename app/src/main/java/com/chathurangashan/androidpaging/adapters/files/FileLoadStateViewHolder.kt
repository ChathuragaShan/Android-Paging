package com.chathurangashan.androidpaging.adapters.files

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.chathurangashan.androidpaging.R
import com.chathurangashan.androidpaging.databinding.FileListLodingAndRetryLayoutBinding

class FileLoadStateViewHolder (private val viewBinding: FileListLodingAndRetryLayoutBinding, retry: () -> Unit)
    : RecyclerView.ViewHolder(viewBinding.root){

    init {

        viewBinding.retryButton.setOnClickListener {
            retry.invoke()
        }
    }

    fun bind(loadState: LoadState) {

        if (loadState is LoadState.Error) {
            viewBinding.errorMessage.text = loadState.error.localizedMessage
        }

        viewBinding.loadingAnimationContainer.isVisible = loadState is LoadState.Loading
        viewBinding.retryLayoutContainer.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): FileLoadStateViewHolder {

            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.file_list_loding_and_retry_layout, parent, false)
            val binding = FileListLodingAndRetryLayoutBinding.bind(view)
            return FileLoadStateViewHolder(binding,retry)

        }
    }
}