package com.andreisingeleytsev.ingetinapp.ui.screens.onboarding_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andreisingeleytsev.ingetinapp.R
import com.andreisingeleytsev.ingetinapp.ui.theme.SecondaryColor
import com.andreisingeleytsev.ingetinapp.ui.utils.Fonts

@Composable
fun OnboardingScreen(onEndOnboard: ()->Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
        Image(
            painter = painterResource(id = R.drawable.onboard_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()) {
            Text(
                text = stringResource(R.string.onboard_txt_1), style = TextStyle(
                    color = Color.White,
                    fontSize = 28.sp,
                    fontFamily = Fonts.font,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ), modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.onboard_txt_2), style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = Fonts.font,
                    textAlign = TextAlign.Center
                ), modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(48.dp))
            Button(onClick = {
                onEndOnboard.invoke()
            }, colors = ButtonDefaults.buttonColors(
                containerColor = SecondaryColor
            ), modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.continue_txt), style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = Fonts.font
                    )
                )
            }
        }
    }
}