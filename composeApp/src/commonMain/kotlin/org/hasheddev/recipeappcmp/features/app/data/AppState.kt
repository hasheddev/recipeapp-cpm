package org.hasheddev.recipeappcmp.features.app.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.hasheddev.recipeappcmp.features.detail.navigation.navigateToDetail
import org.hasheddev.recipeappcmp.features.search.navigation.navigateToSearch
import org.hasheddev.recipeappcmp.features.tabs.navigation.navigateToTabs
import org.hasheddev.recipeappcmp.preferences.AppPreferences
import org.koin.compose.koinInject

@Stable
class AppState(
    val navController: NavHostController,
    scope: CoroutineScope,
    private val appPreferences: AppPreferences
) {
    private var _isLoggedIn = MutableStateFlow(appPreferences.getBool(AppConstants.IS_LOGGED_IN, false))
    val isLoggedIn = _isLoggedIn.asStateFlow()

    fun navigateToTabs() = navController.navigateToTabs()
    fun navigateToSearch() = navController.navigateToSearch()
    fun navigateToDetail(id: Long) = navController.navigateToDetail(id)
    fun onBackClick() = navController.navigateUp()
    fun updateIsLoggedIn(status: Boolean) {
        _isLoggedIn.update { status }
        appPreferences.putBool(AppConstants.IS_LOGGED_IN, status)
    }

    fun onLogeOut() {
        updateIsLoggedIn(false)
    }
}


@Composable
fun rememberAppState(
    navController: NavHostController,
    scope: CoroutineScope = rememberCoroutineScope(),
    appPreferences: AppPreferences = koinInject()
): AppState {

    return remember(navController, scope) {
        AppState(navController, scope, appPreferences)
    }
}