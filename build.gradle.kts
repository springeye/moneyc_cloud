plugins {
    kotlin("multiplatform") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
}

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isArm64 = System.getProperty("os.arch") == "aarch64"
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" && isArm64 -> macosArm64("native")
        hostOs == "Mac OS X" && !isArm64 -> macosX64("native")
        hostOs == "Linux" && isArm64 -> linuxArm64("native")
        hostOs == "Linux" && !isArm64 -> linuxX64("native")
//        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
//                entryPoint="io.ktor.server.cio.EngineMain"
            }
        }
    }
    val ktor_version="2.3.7"
    sourceSets {
        val nativeMain by getting{
            dependencies {
                implementation("io.ktor:ktor-server-core:2.3.7")
                implementation("io.ktor:ktor-server-cio:2.3.7")
                implementation("io.ktor:ktor-server-cors:$ktor_version")
                implementation("io.ktor:ktor-server-resources:$ktor_version")
                implementation("io.ktor:ktor-server-auto-head-response:$ktor_version")
                implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
                implementation("io.ktor:ktor-server-auth:$ktor_version")
                implementation("io.ktor:ktor-server-cors:$ktor_version")
                implementation("io.ktor:ktor-server-forwarded-header:$ktor_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

            }
        }
        val nativeTest by getting
//        nativeMain.dependencies {
//            implementation("io.ktor:ktor-server-core:2.3.7")
//            implementation("io.ktor:ktor-server-cio:2.3.7")
//        }
        nativeTest.dependencies {
            implementation(kotlin("test"))
            implementation("io.ktor:ktor-server-test-host:2.3.7")
        }
    }
}
