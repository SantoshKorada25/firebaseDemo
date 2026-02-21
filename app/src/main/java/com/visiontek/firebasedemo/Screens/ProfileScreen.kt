package com.visiontek.firebasedemo.Screens
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.visiontek.firebasedemo.R


data class ProfileOption(val icon: ImageVector,val title:String)
/*
@Composable
fun ProfileScreen() {
        //ProfileListItem(ProfileOption(Icons.Default.AccountCircle, "Account"))
}
*/

@Composable
fun Profile2Screen(onLogout: () -> Unit) {
    val options = listOf(
        ProfileOption(Icons.Default.Person, "Edit Profile"),
        ProfileOption(Icons.Default.Lock, "Change Password"),
        ProfileOption(Icons.Default.Notifications, "Notifications"),
        ProfileOption(Icons.Default.Security, "Security"),
        ProfileOption(Icons.Default.Language, "Language")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9)) // Slight off-white background
            .padding(horizontal = 24.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { /* Handle back */ },
                modifier = Modifier.background(Color.White, CircleShape)
            ) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back", modifier = Modifier.size(18.dp))
            }
            Text(
                text = "My Profile",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(48.dp)) // To balance the back button
        }

        // Profile Image & Info
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center) {
                // Purple background circle
                Surface(
                    modifier = Modifier.size(120.dp),
                    shape = CircleShape,
                    color = Color(0xFF9173FF)
                ) {}
                // User Image
                Image(
                    painter = painterResource(R.drawable.user), // Replace with your asset
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Ramesh", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Text(text = "s@gmail.com", color = Color.Gray, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "General", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))

        // List of options
        LazyColumn {
            items(options) { option ->
                ProfileListItem(option)
            }
        }
    }
}

@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    val options = listOf(
        ProfileOption(Icons.Default.Person, "Edit Profile"),
        ProfileOption(Icons.Default.Lock, "Change Password"),
        ProfileOption(Icons.Default.Notifications, "Notifications"),
        ProfileOption(Icons.Default.Security, "Security"),
        ProfileOption(Icons.Default.Language, "Language")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Pure white for consistency
            .padding(horizontal = 24.dp)
    ) {
        // Top Bar - Mathematically Centered
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                IconButton(
                    onClick = { /* Handle back */ },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .background(Color(0xFFF7F7F7), CircleShape) // Light gray for subtle look
                ) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        modifier = Modifier.size(18.dp)
                    )
                }

                Text(
                    text = "My Profile",
                    modifier = Modifier.align(Alignment.Center),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black, // Match Auth Screen Boldness
                        color = Color(0xFF1A2130)
                    )
                )
            }
        }

        // Profile Image Section with "Edit" Symbol
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    // Border Circle
                    Surface(
                        modifier = Modifier.size(120.dp),
                        shape = CircleShape,
                        color = Color.Transparent,
                        border = BorderStroke(2.dp, Color(0xFFFFD500)) // Yellow Accent Border
                    ) {
                        Image(
                            painter = painterResource(R.drawable.user),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .padding(6.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // The Edit Symbol - Floating Action Style
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .background(Color(0xFF1A2130), CircleShape) // Navy Background
                            .clip(CircleShape)
                            .clickable { /* Edit Image */ }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color(0xFFFFD500), // Yellow Pencil
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Ramesh",
                    fontWeight = FontWeight.Black,
                    fontSize = 24.sp,
                    color = Color(0xFF1A2130)
                )
                Text(text = "s@gmail.com", color = Color.Gray, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            Text(
                text = "General",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1A2130),
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        // List of options (Preserving your items logic)
        items(options) { option ->
            ProfileListItem(option)
        }

        // Add a Logout Button at the bottom to match Auth Screen style
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7F7F7)), // Light Gray
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Logout", color = Color.Red, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun AddProfilePhotoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Text
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Add a Profile Photo",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Add an image to customize your account and discover a more enhanced experience.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                lineHeight = 22.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Profile Image with Edit Button
        Box(
            modifier = Modifier.size(240.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            // Profile Image (Circular)
            Image(
                painter = painterResource(id = R.drawable.user), // Your image resource
                contentDescription = "Profile Photo",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color(0xFF9173FF)), // Purple background color
                contentScale = ContentScale.Crop
            )

            // Edit Icon (Pencil)
            Surface(
                modifier = Modifier
                    .size(56.dp)
                    .offset(x = (-8).dp, y = (-8).dp),
                shape = CircleShape,
                color = Color(0xFF9173FF), // Matches the purple theme
                border = BorderStroke(4.dp, Color.White)
            ) {
                IconButton(onClick = { /* Open Gallery/Camera */ }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Photo",
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1.5f))

        // Continue Button
        Button(
            onClick = { /* Navigate */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9173FF))
        ) {
            Text(
                text = "Continue",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }
}

@Composable
fun ProfileListItem(option: ProfileOption) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = option.icon,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = option.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(onLogout = {})
    //ProfileListItem(ProfileOption(Icons.Default.AccountCircle, "Account"))
}