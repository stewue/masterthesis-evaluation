package ch.uzh.ifi.seal.smr.soa.evaluation

import ch.uzh.ifi.seal.smr.soa.utils.toFileSystemName
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File
import java.nio.file.Paths

class EvaluationCurrentTag(inputFile: File, inputDir: String, outputDir: File, outputFile: File) : Evaluation(inputFile, inputDir, outputDir, outputFile) {
    override fun processProject(project: String, sourceDir: File, outputDir: File, outputFile: File) {
        super.processProject(project, sourceDir, outputDir, outputFile)

        val repository = getRepo(sourceDir)
        val git = Git(repository)
        val tag = getLastTag(git)
        if (tag != null) {
            git.checkout().setName(tag.second)
            log.info("[$project] Checkout version ${tag.second}")
            evaluate(project, tag.second, tag.third, sourceDir, Paths.get(outputDir.absolutePath, "${project.toFileSystemName}.csv"), outputFile)
        } else {
            log.warn("[$project] Does not have a tag")
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