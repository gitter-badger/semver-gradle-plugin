package com.github.rising3.gradle.semver.plugins

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class SemVerGradlePluginTest extends Specification {
    def "plugin registers task"() {
        given:
        def project = ProjectBuilder.builder().build()

        when:
        project.plugins.apply("com.github.rising3.semver")

        then:
        project.tasks.findByName("semver") != null
    }
}
