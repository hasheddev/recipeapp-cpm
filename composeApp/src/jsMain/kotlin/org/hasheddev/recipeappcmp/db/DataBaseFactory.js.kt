package org.hasheddev.recipeappcmp.db

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import org.hasheddev.recipeappcmp.RecipeAppCmpAppDb
import org.w3c.dom.Worker

actual class DataBaseFactory {
    actual suspend fun createDriver(): SqlDriver {
        val driver = WebWorkerDriver(
            Worker(
                js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")
            )
        )
        driver.execute(null, "PRAGMA foreign_keys = ON", 0)
        RecipeAppCmpAppDb.Schema.awaitCreate(driver)
        return driver
    }

}