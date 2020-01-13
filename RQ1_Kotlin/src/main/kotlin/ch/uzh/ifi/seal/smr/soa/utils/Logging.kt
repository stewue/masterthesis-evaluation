package ch.uzh.ifi.seal.smr.soa.utils

import java.io.OutputStream
import java.io.PrintStream

fun disableSystemErr() {
    System.setErr(PrintStream(object : OutputStream() {
        override fun write(b: Int) {}
    }))
}