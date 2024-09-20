package com.example

import org.gradle.api.Plugin
import org.gradle.api.Project

class HelloWorldPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.tasks.register("helloWorld") {
            it.doLast {
                println("Hello world!")
            }
        }
    }
}