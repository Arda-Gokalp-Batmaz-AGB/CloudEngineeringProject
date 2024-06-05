import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
    `kotlin-dsl`
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

//tasks.withType<KotlinCompile>().configureEach {
//    kotlinOptions {
//        jvmTarget = "17" // Set this to a valid version: "1.8", "11", etc.
//    }
//}
repositories {
    google()
    mavenCentral()
}