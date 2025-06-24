package org.hasheddev.recipeappcmp.features.common.data.db.util

import app.cash.sqldelight.ColumnAdapter

val listOfStringAdapter = object: ColumnAdapter<List<String>, String> {
    override fun decode(databaseValue: String): List<String> {
        return if(databaseValue.isEmpty()) listOf() else databaseValue.split(",")
    }

    override fun encode(value: List<String>) = value.joinToString(separator = ",")
}