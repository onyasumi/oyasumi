package ui

import model.HTTPRequestType
import kotlinx.coroutines.flow.MutableStateFlow
import model.Header


class MainFormVM {

    val requestMethod: MutableStateFlow<HTTPRequestType> = MutableStateFlow(HTTPRequestType.POST)

    var url: String = ""
    var body: String = ""

    val headers: ArrayList<Header> = ArrayList()
    val headersVersionLock: MutableStateFlow<Int> = MutableStateFlow(1)

    val addHeader = {
        headers.add(Header())
        headersVersionLock.value += 1
    }

    fun deleteHeader(header: Header) {
        headers.remove(header)
        headersVersionLock.value += 1
    }

}
