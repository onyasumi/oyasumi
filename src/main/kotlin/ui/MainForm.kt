package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.Dropdown
import components.DropdownItem
import model.HTTPRequestType
import model.key
import model.value

@Composable
fun MainForm(vm: MainFormVM) {

    Surface {

        Row {

            Column(Modifier.weight(1f)) {

                Column(Modifier.weight(1f).verticalScroll(rememberScrollState())) {

                    Dropdown(
                        items = listOf(
                            DropdownItem(HTTPRequestType.GET),
                            DropdownItem(HTTPRequestType.POST),
                            DropdownItem(HTTPRequestType.PUT),
                            DropdownItem(HTTPRequestType.PATCH),
                            DropdownItem(HTTPRequestType.DELETE)
                        ),
                        onValueChange = vm.onRequestMethodChanged,
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
                                        vm.modHeader(header, key = it)
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
                                        vm.modHeader(header, value = it)
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

                    Button(onClick = vm.addHeader, modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 10.dp).fillMaxWidth()) {
                        Text("Add Custom Header")
                    }

                    Row(Modifier.padding(6.dp, 0.dp, 0.dp, 0.dp)) {

                        var isJson by remember { mutableStateOf(vm.isJson) }
                        Checkbox(checked = isJson, onCheckedChange = {
                            isJson = it
                            vm.isJson = it
                        })

                        Text(text ="Set Content-type to application/json", fontSize = 20.sp, modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 2.dp).align(Alignment.CenterVertically))

                    }

                    Row(Modifier.padding(6.dp, 0.dp, 0.dp, 0.dp)) {

                        var allowInsecure by remember { mutableStateOf(vm.allowSelfSigned) }
                        Checkbox(checked = allowInsecure, onCheckedChange = {
                            allowInsecure = it
                            vm.allowSelfSigned = it
                        })

                        Text(text ="Allow self-signed certificates", fontSize = 20.sp, modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 2.dp).align(Alignment.CenterVertically))

                    }

                    Row(Modifier.padding(6.dp, 0.dp, 0.dp, 0.dp)) {

                        var isVerbose by remember { mutableStateOf(vm.verbose) }
                        Checkbox(checked = isVerbose, onCheckedChange = {
                            isVerbose = it
                            vm.verbose = it
                        })

                        Text(text ="Verbose", fontSize = 20.sp, modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 2.dp).align(Alignment.CenterVertically))

                    }

                    Spacer(Modifier.weight(1f))

                }

                Column(Modifier.height(IntrinsicSize.Min)) {

                    Divider(Modifier.padding(15.dp, 10.dp, 15.dp, 5.dp))

                    TextField(value = vm.curlCommand.collectAsState().value,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("cURL output") },
                        singleLine = false,
                        minLines = 5,
                        maxLines = 5,
                        modifier = Modifier.padding(20.dp, 20.dp, 20.dp, 0.dp).fillMaxWidth()
                    )

                    Row(Modifier.padding(20.dp)) {

                        Button(onClick = {},
                            modifier = Modifier.weight(1f)) {
                            Text("Copy Command")
                        }

                        Spacer(Modifier.width(20.dp))

                        Button(onClick = {},
                            modifier = Modifier.weight(1f)) {
                            Text("Send Request")
                        }

                    }

                }

            }

            Divider(Modifier.width(1.dp).fillMaxHeight())

            TextField(value = "Sample Text",
                onValueChange = {},
                readOnly = true,
                label = { Text("Response") },
                singleLine = false,
                modifier = Modifier.padding(20.dp).weight(1f).fillMaxHeight()
            )

        }

    }

}