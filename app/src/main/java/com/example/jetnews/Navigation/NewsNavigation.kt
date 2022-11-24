package com.example.jetnews.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetnews.screens.AccountSetup.FillYourProfile.FillYourProfileScreen
import com.example.jetnews.screens.AccountSetup.SelectYourCountry.SelectYourCountry
import com.example.jetnews.screens.ChangeYourCountry
import com.example.jetnews.screens.Help
import com.example.jetnews.screens.HomeAction_search_home_profile.Account.AccountScreen
import com.example.jetnews.screens.HomeAction_search_home_profile.Account.EditProfile.EditProfile
import com.example.jetnews.screens.HomeAction_search_home_profile.Account.Setting.SettingScreen
import com.example.jetnews.screens.HomeAction_search_home_profile.Detail.DetailScreen
import com.example.jetnews.screens.HomeAction_search_home_profile.Home.HomeScreen
import com.example.jetnews.screens.HomeAction_search_home_profile.MainScreen
import com.example.jetnews.screens.HomeAction_search_home_profile.MyNews.MyNewsScreen
import com.example.jetnews.screens.MainViewModel
import com.example.jetnews.screens.SignUp_SingIn.ForgotPassword.ForgetPasswordScreen
import com.example.jetnews.screens.SignUp_SingIn.ResetPassword.ResetPasswordScreen
import com.example.jetnews.screens.SignUp_SingIn.SignIn.SignInScreen
import com.example.jetnews.screens.SignUp_SingIn.SignUp.SignUpScreen
import com.example.jetnews.screens.SplashScreen.SplashScreen
import com.example.jetnews.screens.UserDetail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//@RequiresApi(Build.VERSION_CODES.O)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsNavigation(mainViewModel: MainViewModel,auth: FirebaseAuth){
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = JetScreens.SplashScreen.name, route = JetNavGraph.Root_Graph.name){

        composable(JetScreens.SplashScreen.name){
            SplashScreen(navController = navController, mainViewModel,auth = auth)
        }

        authenticationGraph(navController, auth = auth, mainViewModel = mainViewModel)

        accountSetupGraph(navController,mainViewModel = mainViewModel,auth)

        composable(JetScreens.MainScreen.name){
            MainScreen(mainViewModel = mainViewModel,auth = auth)
        }
    }
}



fun NavGraphBuilder.authenticationGraph(navController: NavHostController,mainViewModel: MainViewModel,auth: FirebaseAuth){
    navigation(startDestination = JetScreens.SingInScreen.name, route = JetNavGraph.Auth_Graph.name){


        composable(JetScreens.SingInScreen.name){
            SignInScreen(navController,mainViewModel = mainViewModel,auth =auth )
        }

        composable(JetScreens.SingUpScreen.name){
            SignUpScreen(navController = navController,mainViewModel,auth)
        }

//        composable(JetScreens.ResetPasswordScreen.name){
//            ResetPasswordScreen(navController = navController)
//        }

        composable(JetScreens.ForgotPasswordScreen.name){
            ForgetPasswordScreen(navController = navController,auth = auth)
        }

        accountSetupGraph(navController, mainViewModel, auth)
    }

}

fun NavGraphBuilder.accountSetupGraph(navController:NavHostController,mainViewModel: MainViewModel,auth: FirebaseAuth){
    navigation(startDestination = JetScreens.FillProfileScreen.name, route = JetNavGraph.Account_Setup_Graph.name){
        composable(JetScreens.FillProfileScreen.name){
            FillYourProfileScreen(navController = navController, mainViewModel = mainViewModel,auth = auth)
        }

        composable(JetScreens.SelectCountyScreen.name){
            SelectYourCountry(navController = navController,auth = auth, mainViewModel = mainViewModel)
        }
    }
}




@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavGraph(navController: NavHostController = rememberNavController(),mainViewModel: MainViewModel, innerpadding: PaddingValues,auth: FirebaseAuth){
    NavHost(navController = navController,startDestination = JetScreens.HomeScreen.name,route = JetNavGraph.Bottom_Graph.name){
        composable(JetScreens.HomeScreen.name){
            HomeScreen(navController = navController, mainViewModel = mainViewModel, auth = auth)
        }

        composable(JetScreens.MyNewsScreen.name){
            MyNewsScreen(navController = navController,mainViewModel = mainViewModel)
        }

        composable(JetScreens.AccountScreen.name){
            AccountScreen(navController = navController,mainViewModel = mainViewModel)
        }


        composable(JetScreens.DetailScreen.name){
            DetailScreen(navController = navController, mainViewModel = mainViewModel,auth = auth)
        }

        composable(JetScreens.SettingScreen.name){
            SettingScreen(navController = navController, mainViewModel = mainViewModel,auth)
        }

        composable(JetScreens.EditProfileScreen.name){
            EditProfile(navController = navController, mainViewModel = mainViewModel, auth = auth)
        }


        composable(JetScreens.SelectCountyScreen.name){
            SelectYourCountry(navController = navController, mainViewModel = mainViewModel, auth = auth)
        }

        composable(JetScreens.ChangeCountryScreen.name){
            ChangeYourCountry(navController = navController, mainViewModel = mainViewModel, auth = auth)
        }

        composable(JetScreens.HelpScreen.name){
            Help()
        }

        composable(JetScreens.UserDetailScreen.name){
            UserDetail(navController = navController, mainViewModel = mainViewModel)
        }

        authenticationGraph(navController, mainViewModel, auth)



    }
}



