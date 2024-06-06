plugins {
    id ("java-library")
    id ("org.jetbrains.kotlin.jvm")

}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies {
    coreApi()
    implementation(Dependencies.kotlinxSerialization)
    implementation (Dependencies.gson)
    implementation(Dependencies.coroutineCore)
//    implementation(Dependencies.coroutineAndroid)
    testOnlyKotlin()
}