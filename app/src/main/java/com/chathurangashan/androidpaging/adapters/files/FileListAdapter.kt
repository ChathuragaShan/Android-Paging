package com.chathurangashan.androidpaging.adapters.files

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.chathurangashan.androidpaging.data.general.FileInformation

class FileListAdapter(private val onClickFile:(FileInformation?) -> Unit)
    : PagingDataAdapter<FileInformation,FileListViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileListViewHolder {
        return FileListViewHolder.create(parent,onClickFile)
    }

    override fun onBindViewHolder(holder: FileListViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null){
            holder.bindRepoData(item)
        }
    }

    companion object {
        private val itemComparator = object : DiffUtil.ItemCallback<FileInformation>() {
            override fun areItemsTheSame(oldItem: FileInformation, newItem: FileInformation): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: FileInformation, newItem: FileInformation): Boolean {
                return oldItem == newItem
            }

        }
    }
}