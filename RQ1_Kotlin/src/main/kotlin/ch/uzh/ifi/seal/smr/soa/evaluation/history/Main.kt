package ch.uzh.ifi.seal.smr.soa.evaluation.history

import ch.uzh.ifi.seal.smr.soa.utils.disableSystemErr
import ch.uzh.ifi.seal.smr.soa.utils.toFileSystemName
import org.eclipse.jgit.api.Git
import java.io.File
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    disableSystemErr()

    if (args.size != 2) {
        // inputFile = see input.csv as example file
        // inputDir = Folder where all projects are store
        println("Needed arguments: inputFile inputDir")
        exitProcess(-1)
    }

    val inputFile = File(args[0])
    val inputDir = args[1]

    inputFile.forEachLine { project ->
        val dir = File(inputDir + project.toFileSystemName)
        if (dir.exists()) {
            processProject(project, dir)
        }
    }
}

private fun processProject(project: String, dir: File) {
    val repository = HistoryManager.getRepo(dir)
    val git = Git(repository)
    HistoryManager.resetToBranch(git)
    val commits = HistoryManager.sampleCommits(repository, git)
    commits.forEach { commit ->
        val commitId = commit.second.name
        val commitTime = commit.first
        println("$project;$commitTime;$commitId")
    }
}