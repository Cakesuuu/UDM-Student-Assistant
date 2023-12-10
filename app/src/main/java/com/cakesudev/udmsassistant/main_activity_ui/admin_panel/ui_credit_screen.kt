package com.cakesudev.udmsassistant.main_activity_ui.admin_panel

import android.graphics.drawable.Drawable
import android.media.Image
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cakesudev.udmsassistant.R
import com.cakesudev.udmsassistant.database.DatabaseAdminData
import com.cakesudev.udmsassistant.database.DatabaseAdminEvent
import com.cakesudev.udmsassistant.database.DatabaseAdminState
import com.cakesudev.udmsassistant.database.DatabaseDeadlineEvent
import com.cakesudev.udmsassistant.database.DatabaseNoteEvent
import com.cakesudev.udmsassistant.input_activity_ui.UI_Title
import com.cakesudev.udmsassistant.input_activity_ui.Variable_DataType
import com.cakesudev.udmsassistant.input_activity_ui.isNoteBodyFilled
import com.cakesudev.udmsassistant.ui.theme.FontAlataFamily
import com.cakesudev.udmsassistant.ui.theme.theme_grey_dark
import com.cakesudev.udmsassistant.ui.theme.theme_grey_darker
import com.cakesudev.udmsassistant.ui.theme.udm_green_light
import com.cakesudev.udmsassistant.ui.theme.white

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UI_Credit_Screen(
    navController: NavController,
    adminState: DatabaseAdminState,
    adminEvent: (DatabaseAdminEvent) -> Unit
){
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Component_Admin_Login(adminState = adminState, adminEvent = adminEvent, navController = navController)

        Text(
            text = "Capstone Team Members",
            color = white,
            fontFamily = FontAlataFamily,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )

        FlowRow(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 10.dp)
                .fillMaxWidth(1f)
                .border(1.dp, udm_green_light, RoundedCornerShape(10.dp)),
            horizontalArrangement = Arrangement.SpaceEvenly,
            maxItemsInEachRow = 3
        ) {

            Component_Member_Card(
                memberName = "Charisse Villanueva",
                memberImage = R.drawable.cv,
                memberTitle = "Team Leader",
                modifier = Modifier
                    .weight(1f)
            )

            Component_Member_Card(
                memberName = "John Cydrex H. Vitug",
                memberImage = R.drawable.jchv,
                memberTitle = "Programmer",
                modifier = Modifier
                    .weight(1f)
            )

            Component_Member_Card(
                memberName = "Marie Louise Fuentiblanca",
                memberImage = R.drawable.mlf,
                memberTitle = "Secretary",
                modifier = Modifier
                    .weight(1f)
            )

            Component_Member_Card(
                memberName = "Zhailane Murawski",
                memberImage = R.drawable.zm,
                memberTitle = "Team Member",
                modifier = Modifier
                    .weight(1f)
            )

            Component_Member_Card(
                memberName = "Jhon Rafael Piñero",
                memberImage = R.drawable.rp,
                memberTitle = "Team Member",
                modifier = Modifier
                    .weight(1f)
            )

            Component_Member_Card(
                memberName = "Kim Carlo Rosita",
                memberImage = R.drawable.kcr,
                memberTitle = "Team Member",
                modifier = Modifier
                    .weight(1f)
            )

            Component_Member_Card(
                memberName = "Jonald Huab",
                memberImage = R.drawable.jh,
                memberTitle = "Team Member",
                modifier = Modifier
                    .weight(1f)
            )
        }

    }
    
}

var UserTitle = "PLACEHOLDER"

@Composable
fun Component_Member_Card(
    memberName: String,
    memberImage: Int,
    memberTitle: String,
    modifier: Modifier
){
    Column(
        modifier
    ){
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                modifier = Modifier
                    .height(130.dp)
                    .width(130.dp),
                painter = painterResource(memberImage),
                contentDescription = "Nothing."
            )

            Text(
                text = memberName,
                color = white,
                fontFamily = FontAlataFamily,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = memberTitle,
                color = white,
                fontFamily = FontAlataFamily,
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
        
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Component_Admin_Login(
    adminState: DatabaseAdminState,
    adminEvent: (DatabaseAdminEvent) -> Unit,
    navController: NavController
){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .border(1.dp, udm_green_light, RoundedCornerShape(10.dp))
            .padding(20.dp)
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Admin Login",
            color = white,
            fontFamily = FontAlataFamily,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )

        var UserName by remember { mutableStateOf("") }
        var Password by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {

            Text(
                text = "Username",
                color = white,
                fontFamily = FontAlataFamily
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = theme_grey_darker)
                    .padding(top = 5.dp),
                value = UserName,
                onValueChange = {
                    UserName = it
                },
                placeholder = { Text("Username") },
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

            Text(
                text = "Password",
                color = white,
                fontFamily = FontAlataFamily
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = theme_grey_darker)
                    .padding(top = 5.dp),
                value = Password,
                onValueChange = {
                    Password = it
                },
                placeholder = { Text("Password") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = white,
                    unfocusedBorderColor = udm_green_light
                ),
                visualTransformation = PasswordVisualTransformation()
            )

        }

        val context = LocalContext.current

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = theme_grey_dark)
                .border(1.dp, udm_green_light, RoundedCornerShape(10.dp))
                .background(udm_green_light),
            onClick = {
                if(UserName == "Admin" && Password == "Admin"){
                    UserTitle = "OSA Department Admin"
                    navController.navigate("Input_UI_Screen")
                    UI_Title = "Set New Announcement"
                    Variable_DataType = "Admin"
                    adminEvent(DatabaseAdminEvent.SetDataType(Variable_DataType))
                } else {
                    Toast.makeText(context, "Incorrect login details.", Toast.LENGTH_SHORT).show()
                }
            }
        ){
            Text(
                text = "LOGIN",
                color = white,
                fontFamily = FontAlataFamily
            )
        }

    }

}