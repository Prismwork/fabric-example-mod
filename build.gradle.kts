plugins {
    id("java")
    id("fabric-loom").version("1.0-SNAPSHOT")
    id("maven-publish")
}

group = property("maven_group")!!
version = "${property("mod_version")}+mc${property("minecraft_version")}"

repositories {
    // Add Maven repositories to pull dependencies from.
    // For example:
    maven("https://maven.terraformersmc.com/releases/") {
        name = "TerraformersMC"
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_api_version")}")
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(mutableMapOf("version" to project.version))
        }
    }

    jar {
        from("LICENSE")
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                artifact(remapJar) {
                    builtBy(remapJar)
                }
            }
        }

        repositories {
            // Publish to repos
        }
    }
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
