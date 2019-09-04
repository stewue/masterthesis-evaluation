package ch.uzh.ifi.seal.smr.soa.evaluation.comments

import org.eclipse.jdt.core.dom.ASTVisitor
import org.eclipse.jdt.core.dom.BlockComment
import org.eclipse.jdt.core.dom.CompilationUnit
import org.eclipse.jdt.core.dom.LineComment

class CommentVisitor(private var compilationUnit: CompilationUnit, private val source: List<String>) : ASTVisitor() {
    var counter = 0

    override fun visit(node: LineComment): Boolean {

        val startLineNumber = compilationUnit.getLineNumber(node.startPosition) - 1
        val lineComment = source[startLineNumber].trim { it <= ' ' }

        check(lineComment)

        return true
    }

    override fun visit(node: BlockComment): Boolean {

        val startLineNumber = compilationUnit.getLineNumber(node.startPosition) - 1
        val endLineNumber = compilationUnit.getLineNumber(node.startPosition + node.length) - 1

        val blockComment = StringBuffer()

        for (lineCount in startLineNumber..endLineNumber) {

            val blockCommentLine = source[lineCount].trim { it <= ' ' }
            blockComment.append(blockCommentLine)
            if (lineCount != endLineNumber) {
                blockComment.append("\n")
            }
        }

        check(blockComment.toString())

        return true
    }

    private fun check(text: String) {
        if (text.contains("@Benchmark")) {
            counter++
        }
    }
}