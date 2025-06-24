package org.hasheddev.recipeappcmp.db

import app.cash.sqldelight.db.SqlDriver

const val DB_FILE_NAME = "cmprecipe.db"

expect class DataBaseFactory {
    suspend fun createDriver(): SqlDriver
}