package com.example.jetnews.screens

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnews.Model.Article
import com.example.jetnews.Model.News
import com.example.jetnews.Model.UserInfoData
import com.example.jetnews.Repository.NewsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Objects
import javax.inject.Inject




@HiltViewModel
class MainViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel(){
     private var _news = MutableLiveData<News?>()
    var news = _news as LiveData<News?>

    var country = mutableStateOf("")

    val auth = Firebase.auth
    private val currentUser = auth.currentUser?.email.toString().split("@")[0]
    val database = FirebaseDatabase.getInstance().getReference(currentUser).child("UserData")

    val clickedNews = mutableStateOf<Article?>(null)
    //getting user data
    val userData = mutableStateOf<UserInfoData?>(UserInfoData())
    //getting article from firebase
//    val articleFire = mutableStateOf<List<Article>?>(emptyList())
    val articleFire:MutableList<Article> = mutableListOf()


//    init {
//        getRealtimeData(database = database)
//    }

    fun getRealtimeData(database:DatabaseReference){
        val dataListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue<UserInfoData>()
//                Log.d("dataFire",data!!.toString())
                userData.value = data
                if (userData.value?.article?.values != null) {
                    for (i in userData.value!!.article!!.values) {
                        if (i in articleFire){

                        }else {
                            articleFire.add(i)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("onCancelled",error.toException().toString())
            }
        }

        database.addValueEventListener(dataListener)

    }


    fun selectedCountry(countryCode:String){
        country.value = countryCode
    }

    fun getTopHeadlines(
        country:String,
        category:String
    ){
        viewModelScope.launch {
            val response = newsRepository.getTopHeadlines(country,category)
            _news.value = response
        }
    }

    fun getSearchedNews(
        language:String,
        query:String
    ){
        viewModelScope.launch {
            try{
                val response = newsRepository.searchedNews(language, query)
                _news.value = response

            }catch (e:Exception){
                _news.value = null
            }

        }
    }

    fun createUserWithEmailAndPassword(auth:FirebaseAuth,email:String,password:String,onSuccess:() -> Unit,onFailure:() -> Unit){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(){task ->
                if(task.isSuccessful){
                    onSuccess()
                }else{
                    onFailure()
                }
            }
    }

    fun singInWithEmailAndPassword(auth: FirebaseAuth,email: String,password: String,onSuccess: () -> Unit,onFailure:() -> Unit){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(){task ->
                if (task.isSuccessful){
                    onSuccess()
                }else{
                    Log.w("Firebase", "createUserWithEmail:failure", task.exception)

                }
            }
    }

    fun sendNewsFireB(user:String,data:UserInfoData){
        newsRepository.sendNewsFireB(user, data)
    }

    fun pushListOfArticle(user:String,art:Article){
        newsRepository.pushListOfArticle(user, art)
    }

    fun editProfile(user:String,userName:String,aboutMe:String){
        newsRepository.editProfile(user, userName, aboutMe)
    }

    fun selectCountry(user:String,country: String){
        newsRepository.selectCountry(user, country)
    }

    fun removeArticle(art: Article){

    }

}