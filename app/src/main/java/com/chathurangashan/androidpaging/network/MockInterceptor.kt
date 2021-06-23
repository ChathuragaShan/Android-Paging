package com.chathurangashan.androidpaging.network

import com.chathurangashan.androidpaging.data.moshi.file_phasing.FileList
import com.chathurangashan.androidpaging.data.moshi.file_phasing.FileListItem
import com.chathurangashan.androidpaging.data.moshi.response.Data
import com.chathurangashan.androidpaging.data.moshi.response.FileListResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer


/**
 * Network interceptor class that act as a remote server which supply the response accordingly
 */
class MockInterceptor : Interceptor {

    private var attempts = 0
    private fun wantRandomError() = attempts++ % 3 == 0

    override fun intercept(chain: Interceptor.Chain): Response {

        Thread.sleep(5000) // fake delay

        val request = chain.request()
        val url = request.url.toString()

        if (url.contains("www.dummyurl.com/files")) {

            val page = request.url.queryParameter("page")?.toInt()
            val limit = request.url.queryParameter("limit")?.toInt()

            return if (!wantRandomError()) {
                processResponse(request, page, limit)
            } else {
                randomServerError(chain.request())
            }
        }

        return chain.proceed(request)
    }

    /**
     * responsible for getting the request body as a moshi object
     *
     * @param request: Retrofit request
     */
    private inline fun <reified T> getRequestBody(request: Request): T {

        val copy: Request = request.newBuilder().build()
        val buffer = Buffer()
        copy.body!!.writeTo(buffer)
        val requestBodyString = buffer.readUtf8()

        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(T::class.java)
        return jsonAdapter.fromJson(requestBodyString) as T
    }

    /**
     * Responsible for validating request data and returning response accordingly.
     */
    private fun processResponse(request: Request,page: Int?, limit: Int?): Response {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val jsonFileListResponseAdapter: JsonAdapter<FileListResponse> =
            moshi.adapter(FileListResponse::class.java)

        if (page != null && limit != null){

            val jsonString  = getJson("files1.json")

            val jsonFile1Adapter: JsonAdapter<FileList> = moshi.adapter(FileList::class.java)

            val fileList = jsonFile1Adapter.fromJson(jsonString)
            val totalDataCount = fileList!!.files.size

            if(totalDataCount > (limit * (page - 1))){

                val dataPartition  =
                    if(page * limit < totalDataCount){
                        fileList.files.subList((page - 1) * limit, page * limit)
                    }else{
                        fileList.files.subList((page - 1) * limit, totalDataCount)
                    }

                val dataList = mutableListOf<Data>()
                dataPartition.forEach {
                    val data = Data(it.fileType,it.modifiedDate,it.name)
                    dataList.add(data)
                }

                //val allSentCount = (page - 1) * limit + dataPartition.count()
                val fileListResponse =
                    FileListResponse(dataList, null, true,page,totalDataCount)

                return Response.Builder()
                    .code(200)
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .message("Input Field Error")
                    .body(jsonFileListResponseAdapter.toJson(fileListResponse)
                        .toResponseBody("application/json".toMediaType()))
                    .build()

            }else{

                val fileListResponse = FileListResponse(
                    null,
                    "You have reached the end of list",
                    false)

                return Response.Builder()
                    .code(200)
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .message("Input Field Error")
                    .body(jsonFileListResponseAdapter.toJson(fileListResponse)
                        .toResponseBody("application/json".toMediaType()))
                    .build()
            }

        }else{

            val fileListResponse = if(page != null){
                FileListResponse(null, "Page number cannot be empty", false)
            }else{
                FileListResponse(null, "Limit cannot be empty", true)
            }

            return Response.Builder()
                .code(200)
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("Input Field Error")
                .body(jsonFileListResponseAdapter.toJson(fileListResponse)
                    .toResponseBody("application/json".toMediaType()))
                .build()

        }

    }

    private fun randomServerError(request: Request): Response {

        return Response.Builder()
                .code(500)
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("Internal server error")
                .body("{}".toResponseBody("application/json".toMediaType()))
                .build()
    }

    /**
     * Function responsible for reading Json content from the file inside resource folder
     *
     * @param path: file path
     */
    private fun getJson(path: String): String {
        val resourceAsStream = this.javaClass.classLoader!!.getResourceAsStream(path)
        return String(resourceAsStream.readBytes())
    }

}