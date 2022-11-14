package com.example.duck.fastnotes.domain.repository

import com.example.duck.fastnotes.data.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {

    val isRegistered: Flow<Boolean>

    suspend fun getUserInfo(): Result<UserInfo>

    suspend fun writeUserInfo(userInfo: UserInfo): Boolean
}