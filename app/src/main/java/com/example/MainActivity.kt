package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.ui.StudyMartApp
import com.example.ui.theme.StudyMartTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      StudyMartTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          StudyMartApp(modifier = Modifier.padding(innerPadding))
        }
      }
    }
  }
}
