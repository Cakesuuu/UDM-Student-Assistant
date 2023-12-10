package com.cakesudev.udmsassistant

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.print.PrintAttributes.Margins
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import com.cakesudev.udmsassistant.ui.theme.UDMSAssistantTheme
import com.cakesudev.udmsassistant.ui.theme.theme_grey_dark
import kotlinx.coroutines.delay

class SplashArt : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fun launch() {
            val fire = Intent(this, MainActivity::class.java)

            startActivity(fire)
            finish()
        }

        if (Build.VERSION.SDK_INT < 31){
            setContent {
                UDMSAssistantTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = theme_grey_dark
                    ) {
                        SetSplashImage()

                        Handler(Looper.getMainLooper()).postDelayed({
                            launch()}, 1000)

                    }
                }
            }
        } else {

            launch()

        }

    }
}

@Preview
@Composable
fun SetSplashImage(

) {
    Image(
        painter = painterResource(id = R.drawable.udmsa_icon),
        contentDescription = "App Image" ,
        modifier = Modifier
            .size(100.dp)
            .padding(150.dp)
    )
}