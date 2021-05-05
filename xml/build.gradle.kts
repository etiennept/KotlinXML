import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.0"
    `maven-publish`
}

group = "org.kotlin"
version = "0.1"



dependencies {
    testImplementation(kotlin("test-junit"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
publishing {
    repositories {
        maven {
            val releasesRepoUrl = uri("$buildDir/repos/releases")
            val snapshotsRepoUrl = uri("$buildDir/repos/snapshots")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "org.kotlin"
            artifactId = "org.xml"
            version = "0.3"
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
               /* name.set("My Library")
                description.set("A concise description of my library")
                url.set("http://www.example.com/library")
                properties.set(mapOf(
                    "myProp" to "value",
                    "prop.with.dots" to "anotherValue"
                ))
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }*/
                developers {
                    developer {
                        id.set("etiennept")
                        name.set("Etienne Pellissiet-Tanon")
                        email.set("etiennept@gmail.com")
                    }
                }
                scm {
                    connection.set("https://github.com/etiennept/KotlinXML.git")
                   /* developerConnection.set("scm:git:ssh://example.com/my-library.git")
                   url.set("http://example.com/my-library/") */
                }
            }

        }
    }
}