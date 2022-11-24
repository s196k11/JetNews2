package com.example.jetnews.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import com.example.jetnews.R

sealed class BottomNavItems(val route:String, val label:String, val icon: Int){

    object Home:BottomNavItems(JetScreens.HomeScreen.name,"Home", R.drawable.house)
    object MyNews:BottomNavItems(JetScreens.MyNewsScreen.name,"My News",R.drawable.bookmark)
    object Account:BottomNavItems(JetScreens.AccountScreen.name,"Account",R.drawable.account)
}
