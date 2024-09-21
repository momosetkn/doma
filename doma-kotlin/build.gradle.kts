plugins {
    kotlin("jvm") apply true
}

description = "doma-kotlin"

dependencies {
    api(project(":doma-core"))
    val komapperVersion = "3.0.0"
    implementation("org.komapper:komapper-core:$komapperVersion")
    implementation("org.komapper:komapper-annotation:$komapperVersion")
    testImplementation(project(":doma-mock"))
}

tasks.javadoc {
    enabled = false
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.add("-opt-in=org.komapper.annotation.KomapperExperimentalAssociation")
        }
    }
}
