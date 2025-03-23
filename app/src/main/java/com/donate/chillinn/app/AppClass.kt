package com.donate.chillinn.app

import android.app.Application

class AppClass : Application () {

    var token: String = ""
    override fun onCreate() {
        super.onCreate()
    }
}