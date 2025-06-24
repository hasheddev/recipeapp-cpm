package org.hasheddev.recipeappcmp.di

import org.hasheddev.recipeappcmp.features.detail.data.datasource.RecipeDetailLocalDataSourceImpl
import org.hasheddev.recipeappcmp.features.detail.data.datasource.RecipeDetailRemoteDataSourceImpl
import org.hasheddev.recipeappcmp.features.detail.data.repository.RecipeDetailRepositoryImpl
import org.hasheddev.recipeappcmp.features.detail.domain.RecipeDetailLocalDataSource
import org.hasheddev.recipeappcmp.features.detail.domain.RecipeDetailRemoteDataSource
import org.hasheddev.recipeappcmp.features.detail.domain.repository.RecipeDetailRepository
import org.hasheddev.recipeappcmp.features.favorites.data.FavoriteRecipeLocalDataSourceImpl
import org.hasheddev.recipeappcmp.features.favorites.data.FavoriteRecipeRepositoryImpl
import org.hasheddev.recipeappcmp.features.favorites.domain.FavoriteRecipeLocalDataSource
import org.hasheddev.recipeappcmp.features.favorites.domain.FavoriteRecipeRepository
import org.hasheddev.recipeappcmp.features.feeds.data.datasource.FeedLocalDataSourceImpl
import org.hasheddev.recipeappcmp.features.feeds.data.datasource.FeedRemoDataSourceImpl
import org.hasheddev.recipeappcmp.features.feeds.data.repository.FeedRepositoryImpl
import org.hasheddev.recipeappcmp.features.feeds.domain.FeedLocalDataSource
import org.hasheddev.recipeappcmp.features.feeds.domain.FeedRemoteDataSource
import org.hasheddev.recipeappcmp.features.feeds.domain.repository.FeedRepository
import org.hasheddev.recipeappcmp.features.search.data.datasource.SearchRecipeDataSource
import org.hasheddev.recipeappcmp.features.search.data.datasource.SearchRecipeLocalDataSourceImpl
import org.hasheddev.recipeappcmp.features.search.data.repository.SearchRecipeRepositoryImpl
import org.hasheddev.recipeappcmp.features.search.domain.repository.SearchRecipeRepository
import org.hasheddev.recipeappcmp.preferences.AppPreferences
import org.hasheddev.recipeappcmp.preferences.AppPreferencesImpl
import org.koin.dsl.module

fun dataModule() = module {
    single<FeedLocalDataSource> {
        FeedLocalDataSourceImpl(get())
    }

    single<FeedRemoteDataSource> {
        FeedRemoDataSourceImpl(get())
    }

    single<FeedRepository> {
        FeedRepositoryImpl(get(), get())
    }

    single<RecipeDetailLocalDataSource> {
        RecipeDetailLocalDataSourceImpl(get(), get())
    }

    single<RecipeDetailRemoteDataSource> {
        RecipeDetailRemoteDataSourceImpl(get())
    }

    single<RecipeDetailRepository> {
        RecipeDetailRepositoryImpl(get(), get())
    }

    single<FavoriteRecipeLocalDataSource> {
        FavoriteRecipeLocalDataSourceImpl(get())
    }

    single<FavoriteRecipeRepository>{
        FavoriteRecipeRepositoryImpl(get())
    }

    single<AppPreferences>{
      AppPreferencesImpl(get())
    }

    single<SearchRecipeDataSource>{
        SearchRecipeLocalDataSourceImpl(get())
    }

    single<SearchRecipeRepository>{
        SearchRecipeRepositoryImpl(get())
    }

}