plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "2.0.10"
}

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("hello-world-plugin") {
            id = "com.example.hello-world-plugin"
            implementationClass = "com.example.HelloWorldPlugin"
        }
    }
}