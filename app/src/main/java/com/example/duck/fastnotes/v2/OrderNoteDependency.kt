package com.example.duck.fastnotes.v2

import by.mtbank.data.ContractsRepository
import by.mtbank.ui.logiccomponent.CardConstructorHelper
import by.mtbank.v2.core.data.AddressLocationProvider
import by.mtbank.v2.core.data.BankOfficeProvider
import by.mtbank.v2.core.data.LocationSource
import by.mtbank.v2.core.data.network.service.CardConstructorApiService
import by.mtbank.v2.core.data.network.service.UserApiService
import by.mtbank.v2.core.platform.Resources

interface OrderNoteDependency {
    fun provideResources(): Resources
    fun provideContractRepository(): ContractsRepository
    fun provideLocationSource(): LocationSource
    fun provideUserApiService(): UserApiService
}
