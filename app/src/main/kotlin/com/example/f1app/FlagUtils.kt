package com.example.f1app.utils

import com.example.f1app.R

fun getFlagForMeeting(meetingName: String?): Int {
    return when {
        meetingName?.contains("Australian", ignoreCase = true) == true -> R.drawable.flag_australia
        meetingName?.contains("Chinese", ignoreCase = true) == true -> R.drawable.flag_china
        meetingName?.contains("Japanese", ignoreCase = true) == true -> R.drawable.flag_japan
        meetingName?.contains("Bahrain", ignoreCase = true) == true -> R.drawable.flag_bahrain
        meetingName?.contains("Saudi", ignoreCase = true) == true -> R.drawable.flag_saudi
        meetingName?.contains("Miami", ignoreCase = true) == true -> R.drawable.flag_usa
        meetingName?.contains("Emilia", ignoreCase = true) == true -> R.drawable.flag_italy
        meetingName?.contains("Monaco", ignoreCase = true) == true -> R.drawable.flag_monaco
        meetingName?.contains("Spanish", ignoreCase = true) == true -> R.drawable.flag_spain
        meetingName?.contains("Barcelona", ignoreCase = true) == true -> R.drawable.flag_spain
        meetingName?.contains("Canadian", ignoreCase = true) == true -> R.drawable.flag_canada
        meetingName?.contains("Austrian", ignoreCase = true) == true -> R.drawable.flag_austria
        meetingName?.contains("British", ignoreCase = true) == true -> R.drawable.flag_uk
        meetingName?.contains("Belgian", ignoreCase = true) == true -> R.drawable.flag_belgium
        meetingName?.contains("Hungarian", ignoreCase = true) == true -> R.drawable.flag_hungary
        meetingName?.contains("Dutch", ignoreCase = true) == true -> R.drawable.flag_netherlands
        meetingName?.contains("Italian", ignoreCase = true) == true -> R.drawable.flag_italy
        meetingName?.contains("Azerbaijan", ignoreCase = true) == true -> R.drawable.flag_azerbaijan
        meetingName?.contains("Singapore", ignoreCase = true) == true -> R.drawable.flag_singapore
        meetingName?.contains("United States", ignoreCase = true) == true -> R.drawable.flag_usa
        meetingName?.contains("Mexico", ignoreCase = true) == true -> R.drawable.flag_mexico
        meetingName?.contains("São Paulo", ignoreCase = true) == true -> R.drawable.flag_brazil
        meetingName?.contains("Brazilian", ignoreCase = true) == true -> R.drawable.flag_brazil
        meetingName?.contains("Las Vegas", ignoreCase = true) == true -> R.drawable.flag_usa
        meetingName?.contains("Qatar", ignoreCase = true) == true -> R.drawable.flag_qatar
        meetingName?.contains("Abu Dhabi", ignoreCase = true) == true -> R.drawable.flag_uae
        else -> R.drawable.flag_australia
    }
}

fun getCountryName(meetingName: String?): String {
    return when {
        meetingName?.contains("Australian", ignoreCase = true) == true -> "Australia"
        meetingName?.contains("Chinese", ignoreCase = true) == true -> "China"
        meetingName?.contains("Japanese", ignoreCase = true) == true -> "Japan"
        meetingName?.contains("Bahrain", ignoreCase = true) == true -> "Bahrain"
        meetingName?.contains("Saudi", ignoreCase = true) == true -> "Saudi Arabia"
        meetingName?.contains("Miami", ignoreCase = true) == true -> "Miami"
        meetingName?.contains("Emilia", ignoreCase = true) == true -> "Emilia Romagna"
        meetingName?.contains("Monaco", ignoreCase = true) == true -> "Monaco"
        meetingName?.contains("Spanish", ignoreCase = true) == true -> "Spain"
        meetingName?.contains("Barcelona", ignoreCase = true) == true -> "Spain"
        meetingName?.contains("Canadian", ignoreCase = true) == true -> "Canada"
        meetingName?.contains("Austrian", ignoreCase = true) == true -> "Austria"
        meetingName?.contains("British", ignoreCase = true) == true -> "Great Britain"
        meetingName?.contains("Belgian", ignoreCase = true) == true -> "Belgium"
        meetingName?.contains("Hungarian", ignoreCase = true) == true -> "Hungary"
        meetingName?.contains("Dutch", ignoreCase = true) == true -> "Netherlands"
        meetingName?.contains("Italian", ignoreCase = true) == true -> "Italy"
        meetingName?.contains("Azerbaijan", ignoreCase = true) == true -> "Azerbaijan"
        meetingName?.contains("Singapore", ignoreCase = true) == true -> "Singapore"
        meetingName?.contains("United States", ignoreCase = true) == true -> "USA"
        meetingName?.contains("Mexico", ignoreCase = true) == true -> "Mexico"
        meetingName?.contains("São Paulo", ignoreCase = true) == true -> "Brazil"
        meetingName?.contains("Brazilian", ignoreCase = true) == true -> "Brazil"
        meetingName?.contains("Las Vegas", ignoreCase = true) == true -> "Las Vegas"
        meetingName?.contains("Qatar", ignoreCase = true) == true -> "Qatar"
        meetingName?.contains("Abu Dhabi", ignoreCase = true) == true -> "Abu Dhabi"
        else -> meetingName?.replace(" Grand Prix", "") ?: "Unknown"
    }
}

