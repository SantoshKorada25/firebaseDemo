package com.visiontek.firebasedemo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.visiontek.firebasedemo.Screens.AuthScreen
import com.visiontek.firebasedemo.Screens.HomeScreen
import com.visiontek.firebasedemo.Screens.ProfileScreen
import com.visiontek.firebasedemo.navigation.Screen
import com.visiontek.firebasedemo.ui.theme.FirebaseDemoTheme
import com.visiontek.firebasedemo.viewmodel.AuthState
import com.visiontek.firebasedemo.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: AuthViewModel = viewModel()
            val authState by viewModel.authState.collectAsStateWithLifecycle()
            FirebaseDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when(authState) {
                        is AuthState.Success -> {
                            MainAppContent(viewModel, onLogout = { viewModel.logout() })
                        }
                        else -> {
                            AuthScreen(
                                viewModel=viewModel,
                                authState = authState,
                                modifier=Modifier.padding(innerPadding)
                            )

                        }
                    }

                }
            }
        }
    }
}


/*@Composable
fun MainAppContent(viewModel: AuthViewModel, onLogout: () -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                NavigationBarItem(
                    selected = currentRoute == Screen.Home.route,
                    onClick = { navController.navigate(Screen.Home.route) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = currentRoute == Screen.Profile.route,
                    onClick = { navController.navigate(Screen.Profile.route) },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(onLogout = onLogout)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(onLogout = onLogout)
            }
        }
    }
}*/

@Composable
fun MainAppContent(viewModel: AuthViewModel, onLogout: () -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        containerColor = Color.White, // Ensure background stays clean white
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp, // Adds a subtle shadow above the bar
                contentColor = Color(0xFF1A2130)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // Home Item
                NavigationBarItem(
                    selected = currentRoute == Screen.Home.route,
                    onClick = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(26.dp)
                        )
                    },
                    label = {
                        Text(
                            "Home",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1A2130),
                        unselectedIconColor = Color.LightGray,
                        selectedTextColor = Color(0xFF1A2130),
                        unselectedTextColor = Color.LightGray,
                        indicatorColor = Color(0xFFFFD500) // The Yellow selection pill
                    )
                )

                // Profile Item
                NavigationBarItem(
                    selected = currentRoute == Screen.Profile.route,
                    onClick = {
                        navController.navigate(Screen.Profile.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(26.dp)
                        )
                    },
                    label = {
                        Text(
                            "Profile",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1A2130),
                        unselectedIconColor = Color.LightGray,
                        selectedTextColor = Color(0xFF1A2130),
                        unselectedTextColor = Color.LightGray,
                        indicatorColor = Color(0xFFFFD500) // The Yellow selection pill
                    )
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(onLogout = onLogout)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(onLogout = onLogout)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AuthPreview() {
    FirebaseDemoTheme {
        AuthScreen(viewModel = viewModel(), authState = AuthState.Idle )
    }
}