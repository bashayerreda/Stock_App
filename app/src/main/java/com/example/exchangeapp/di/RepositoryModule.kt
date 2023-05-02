package com.example.exchangeapp.di

import com.example.exchangeapp.data.repository.ExchangeRepoImp
import com.example.exchangeapp.data.vcs.CompanyListParserImpl
import com.example.exchangeapp.data.vcs.CompanyListVcsParser
import com.example.exchangeapp.domain.model.CompanyListing
import com.example.exchangeapp.domain.repository.ExchangeRepository
import com.opencsv.CSVParser
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListParserImpl
    ): CompanyListVcsParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: ExchangeRepoImp
    ): ExchangeRepository
}