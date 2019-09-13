package ch.uzh.ifi.seal.smr.soa.analysis.java

import java.io.File

class JavaTargetVersionExtractor(private val dir: File) : JavaVersionExtractor(dir, "target")