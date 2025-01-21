val mod_id: String by project

val shared: Project = project(":shared")
evaluationDependsOn(shared.path)

plugins {
    id("net.neoforged.moddev") version "2.0.61-beta"
}

neoForge {
    version = project.property("neo_version") as String

    parchment {
        mappingsVersion = project.property("parchment_mappings_version") as String
        minecraftVersion = project.property("parchment_minecraft_version") as String
    }

    runs {
        create("client") {
            client()

            // Comma-separated list of namespaces to load gametests from. Empty = all namespaces.
            systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
        }

        create("server") {
            server()
            programArgument("--nogui")
            systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
        }

        // This run config launches GameTestServer and runs all registered gametests, then exits.
        // By default, the server will crash when no gametests are provided.
        // The gametest system is also enabled by default for other run configs under the /test command.
        create("gameTestServer") {
            type = "gameTestServer"
            systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
        }

        // applies to all the run configs above
        configureEach {
            // Recommended logging data for a userdev environment
            systemProperty("forge.logging.markers", "REGISTRIES")

            // Recommended logging level for the console
            logLevel = org.slf4j.event.Level.DEBUG
        }
    }

    mods.create(mod_id) {
        sourceSet(sourceSets.main.get())
        sourceSet(shared.sourceSets.main.get())
    }
}

dependencies {
    compileOnly(shared)

    implementation("thedarkcolour:kotlinforforge-neoforge:5.5.0")
}

repositories {
    maven {
        name = "Kotlin for Forge"
        setUrl("https://thedarkcolour.github.io/KotlinForForge/")
    }
}

tasks.withType<ProcessResources>().configureEach {
    val replaceProperties = mapOf(
        "minecraft_version" to project.property("minecraft_version") as String,
        "minecraft_version_range" to project.property("minecraft_version_range") as String,
        "neo_version" to project.property("neo_version") as String,
        "neo_version_range" to project.property("neo_version_range") as String,
        "loader_version_range" to project.property("loader_version_range") as String,
        "mod_id" to mod_id,
        "mod_name" to project.property("mod_name") as String,
        "mod_license" to project.property("mod_license") as String,
        "mod_version" to project.property("mod_version") as String,
        "mod_authors" to project.property("mod_authors") as String,
        "mod_description" to project.property("mod_description") as String
    )
    inputs.properties(replaceProperties)

    expand(replaceProperties)
    from("src/main/templates")
    into("build/generated/sources/modMetadata")
}