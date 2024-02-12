package model

class CurlBuilder {

    var requestMethod: HTTPRequestType = HTTPRequestType.POST

    var url: String = ""
    var body: String = ""

    val headers: ArrayList<Header> = ArrayList()

    var isJson: Boolean = false
    var allowSelfSigned: Boolean = false
    var verbose: Boolean = true


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
        for(header in headers) {
            command.add("-H \'${header.key}: ${header.value}")
        }

        // Add JSON content type
        if(isJson) command.add("-H \"Content-type: application/json\"")

        // Add body
        if(body.isNotBlank()) {
            command.add("-d")
            command.add("\'$body\'")
        }

        // Add URL
        if(url.isNotBlank()) command.add("\'$url\'")

        return command.joinToString(" ")

    }


}