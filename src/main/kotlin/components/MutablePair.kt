package components

import java.io.Serializable

data class MutablePair<A, B>(var first: A, var second: B): Serializable {

    override fun toString(): String {
        return "( ${first.toString()}, ${second.toString()})"
    }

}

infix fun <A, B> A.to(that: B): MutablePair<A, B> = MutablePair(this, that)

fun <T> MutablePair<T, T>.toList(): List<T> {
    return listOf(this.first, this.second)
}
