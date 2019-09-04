package ch.uzh.ifi.seal.smr.soa.evaluation

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File

class EvaluationCurrentTag(inputFile: File, inputDir: String, outputDir: File, outputFile: File) : Evaluation(inputFile, inputDir, outputDir, outputFile) {
    override fun processProject(project: String, sourceDir: File, outputDir: File, outputFile: File) {
        getRepo(sourceDir).use { repository ->
            Git(repository).use { git ->
                val tag = getLastTag(git)
                if (tag != null) {
                    git.checkout().setName(tag.second)
                    log.info("[$project] Checkout version ${tag.second}")
                    evaluate(project, tag.second, tag.third, sourceDir, outputDir, outputFile)
                } else {
                    log.warn("[$project] Does not have a tag")
                }
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
}