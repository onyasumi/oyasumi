package ui

import HTTPRequestType
import kotlinx.coroutines.flow.MutableStateFlow

class MainFormVM {

    val requestMethod: MutableStateFlow<HTTPRequestType> = MutableStateFlow(HTTPRequestType.POST)

    val url: MutableStateFlow<String> = MutableStateFlow("")
    val body: MutableStateFlow<String> = MutableStateFlow("")

}