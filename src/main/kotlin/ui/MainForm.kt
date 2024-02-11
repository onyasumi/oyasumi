package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.Dropdown
import components.DropdownItem
import model.HTTPRequestType
import model.key
import model.value

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

            var url by remember { mutableStateOf(vm.url) }
            TextField(value = url,
                onValueChange = {
                    vm.url = it
                    url = it
                },
                label = { Text("URL") },
                singleLine = true,
                modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 20.dp).fillMaxWidth()
            )

            var body by remember { mutableStateOf(vm.body) }
            TextField(value = body,
                onValueChange = {
                    vm.body = it
                    body = it
                },
                label = { Text("Body") },
                singleLine = false,
                minLines = 5,
                modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 20.dp).fillMaxWidth()
            )

            val headerVersionVM by vm.headersVersionLock.collectAsState()
            var headersVersionLock by remember { mutableIntStateOf(0) }

            val httpHeaders = @Composable {

                for(header in vm.headers) {

                    Row(Modifier.padding(20.dp, 0.dp, 20.dp, 20.dp)) {

                        var key by remember { mutableStateOf(header.key) }
                        TextField(value = key,
                            onValueChange = {
                                header.key = it
                                key = it
                            },
                            label = { Text("Header Key") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(Modifier.width(20.dp))

                        var value by remember { mutableStateOf(header.value) }
                        TextField(value = value,
                            onValueChange = {
                                header.value = it
                                value = it
                            },
                            label = { Text("Header Value") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(Modifier.width(20.dp))

                        Button(onClick = {vm.deleteHeader(header)},
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
                            modifier = Modifier.height(55.dp)) {
                            Text("Remove")
                        }

                    }

                }


            }

            if(headersVersionLock != headerVersionVM) {
                httpHeaders()
                headersVersionLock = headerVersionVM
            } else {
                httpHeaders()
                headersVersionLock = headerVersionVM
            }

            Button(onClick = vm.addHeader, modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 20.dp).fillMaxWidth()) {
                Text("Add Custom Header")
            }

        }
    }

}