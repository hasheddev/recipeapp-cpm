package org.hasheddev.recipeappcmp.di

import org.hasheddev.recipeappcmp.features.detail.ui.RecipeDetailViewModel
import org.hasheddev.recipeappcmp.features.favorites.ui.FavoriteScreenViewModel
import org.hasheddev.recipeappcmp.features.feeds.ui.FeedViewModel
import org.hasheddev.recipeappcmp.features.login.ui.LoginViewModel
import org.hasheddev.recipeappcmp.features.profile.ui.ProfileViewModel
import org.hasheddev.recipeappcmp.features.search.ui.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun viewModelModule() = module {
    viewModel {
        FeedViewModel(get())
    }

    viewModel {
        RecipeDetailViewModel(get())
    }

    viewModel {
        FavoriteScreenViewModel(get())
    }

    viewModel {
        ProfileViewModel()
    }
    viewModel {
        LoginViewModel()
    }
    viewModel {
        SearchViewModel(get())
    }
}