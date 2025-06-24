package org.hasheddev.recipeappcmp.di

import org.hasheddev.recipeappcmp.db.DataBaseFactory
import org.hasheddev.recipeappcmp.preferences.MultiplatformSettingsFactory
import org.koin.dsl.module

val jsModules = module {
    single {
        DataBaseFactory()
    }

    single {
        MultiplatformSettingsFactory()
    }
}

fun initKoinJS() = initkoin(additionalModule = listOf(jsModules))