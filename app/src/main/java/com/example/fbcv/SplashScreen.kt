package com.example.fbcv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000.milliseconds)
        onFinished()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(FbcvBlue),
        contentAlignment = Alignment.Center
    ) {
        Text("FBCV", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold)
    }
}
