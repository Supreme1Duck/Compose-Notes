package com.example.duck.fastnotes.domain.repository

import com.example.duck.fastnotes.domain.data.UserInfo

interface UserInfoRepository {

    suspend fun getUserData(): UserInfo

    suspend fun addLogin(login: String)

    suspend fun clearUserInfo()
}