package com.example.exchangeapp.presentation

import androidx.lifecycle.ViewModel
import com.example.exchangeapp.domain.repository.ExchangeRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(private val exchangeRepository: ExchangeRepository) : ViewModel() {
}