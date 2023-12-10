package com.cakesudev.udmsassistant.input_activity_ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
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
import com.cakesudev.udmsassistant.database.DatabaseNoteEvent
import com.cakesudev.udmsassistant.database.DatabaseNoteState
import com.cakesudev.udmsassistant.ui.theme.FontAlataFamily
import com.cakesudev.udmsassistant.ui.theme.theme_grey_dark
import com.cakesudev.udmsassistant.ui.theme.theme_grey_darker
import com.cakesudev.udmsassistant.ui.theme.udm_green_light
import com.cakesudev.udmsassistant.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UI_NoteInput(
    navController: NavController,
    noteState: DatabaseNoteState,
    noteEvent: (DatabaseNoteEvent) -> Unit,
    modifier: Modifier = Modifier
) {
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

        //Subject Name
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

            var Note_Title by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = theme_grey_dark)
                    .padding(top = 5.dp),
                value = Note_Title,
                onValueChange = {
                    Note_Title = it
                    noteEvent(DatabaseNoteEvent.SetNoteTitle(it))
                    isNoteTitleFilled = true
                },
                placeholder = { Text("Enter title here.") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = white,
                    unfocusedBorderColor = udm_green_light
                )
            )

        }

        //Note Body
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {

            var Note_Body by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = theme_grey_darker)
                    .padding(top = 5.dp),
                value = Note_Body,
                onValueChange = {
                    Note_Body = it
                    noteEvent(DatabaseNoteEvent.SetNoteBody(it))
                    isNoteBodyFilled = true
                },
                placeholder = { Text("Enter text here.") },
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

                    if (isNoteTitleFilled == false || isNoteBodyFilled == false) {
                        Toast.makeText(
                            context,
                            "Please enter a title and a note.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Component_SubmitButton
                    }

                    noteEvent(DatabaseNoteEvent.SaveData)
                    Variable_DataType = ""
                    isNoteTitleFilled = false
                    isNoteBodyFilled = false
                    navController.popBackStack()

                }

            )
        }

    }

}