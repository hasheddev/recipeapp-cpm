package org.hasheddev.recipeappcmp.features.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.hasheddev.recipeappcmp.features.common.ui.ErrorContent
import org.koin.compose.viewmodel.koinViewModel
import recipeapp_cpm.composeapp.generated.resources.Res
import recipeapp_cpm.composeapp.generated.resources.app_name
import recipeapp_cpm.composeapp.generated.resources.recipe_app_logo
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenModalBottomSheet(
    loginViewModel: LoginViewModel,
    showBottomSheet: Boolean,
    onClose: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { false }
    )

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()

    val clearInputFields = {
        email = ""
        password = ""
    }

    val scope = rememberCoroutineScope()
    val onCloseItemClick = {
        scope.launch {
            clearInputFields()
            bottomSheetState.hide()
        }.invokeOnCompletion {
            if(!bottomSheetState.isVisible){
                onClose()
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            contentColor = MaterialTheme.colorScheme.background,
            dragHandle = {},
            onDismissRequest = {
                onClose()
                clearInputFields()
            },
            sheetState = bottomSheetState,
            properties = ModalBottomSheetProperties(shouldDismissOnBackPress = true)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 24.dp).padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.app_name),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        modifier = Modifier.clickable { onCloseItemClick() },
                        contentDescription = "close",
                        imageVector = Icons.Outlined.Close,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.recipe_app_logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                            focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                            cursorColor = MaterialTheme.colorScheme.primaryContainer,
                            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        value = email,
                        onValueChange = { email = it },
                        label = {
                            Text(text = "email")
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                            focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                            cursorColor = MaterialTheme.colorScheme.primaryContainer,
                            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        value = password,
                        onValueChange = { password = it },
                        label = {
                            Text(text = "password")
                        },
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    when(loginState) {
                        is LoginState.Error -> ErrorContent((loginState as LoginState.Error).message)
                        is LoginState.Loading -> CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        is LoginState.Success -> {
                            LaunchedEffect(Unit){
                                onLoginSuccess()
                                onCloseItemClick()
                            }
                        }
                        else -> Unit
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth().height(45.dp),
                        colors = ButtonDefaults.buttonColors().copy(
                            contentColor = MaterialTheme.colorScheme.primaryContainer,
                            containerColor = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.primaryContainer)
                        ),
                        onClick = {
                            loginViewModel.login(email, password)
                        },
                        enabled = loginState !is LoginState.Loading
                    ) {
                        Text("Login", color = MaterialTheme.colorScheme.onPrimary)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

