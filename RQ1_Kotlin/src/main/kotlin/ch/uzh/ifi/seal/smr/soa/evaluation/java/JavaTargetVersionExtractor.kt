package ch.uzh.ifi.seal.smr.soa.evaluation.java

import java.io.File

class JavaTargetVersionExtractor(private val dir: File) : JavaVersionExtractor(dir, "target")