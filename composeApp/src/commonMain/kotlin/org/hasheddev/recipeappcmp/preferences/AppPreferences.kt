package org.hasheddev.recipeappcmp.preferences

interface AppPreferences {
    fun clear()

    fun getInt(key: String, defaultValue: Int): Int

    fun getIntOrNull(key: String): Int?

    fun getLong(key: String, defaultValue: Long): Long

    fun getLongOrNull(key: String): Long?

    fun getBool(key: String, defaultValue: Boolean): Boolean

    fun getBoolOrNull(key: String): Boolean?


    fun getString(key: String, defaultValue: String): String

    fun getStringOrNull(key: String): String?

    fun putInt(key: String, value: Int)

    fun putBool(key: String, value: Boolean)

    fun putString(key: String, value: String)

    fun putLong(key: String, value: Long)

    fun hasKey(key: String): Boolean

    fun remove(key: String)
}