package com.example.duck.fastnotes.data

import java.io.Serializable

data class UserInfoData(
    val login: String,
    val name: String,
    val imageUrl: String,
    val registeredSince: String,
    val notRegistered: Boolean = false
): Serializable