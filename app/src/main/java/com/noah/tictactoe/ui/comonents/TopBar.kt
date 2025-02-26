package com.noah.tictactoe.ui.comonents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TopBar(text: String) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp)
    ){
        Text(
            text = text,
            style = TextStyle(
                fontSize = 26.sp,
                color = Color.White
            )
        )
    }
}

@Preview
@Composable
private fun TopBarPrev() {
    Block {
        TopBar("test")
    }
}