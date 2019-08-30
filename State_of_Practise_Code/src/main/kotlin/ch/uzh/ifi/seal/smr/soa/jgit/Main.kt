package ch.uzh.ifi.seal.smr.soa.jgit

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File


fun main() {
    getRepo().use{ repository ->
        Git(repository).use{ git ->
            val commits = git.log().all().call()

            for (commit in commits) {
                println("LogCommit: $commit")
            }
            println(commits.toList().size)
        }
    }
}

private fun getRepo(): Repository {
    val repoDir = File("D:\\test\\.git")

    val builder = FileRepositoryBuilder()
    val repo = builder.setGitDir(repoDir)
            .readEnvironment()
            .findGitDir()
            .build()

    println("Having repository: " + repo.directory)

    return repo
}