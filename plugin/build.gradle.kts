plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "2.0.0"
}

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("hello-world-plugin") {
            id = "com.example.hello-world"
            implementationClass = "com.example.HelloWorldPlugin"
        }
    }
}

dependencies {
    testImplementation(gradleTestKit())
    testImplementation(platform("org.junit:junit-bom:5.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}