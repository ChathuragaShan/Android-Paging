package com.chathurangashan.androidpaging.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chathurangashan.androidpaging.data.general.FileInformation
import com.chathurangashan.androidpaging.network.ApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1
const val PAGE_ITEM_LIMIT = 20

class FileListPagingSource (private val apiService: ApiService) : PagingSource<Int, FileInformation>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FileInformation> {

        try {

            val position = params.key ?: STARTING_PAGE_INDEX
            val postsResponse = apiService.getFiles(position, PAGE_ITEM_LIMIT)

            if (postsResponse.isSuccessful && postsResponse.body() != null) {

                val responseBody = postsResponse.body()
                val fileInformationList = mutableListOf<FileInformation>()

                responseBody!!.data?.forEach {

                    val fileInformation = FileInformation(it.name, it.fileType, it.modifiedDate)
                    fileInformationList.add(fileInformation)
                }

                val totalRetrievedItems = (position + 1) * PAGE_ITEM_LIMIT

                return LoadResult.Page(
                    data = fileInformationList,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (totalRetrievedItems < responseBody.total) responseBody.page.plus(1) else null
                )

            } else {

                return LoadResult.Error(
                    Throwable(
                        postsResponse.message(), Throwable("Response Error")
                    )
                )
            }

        } catch (throwable: Throwable) {

            val errorThrowable = when (throwable) {
                is HttpException -> {
                    Throwable(
                        throwable.message(),
                        Throwable("Connection Error")
                    )
                }
                is IOException ->
                    Throwable(
                        "Something Went Wrong Please Try again later.",
                        Throwable("Processing Error")
                    )
                else -> {
                    Throwable(
                        "Something Went Wrong Please Try again later.",
                        Throwable("Processing Error")
                    )
                }
            }

            return LoadResult.Error(errorThrowable)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FileInformation>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}