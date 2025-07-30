plugins {
    kotlin("multiplatform")
    `maven-publish`
}

group = "io.github.inhanzt"
version = "1.0.0"

repositories {
    google()
    mavenCentral()
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

