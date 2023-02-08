plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.asciidoctor.convert") version "2.4.0"
    id("au.com.dius.pact") version "4.4.5"
}

group = "com.wgdetective"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val snippetsDir by extra { file("build/generated-snippets") }
extra["springCloudVersion"] = "2022.0.1"
extra["testcontainersVersion"] = "1.17.6"
extra["mapstructVersion"] = "1.5.2.Final"
extra["mysqlr2dbcVersion"] = "0.8.1.RELEASE"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("io.projectreactor.kafka:reactor-kafka")
    // TODO remove version
    implementation("org.mapstruct:mapstruct:${property("mapstructVersion")}")

    compileOnly("org.projectlombok:lombok")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:${property("mapstructVersion")}")

    testAnnotationProcessor("org.mapstruct:mapstruct-processor:${property("mapstructVersion")}")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:kafka")

    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    testImplementation("au.com.dius.pact.provider:junit5spring:4.4.5")

}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    outputs.dir(snippetsDir)
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

tasks.withType<JacocoReport> {
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.map {
            fileTree(it).apply {
                exclude(
                    "com/wgdetective/pactexample/*/config/*",
                    "com/wgdetective/pactexample/**/mapper/*",
                    "com/wgdetective/pactexample/*/dto/*",
                    "com/wgdetective/pactexample/*/model/*",
                    "com/wgdetective/pactexample/*/entity/*",
                    "com/wgdetective/pactexample/*/exception/*"
                )
            }
        }))
    }
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
}

tasks.register("copyPacts", Copy::class) {
    from("build/pacts/")
    into("src/test/resources/pacts/")
}

fun getGitHash() : String{
    val stdout = org.apache.commons.io.output.ByteArrayOutputStream()
    exec {
        commandLine = "git rev-parse --short HEAD".split(" ")
        standardOutput = stdout
    }
    return String(stdout.toByteArray()).trim()
}

fun getGitBranch() : String {
    val stdout = org.apache.commons.io.output.ByteArrayOutputStream()
    exec {
        commandLine = "git rev-parse --abbrev-ref HEAD".split(" ")
        standardOutput = stdout
    }
    return String(stdout.toByteArray()).trim()
}

//pact {
//    publish {
//        pactDirectory = "build/pacts/"
//        pactBrokerUrl = "http://localhost:9292/"
//        tags = listOf(getGitBranch(), "test", "prod")
//        consumerVersion = getGitHash()
//    }
//}
