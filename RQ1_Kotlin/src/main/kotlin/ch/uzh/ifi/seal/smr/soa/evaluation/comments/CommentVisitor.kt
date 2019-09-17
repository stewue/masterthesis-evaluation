package ch.uzh.ifi.seal.smr.soa.evaluation.comments

import org.apache.logging.log4j.LogManager
import org.eclipse.jdt.core.dom.*

private val log = LogManager.getLogger()

class CommentVisitor(private var compilationUnit: CompilationUnit, private val source: List<String>, private val sourceFile: String, private val project: String) : ASTVisitor() {
    val methodNames = mutableListOf<String>()

    override fun visit(node: LineComment): Boolean {

        val startLineNumber = compilationUnit.getLineNumber(node.startPosition) - 1
        val lineComment = source[startLineNumber].trim { it <= ' ' }

        check(lineComment, node, startLineNumber)

        return true
    }

    override fun visit(node: BlockComment): Boolean {
        val startLineNumber = compilationUnit.getLineNumber(node.startPosition) - 1
        val endLineNumber = compilationUnit.getLineNumber(node.startPosition + node.length) - 1

        for (lineCount in startLineNumber..endLineNumber) {
            val blockCommentLine = source[lineCount].trim { it <= ' ' }

            check(blockCommentLine, node, lineCount)
        }

        return true
    }

    private fun check(text: String, node: ASTNode, lineNumber: Int) {
        if (text.contains("@Benchmark")) {

            var currentLineNumber = lineNumber
            while (currentLineNumber < source.size) {
                val currentLine = source[currentLineNumber].replace("//", "").replace("/*", "").trim()

                if (currentLine.contains("public")) {
                    val name = currentLine.substringBefore('(').substringAfterLast(' ')

                    if (name.isNullOrBlank()) {
                        log.error("[$project] cannot extract method name for line $lineNumber in file $sourceFile")
                    } else {
                        methodNames.add(name)
                    }

                    break
                } else {
                    currentLineNumber++
                }
            }
        }
    }
}