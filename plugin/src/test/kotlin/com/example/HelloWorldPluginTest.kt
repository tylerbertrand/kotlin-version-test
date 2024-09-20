package com.example

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome.SUCCESS
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File


class HelloWorldPluginTest {

    @TempDir
    lateinit var testProjectDir : File
    private lateinit var settingsFile : File
    private lateinit var buildFile : File
    private lateinit var gradleDir : File
    private lateinit var versionCatalogFile : File

    @BeforeEach
    fun setup() {
        settingsFile = File(testProjectDir, "settings.gradle.kts")
        buildFile = File(testProjectDir, "build.gradle.kts")
        gradleDir = File(testProjectDir, "gradle")
        gradleDir.mkdir()
        versionCatalogFile = File(gradleDir, "libs.versions.toml")
    }

    @Test
    fun helloWorldPluginTest() {
        settingsFile.writeText("rootProject.name = \"hello-world-plugin-test\"")
        buildFile.writeText("""
            buildscript {
              repositories {
                mavenCentral()
              }
              dependencies {
                classpath(libs.kotlin.gradle.plugin)
              }
            }
            
            repositories {
                mavenCentral()
            }
            
            plugins {
                alias(libs.plugins.kotlin)
                id("com.example.hello-world")
            }
            
            allprojects {
                apply {
                    plugin("org.jetbrains.kotlin.jvm")
                }
            }
        """.trimIndent())

        versionCatalogFile.writeText("""
            [versions]
            kotlin-version = "2.0.0"
            
            [libraries]
            kotlin-gradle-plugin = { module = "org.jetbrains.kotlin:kotlin-gradle-plugin", version.ref = "kotlin-version" }
            
            [plugins]
            kotlin = { id = "org.jetbrains.kotlin.jvm", version.ref = "kotlin-version" }
        """.trimIndent())

        val result: BuildResult = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withPluginClasspath()
            .withArguments("helloWorld")
            .build()

        assertTrue(result.output.contains("Hello world!"))
        assertEquals(SUCCESS, result.task(":helloWorld")!!.outcome)
    }
}