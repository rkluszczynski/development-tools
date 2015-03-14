package pl.info.rkluszczynski.gradle.scp

import org.gradle.api.Plugin
import org.gradle.api.Project

class ScpPlugin implements Plugin<Project> {
    void apply(Project target) {
        target.task('scp', type: ScpTask)
    }
}
