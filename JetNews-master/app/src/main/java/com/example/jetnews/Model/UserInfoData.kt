package com.example.jetnews.Model



data class UserInfoData(
    val userName: String? = null,
    val email:String? = null,
    val aboutMe:String?= null,
    val country:String? = null,
    val language:String?= null,
    val article: Map<String, Article>? = null
)
