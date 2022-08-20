package com.example.duck.fastnotes.domain.repository

import com.example.duck.fastnotes.data.UserInfo

interface UserInfoRepository {

    suspend fun getUserInfo(): Result<UserInfo>

    suspend fun writeUserInfo(userInfo: UserInfo): Boolean
}