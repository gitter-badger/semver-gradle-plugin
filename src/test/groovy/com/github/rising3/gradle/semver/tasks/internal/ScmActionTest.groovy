package com.github.rising3.gradle.semver.tasks.internal

import com.github.rising3.gradle.semver.SemVer
import com.github.rising3.gradle.semver.scm.ScmProvider
import spock.lang.Specification

class ScmActionTest extends Specification {
    ScmProvider scm
    ScmAction target

    def setup() {
        scm = Mock()
        target = new ScmAction(scm)
    }

    def "default"() {
        given:
        target.setNoCommand(false)
        target.setNoTagVersion(false)
        target.setVersionMessage('version %s release')
        target.setVersionTagPrefix('ver')

        when:
        target('1.0.0', 'filename')

        then:
        1 * scm.add('filename')
        1 * scm.commit('version 1.0.0 release')
        1 * scm.tag('ver1.0.0', 'version 1.0.0 release', true)
    }

    def "No SCM command"() {
        given:
        target.setNoCommand(true)
        target.setNoTagVersion(false)
        target.setVersionMessage('v%s')
        target.setVersionTagPrefix('v')

        when:
        target('1.0.0', 'filename')

        then:
        0 * scm.add('filename')
        0 * scm.commit('v1.0.0')
        0 * scm.tag('v1.0.0', 'v1.0.0', true)
    }

    def "No tag version"() {
        given:
        target.setNoCommand(false)
        target.setNoTagVersion(true)
        target.setVersionMessage('v%s')
        target.setVersionTagPrefix('v')

        when:
        target('1.0.0', 'filename')

        then:
        1 * scm.add('filename')
        1 * scm.commit('v1.0.0')
        0 * scm.tag('v1.0.0', 'v1.0.0', true)
    }
}
