package com.example.jetnews.screens.HomeAction_search_home_profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetnews.Navigation.BottomNavGraph
import com.example.jetnews.Navigation.BottomNavItems
import com.example.jetnews.Navigation.JetScreens
import com.example.jetnews.screens.HomeAction_search_home_profile.Home.NewsBox
import com.example.jetnews.screens.HomeAction_search_home_profile.Home.NewsTypes
import com.example.jetnews.screens.MainViewModel
import com.google.firebase.auth.FirebaseAuth

//@RequiresApi(Build.VERSION_CODES.O)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavHostController = rememberNavController(),mainViewModel: MainViewModel = hiltViewModel(),auth:FirebaseAuth){
    val text = rememberSaveable {mutableStateOf("")}
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val items = listOf(
        BottomNavItems.Home,
        BottomNavItems.MyNews,
        BottomNavItems.Account
    )

    Scaffold(
        bottomBar = {
            if (navBackStackEntry?.destination?.route == JetScreens.HomeScreen.name || navBackStackEntry?.destination?.route == JetScreens.MyNewsScreen.name || navBackStackEntry?.destination?.route == JetScreens.AccountScreen.name) {
                BottomNavigation(modifier = Modifier.clip(RoundedCornerShape(15.dp,
                    15.dp,
                    1.dp,
                    1.dp))) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination?.route

                    items.forEach { screen ->
                        BottomNavigationItem(
                            icon = {
                                Icon(painter = painterResource(id = screen.icon),
                                    contentDescription = screen.label,
                                    modifier = Modifier.size(25.dp))
                            },
                            label = { Text(text = screen.label) },
                            alwaysShowLabel = false,
                            selected = currentDestination == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            })
                    }

                }
            }
        },
        content =  { padding ->
            BottomNavGraph(navController = navController,mainViewModel,innerpadding = padding, auth = auth)
        }
    )
}