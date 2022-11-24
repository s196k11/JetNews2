package com.example.jetnews.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "UserDataTbl")
data class UserData(

    @ColumnInfo(name = "UserName")
    val userName:String,

    @PrimaryKey()
    @ColumnInfo(name = "email")
    val email:String,

    @ColumnInfo(name = "aboutYou")
    val aboutYou:String,

    @ColumnInfo(name = "Country")
    val country:String,

    @ColumnInfo(name = "language")
    val language:String,
)
