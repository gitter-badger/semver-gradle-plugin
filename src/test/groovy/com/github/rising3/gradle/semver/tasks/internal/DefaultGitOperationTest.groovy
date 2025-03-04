/*
 * Copyright (C) 2021 rising3 <michio.nakagawa@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.rising3.gradle.semver.tasks.internal


import com.github.rising3.gradle.semver.git.GitProvider
import com.github.rising3.gradle.semver.plugins.SemVerGradlePluginExtension
import spock.lang.Specification

class DefaultGitOperationTest extends Specification {
    private SemVerGradlePluginExtension ext
    private GitProvider git
    private DefaultGitOperation target

    def setup() {
        ext = new SemVerGradlePluginExtension()
        git = Mock(GitProvider)
    }

    def "Should create commit, and tag"() {
        given:
        ext.versionGitMessage = 'version %s release'
        ext.versionTagPrefix = 'ver'
        ext.noGitCommitVersion = false
        ext.noGitTagVersion = false
        ext.noGitPush = true
        ext.noGitPushTag = true
        target = new DefaultGitOperation(git, ext)

        when:
        target('1.0.0', ['filename'])

        then:
        1 * git.add('filename')
        1 * git.commit('version 1.0.0 release')
        1 * git.tag('ver1.0.0', 'version 1.0.0 release', true)
    }

    def "Should not create commit"() {
        given:
        ext.versionGitMessage = 'v%s'
        ext.versionTagPrefix = 'v'
        ext.noGitCommitVersion = true
        ext.noGitTagVersion = false
        ext.noGitPush = true
        ext.noGitPushTag = true
        target = new DefaultGitOperation(git, ext)

        when:
        target('1.0.0', ['filename'])

        then:
        0 * git.add('filename')
        0 * git.commit('v1.0.0')
        1 * git.tag('v1.0.0', 'v1.0.0', true)
    }

    def "Should not create tag"() {
        given:
        ext.versionGitMessage = 'v%s'
        ext.versionTagPrefix = 'v'
        ext.noGitCommitVersion = false
        ext.noGitTagVersion = true
        ext.noGitPush = true
        ext.noGitPushTag = true
        target = new DefaultGitOperation(git, ext)

        when:
        target('1.0.0', ['filename'])

        then:
        1 * git.add('filename')
        1 * git.commit('v1.0.0')
        0 * git.tag('v1.0.0', 'v1.0.0', true)
    }
}
