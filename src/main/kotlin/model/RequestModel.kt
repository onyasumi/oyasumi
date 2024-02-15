package model

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class RequestModel {

    var requestMethod: HTTPRequestType = HTTPRequestType.POST

    var urlString: String = ""
    var body: String = ""

    val headerList: ArrayList<Header> = ArrayList()

    var isJson: Boolean = false
    var allowSelfSigned: Boolean = false
    var verbose: Boolean = true


    // Creates curl command
    fun buildCurl(): String {

        val command: MutableList<String> = ArrayList()
        command.add("curl")

        // Enable verbose mode
        if(verbose) command.add("-v")

        // Allow self-signed certs
        if(allowSelfSigned) command.add("--insecure")

        // Add request method
        command.add(requestMethod.flag)

        // Add custom headers
        for(header in headerList) {
            if(header.isNotBlank()) command.add("-H \'${header.key}: ${header.value}\'")
        }

        // Add JSON content type
        if(isJson) command.add("-H \"Content-type: application/json\"")

        // Add body
        if(body.isNotBlank()) {
            command.add("-d")
            command.add("\'$body\'")
        }

        // Add URL
        if(urlString.isNotBlank()) command.add("\'$urlString\'")

        return command.joinToString(" ")

    }


    // Send HTTP request
    suspend fun sendRequest(): String {

        val requestBuilder = HttpRequestBuilder().apply {

            url(urlString)

            expectSuccess = true
            setBody(body)

            if(isJson) header("Content-type", "application/json")
            for(h in headerList) header(h.key, h.value)
        }

        val client = HttpClient()

        val response: HttpResponse = client.get(requestBuilder)
        val body: String = response.bodyAsText()

        client.close()

        return body

    }

}