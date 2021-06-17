package com.chathurangashan.androidpaging.adapters.file_paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chathurangashan.androidpaging.R
import com.chathurangashan.androidpaging.data.general.FileInformation
import com.chathurangashan.androidpaging.data.moshi.file_phasing.FileListItem
import com.chathurangashan.androidpaging.databinding.FileListItemLayoutBinding
import com.squareup.picasso.Picasso

class FileListViewHolder(private val viewBinding: FileListItemLayoutBinding,
    private val onClickFile:(FileInformation?) -> Unit) :
    RecyclerView.ViewHolder(viewBinding.root) {

    private var fileListItem: FileInformation? = null

    fun bindRepoData(fileListItem: FileInformation) {

        this.fileListItem = fileListItem

        Picasso.get()
            .load(getIconForFileType(fileListItem.fileType))
            .resizeDimen(R.dimen.file_list_profile_image_size,R.dimen.file_list_profile_image_size)
            .into(viewBinding.fileTypeImageView)

        viewBinding.fileNameTextView.text = fileListItem.name
        viewBinding.modifiedDateTextView.text = fileListItem.modifiedDate

    }

    private fun getIconForFileType(filetype: String): Int{

        return when(filetype){
            "DOC" -> R.drawable.doc_icon
            "PDF" -> R.drawable.pdf_icon
            "PNG" -> R.drawable.png_icon
            "CVS" -> R.drawable.csv_icon
            "ZIP" -> R.drawable.zip_icon
            "JPG" -> R.drawable.jpg_icon
            else -> R.drawable.txt_icon
        }
    }

    companion object {
        fun create(parent: ViewGroup, onClickFile:(fileInformation: FileInformation?) -> Unit): FileListViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.file_list_item_layout, parent, false)
            val binding = FileListItemLayoutBinding.bind(view)
            return FileListViewHolder(binding,onClickFile)
        }
    }

}