# Source Code Parser

To run these source code parser, you must first do the following in the _Bencher_ project

i. Add the code snippet `apply plugin: 'maven-publish'` after `apply plugin: 'application'` in the _build.gradle_ file

```groovy
apply plugin: 'application'
apply plugin: 'maven-publish'
```

ii. Add the following code snippet at the end of the _build.gradle_ file

```groovy
// add project to local maven repo
publishing {
    publications {
        maven(MavenPublication) {
            groupId = "$group"
            artifactId = "${rootProject.name}"
            version = "$version"

            from components.java
        }
    }
}
```

iii. Replace the following code in the class `ConfigBasedConfigurator`

```kotlin
return if (valid(c)) {
    Either.right(c)
} else {
    Either.left("Invalid configuration for benchmark ($bench) and provided default/class/benchmark configurations")
}
```

with

```kotlin
return Either.right(c)
```

iv. Run 

```
gradle publishToMavenLocal
``` 

# Generated files

| File / Folder        | Script           |
| ------------- |:-------------:|
| aggregated/annotationpresent.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.annotationpresent.MainKt |
| aggregated/benchmarkrepocorrelation.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.benchmarkrepocorrelation.MainKt |
| aggregated/codechanged.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.hash.MainKt |
| aggregated/comments.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.comments.MainKt |
| aggregated/configurationchanged.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.hash.MainKt |
| aggregated/executiontime.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.executiontime.MainKt |
| aggregated/executiontimerepocorrelation.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.executiontimerepocorrelation.MainKt |
| aggregated/features.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.features.MainKt |
| aggregated/featuresperproject.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.featuresperproject.MainKt |
| aggregated/forkahead.csv | Run ch.uzh.ifi.seal.smr.soa.datapreparation.forkahead.MainKt |
| aggregated/forkchanged.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.forkchanged.MainKt |
| aggregated/isdefaultvalue.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.isdefaultvalue.MainKt |
| aggregated/javaversion.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.javaversion.MainKt |
| aggregated/javaversionchange.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.javaversionchange.MainKt |
| aggregated/jmh121update.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.jmh121update.MainKt |
| aggregated/jmhversion.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.jmhversion.MainKt |
| aggregated/jmhversionage.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.jmhversionage.MainKt |
| aggregated/jmhversionchange.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.jmhversionchange.MainKt |
| aggregated/jmhversionnormalized.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.jmhversionnormalized.MainKt |
| aggregated/numberofbenchmarks.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.numberofbenchmarks.MainKt |
| aggregated/samplecommits.csv |  Run ch.uzh.ifi.seal.smr.soa.analysis.samplecommits.MainKt |
| aggregated/valuecorrelation.csv | Run ch.uzh.ifi.seal.smr.soa.analysis.valuecorrelation.MainKt |
| comments/current-commit-comments.csv | Run ch.uzh.ifi.seal.smr.soa.evaluation.comments.MainKt |
| current-commit/results | Run ch.uzh.ifi.seal.smr.soa.evaluation.MainKt (and choose evaluation type) |
| current-commit/merged.csv | Run ch.uzh.ifi.seal.smr.soa.utils.filemerger.MainKt |
| current-commit/merged-isMain.csv | Run ch.uzh.ifi.seal.smr.soa.utils.resultismain.MainKt |
| current-tag/results | Run ch.uzh.ifi.seal.smr.soa.evaluation.MainKt (and choose evaluation type) |
| history/results | Run ch.uzh.ifi.seal.smr.soa.evaluation.MainKt (and choose evaluation type) |
| history/history-selected-commits.csv | Run ch.uzh.ifi.seal.smr.soa.evaluation.history.MainKt |
| history/merged.csv | Run ch.uzh.ifi.seal.smr.soa.utils.filemerger.MainKt (And change input + output dir) |
| history/per-project | Run ch.uzh.ifi.seal.smr.soa.utils.filemergerperproject.MainKt |
