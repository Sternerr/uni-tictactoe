package com.noah.tictactoe.ui.comonents

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Cell(
    mark: String,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonColors(
            contentColor = Color.White,
            containerColor = Color(0xFF202028),
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified
        ),

        shape = RoundedCornerShape(10.dp),

        modifier = Modifier
            .width(99.dp)
            .height(103.dp),

        onClick = onClick
    ){
        Text(
            text = mark,
            style = TextStyle(
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold
            )

        )
    }
}
