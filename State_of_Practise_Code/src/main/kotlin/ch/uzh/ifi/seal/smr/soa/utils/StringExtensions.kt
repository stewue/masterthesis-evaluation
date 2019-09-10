package ch.uzh.ifi.seal.smr.soa.utils

import org.apache.commons.codec.binary.Hex

val String.toGithubName: String
    inline get() = this.replace("#", "/")

val String.toFileSystemName: String
    inline get() = this.replace("/", "#")

val ByteArray.convertToString: String
    inline get() = Hex.encodeHexString(this)

val String.outerClass: String
    inline get() = this.substringBefore('#')