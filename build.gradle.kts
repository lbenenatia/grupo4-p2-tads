plugins {
    id("java")
}

group = "um.edu.uy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //CSV
    implementation("com.opencsv:opencsv:5.7.1")

    //Tests
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}