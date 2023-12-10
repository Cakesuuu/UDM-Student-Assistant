package com.cakesudev.udmsassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.cakesudev.udmsassistant.database.DatabaseAdminViewModel
import com.cakesudev.udmsassistant.database.DatabaseDeadlineViewModel
import com.cakesudev.udmsassistant.database.DatabaseNoteViewModel
import com.cakesudev.udmsassistant.database.DatabaseScheduleViewModel
import com.cakesudev.udmsassistant.database.MainDatabase
import com.cakesudev.udmsassistant.input_activity_ui.UI_Input
import com.cakesudev.udmsassistant.main_activity_ui.MainScreen
import com.cakesudev.udmsassistant.main_activity_ui.admin_panel.UI_Credit_Screen
import com.cakesudev.udmsassistant.ui.theme.UDMSAssistantTheme
import com.cakesudev.udmsassistant.ui.theme.theme_grey_dark
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            MainDatabase::class.java,
            "DataDatabase.db"
        ).build()
    }
    private val scheduleViewModel by viewModels<DatabaseScheduleViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DatabaseScheduleViewModel(db.ScheduleDatabaseDao) as T
                }
            }
        }
    )

    private val deadlineViewModel by viewModels<DatabaseDeadlineViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DatabaseDeadlineViewModel(db.DeadlineDatabaseDao) as T
                }
            }
        }
    )

    private val noteViewModel by viewModels<DatabaseNoteViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DatabaseNoteViewModel(db.NoteDatabaseDao) as T
                }
            }
        }
    )

    private val adminViewModel by viewModels<DatabaseAdminViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DatabaseAdminViewModel(db.AdminDatabaseDao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UDMSAssistantTheme {

                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = theme_grey_dark
                )

                val navControl = rememberNavController()

                NavHost(navController = navControl, startDestination = "Main_Menu_Screen") {

                    composable("Main_Menu_Screen") {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = theme_grey_dark
                        ) {
                            val schedule_state by scheduleViewModel.state.collectAsState()
                            val deadline_state by deadlineViewModel.state.collectAsState()
                            val note_state by noteViewModel.state.collectAsState()
                            val admin_state by adminViewModel.state.collectAsState()

                            MainScreen(
                                navController = navControl,
                                scheduleState = schedule_state,
                                scheduleEvent = scheduleViewModel::onEvent,
                                noteState = note_state,
                                noteEvent = noteViewModel::onEvent,
                                deadlineState = deadline_state,
                                deadlineEvent = deadlineViewModel::onEvent,
                                adminState = admin_state,
                                adminEvent = adminViewModel::onEvent
                            )

                        }
                    }

                    composable("Input_UI_Screen", ){
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = theme_grey_dark
                        ) {

                            val schedule_state by scheduleViewModel.state.collectAsState()
                            val deadline_state by deadlineViewModel.state.collectAsState()
                            val note_state by noteViewModel.state.collectAsState()
                            val admin_state by adminViewModel.state.collectAsState()

                            UI_Input(
                                navController = navControl,
                                scheduleState = schedule_state,
                                scheduleEvent = scheduleViewModel::onEvent,
                                noteState = note_state,
                                noteEvent = noteViewModel::onEvent,
                                deadlineState = deadline_state,
                                deadlineEvent = deadlineViewModel::onEvent,
                                adminState = admin_state,
                                adminEvent = adminViewModel::onEvent
                            )

                        }
                    }

                    composable("UI_Credit_Screen", ){
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = theme_grey_dark
                        ) {
                            val admin_state by adminViewModel.state.collectAsState()

                            UI_Credit_Screen(
                                navController = navControl,
                                adminState = admin_state,
                                adminEvent = adminViewModel::onEvent
                            )

                        }
                    }

                }

            }
        }
    }
}