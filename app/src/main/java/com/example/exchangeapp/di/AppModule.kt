package com.example.exchangeapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.exchangeapp.data.local.ExchangeDao
import com.example.exchangeapp.data.local.ExchangeDataBase
import com.example.exchangeapp.data.remote.ExchangApi
import com.example.exchangeapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): ExchangApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

   @Provides
   @Singleton
   fun provideStockDatabase(app: Application): ExchangeDataBase {
       return Room.databaseBuilder(
           app,
           ExchangeDataBase::class.java,
           Constants.databasename
       ).build()
   }
}
