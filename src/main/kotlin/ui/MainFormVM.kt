package ui

import components.DropdownItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.*


class MainFormVM(private val request: RequestModel) {

    val requestMethod: MutableStateFlow<HTTPRequestType> = MutableStateFlow(HTTPRequestType.POST)
    val onRequestMethodChanged: (DropdownItem<HTTPRequestType>) -> Unit = { type: DropdownItem<HTTPRequestType> ->
        request.requestMethod = type.value
        buildCurl()
    }

    var url: String
        get() = request.urlString
        set(value) {
            request.urlString = value
            buildCurl()
        }

    var body: String
        get() = request.body
        set(value) {
            request.body = value
            buildCurl()
        }

    var isJson: Boolean
        get() = request.isJson
        set(value) {
            request.isJson = value
            buildCurl()
        }
    var allowSelfSigned: Boolean
        get() = request.allowSelfSigned
        set(value) {
            request.allowSelfSigned = value
            buildCurl()
        }
    var verbose: Boolean
        get() = request.verbose
        set(value) {
            request.verbose = value
            buildCurl()
        }

    val headersVersionLock: MutableStateFlow<Int> = MutableStateFlow(1)
    val headers: List<Header> = request.headerList

    val addHeader = {
        request.headerList.add(Header())
        headersVersionLock.value += 1
        buildCurl()
    }

    fun modHeader(header: Header, key: String? = null, value: String? = null) {
        if(key != null) header.key = key
        if(value != null) header.value = value
        buildCurl()
    }

    fun deleteHeader(header: Header) {
        request.headerList.remove(header)
        headersVersionLock.value += 1
        buildCurl()
    }

    val curlCommand: MutableStateFlow<String> = MutableStateFlow(request.buildCurl())

    private fun buildCurl() {
        curlCommand.value = request.buildCurl()
    }

    val requestResult: MutableStateFlow<String> = MutableStateFlow("")

    val sendResult: () -> Unit = {

        requestResult.value = "Loading..."

        CoroutineScope(Dispatchers.IO).launch {
            requestResult.value = request.sendRequest()
        }

    }

}
