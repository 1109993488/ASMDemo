package com.blingbling.plugin.log

import org.gradle.api.Plugin
import org.gradle.api.Project

class LogPlugin implements Plugin<Project> {

    void apply(Project project) {
        System.out.println("------------------开始----------------------")

        // ...

        System.out.println("------------------结束----------------------->")
    }
}