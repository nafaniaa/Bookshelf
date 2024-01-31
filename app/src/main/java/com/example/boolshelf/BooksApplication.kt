package com.example.boolshelf

import android.app.Application
import com.example.boolshelf.data.AppContainer
import com.example.boolshelf.data.DefaultAppContainer

class BooksApplication : Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}