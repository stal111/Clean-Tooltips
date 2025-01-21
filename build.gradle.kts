plugins {
    kotlin("jvm") version "2.1.0"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "java")
    apply(plugin = "idea")

    version = project.property("mod_version") as String
    group = project.property("mod_group_id") as String

    repositories {
        maven {
            url = uri("https://www.cursemaven.com")
        }
    }

    kotlin {
        jvmToolchain(21)
    }

    idea {
        module {
            isDownloadSources = true
            isDownloadJavadoc = true
        }
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}