package com.martial.beemr.utils

import java.text.SimpleDateFormat

object Constants {
    const val BASE_URL = "https://testscoretracker.herokuapp.com/"
    val DATE_FORMAT_PARSE = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val DATE_FORMAT_LOCAL = SimpleDateFormat("yyyy MMM dd")
}