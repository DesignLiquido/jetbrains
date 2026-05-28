plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.intellij") version "1.17.4"
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

kotlin {
    jvmToolchain(17)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

// Separate configuration so we control ANTLR invocation order ourselves
val antlrTool by configurations.creating

val antlrOutputDir = layout.buildDirectory.dir("generated-src/antlr/main").get().asFile

dependencies {
    antlrTool("org.antlr:antlr4:4.13.2")
    implementation("org.antlr:antlr4-intellij-adaptor:0.1")
    implementation("org.antlr:antlr4-runtime:4.13.2")
}

sourceSets {
    main {
        java {
            srcDir(antlrOutputDir)
        }
    }
}

intellij {
    version.set("2022.3")
    type.set("IC")
    pluginName.set(providers.gradleProperty("pluginName").get())
}

// Pass 1: compile lexer grammar -> produces DeleguaLexer.tokens in antlrOutputDir
val generateLexer = tasks.register<JavaExec>("generateLexer") {
    classpath = antlrTool
    mainClass.set("org.antlr.v4.Tool")
    val lexerFile = file("../delegua/gramaticas/DeleguaLexer.g4")
    inputs.file(lexerFile)
    outputs.dir(antlrOutputDir)
    doFirst { antlrOutputDir.mkdirs() }
    args = listOf(
        "-o", antlrOutputDir.absolutePath,
        "-package", "com.designliquido.delegua",
        "-visitor",
        "-long-messages",
        lexerFile.absolutePath
    )
}

// Pass 2: compile parser grammar, using -lib to find DeleguaLexer.tokens
val generateParser = tasks.register<JavaExec>("generateParser") {
    dependsOn(generateLexer)
    classpath = antlrTool
    mainClass.set("org.antlr.v4.Tool")
    val parserFile = file("../delegua/gramaticas/DeleguaParser.g4")
    inputs.file(parserFile)
    inputs.dir(antlrOutputDir)
    outputs.dir(antlrOutputDir)
    args = listOf(
        "-o", antlrOutputDir.absolutePath,
        "-lib", antlrOutputDir.absolutePath,
        "-package", "com.designliquido.delegua",
        "-visitor",
        "-long-messages",
        parserFile.absolutePath
    )
}

tasks {
    patchPluginXml {
        sinceBuild.set(providers.gradleProperty("pluginSinceBuild").get())
        untilBuild.set(providers.gradleProperty("pluginUntilBuild").get())
    }
    compileJava {
        dependsOn(generateParser)
    }
    compileKotlin {
        dependsOn(generateParser)
    }
    wrapper {
        gradleVersion = "8.10"
    }
}
