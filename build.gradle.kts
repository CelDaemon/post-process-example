plugins {
    id("net.fabricmc.fabric-loom-remap") version "1.15-SNAPSHOT"
}

val modVersion: String by project
val mavenGroup: String by project
val archivesBaseName: String by project
val minecraftVersion: String by project
val loaderVersion: String by project
val fabricVersion: String by project

version = modVersion
group = mavenGroup

base {
    archivesName = archivesBaseName
}

loom {
    accessWidenerPath = file("src/main/resources/post-process-example.classtweaker")
    splitEnvironmentSourceSets()

    val client by sourceSets.existing

    mods {
        register("post-process-example") {
            sourceSet(sourceSets.main.get())
            sourceSet(client.get())
        }
    }
}

fabricApi {
    configureDataGeneration {
        client = true
    }
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")

    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")
}

tasks.processResources {
    inputs.property("version", modVersion)
    inputs.property("minecraft_version", minecraftVersion)
    inputs.property("loader_version", loaderVersion)

    filesMatching("fabric.mod.json") {
        expand(
            "version" to modVersion,
            "minecraft_version" to minecraftVersion,
            "loader_version" to loaderVersion
        )
    }
}

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 21
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_$archiveBaseName" }
    }
}