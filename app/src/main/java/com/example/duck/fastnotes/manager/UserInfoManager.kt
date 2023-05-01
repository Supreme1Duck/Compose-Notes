package com.example.duck.fastnotes.manager

import com.example.duck.fastnotes.data.Mapper.toUserInfo
import com.example.duck.fastnotes.domain.data.UserInfo
import com.example.duck.fastnotes.domain.repository.UserInfoRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class UserInfoManager(
    private val preferenceManager: PreferenceManager,
) : UserInfoRepository {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override suspend fun isRegistered(): Boolean {
        return firebaseAuth.currentUser != null || getUserData().notRegistered
    }

    override suspend fun setContinuedWithoutRegistration() {
        preferenceManager.setContinuedWithoutRegistration()
    }

    override suspend fun registerUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

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