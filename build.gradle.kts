import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.detekt)
}

val detektFormatting = libs.detekt.formatting

subprojects {
    apply (plugin = "io.gitlab.arturbosch.detekt")
    dependencies {
        detektPlugins(detektFormatting)
    }
    detekt {
        config.from(rootProject.files("config/detekt/detekt.yml"))
    }
    apply (plugin = "com.diffplug.spotless")
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            ktlint().editorConfigOverride(
                mapOf(
                    "max_line_length" to 2147483647,
                    "ktlint_code_style" to "android",
                    "function-start-of-body-spacing" to false,
                    "ij_kotlin_allow_trailing_comma" to true,
                    "disabled_rules" to
                            "function-start-of-body-spacing," +
                            "filename," +
                            "annotation,annotation-spacing," +
                            "argument-list-wrapping," +
                            "double-colon-spacing," +
                            "enum-entry-name-case," +
                            "multiline-if-else," +
                            "no-empty-first-line-in-method-block," +
                            "package-name," +
                            "trailing-comma," +
                            "spacing-around-angle-brackets," +
                            "spacing-between-declarations-with-annotations," +
                            "spacing-between-declarations-with-comments," +
                            "unary-op-spacing"
                )
            )
            // licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        }

        kotlinGradle {
            target("*.gradle.kts")
            ktlint()
        }
    }

}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
        xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
        txt.required.set(true) // similar to the console output, contains issue signature to manually edit baseline files
        sarif.required.set(true) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with GitHub Code Scanning
        md.required.set(true) // simple Markdown format
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
}



tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}