package com.example.concentration_timer

import android.app.Application

class App : Application() {
    val database by lazy { RecordDatabase.createDataBase(this)}
}