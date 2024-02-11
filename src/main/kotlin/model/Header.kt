package model

import components.MutablePair

typealias Header = MutablePair<String, String>

fun Header(): Header {
    return Header("", "")
}

var Header.key: String
    get() = this.first
    set(value) { this.first = value }

var Header.value: String
    get() = this.second
    set(value) { this.second = value }