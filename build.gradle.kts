import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.5"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("com.arenagod.gradle.MybatisGenerator") version "1.4"
	kotlin("jvm") version "1.7.0"
	kotlin("plugin.spring") version "1.7.0"
}

group = "com.book.manager"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_15

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.2")
	implementation("org.mybatis.dynamic-sql:mybatis-dynamic-sql:1.4.1")
	implementation("mysql:mysql-connector-java:8.0.30")
	mybatisGenerator("org.mybatis.generator:mybatis-generator-core:1.4.1")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.session:spring-session-data-redis")
	implementation("redis.clients:jedis")
	implementation("org.springframework.boot:spring-boot-starter-aop")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.1")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
	testImplementation("org.assertj:assertj-core:3.23.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "15"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

mybatisGenerator {
	verbose = true
	configFile = "${projectDir}/src/main/resources/generatorConfig.xml"
	dependencies {
		mybatisGenerator(group="mysql", name="mysql-connector-java", version="8.0.30")
	}
}
