package com.chathurangashan.androidpaging.adapters.files

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class FileLoadStateAdapter (private val retry: () -> Unit)
    : LoadStateAdapter<FileLoadStateViewHolder>(){

    override fun onBindViewHolder(holder: FileLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FileLoadStateViewHolder {
        return FileLoadStateViewHolder.create(parent, retry)
    }

}