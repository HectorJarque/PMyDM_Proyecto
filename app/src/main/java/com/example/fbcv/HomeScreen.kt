package com.example.fbcv

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onProfile: () -> Unit,
    onClubs: () -> Unit,
    onCompetitions: () -> Unit,
    onLive: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = CircleShape,
                color = Color.White,
                border = BorderStroke(2.dp, FbcvOrange),
                modifier = Modifier.size(64.dp).clickable { onProfile() }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Add, contentDescription = "Perfil", tint = FbcvBlue)
                }
            }
        }

        HomeBlock("CLUBS", FbcvGrey, FbcvBlue, Modifier.weight(1f), onClubs)
        HomeBlock("COMPETICIONES", FbcvOrange, Color.White, Modifier.weight(1f), onCompetitions)
        HomeBlock("PARTIDOS EN DIRECTO", FbcvBlue, Color.White, Modifier.weight(1f), onLive)
    }
}

@Composable
private fun HomeBlock(
    text: String,
    background: Color,
    textColor: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth().background(background).clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = textColor, fontWeight = FontWeight.Bold)
    }
}
