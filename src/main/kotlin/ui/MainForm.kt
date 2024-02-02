package ui

import Dropdown
import DropdownItem
import HTTPRequestType
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainForm(vm: MainFormVM) {

    Surface {
        Column {

            Dropdown(
                items = listOf(
                    DropdownItem(HTTPRequestType.GET),
                    DropdownItem(HTTPRequestType.POST),
                    DropdownItem(HTTPRequestType.PUT),
                    DropdownItem(HTTPRequestType.PATCH),
                    DropdownItem(HTTPRequestType.DELETE)
                ),
                selectedValue = vm.requestMethod,
                label = "HTTP Method"
            )

            val url by vm.url.collectAsState()
            TextField(value = url,
                onValueChange = {
                    vm.url.value = it
                },
                label = { Text("URL") },
                singleLine = true,
                modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 20.dp).fillMaxWidth()
            )

            val body by vm.body.collectAsState()
            TextField(value = body,
                onValueChange = {
                    vm.body.value = it
                },
                label = { Text("Body") },
                singleLine = false,
                minLines = 5,
                modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 20.dp).fillMaxWidth()
            )

        }
    }

}