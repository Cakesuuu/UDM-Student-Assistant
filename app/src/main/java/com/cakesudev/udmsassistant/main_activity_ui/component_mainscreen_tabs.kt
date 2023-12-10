import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.cakesudev.udmsassistant.database.DatabaseAdminEvent
import com.cakesudev.udmsassistant.database.DatabaseAdminState
import com.cakesudev.udmsassistant.database.DatabaseDeadlineEvent
import com.cakesudev.udmsassistant.database.DatabaseDeadlineState
import com.cakesudev.udmsassistant.database.DatabaseNoteEvent
import com.cakesudev.udmsassistant.database.DatabaseNoteState
import com.cakesudev.udmsassistant.database.DatabaseScheduleEvent
import com.cakesudev.udmsassistant.database.DatabaseScheduleState
import com.cakesudev.udmsassistant.main_activity_ui.DeadlineScreen
import com.cakesudev.udmsassistant.main_activity_ui.NotesScreen
import com.cakesudev.udmsassistant.main_activity_ui.OfficialScreen
import com.cakesudev.udmsassistant.main_activity_ui.ScheduleScreen
import com.cakesudev.udmsassistant.ui.theme.FontAlataFamily
import com.cakesudev.udmsassistant.ui.theme.theme_grey
import com.cakesudev.udmsassistant.ui.theme.theme_grey_dark
import com.cakesudev.udmsassistant.ui.theme.theme_grey_darker
import com.cakesudev.udmsassistant.ui.theme.udm_green_light
import com.cakesudev.udmsassistant.ui.theme.white
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

class tabItems(
    val title : String
)

val tabItemContent = listOf(
    tabItems(
        title = "Schedules"
    ),
    tabItems(
        title = "Deadline"
    ),
    tabItems(
        title = "Notes"
    ),
    tabItems(
        title = "Official"
    )
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun StartTabLayout(
    scheduleState: DatabaseScheduleState,
    scheduleEvent: (DatabaseScheduleEvent) -> Unit,
    noteState: DatabaseNoteState,
    noteEvent: (DatabaseNoteEvent) -> Unit,
    deadlineState: DatabaseDeadlineState,
    deadlineEvent: (DatabaseDeadlineEvent) -> Unit,
    adminState: DatabaseAdminState,
    adminEvent: (DatabaseAdminEvent) -> Unit
){
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState (0)

    LaunchedEffect(selectedTabIndex){
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress){
        if(!pagerState.isScrollInProgress){
            selectedTabIndex = pagerState.currentPage
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(theme_grey_dark)
    ){
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            divider = {},
            indicator = {},
            containerColor = theme_grey_darker
        ) {
            tabItemContent.forEachIndexed() { index, item ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = item.title,
                            color = if(index == selectedTabIndex){
                                udm_green_light
                            } else {
                                white
                            },
                            fontFamily = FontAlataFamily
                        )
                    },
                    modifier = Modifier
                        .background(
                            if(index == selectedTabIndex){
                                theme_grey
                            } else {
                                theme_grey_dark
                            }
                        )
                )
            }

        }

        HorizontalPager(
            state = pagerState,
            count = 5,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            userScrollEnabled = true
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ){
                when (selectedTabIndex){
                    0 -> ScheduleScreen(scheduleState = scheduleState, scheduleEvent = scheduleEvent)
                    1 -> DeadlineScreen(deadlineState = deadlineState, deadlineEvent = deadlineEvent)
                    2 -> NotesScreen(noteState = noteState, noteEvent = noteEvent)
                    3 -> OfficialScreen(adminState = adminState, adminEvent = adminEvent)
                }

            }

        }

    }

}