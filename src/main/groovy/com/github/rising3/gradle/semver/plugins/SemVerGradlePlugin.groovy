package com.github.rising3.gradle.semver.plugins

import com.github.rising3.gradle.semver.tasks.SemVerTask
import org.gradle.api.Project
import org.gradle.api.Plugin

/**
 * Semantic Versioning Gradle Plugin.
 *
 * @author rising3
 */
class SemVerGradlePlugin implements Plugin<Project> {
    /**
     * task name.
     */
    private static final String TASK_NAME = 'semver'

    @Override
    void apply(Project project) {
        project.extensions.create(TASK_NAME, SemVerGradlePluginExtension)
        project.task(TASK_NAME, type: SemVerTask)
    }
}
