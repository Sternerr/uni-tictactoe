package com.noah.tictactoe.ui.comonents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlayerNameComponent(
    mark: String,
    playerName: String
) {
    Row(
        modifier = Modifier
            .border(BorderStroke(3.dp, Color(0xFF292934)), RoundedCornerShape(5.dp))
            .padding(vertical = 8.dp, horizontal = 16.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = mark,
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = playerName,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun PlayerNameComponentPrev() {
    PlayerNameComponent("X","Test")
}
