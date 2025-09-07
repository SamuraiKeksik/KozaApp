package com.example.kozaapp.mainApp.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kozaapp.R
import com.example.kozaapp.mainApp.ui.screens.MainScreenEnum

@Composable
fun AnimalInfoCard(
    navItem: AnimalCardNavItem,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit
) {
    Card(
        modifier = modifier
            .width(175.dp)
            .height(200.dp),
        border = BorderStroke(1.dp, color = Color.Black)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                painter = painterResource(navItem.backgroundRes),
                contentDescription = null,
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            //Invisible button
            Button(
                onClick = { onButtonClick() },
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .alpha(0f),
                shape = RoundedCornerShape(0.dp),
            ) { }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(navItem.labelRes),
                    modifier = Modifier.padding(15.dp),
                    style = MaterialTheme.typography.headlineSmall,

                    )
                Text(
                    text = stringResource(R.string.quantity_label, 0),
                    modifier = Modifier.padding(15.dp),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }
    }
}

@Preview
@Composable
fun AnimalInfoCardPreview() {
    AnimalInfoCard(
        navItem = AnimalCardNavItem.GoatsScreen,
        modifier = Modifier,
        onButtonClick = {}
    )
}
