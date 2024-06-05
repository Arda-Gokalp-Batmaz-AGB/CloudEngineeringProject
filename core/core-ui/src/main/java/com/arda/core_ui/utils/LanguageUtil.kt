package com.arda.dystherapy.utils
import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.ConfigurationCompat
import com.arda.dystherapy.domain.enums.LangEnum
import com.arda.dystherapy.util.DebugTagsEnumUtils
import java.util.*

class LanguageUtil {

    companion object {
        private val TAG = DebugTagsEnumUtils.UITag.tag
        private lateinit  var appContext: Context
        var currentLanguage by mutableStateOf(LangEnum.EMPTY)
        var lastLanguage = LangEnum.EMPTY

        fun init(context: Context){
            appContext = context
        }
        fun getDefaultLang() {
            val prefences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val lang = prefences.getString("language", "")

            if (lang == null || lang == "") {
                var systemLang = ConfigurationCompat.getLocales(Resources.getSystem().configuration)
                    .get(0)?.language
                systemLang = systemLang?.uppercase()
                Log.v(TAG, "DEFAULT LANGUAGE: " + systemLang)
                if (systemLang == null || !com.arda.dystherapy.components.country_code_picker.getListOfLanguages()
                        .any { country ->
                        country.languageCode.equals(
                            systemLang
                        )
                    }) {
                    currentLanguage = LangEnum.from("EN")!!
                } else {
                    currentLanguage = LangEnum.from(systemLang)!!
                }
            } else {
                currentLanguage = LangEnum.from(lang)!!
            }
            changeLanguage(currentLanguage)
            Log.v(TAG, "LANGUAGE SET TO " + currentLanguage)
        }

        fun changeLanguage(newLang: LangEnum) {
            lastLanguage = currentLanguage
            currentLanguage = newLang

            if (currentLanguage.code == appContext.getResources().configuration.locales.get(0)
                    .toString().uppercase()
            ) {
                Log.v(TAG, "The language CANT changed it is already " + currentLanguage)
                return
            }
            val prefences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val editor = prefences.edit()
            editor.putString("language", currentLanguage.code)
            editor.apply();
            val resources: Resources = appContext.getResources()
            val locale = Locale(currentLanguage.code.lowercase())
            Locale.setDefault(locale)
            val config = resources.configuration
            config.setLocale(locale)
            config.setLayoutDirection(locale)
            val configuration = resources.configuration;
            val displayMetrics = resources.displayMetrics;
            configuration.setLocale(Locale(currentLanguage.code.lowercase()))
            resources.updateConfiguration(configuration, displayMetrics)
            configuration.locale = Locale(currentLanguage.code.lowercase())
            resources.updateConfiguration(configuration, displayMetrics)

            Log.v(TAG, "Language Changed to " + config.locales.get(0))
        }
    }
}