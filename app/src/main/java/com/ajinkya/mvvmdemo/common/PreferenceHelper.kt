package com.ajinkya.mvvmdemo.common

import android.content.SharedPreferences
import com.ajinkya.mvvmdemo.common.Constants.EXCEPTION_NOT_YET_IMPLEMENTED


private val PREF_LANGUAGE = "pref_language"

class PreferenceHelper {

    companion object {
        const val PREF_LOGIN_DATA = "pref_login_data"
        const val PREF_SHOULD_SHOW_TOUR = "pref_should_show_tour"

        const val PREF_IS_USER_LOGGED_IN = "pref_is_user_logged_in"
        const val PREF_IS_DEVICE_REGISTERED = "pref_is_device_registered"
        const val PREF_IS_USER_REGISTERED = "pref_is_user_registered"

        const val PREF_ACCESS_TOKEN = "pref_access_token"
        const val PREF_REFRESH_TOKEN = "pref_refresh_token"
        const val PREF_PROFILE_DATA = "pref_profile_data"

    }
}

inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}

/**
 * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
 */
operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        /*is LoginData -> edit {
            it.putString(key, Gson().toJson(value))
        }
        is ProfileData -> edit {
            it.putString(key, Gson().toJson(value))
        }*/

        else -> throw UnsupportedOperationException(EXCEPTION_NOT_YET_IMPLEMENTED)
    }
}

/**
 * finds value on given key.
 * [T] is the type of value
 * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
 */
inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T?
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
//        LoginData::class -> getLoginData(this) as T?
//        ProfileData::class -> getProfileData(this) as T?
        else -> throw UnsupportedOperationException(EXCEPTION_NOT_YET_IMPLEMENTED)
    }
}

fun SharedPreferences.saveAccessToken(accessToken: String?) {
    if (accessToken != null) {
        this.set(PreferenceHelper.PREF_ACCESS_TOKEN, accessToken)
    }
}
/*fun SharedPreferences.saveAccessToken(accessToken: String?) {
    if (accessToken != null) {
        this.set(PreferenceHelper.PREF_ACCESS_TOKEN, TOKEN_PREFIX_BEARER + accessToken)
    }
}

fun SharedPreferences.getAccessToken(): String {
    return this.getString(PreferenceHelper.PREF_ACCESS_TOKEN, "")
}*/

/*
fun getLoginData(sharedPreferences: SharedPreferences): LoginData? {
    val loginData: LoginData? = null
    val json = sharedPreferences.getString(PreferenceHelper.PREF_LOGIN_DATA, "")
    if (TextUtils.isEmpty(json)) {
        return loginData
    }
    return Gson().fromJson(json, LoginData::class.java) as LoginData
}

fun getProfileData(sharedPreferences: SharedPreferences): ProfileData? {

    val profileData: ProfileData? = null
    val json = sharedPreferences.getString(PreferenceHelper.PREF_PROFILE_DATA, "")
    if (TextUtils.isEmpty(json)) {
        return profileData
    }
    return Gson().fromJson(json, ProfileData::class.java) as ProfileData
}

fun SharedPreferences.saveLanguageCode(languageCode: Constants.Language) {
    this.set(PREF_LANGUAGE, languageCode.value)
}

fun SharedPreferences.getLanguageCode(): String {
    return this.getString(PREF_LANGUAGE, DEFAULT_LANGUAGE_CODE)
}


fun SharedPreferences.clearAllPref() {
    this.edit().clear().apply()
}
*/


