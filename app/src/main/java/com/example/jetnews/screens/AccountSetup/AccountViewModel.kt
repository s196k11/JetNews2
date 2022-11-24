package com.example.jetnews.screens.AccountSetup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetnews.Repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AccountViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel(){

    var country = mutableStateOf("")


    fun selectedCountry(countryCode:String){
        country.value = countryCode
    }
}