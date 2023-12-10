package com.cakesudev.udmsassistant.input_activity_ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cakesudev.udmsassistant.R
import com.cakesudev.udmsassistant.database.DatabaseAdminEvent
import com.cakesudev.udmsassistant.database.DatabaseAdminState
import com.cakesudev.udmsassistant.ui.theme.FontAlataFamily
import com.cakesudev.udmsassistant.ui.theme.theme_grey_dark
import com.cakesudev.udmsassistant.ui.theme.theme_grey_darker
import com.cakesudev.udmsassistant.ui.theme.udm_green_light
import com.cakesudev.udmsassistant.ui.theme.white

var isNoticeTitleFilled = false
var isNoticeBodyFilled = false

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UI_AdminInput(
    navController: NavController,
    adminState: DatabaseAdminState,
    adminEvent: (DatabaseAdminEvent) -> Unit
){
    val imeState = rememberIMEState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value){
        if (imeState.value){
            scrollState.scrollTo(scrollState.maxValue)
        }
    }

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Notice Name
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Text(
                text = "Title",
                color = white,
                fontFamily = FontAlataFamily
            )

            var Notice_Title by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = theme_grey_dark)
                    .padding(top = 5.dp),
                value = Notice_Title,
                onValueChange = {
                    Notice_Title = it
                    adminEvent(DatabaseAdminEvent.SetNoticeTitle(it))
                    isNoticeTitleFilled = true
                },
                placeholder = { Text("Enter title here.") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = white,
                    unfocusedBorderColor = udm_green_light
                )
            )

        }

        //Notice Body
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {

            Text(
                text = "Announcement",
                color = white,
                fontFamily = FontAlataFamily
            )

            var Notice_Body by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = theme_grey_darker)
                    .padding(top = 5.dp),
                value = Notice_Body,
                onValueChange = {
                    Notice_Body = it
                    adminEvent(DatabaseAdminEvent.SetNoticeBody(it))
                    isNoticeBodyFilled = true
                },
                placeholder = { Text("Enter announcement here.") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = white,
                    unfocusedBorderColor = udm_green_light
                )
            )

        }

        //Announcement Poster
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {

            Text(
                text = "Post as:",
                color = white,
                fontFamily = FontAlataFamily
            )

            var Poster by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = theme_grey_darker)
                    .padding(top = 5.dp),
                value = Poster,
                onValueChange = {
                    Poster = it
                    when (Poster){
                        "CET" -> {
                            adminEvent(DatabaseAdminEvent.SetNoticeImage(R.drawable.cet_logo))
                        }
                        "OSA" -> {
                            adminEvent(DatabaseAdminEvent.SetNoticeImage(R.drawable.udm_logo))
                        }
                        "UDM" -> {
                            adminEvent(DatabaseAdminEvent.SetNoticeImage(R.drawable.udm_logo))
                        }
                        else -> {
                            adminEvent(DatabaseAdminEvent.SetNoticeImage(R.drawable.udmsa_icon))
                        }
                    }
                    adminEvent(DatabaseAdminEvent.SetAdminName(it))
                    isNoticeBodyFilled = true
                },
                placeholder = { Text("Enter department name or initials here.") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = white,
                    unfocusedBorderColor = udm_green_light
                )
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {
            val context = LocalContext.current

            Component_SubmitButton(
                onClick = {

                    if (isNoticeTitleFilled == false || isNoticeBodyFilled == false) {
                        Toast.makeText(
                            context,
                            "Please enter a title and an announcement.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Component_SubmitButton
                    }

                    adminEvent(DatabaseAdminEvent.SaveData)
                    Variable_DataType = ""
                    isNoticeTitleFilled = false
                    isNoticeBodyFilled = false
                    navController.popBackStack()

                }

            )
        }

    }
}