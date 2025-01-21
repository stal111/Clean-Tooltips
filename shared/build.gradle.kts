plugins {
    id("net.neoforged.moddev") version "2.0.61-beta"
}

repositories {
    maven {
        url = uri("https://www.cursemaven.com")
    }
    mavenCentral()
}

neoForge {
    neoFormVersion = "1.21.4-20241203.161809"

    parchment {
        mappingsVersion = project.property("parchment_mappings_version") as String
        minecraftVersion = project.property("parchment_minecraft_version") as String
    }
}


dependencies {
    compileOnly("net.fabricmc:sponge-mixin:${project.property("mixin_version") as String}")
    compileOnly("io.github.llamalad7:mixinextras-common:${project.property("mixinextras_version") as String}")
    annotationProcessor("io.github.llamalad7:mixinextras-common:${project.property("mixinextras_version") as String}")
}