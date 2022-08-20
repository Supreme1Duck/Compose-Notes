package com.example.duck.fastnotes.manager

import android.app.Application
import com.example.duck.fastnotes.BuildConfig
import com.example.duck.fastnotes.data.UserInfo
import com.example.duck.fastnotes.domain.repository.UserInfoRepository
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import kotlin.Result

class UserInfoManager : UserInfoRepository {
    override suspend fun getUserInfo(): Result<UserInfo> {
        val result = runCatching {
            val fileInputStream = FileInputStream("UserInfo.txt")
            val objectInputStream = ObjectInputStream(fileInputStream)

            val userInfo = objectInputStream.use {
                it.readObject() as UserInfo
            }

            userInfo
        }
        return result
    }

    override suspend fun writeUserInfo(userInfo: UserInfo): Boolean {
        val result = runCatching {
//            val fileOutputStream = FileOutputStream(BuildConfig.UserInfoFilePath)
           val fileOutputStream = FileOutputStream("UserInfo.txt")
            val objectOutputStream = ObjectOutputStream(fileOutputStream)

            objectOutputStream.use {
                it.writeObject(userInfo)
            }

            fileOutputStream.close()
        }
        return result.isSuccess
    }
}