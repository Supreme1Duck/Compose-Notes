package com.example.duck.fastnotes.features.dashboard.home

import com.example.duck.fastnotes.utils.ui.Reducer

class HomeScreenReducer(initial: HomeState) : Reducer<HomeState, HomeScreenEvents>(initial) {

    override fun reduce(oldState: HomeState, event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.ShowUserData -> {
                setState(oldState.copy(userName = event.name))
            }
            is HomeScreenEvents.ShowList -> {
                setState(oldState.copy(taskList = event.list, isLoading = false))
            }
            HomeScreenEvents.DismissDialog -> {
                setState(oldState.copy(isShowDialog = false))
            }
            HomeScreenEvents.ShowDialog -> {
                setState(oldState.copy(isShowDialog = true))
            }
            HomeScreenEvents.ShowLoading -> {
                setState(oldState.copy(isLoading = true))
            }
            else -> {

            }
        }
    }
}