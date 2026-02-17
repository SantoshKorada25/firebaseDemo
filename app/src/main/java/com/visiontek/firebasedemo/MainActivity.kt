package com.visiontek.firebasedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.visiontek.firebasedemo.ui.theme.FirebaseDemoTheme
import com.visiontek.firebasedemo.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: AuthViewModel = viewModel()
            val loginStatus by viewModel.loginState.collectAsStateWithLifecycle()
            FirebaseDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   if (loginStatus ==true){
                       HomeScreen(
                           onLogout = { viewModel.logout() },
                           modifier = Modifier.padding(innerPadding)
                       )
                   }
                    else  {
                       AuthScreen(
                           viewModel = viewModel,
                           // You can pass the loginStatus to show an error if it's false
                           isError = loginStatus == false,
                           modifier = Modifier.padding(innerPadding)
                       )
                    }

                }
            }
        }
    }
}


@Composable
fun AuthScreen(modifier: Modifier = Modifier, viewModel: AuthViewModel, isError: Boolean) {
    // State to toggle between Login and Sign Up
    var isLogin by remember { mutableStateOf(true) }

    // Form input states
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isLogin) "Firebase Features" else "Create Account",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        if (isError) {
            Text(
                text = "Authentication Failed. Please check your credentials.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        // Confirm Password Field (Only visible during Sign Up)
        if (!isLogin) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Action Button
        Button(
            onClick = {
                if (isLogin) {
                    println("Clicked LOGIN")
                    viewModel.login(email,password)
                } else {
                    // TODO: Implement Sign Up Logic
                    println("Clicked SignUp")
                    viewModel.signUp(email,password)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLogin) "Login" else "Sign Up")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Toggle Text
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = if (isLogin) "Don't have an account? " else "Already have an account? ")
            Text(
                text = if (isLogin) "Sign Up" else "Login",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { isLogin = !isLogin }
            )
        }
    }
}

@Composable
fun HomeScreen(onLogout: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Home Screen!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthPreview() {
    FirebaseDemoTheme {
        AuthScreen(viewModel = viewModel(), isError = false)
    }
}