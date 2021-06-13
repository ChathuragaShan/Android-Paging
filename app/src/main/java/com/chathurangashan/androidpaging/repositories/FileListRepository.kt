package com.chathurangashan.androidpaging.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chathurangashan.androidpaging.data.FileListPagingSource
import com.chathurangashan.androidpaging.data.general.FileInformation
import com.chathurangashan.androidpaging.network.ApiService
import kotlinx.coroutines.flow.Flow
import com.chathurangashan.androidpaging.data.PAGE_ITEM_LIMIT
import javax.inject.Inject

class FileListRepository @Inject constructor(private val  apiService: ApiService): BaseRepository() {

    fun getFileList(): Flow<PagingData<FileInformation>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_ITEM_LIMIT, enablePlaceholders = false),
            pagingSourceFactory = { FileListPagingSource(apiService) }
        ).flow
    }
}