To run these code snippets, you must first do the following in the _Bencher_ project

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

iii. Run 

```
gradle publishToMavenLocal
``` 