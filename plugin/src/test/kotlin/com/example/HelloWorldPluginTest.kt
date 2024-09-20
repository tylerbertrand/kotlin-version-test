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

    @BeforeEach
    fun setup() {
        settingsFile = File(testProjectDir, "settings.gradle.kts")
        buildFile = File(testProjectDir, "build.gradle.kts")
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
                classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.10")
              }
            }
            
            repositories {
                mavenCentral()
            }
            
            plugins {
                kotlin("jvm") version "2.0.0"
                id("com.example.hello-world")
            }
            
            allprojects {
                apply {
                    plugin("org.jetbrains.kotlin.jvm")
                }
            }
        """.trimIndent())

        val result: BuildResult = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withPluginClasspath()
            .withArguments("helloWorld")
            .build()

        assertTrue(result.getOutput().contains("Hello world!"))
        assertEquals(SUCCESS, result.task(":helloWorld")!!.outcome)
    }
}