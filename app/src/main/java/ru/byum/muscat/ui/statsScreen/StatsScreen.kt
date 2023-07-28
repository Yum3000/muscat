package ru.byum.muscat.ui.statsScreen


import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.byum.muscat.data.AddContactDialog
import ru.byum.muscat.data.ContactEvent
import ru.byum.muscat.data.ContactState
import ru.byum.muscat.data.SortType
import ru.byum.muscat.data.local.di.DatabaseModule
import ru.byum.muscat.data.local.di.DatabaseModule_ProvideAppDatabaseFactory
import java.time.format.TextStyle


@ExperimentalMaterial3Api
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatsScreen (modifier: Modifier = Modifier,
                 navController: NavController,
                 state: ContactState,
                onEvent: (ContactEvent) -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Statistic Screen" ,
            color = Color.Green,
            style = androidx.compose.ui.text.TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.padding(24.dp)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(modifier = Modifier.width(96.dp), onClick = { navController.popBackStack()}) {
            Icon(Icons.Filled.ArrowBack, modifier = Modifier.size(128.dp), contentDescription = "Back")
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            onEvent(ContactEvent.ShowDialog)
        })
        {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add contact")
        }
    })
    { padding ->
        if (state.isAddingContact){

            AddContactDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                    verticalAlignment =Alignment.CenterVertically
                ) {

                    SortType.values().forEach { sortType ->
                    Row(
                        modifier = Modifier.clickable { onEvent(ContactEvent.SortContacts(sortType)) },
                        verticalAlignment = CenterVertically
                    ){
                        RadioButton(
                            selected = state.sortType == sortType,
                            onClick = { onEvent(ContactEvent.SortContacts(sortType)) }
                        )
                        Text(
                            text = sortType.name
                        )
                    }

                    }
                }

            }
            items(state.contacts) { contact ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Column(modifier = Modifier.weight(1f))
                    {
                        Text(
                            text = "${contact.firstName} ${contact.lastName}",
                            fontSize = 20.sp
                        )

                        Text(text = contact.phoneNumber, fontSize = 12.sp)

                    }
                    IconButton(onClick = {
                        onEvent(ContactEvent.DeleteContact(contact))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete contact")
                    }
                }
            }
        }
    }
}