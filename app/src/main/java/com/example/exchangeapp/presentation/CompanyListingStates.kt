package com.example.exchangeapp.presentation

import com.example.exchangeapp.domain.model.CompanyListing

data class CompanyListingStates(
    val companyListing: List<CompanyListing> =  emptyList<CompanyListing>(),
    val loadingStatus : Boolean = false,
    val refreshing : Boolean = false,
    val searchQuery : String = ""
)