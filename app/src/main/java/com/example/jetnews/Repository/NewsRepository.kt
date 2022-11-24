package com.example.jetnews.Repository


import android.util.Log
import com.example.jetnews.Data.DataOrException
import com.example.jetnews.Model.Article
import com.example.jetnews.Model.News
import com.example.jetnews.Model.UserInfoData
import com.example.jetnews.Network.NewsAPI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
//
//class NewsRepository @Inject constructor(private val newsAPI: NewsAPI) {
//
//    suspend fun getTopHeadlines(
//        country: String,
//        language: String,
//        category: String,
//
//    ):DataOrException<News,Boolean,Exception>{
//
//        val response = try{
//            newsAPI.topHeadlines(
//                country = country,
//                language = language,
//                category = category,
//            )
//        } catch (e:Exception){
//
//            Log.d("error", e.toString())
//            return DataOrException(e=e)
//        }
//
//        Log.d("response", response.toString())
//        return withContext(Dispatchers.IO){ DataOrException(data = response)}
//    }
//
//    suspend fun getSearchNews(country: String,language: String,search: String? = null):DataOrException<News,Boolean,Exception>{
//        val responce = try {
//
//            newsAPI.getSearchNews(country = country, language = language,search = search!!)
//        }catch (e:Exception){
//            Log.d("error  ",e.toString())
//            return DataOrException(e = e)
//        }
//
//        Log.d("response", responce.toString())
//        return DataOrException(data = responce)
//    }
//}





class NewsRepository @Inject constructor(private val newsAPI: NewsAPI) {

    val database = Firebase.database("https://jet-news-f244e-default-rtdb.firebaseio.com")

    suspend fun getTopHeadlines(
        country:String,
        category:String,
    ):News{
        return withContext(Dispatchers.IO){
            newsAPI.topHeadlines(country,category)
        }
    }


    suspend fun searchedNews(
        language:String,
        query:String
    ):News{
        return withContext(Dispatchers.IO){
            newsAPI.getSearchNews(language = language, search = query)
        }
    }



    fun sendNewsFireB(user:String,data:UserInfoData){
        database.reference.child(user).child("UserData").setValue(data)
    }

    fun pushListOfArticle(user:String,art:Article){
        database.reference.child(user).child("UserData").child("article").push().setValue(art)
    }

    fun editProfile(user: String,userName:String,aboutMe:String){
        database.reference.child(user).child("UserData").child("userName").setValue(userName)
        database.reference.child(user).child("UserData").child("aboutMe").setValue(aboutMe)
    }

    fun selectCountry(user:String,country: String){
        database.reference.child(user).child("UserData").child("country").setValue(country)
    }


}