import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Dependencies {
    const val composeMaterial = "androidx.compose.material3:material3:${Versions.composeMaterial3}"
    const val composeMaterialWindow = "androidx.compose.material3:material3-window-size-class:${Versions.composeMaterial3}"
    const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.compose}"
    const val composeRuntimeLiveData = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
    const val composeActivity = "androidx.activity:activity-compose:${Versions.compose}"
    const val composeMaterialIcons = "androidx.compose.material:material-icons-extended:${Versions.compose}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintLayout}"
    const val flowLayout = "com.google.accompanist:accompanist-flowlayout:${Versions.flowLayout}"
    const val hiltNav = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNav}"
    const val composeNav = "androidx.navigation:navigation-compose:${Versions.composeNav}"
    const val konfetiCompose = "nl.dionsegijn:konfetti-compose:${Versions.konfetiCompose}"
    const val swipeRefCompose = "com.google.accompanist:accompanist-swiperefresh:${Versions.swipeCompose}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val appCompatRes = "androidx.appcompat:appcompat-resources:${Versions.appCompat}"

    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    const val ktorJava = "io.ktor:ktor-client-java:${Versions.ktor}"
    const val ktorCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val ktorContent = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
    const val ktorJson = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    const val ktorSerial = "io.ktor:ktor-client-serialization:${Versions.ktor_log_serial}"
    const val ktorLogging = "io.ktor:ktor-client-logging-jvm:${Versions.ktor_log_serial}"
    const val ktorAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"

//    implementation 'io.ktor:ktor-client-android:2.2.4'
//    implementation 'io.ktor:ktor-client-serialization:1.5.0'
//    implementation 'io.ktor:ktor-client-logging-jvm:1.5.0'
//    implementation("io.ktor:ktor-client-core:2.2.4")
//    implementation("io.ktor:ktor-client-content-negotiation:2.2.4")
//    implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.4")
    const val openaiClient = "com.aallam.openai:openai-client:${Versions.openai}"
    const val geminiClient = "com.google.ai.client.generativeai:generativeai:${Versions.gemini}"

    const val coil = "io.coil-kt:coil-compose:${Versions.coil}"

    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebase}"
    const val firebaseAuth = "com.google.firebase:firebase-auth-ktx"
    const val firebaseFunction = "com.google.firebase:firebase-functions-ktx"
    const val firebaseFirestore = "com.google.firebase:firebase-firestore-ktx"
    const val firebaseStorage = "com.google.firebase:firebase-storage-ktx"
    const val firebasePerformance = "com.google.firebase:firebase-perf"

    const val sqldelightAndroidDriver = "app.cash.sqldelight:android-driver:${Versions.sqdelight}"
    const val sqldelightCoroutineExtension = "app.cash.sqldelight:coroutines-extensions:${Versions.sqdelight}"

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"

    const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.reflect}"

    const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutineCore}"
    const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutineAndroid}"

    const val splashScreen = "androidx.core:core-splashscreen:${Versions.splashScreen}"

    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    const val ktxLifeCycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ktxLifecycle}"

    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoTest}"
    const val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockitoTest}"
    const val testJunit = "junit:junit:${Versions.testJunit}"
    const val testExtJunit = "androidx.test.ext:junit:${Versions.testExtJunit}"
    const val testEspresso = "androidx.test.espresso:espresso-core:${Versions.testEspresso}"
    const val testUiJunit = "androidx.compose.ui:ui-test-junit4:${Versions.testUiJunit}"

    const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinStdlib}"
    const val metadataJvm = "org.jetbrains.kotlinx:kotlinx-metadata-jvm:${Versions.metadataJvm}"


    const val httpOk = "com.squareup.okhttp3:okhttp:${Versions.httpOk}"

//    const val googleFonts = "androidx.compose.ui:ui-text-google-fonts:${Versions.googleFonts}"
}
fun DependencyHandler.testOnlyKotlin(){
    testImplementation(Dependencies.testJunit)
    testImplementation(Dependencies.mockitoCore)
    //androidTestImplementation
}
fun DependencyHandler.testFullAndroid(){
    testImplementation(Dependencies.mockitoCore)
    androidTestImplementation(Dependencies.mockitoAndroid)
    testImplementation(Dependencies.testJunit)
    androidTestImplementation(Dependencies.testExtJunit)
    androidTestImplementation(Dependencies.testEspresso)
    androidTestImplementation(Dependencies.testUiJunit)
    //testImplementation ("org.mockito:mockito-core:2.24.5")
    //androidTestImplementation
}
fun DependencyHandler.sqldelightAndroid(){
    implementation(Dependencies.sqldelightAndroidDriver)
    implementation(Dependencies.sqldelightCoroutineExtension)
}
fun DependencyHandler.reflect(){
    implementation(Dependencies.reflect)
}
fun DependencyHandler.appCompat(){
    implementation(Dependencies.appCompat)
    implementation(Dependencies.appCompatRes)
}
fun DependencyHandler.firebasePerformance() {
    implementation(platform(Dependencies.firebaseBom))
    implementation(Dependencies.firebasePerformance)
}
fun DependencyHandler.firebaseFull() {
    implementation(platform(Dependencies.firebaseBom))
    implementation(Dependencies.firebaseAuth)
    implementation(Dependencies.firebaseFunction)
    implementation(Dependencies.firebaseFirestore)
    implementation(Dependencies.firebaseStorage)
}
fun DependencyHandler.coil() {
    implementation(Dependencies.coil)
}
fun DependencyHandler.ktor() {
//    implementation(Dependencies.ktorJava)
    implementation(Dependencies.ktorCore)
    implementation(Dependencies.ktorContent)
    implementation(Dependencies.ktorJson)
    implementation(Dependencies.ktorSerial)
    implementation(Dependencies.ktorLogging)
}
fun DependencyHandler.ktorAndroid() {
    implementation(Dependencies.ktorAndroid)
}
fun DependencyHandler.generativeaiclients() {
    implementation(Dependencies.openaiClient)
    implementation(Dependencies.geminiClient)
}
fun DependencyHandler.compose() {
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeRuntime)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeUiTooling)
    implementation(Dependencies.composeRuntimeLiveData)
    implementation(Dependencies.composeRuntimeLiveData)
    implementation(Dependencies.composeActivity)
    implementation(Dependencies.composeMaterialIcons)
    implementation(Dependencies.composeMaterial)
    implementation(Dependencies.composeMaterialWindow)
    implementation(Dependencies.flowLayout)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.composeNav)
    implementation(Dependencies.hiltNav)
    implementation(Dependencies.konfetiCompose)
    implementation(Dependencies.swipeRefCompose)
    debugImplementation(Dependencies.composeUiToolingPreview)
    debugImplementation(Dependencies.composeUiTooling)
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)
//    kapt( Dependencies.metadataJvm)
}

fun DependencyHandler.coreApi() {
    implementation(project(":core:core-api"))
}
fun DependencyHandler.coreUi() {
    implementation(project(":core:core-ui"))
}
fun DependencyHandler.authImpl() {
    implementation(project(":auth:auth-impl"))
}
fun DependencyHandler.authApi() {
    implementation(project(":auth:auth-api"))
}
fun DependencyHandler.authUi() {
    implementation(project(":auth:auth-ui"))
}
