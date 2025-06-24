package org.hasheddev.recipeappcmp

import org.hasheddev.recipeappcmp.db.DataBaseFactory
import org.hasheddev.recipeappcmp.di.initkoin
import org.hasheddev.recipeappcmp.preferences.MultiplatformSettingsFactory
import org.koin.dsl.module

val iosModules = module {
    single {
        DataBaseFactory()
    }
    single {
        MultiplatformSettingsFactory()
    }
}

fun initKoinIOS() = initkoin(additionalModule = listOf(iosModules))