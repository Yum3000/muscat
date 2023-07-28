package ru.byum.muscat.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun AddContactDialog(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { onEvent(ContactEvent.HideDialog) },
        modifier = Modifier,
    )
    {
        Text(text = "Add contact")


        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TextField(
                value = state.firstName,
                onValueChange = {
                    onEvent(ContactEvent.setFirstName(it))
                },
                placeholder = {
                    Text(text = "First name")
                })

            TextField(
                value = state.lastName,
                onValueChange = {
                    onEvent(ContactEvent.setLastName(it))
                },
                placeholder = {
                    Text(text = "Last name")
                })

            TextField(
                value = state.phoneNumber,
                onValueChange = {
                    onEvent(ContactEvent.setPhoneNumber(it))
                },
                placeholder = {
                    Text(text = "Phone number")
                })

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){
                Button(onClick = {
                    onEvent(ContactEvent.SaveContact)
                }) {
                    Text(text = "Save")
                }
            }


        }

    }


}