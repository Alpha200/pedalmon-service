plugins {
	kotlin("jvm") version "2.1.20"
	kotlin("plugin.spring") version "2.1.21"
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "2.1.21"
}

group = "eu.sendzik"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.osgeo.org/repository/release/") }
}

val hibernateVersion = "6.6.13.Final"
val geotoolsVersion = "33.1"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-database-postgresql")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.locationtech.jts:jts-core")
	implementation("org.hibernate:hibernate-spatial:${hibernateVersion}")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
	implementation("org.geolatte:geolatte-geom")
	implementation("org.geotools:gt-main:${geotoolsVersion}") {
		exclude(group = "javax.media", module = "jai_core")
	}
	implementation("org.geotools:gt-epsg-wkt:${geotoolsVersion}") {
		exclude(group = "javax.media", module = "jai_core")
	}
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
