package org.hasheddev.recipeappcmp

import android.app.Application
import org.hasheddev.recipeappcmp.db.DataBaseFactory
import org.hasheddev.recipeappcmp.di.initkoin
import org.hasheddev.recipeappcmp.preferences.MultiplatformSettingsFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class RecipeApplication: Application(){
    private val androidModules = module {
        single {
            DataBaseFactory(
                applicationContext
            )
        }

        single {
            MultiplatformSettingsFactory(applicationContext)
        }
    }

    override fun onCreate() {
        super.onCreate()
        setUpKoin()
    }

    private fun setUpKoin() {
        initkoin(
            additionalModule = listOf(androidModules)
        ) {
            androidContext(applicationContext)
        }
    }
}