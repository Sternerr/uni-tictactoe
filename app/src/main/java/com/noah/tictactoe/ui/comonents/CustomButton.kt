package com.noah.tictactoe.ui.comonents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),

        shape = RoundedCornerShape(10.dp),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = "Join Lobby",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}