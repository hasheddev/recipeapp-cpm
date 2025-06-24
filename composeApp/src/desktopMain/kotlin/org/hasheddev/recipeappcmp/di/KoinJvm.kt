package org.hasheddev.recipeappcmp.di

import org.hasheddev.recipeappcmp.db.DataBaseFactory
import org.hasheddev.recipeappcmp.preferences.MultiplatformSettingsFactory
import org.koin.dsl.module

val jvmModules = module {
    single {
        DataBaseFactory()
    }

    single {
        MultiplatformSettingsFactory()
    }
}

fun initKoinJVM() = initkoin(additionalModule = listOf(jvmModules))