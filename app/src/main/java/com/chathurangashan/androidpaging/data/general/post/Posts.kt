package com.chathurangashan.androidpaging.data.general.post

import com.chathurangashan.androidpaging.data.general.post.Item

data class Posts(
        val total: Int,
        val currentPage: Int,
        val data: List<Item>
)