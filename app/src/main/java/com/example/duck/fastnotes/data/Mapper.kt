package com.example.duck.fastnotes.data

import com.example.duck.fastnotes.domain.data.UserInfo

object Mapper {

    fun UserInfoData.toUserInfo(): UserInfo {
        return UserInfo(
            login,
            name,
            imageUrl,
            registeredSince
        )
    }

}