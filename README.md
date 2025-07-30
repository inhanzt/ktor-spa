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
