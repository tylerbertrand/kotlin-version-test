plugins {
    id("com.gradle.develocity") version "3.18.1"
    id("com.gradle.common-custom-user-data-gradle-plugin") version "2.0.2"
}

develocity {
    server = "https://ge.solutions-team.gradle.com"
}

rootProject.name = "kotlin-version-test"

include("plugin")
