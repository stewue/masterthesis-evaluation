package ch.uzh.ifi.seal.smr.soa.evaluation.comments

import ch.uzh.ifi.seal.bencher.analysis.finder.jdt.FullyQualifiedNameHelper
import org.eclipse.jdt.core.dom.ASTVisitor
import org.eclipse.jdt.core.dom.TypeDeclaration

class ClassVisitor : ASTVisitor() {
    var fullyQualifiedClassName: String? = ""

    override fun visit(node: TypeDeclaration): Boolean {
        fullyQualifiedClassName = FullyQualifiedNameHelper.getClassName(node)
        return false
    }
}