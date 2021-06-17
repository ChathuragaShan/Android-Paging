package com.chathurangashan.androidpaging.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chathurangashan.androidpaging.data.general.FileInformation
import com.chathurangashan.androidpaging.repositories.FileListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FileListViewModel @Inject constructor(private val repository: FileListRepository)
    : BaseViewModel(repository) {

    fun getFileDataStream(): Flow<PagingData<FileInformation>> {
        return repository.getFileList().cachedIn(viewModelScope)
    }
}