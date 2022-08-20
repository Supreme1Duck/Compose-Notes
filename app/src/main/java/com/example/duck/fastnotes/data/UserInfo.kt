package com.example.duck.fastnotes.data

import java.io.Serializable

data class UserInfo(
    val userName: String,
    val isPremium: Boolean
): Serializable