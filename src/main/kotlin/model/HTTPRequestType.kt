package model

enum class HTTPRequestType(val flag: String) {

    GET("-XGET"),
    POST("-XPOST"),
    PUT("-XPUT"),
    PATCH("-XPATCH"),
    DELETE("-XDELETE")

}