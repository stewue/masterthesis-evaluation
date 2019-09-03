package ch.uzh.ifi.seal.smr.soa.firsttag

import ch.uzh.ifi.seal.smr.soa.utils.disableSystemErr
import ch.uzh.ifi.seal.smr.soa.utils.toGithubName
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    disableSystemErr()

    if (args.size != 1) {
        println("Needed arguments: inputDir")
        exitProcess(-1)
    }

    val dir = File(args[0])

    dir.listFiles().forEach {
        if (it.isDirectory) {
            println(it.name.toGithubName + " => " + doPerProject(it))

        }
    }
}

private fun doPerProject(dir: File): String? {
    getRepo(dir).use { repository ->
        Git(repository).use { git ->
            return getLastTag(git)?.first
        }
    }
}

private fun getRepo(dir: File): Repository {
    val repoDir = File("${dir.absolutePath}/.git")

    val builder = FileRepositoryBuilder()
    return builder.setGitDir(repoDir)
            .readEnvironment()
            .findGitDir()
            .build()
}

private fun getLastTag(git: Git): Triple<String, String, Int>? {
    val tags = git.tagList().call()

    return if (tags.isEmpty()) {
        null
    } else {
        val tagCommitPair = tags.map {
            val commit = if (it.peeledObjectId == null) {
                it.objectId.name
            } else {
                it.peeledObjectId.name
            }

            val tagName = it.name.substringAfter("refs/tags/")

            commit to tagName
        }.toMap()

        val taggedCommits = tagCommitPair.toList().map { it.first }

        val triple = mutableListOf<Triple<String, String, Int>>()
        val commits = git.log().all().call()
        for (commit in commits) {
            if (taggedCommits.contains(commit.name)) {
                val tagName = tagCommitPair.getValue(commit.name)
                triple.add(Triple(tagName, commit.name, commit.commitTime))
            }
        }

        triple.sortByDescending {
            it.third
        }

        triple.first()
    }
}