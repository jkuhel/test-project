plugins {
	// Apply the java-library plugin for API and implementation separation.
	`java-library`
	`maven-publish`
}

repositories {
	// Use Maven Central for resolving dependencies.
	mavenCentral()
}

dependencies {
	// Use JUnit Jupiter for testing.
	testImplementation(libs.junit.jupiter)

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// This dependency is exported to consumers, that is to say found on their compile classpath.
	api(libs.commons.math3)

	// This dependency is used internally, and not exposed to consumers on their own compile classpath.
	implementation(libs.guava)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

tasks.named<Test>("test") {
	// Use JUnit Platform for unit tests.
	useJUnitPlatform()
}

publishing {
  publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
//			from(components["javaPlatform"])
		}
	}
	repositories {
		maven {
			name = "github"
			url = project.uri("https://maven.pkg.github.com/jkuhel/test-project")
			credentials {
				username = project.providers.gradleProperty("herodevs_nes_maven_registry_user").get()
				password = project.providers.gradleProperty("herodevs_nes_maven_registry_token").get()
			}
		}
	}
}

