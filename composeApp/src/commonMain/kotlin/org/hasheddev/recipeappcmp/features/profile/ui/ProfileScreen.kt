package org.hasheddev.recipeappcmp.features.profile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.hasheddev.recipeappcmp.features.common.ui.ErrorContent
import org.hasheddev.recipeappcmp.features.common.ui.Loader
import org.hasheddev.recipeappcmp.features.favorites.ui.FavoriteContent
import org.hasheddev.recipeappcmp.features.profile.domain.User
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import recipeapp_cpm.composeapp.generated.resources.Res
import recipeapp_cpm.composeapp.generated.resources.avatar
import recipeapp_cpm.composeapp.generated.resources.profile_dummy

@Composable
fun ProfileRoute(
    viewModel: ProfileViewModel = koinViewModel(),
    isUserLoggedIn: () -> Boolean,
    openLogInSheet: (() -> Unit) -> Unit,
    onLogOut: () -> Unit,
) {
    val profileState by viewModel.profileState.collectAsStateWithLifecycle()

    ProfileScreen(
        isUserLoggedIn,
        profileState,
        onEditProfile = {},
        onLogin = {
            openLogInSheet {
                viewModel.refresh()
            }
        },
        onLogOut = onLogOut
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun  ProfileScreen(
    isUserLoggedIn: () -> Boolean,
    profileState: ProfileUiState,
    onEditProfile: () -> Unit,
    onLogin: () -> Unit,
    onLogOut: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = { Text(text = "Profile") }
            )
        }
    ) {innerPadding ->
        Column(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
            HorizontalDivider(
                thickness = .3.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = .5f)
            )
            when {
                !isUserLoggedIn() -> {NotLoggedInScreen(onLogin, {})}
                profileState.isLoading -> { Loader() }
                profileState.error != null -> { ErrorContent() }
                profileState.userInfo != null && isUserLoggedIn() -> { ProfileContent(
                    userInfo = profileState.userInfo,
                    onEditProfile = onEditProfile,
                    onLogOut = onLogOut
                ) }
            }
        }
    }
}

@Composable
fun NotLoggedInScreen(
    onLogin: () -> Unit,
    onSignUp: () -> Unit
) {
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(Res.drawable.profile_dummy),
            contentDescription = "PlaceHolder",
            modifier = Modifier.size(120.dp).clip(CircleShape).border(
                0.3.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                CircleShape
            ).background(MaterialTheme.colorScheme.outline),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "You are not logged in",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Login to view your profile",
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(
                text = "Login In",
                style = TextStyle(
                    fontSize = 16.sp,
                ),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
            onClick = onSignUp,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text(
                text = "Sign Up",
                style = TextStyle(
                    fontSize = 16.sp,
                )
            )
        }
    }
}

@Composable
fun ProfileContent(
    userInfo: User,
    onEditProfile: () -> Unit,
    onLogOut: () -> Unit,
   ) {

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(Res.drawable.avatar),
            contentDescription = "Profile Image",
            modifier = Modifier.size(120.dp).clip(CircleShape).border(
                0.3.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                CircleShape
            ).background(MaterialTheme.colorScheme.outline),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = userInfo.name,
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = userInfo.email,
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
            onClick = onEditProfile,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
        ) {
            Text(
                text = "Edit Profile",
                style = TextStyle(
                    fontSize = 16.sp,
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onLogOut,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(
                text = "Logout",
                style = TextStyle(
                    fontSize = 16.sp,
                ),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
