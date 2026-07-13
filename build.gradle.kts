plugins {
    java
    application
}

group = "com.tonic.j2cs"
version = "0.1.0"

repositories {
    mavenLocal()
    //maven { url = uri("https://www.jitpack.io") }
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

dependencies {
    implementation("com.tonic:YABR:1.0.1")
    //implementation("com.github.Tonic-Box:YABR:main-SNAPSHOT")

    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("com.tonic.j2cs.Main")
}

tasks.register<Jar>("shadedJar") {
    group = "build"
    description = "Builds a self-contained runnable J2CS.jar (all dependencies + shim resources bundled)."
    archiveFileName.set("J2CS.jar")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "com.tonic.j2cs.Main"
        attributes["Implementation-Version"] = version
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    }) {
        exclude("META-INF/*.SF", "META-INF/*.RSA", "META-INF/*.DSA", "META-INF/MANIFEST.MF", "module-info.class")
    }
}

tasks.named("build") {
    dependsOn("shadedJar")
}

tasks.processResources {
    from("javacompat") {
        into("javacompat")
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
    }
    systemProperty("j2cs.aot", System.getProperty("j2cs.aot", "false"))
    systemProperty("j2cs.gui", System.getProperty("j2cs.gui", "false"))
    systemProperty("j2cs.regenGolden", System.getProperty("j2cs.regenGolden", "false"))
}
