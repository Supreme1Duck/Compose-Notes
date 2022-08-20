package com.example.duck.fastnotes.usecase

import com.example.duck.fastnotes.data.UserInfo
import com.example.duck.fastnotes.domain.repository.UserInfoRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val repository: UserInfoRepository) {
    suspend operator fun invoke(): Result<UserInfo> {
        return repository.getUserInfo()
    }
}