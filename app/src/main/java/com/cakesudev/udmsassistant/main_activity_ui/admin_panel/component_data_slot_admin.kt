package com.cakesudev.udmsassistant.main_activity_ui.admin_panel

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakesudev.udmsassistant.R
import com.cakesudev.udmsassistant.database.DatabaseAdminEvent
import com.cakesudev.udmsassistant.database.DatabaseAdminState
import com.cakesudev.udmsassistant.database.DatabaseNoteEvent
import com.cakesudev.udmsassistant.database.DatabaseNoteState
import com.cakesudev.udmsassistant.ui.theme.FontAlataFamily
import com.cakesudev.udmsassistant.ui.theme.udm_green_light
import com.cakesudev.udmsassistant.ui.theme.white

@Composable
fun Component_Data_Slot_Admin(
    adminState: DatabaseAdminState,
    adminEvent: (DatabaseAdminEvent) -> Unit
) {

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(adminState.data) { data ->

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                /*IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Notice",
                        tint = colorResource(id = R.color.white)
                    )
                }*/

                val context = LocalContext.current
                var TTS: TextToSpeech? by remember { mutableStateOf(null) }

                DisposableEffect(Unit) {
                    TTS = TextToSpeech(context) { status ->
                        if (status != TextToSpeech.ERROR) {
                            // Set additional config if needed
                        }
                    }

                    onDispose {
                        // Release TextToSpeech when the composable is disposed
                        TTS?.shutdown()
                    }
                }

                IconButton(
                    onClick = {
                        TTS?.speak(data.noticeBody, TextToSpeech.QUEUE_FLUSH, null, null)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Delete Note",
                        tint = colorResource(id = R.color.white)
                    )
                }

                IconButton(
                    onClick = { adminEvent(DatabaseAdminEvent.DeleteNotice(data)) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Note",
                        tint = colorResource(id = R.color.white)
                    )
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 5.dp, top = 0.dp)
                    .border(1.dp, udm_green_light, RoundedCornerShape(10.dp))
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = data.noticeTitle,
                    fontSize = 20.sp,
                    color = white,
                    fontFamily = FontAlataFamily,
                    textAlign = TextAlign.Center
                )

                if(data.noticeImage != 0){
                    Image(
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                            .padding(20.dp),
                        painter = painterResource(data.noticeImage),
                        contentDescription = null
                        )
                }

                Text(
                    text = data.noticeBody,
                    fontSize = 15.sp,
                    color = white,
                    fontFamily = FontAlataFamily,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Posted by: ${data.adminName}",
                    fontSize = 10.sp,
                    color = white,
                    fontFamily = FontAlataFamily,
                    textAlign = TextAlign.Center
                )

            }

        }

        item { Spacer(modifier = Modifier.padding(40.dp)) }

    }

}