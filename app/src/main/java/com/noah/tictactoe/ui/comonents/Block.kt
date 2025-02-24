package com.noah.tictactoe.ui.comonents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Block(
    padding: Dp = 30.dp,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = padding)
    ) {
        content()
    }
}
