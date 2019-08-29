package ch.uzh.ifi.seal.smr.soa.java

import java.io.File

class JavaSourceVersionExtractor(private val dir: File) : JavaVersionExtractor(dir, "source")