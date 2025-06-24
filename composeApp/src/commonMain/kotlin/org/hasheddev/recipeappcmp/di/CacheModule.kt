package org.hasheddev.recipeappcmp.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.hasheddev.recipeappcmp.features.common.data.db.DbHelper
import org.hasheddev.recipeappcmp.features.common.data.db.daos.FavoriteRecipeDao
import org.hasheddev.recipeappcmp.features.common.data.db.daos.RecipeDao
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

fun cacheModule() = module {
    single<CoroutineContext> {
        Dispatchers.Default
    }
    single {
        CoroutineScope(get<CoroutineContext>())
    }

    single {
        DbHelper(get())
    }

    single {
        RecipeDao(get())
    }

    single {
        FavoriteRecipeDao(get())
    }
}