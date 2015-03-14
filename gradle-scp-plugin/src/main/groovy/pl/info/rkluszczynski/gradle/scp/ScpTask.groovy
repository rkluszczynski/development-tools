package pl.info.rkluszczynski.gradle.scp

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ScpTask extends DefaultTask {
    String greeting = 'hello from ScpTask'

    @TaskAction
    def executeScp() {
        println greeting
    }
}
