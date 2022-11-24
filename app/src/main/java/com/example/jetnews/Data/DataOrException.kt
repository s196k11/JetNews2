package com.example.jetnews.Data

import com.example.jetnews.Model.UserInfoData
import java.util.HashMap


sealed class DataOrException<T>(val data:T? = null,val error : String? = null,loading: Boolean? = null){
    class Success<T>(data: T?):DataOrException<T>(data = data)
    class Error<T>(error:String,data: T? = null) : DataOrException<T>(data = data,error = error)
    class Loading<T>(data: T? = null,error: String? = null,loading: Boolean)
}
