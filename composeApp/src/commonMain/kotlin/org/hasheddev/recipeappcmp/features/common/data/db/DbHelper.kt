package org.hasheddev.recipeappcmp.features.common.data.db

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.hasheddev.recipeappcmp.RecipeAppCmpAppDb
import org.hasheddev.recipeappcmp.db.DataBaseFactory
import org.hasheddev.recipeappcmp.features.common.data.db.util.listOfStringAdapter

class DbHelper(
    private val driverFactory: DataBaseFactory
) {
    private var db: RecipeAppCmpAppDb? = null
    private val mutex = Mutex()

    suspend fun <Result: Any?> withDatabase(block: suspend (RecipeAppCmpAppDb) -> Result) =
        mutex.withLock {
            if(db == null) {
                db = createDb(driverFactory)
            }
            return@withLock block(db!!)
        }

    private suspend fun createDb(driverFactory: DataBaseFactory): RecipeAppCmpAppDb {
        return RecipeAppCmpAppDb(
            driver = driverFactory.createDriver(),
            RecipeAdapter = orghasheddevrecipeappcmp.Recipe.Adapter(
                ingredientsAdapter = listOfStringAdapter,
                instructionsAdapter = listOfStringAdapter
            )
        )
    }
}