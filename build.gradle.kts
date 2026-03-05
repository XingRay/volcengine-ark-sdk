plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    `maven-publish`
}

group = "io.github.xingray"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.slf4j.simple)
    testImplementation(libs.kotlin.test)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
    withJavadocJar()
}

kotlin {
    jvmToolchain(17)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "io.github.xingray"
            artifactId = "volcengine-sdk"
            version = "0.0.1"

            from(components["java"])

            pom {
                name.set("volcengine-sdk")
                description.set("Volcengine Ark SDK for Kotlin")
                url.set("https://github.com/xingray/volcengine-sdk")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("xingray")
                        name.set("xingray")
                        email.set("xingray@github.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/xingray/volcengine-sdk.git")
                    developerConnection.set("scm:git:ssh://github.com/xingray/volcengine-sdk.git")
                    url.set("https://github.com/xingray/volcengine-sdk")
                }
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
