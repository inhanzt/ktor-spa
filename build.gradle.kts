buildscript {

    repositories {
        google()
        mavenCentral()
        //gradlePluginPortal() // Warning: this repository is going to shut down soon
        maven(url = "https://jitpack.io")
        maven(url = "https://repo.grails.org/grails/core/")
    }

}

plugins {
    kotlin("multiplatform")
    `maven-publish`
}

group = "com.github.lamba92"
version = `travis-tag` ?: "1.2.1"

repositories {
    google()
    mavenCentral()
    //gradlePluginPortal() // Warning: this repository is going to shut down soon
    maven(url = "https://jitpack.io")
    maven(url = "https://repo.grails.org/grails/core/")
}

allprojects {
    repositories {
        google()
        mavenCentral()
        //gradlePluginPortal() // Warning: this repository is going to shut down soon
        maven(url = "https://jitpack.io")
        maven(url = "https://repo.grails.org/grails/core/")
    }
}

kotlin {
 
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

    sourceSets {

        val ktorVersion: String by project
        val resourceLoaderVersion: String by project

        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-core:$ktorVersion")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation("io.ktor:ktor-server-tests:$ktorVersion")
                implementation("com.github.lamba92:kresourceloader:$resourceLoaderVersion")
            }
        }
    }
}

fun searchPropertyOrNull(name: String, vararg aliases: String): String? {

    fun searchEverywhere(name: String): String? =
        findProperty(name) as? String? ?: System.getenv(name)

    searchEverywhere(name)?.let { return it }

    with(aliases.iterator()) {
        while (hasNext()) {
            searchEverywhere(next())?.let { return it }
        }
    }

    return null
}

@Suppress("PropertyName")
val `travis-tag`
    get() = System.getenv("TRAVIS_TAG").run {
        if (isNullOrBlank()) null else this
    }
