package org.detoeuf

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.junit.Assert.assertTrue

class SwaggerCodeGenTaskTest {
    @Test
    public void canAddTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('swagger', type: SwaggerCodeGenTask)
        assertTrue(task instanceof SwaggerCodeGenTask)
    }

    @Test
    public void basicConfiguration() {

        URL url = Thread.currentThread().getContextClassLoader().getResource('petstore.yaml');
        File file = new File(url.getPath());

        Project project = ProjectBuilder.builder().build()
        def task = project.task('swagger', type: SwaggerCodeGenTask)

        def pluginExtension = new SwaggerPluginExtension(
                inputSpec: file.toString(),
                output: 'target/generated-sources/swagger',
                language: 'java',
                additionalProperties: [
                        'apiPackage': 'com.detoeuf.testApi',
                        'configPackage': 'com.detoeuf.testConfig',
                        'invokerPackage': 'com.detoeuf.testPackage',
                        'modelPackage': 'com.detoeuf.testModel',
                        'library': 'okhttp-gson',
                        'dateLibrary': 'java8'
                ]
        )

        project.extensions.add('swagger', pluginExtension)
        assertTrue(task instanceof SwaggerCodeGenTask)
        task.execute()
    }

    @Test
    public void onlyApisAndModels() {

        URL url = Thread.currentThread().getContextClassLoader().getResource('petstore.yaml');
        File file = new File(url.getPath());

        Project project = ProjectBuilder.builder().build()
        def task = project.task('swagger', type: SwaggerCodeGenTask)

        def pluginExtension = new SwaggerPluginExtension(
                inputSpec: file.toString(),
                output: 'target/generated-sources/swagger',
                language: 'java',
                additionalProperties: [
                        'apiPackage': 'com.detoeuf.testApi',
                        'configPackage': 'com.detoeuf.testConfig',
                        'invokerPackage': 'com.detoeuf.testPackage',
                        'modelPackage': 'com.detoeuf.testModel',
                        'library': 'okhttp-gson',
                        'serializableModel': 'true'
                ],
                apis: '',
                models: '',
                cleanOutputDir: 'false'
                
        )

        project.extensions.add('swagger', pluginExtension)
        assertTrue(task instanceof SwaggerCodeGenTask)
        task.execute()
    }

}
