# Ktor Single Page Application 

Written in Kotlin with ❤️

## Building

This was forked entirely because I inherited a project that depended on this library, but it's not in maven repositories anywhere.

To stick it in your local repository, `./gradlew publishKotlinMultiplatformPublicationToMavenLocal`.

To use it in your project, include `mavenLocal()` in your `build.gradle` file under `buildscript.repositories` and `allprojects.repositories`.


## Usage

Just install the feature in your application with:

```kotlin
install(SinglePageApplication)
```

By default the app is served from the root folder of bundled resources with `index.html` as main page. You can customize stuff like so:

```kotlin
install(SinglePageApplication){

    // main page file name to be served
    defaultPage = "myPage.html"
    
    // folder in which look for you spa files, either
    // inside bundled resources or current working directory
    folderPath = "not/root/folder/"
    
    // The url at which tour spa should be served. This
    // is usefull if you want to serve a spa not at the
    // root of your website
    spaRoute = "/something"
    
    // uses files in the current working directory instead
    // of resources
    useFiles = true
    
    // ignores a url if contains this regex 
    ignoreIfContains = Regex(...)
    
}
```

**All the routes you set up in your Ktor application have higher priority and will shadow eventual SPA routes so keep that in mind.** 

## Under the hood

The feature intercepts all 404s not intercepted by the router and instead of responding an HTTP 404 it serves the `index.html` (or whatever you called it) with HTTP 200 status.

**NB**: Remember to setup a 404 in your spa!

## Install

If using Gradle Kotlin DSL:
```kotlin
repositories {
    jcenter()
}
...
dependencies {
    implementation("com.github.inhanzt", "ktor-spa", "{latest_version}")
}
```

## Generate Publishable build.
Follow along with [this guide](https://central.sonatype.org/publish/requirements/gpg/#signing-a-file) to get signing keys.

Then follow along with [this guide](https://central.sonatype.org/register/central-portal/) to get a central portal account.

Maven wants a specific format for their repository. The `./gradlew publishJvmPublicationToMavenLocal` does some of the work, and will place the project into `~/.m2`.

Copy those files somewhere else, and edit the POM according to maven's requirements.  Here's an example that worked.  Note that versions must not conflict with what's already been uploaded.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <!-- This module was also published with a richer model, Gradle metadata,  -->
  <!-- which should be used instead. Do not delete the following line which  -->
  <!-- is to indicate to Gradle or any Gradle module metadata file consumer  -->
  <!-- that they should prefer consuming it instead. -->
  <!-- do_not_remove: published-with-gradle-metadata -->
  <modelVersion>4.0.0</modelVersion>
  <groupId>io.github.inhanzt</groupId>
  <artifactId>ktor-spa-jvm</artifactId>
  <version>1.0.0</version>
  <name>ktor-spa-jvm</name>
  <description>nah</description>
  <url>https://github.com/inhanzt/ktor-spa</url>
  <licenses>
    <license>
      <name>The Apache Software License, Version 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
    </license>
  </licenses>
  <developers>
    <developer>
      <name>Kyle Sexton</name>
      <email>kyle@gmail.com</email>
      <organization>N/A</organization>
      <organizationUrl>https://github.com/inhanzt/ktor-spa</organizationUrl>
    </developer>
  </developers>
  <scm>
    <connection>scm:git:git://github.com/inhanzt/ktor-spa.git</connection>
    <developerConnection>scm:git:ssh://github.com:inhanzt/ktor-spa.git</developerConnection>
    <url>https://github.com/inhanzt/ktor-spa</url>
  </scm>
  <dependencies>
    <dependency>
      <groupId>org.jetbrains.kotlin</groupId>
      <artifactId>kotlin-stdlib-common</artifactId>
      <version>1.8.21</version>
      <scope>runtime</scope>
    </dependency>
  </dependencies>
</project>

```

```sh
find . -type f -exec bash -c 'md5sum {} | cut -d " " -f 1 > {}.md5 && sha1sum {} | cut -d " " -f 1 > {}.sha1 && gpg -ba {}' \;
```

This should leave you with a folder structure like this, which you can upload as a `io.zip`.

```sh
$ tree io
io
└── github
    └── inhanzt
        └── ktor-spa-jvm
            └── 1.0.0
                ├── ktor-spa-jvm-1.0.0.jar
                ├── ktor-spa-jvm-1.0.0.jar.asc
                ├── ktor-spa-jvm-1.0.0.jar.md5
                ├── ktor-spa-jvm-1.0.0.jar.sha1
                ├── ktor-spa-jvm-1.0.0-javadoc.jar
                ├── ktor-spa-jvm-1.0.0-javadoc.jar.asc
                ├── ktor-spa-jvm-1.0.0-javadoc.jar.md5
                ├── ktor-spa-jvm-1.0.0-javadoc.jar.sha1
                ├── ktor-spa-jvm-1.0.0.module
                ├── ktor-spa-jvm-1.0.0.module.asc
                ├── ktor-spa-jvm-1.0.0.module.md5
                ├── ktor-spa-jvm-1.0.0.module.sha1
                ├── ktor-spa-jvm-1.0.0.pom
                ├── ktor-spa-jvm-1.0.0.pom.asc
                ├── ktor-spa-jvm-1.0.0.pom.md5
                ├── ktor-spa-jvm-1.0.0.pom.sha1
                ├── ktor-spa-jvm-1.0.0-sources.jar
                ├── ktor-spa-jvm-1.0.0-sources.jar.asc
                ├── ktor-spa-jvm-1.0.0-sources.jar.md5
                └── ktor-spa-jvm-1.0.0-sources.jar.sha1
```

The upload will either let you know the package is fine or report errors with files.  Once you have an upload with no errors, you may publish the file.  Note that there is no "unpublish" button.