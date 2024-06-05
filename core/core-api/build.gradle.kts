plugins {
    id ("java-library")
    id ("org.jetbrains.kotlin.jvm")

}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

//tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
//    kotlinOptions {
//        jvmTarget = "17" // Set this to a valid version: "1.8", "11", etc.
//    }
//}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
}