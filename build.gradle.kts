buildscript {

    repositories {
        google()
        mavenCentral()
        maven(url = "https://repo.grails.org/grails/core/")
    }

}

plugins {
    kotlin("multiplatform")
    `maven-publish`
}

group = "com.github.lamba92"

repositories {
    google()
    mavenCentral()
    maven(url = "https://repo.grails.org/grails/core/")
}

allprojects {
    repositories {
        google()
        mavenCentral()
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
            }
        }
    }
}

