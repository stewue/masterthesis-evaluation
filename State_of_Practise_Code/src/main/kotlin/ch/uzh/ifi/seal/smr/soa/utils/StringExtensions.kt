package ch.uzh.ifi.seal.smr.soa.utils

val String.toGithubName: String
    inline get() = this.replace(".", "/")

val String.toFileSystemName: String
    inline get() = this.replace("/", ".")