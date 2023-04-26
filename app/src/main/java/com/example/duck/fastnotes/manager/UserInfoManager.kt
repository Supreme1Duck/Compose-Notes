package com.example.duck.fastnotes.manager

import com.example.duck.fastnotes.data.Mapper.toUserInfo
import com.example.duck.fastnotes.domain.data.UserInfo
import com.example.duck.fastnotes.domain.repository.UserInfoRepository

class UserInfoManager(
    private val preferenceManager: PreferenceManager
) : UserInfoRepository {
    override suspend fun getUserData(): UserInfo {
        return preferenceManager.getUserData().toUserInfo()
    }

    override suspend fun addLogin(login: String) {
        preferenceManager.addLogin(login)
    }

    override suspend fun clearUserInfo() {
        preferenceManager.clearData()
    }
}