package com.example.duck.fastnotes.usecase

import com.example.duck.fastnotes.domain.data.UserInfo
import com.example.duck.fastnotes.domain.repository.UserInfoRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val repository: UserInfoRepository) {
    suspend operator fun invoke(): UserInfo {
        return repository.getUserData()
    }
}