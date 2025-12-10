plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    // Room components
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
//    ksp(libs.androidx.room.compiler)
//
//    //Hilt
//    implementation(libs.hilt.android)
//    implementation(libs.androidx.hilt.navigation.compose)
//    ksp(libs.hilt.android.compiler)
//    ksp(libs.androidx.hilt.compiler)
//
//    androidTestImplementation(libs.androidx.room.testing)
}