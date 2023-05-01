package com.example.duck.fastnotes.domain.repository

import com.example.duck.fastnotes.domain.data.UserInfo

interface UserInfoRepository {

    suspend fun isRegistered(): Boolean

    suspend fun setContinuedWithoutRegistration()

    suspend fun registerUser(email: String, password: String)

    suspend fun loginUser(email: String, password: String)

    suspend fun getUserData(): UserInfo

    suspend fun addLogin(login: String)

    suspend fun clearUserInfo()
}