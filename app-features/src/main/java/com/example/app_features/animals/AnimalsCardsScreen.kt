package com.example.app_features.animals

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_features.R
import com.example.app_features.theme.AppTheme

//object AnimalCardsDestination : NavigationDestination{
//    override val route = "AnimalCardsScreen"
//    @StringRes
//    override val titleRes = R.string.empty_string
//    override val showBottomBar = true
//}

@Composable
fun AnimalsCardsScreen(
    modifier: Modifier = Modifier,
    navigateToGoatsScreen: () -> Unit,
    navigateToCowsScreen: () -> Unit,
    navigateToChickenScreen: () -> Unit,
){
    Column(
        modifier = modifier.padding(
        start = 30.dp,
        end = 30.dp,
    ),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = stringResource(R.string.main_screen_label),
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
        )
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                AnimalInfoCard(
                    labelRes = R.string.goats_label,
                    backgroundRes = R.drawable.goats_background,
                    modifier = Modifier.height(250.dp),
                    onButtonClick = { navigateToGoatsScreen() }
                )
                AnimalInfoCard(
                    labelRes = R.string.cows_label,
                    backgroundRes = R.drawable.cows_background,
                    modifier = Modifier.height(250.dp),
                    onButtonClick = { navigateToCowsScreen() }
                )
            }
        }

    }
}

@Composable
fun AnimalInfoCard(
    @StringRes labelRes: Int,
    @DrawableRes backgroundRes: Int,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit
) {
    Card(
        modifier = modifier
            .width(175.dp)
            .height(200.dp),
        border = BorderStroke(1.dp, color = Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                painter = painterResource(backgroundRes),
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
                    text = stringResource(labelRes),
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
        labelRes = R.string.goats_label,
        backgroundRes = R.drawable.goats_background,
        modifier = Modifier,
        onButtonClick = {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AnimalCardsScreenPreview(){
    AppTheme{
        Surface(modifier = Modifier.fillMaxSize()) {
            //AnimalsScreen()
        }
    }
}