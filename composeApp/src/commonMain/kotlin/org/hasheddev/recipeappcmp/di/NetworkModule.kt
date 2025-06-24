package org.hasheddev.recipeappcmp.di

import io.ktor.client.HttpClient
import org.hasheddev.recipeappcmp.features.common.data.api.httpClient
import org.koin.dsl.module

fun networkModule() = module {

    single<HttpClient> {
        httpClient
    }
}