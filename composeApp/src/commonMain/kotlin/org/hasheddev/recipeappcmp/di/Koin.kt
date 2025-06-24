package org.hasheddev.recipeappcmp.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initkoin(
    additionalModule: List<Module> = emptyList(),
    appDeclaration: KoinAppDeclaration = {}
) {
    startKoin {
        appDeclaration()
        modules(
            additionalModule + cacheModule() + networkModule() + dataModule() + viewModelModule()
        )
    }
}

