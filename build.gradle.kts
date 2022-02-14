import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}

group = "com.crowdstrike"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-web")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.5")
	implementation("redis.clients:jedis")
	implementation("com.redislabs:jredisgraph:2.5.1")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("ch.qos.logback:logback-classic:1.2.10")
	implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")
	implementation("com.google.code.gson:gson:2.8.9")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-params:5.8.2")
	testImplementation("io.mockk:mockk:1.12.2")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
	testImplementation("org.junit.platform:junit-platform-suite-api:1.8.2")


}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register("bootRunLocal"){
	group = "application"
	description = "Runs the Spring Boot application with the local profile"
	doFirst {
		tasks.bootRun.configure {
			systemProperty("spring.profiles.active", "local")
		}
	}
	finalizedBy("bootRun")
}
