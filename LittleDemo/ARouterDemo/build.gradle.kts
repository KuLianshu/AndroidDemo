class ModuleTypePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val isApplication = project.hasProperty("isDevMode")
        if (isApplication) {
            project.pluginManager.apply("com.android.application")
        } else {
            project.pluginManager.apply("com.android.library")
        }
    }
}