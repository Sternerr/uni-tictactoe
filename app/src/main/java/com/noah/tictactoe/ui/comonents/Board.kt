package com.noah.tictactoe.ui.comonents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Board() {
    Column(
        modifier = Modifier
            .height(350.dp)
            .width(350.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF292934))
    ) {
        for (y in 0..2) {
            Spacer(Modifier.weight(1f))
            Row {
                for (x in 0..2) {
                    Spacer(Modifier.weight(1f))
                    Cell("O") {}
                }
                Spacer(Modifier.weight(1f))
            }
        }

        Spacer(Modifier.weight(1f))
    }
}