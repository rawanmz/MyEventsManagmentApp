package com.example.myeventsmanagmentapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

object LocaleUtils {
    @Composable
     fun SetLanguage(locale: String) {
        val localeObj = Locale(locale)
        val configuration = LocalConfiguration.current
        configuration.setLayoutDirection(localeObj)
        configuration.setLocale(localeObj)
        val resources = LocalContext.current.resources
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

}