package com.visiontek.firebasedemo.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.visiontek.firebasedemo.ui.theme.FirebaseDemoTheme
import com.visiontek.firebasedemo.viewmodel.Note
import com.visiontek.firebasedemo.viewmodel.NoteViewModel

@Composable
fun HomeScreen(onLogout: () -> Unit, modifier: Modifier = Modifier) {
    var noteText by remember { mutableStateOf("") }
    var notesList by remember { mutableStateOf(listOf<Note>()) }
    val noteViewModel : NoteViewModel = viewModel()
    LaunchedEffect(Unit) {
        noteViewModel.getNotes {
            notes -> notesList = notes
        }
    }
    LazyColumn( modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp))
    {
        item {
            Column {
                Text(
                    text = "Explore",
                    style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
                )
                Text(
                    text = "Your Personal Cloud Vault",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
        item {
            NoteInputSection(
                value = noteText,
                onValueChange = { noteText = it },
                onAddClick = {
                    if (noteText.isNotBlank()) {
                        //notesList.add() // Add to top
                        noteText = ""
                    }
                }
            )
        }
        item {
            FeaturedQuoteCard()
        }

    }
}

@Composable
fun NoteInputSection(value: String, onValueChange: (String) -> Unit, onAddClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("What's on your mind?") },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFFD500),
                unfocusedBorderColor = Color.LightGray
            )
        )
        Button(
            onClick = onAddClick,
            modifier = Modifier.height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFF1A2130))
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
        }
    }
}

@Composable
fun NoteCard(content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = content,
                modifier = Modifier.weight(1f),
                style = TextStyle(fontSize = 16.sp, color = Color.DarkGray)
            )
            // Optional: Delete Icon
            IconButton(onClick = { /* Handle delete */ }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Gray)
            }
        }
    }
}

@Composable
fun FeaturedQuoteCard() {
    Card (modifier = Modifier
        .fillMaxWidth()
        .height(200.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD500)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(
                text = buildAnnotatedString {
                    append("BE")
                    withStyle (style = SpanStyle(fontWeight = FontWeight.Black)){
                        append("LIEVE")
                    }
                    append("In\n")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                        append("YOU!")
                    }
                    append("RSELF")
                },
                style = TextStyle(
                    fontSize = 44.sp,
                    lineHeight = 40.sp,
                    textAlign = TextAlign.Center,
                    letterSpacing = (-1).sp,
                    color = Color(0xFF1A2130)
                )
            )
        }
    }
}

@Preview(showBackground  =true)
@Composable
fun showHome() {
    FirebaseDemoTheme {
        HomeScreen(onLogout = {})
    }
}