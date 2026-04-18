package com.intellij.ide.starter.examples.driver

import com.intellij.driver.sdk.ui.components.UiComponent.Companion.waitFound
import com.intellij.driver.sdk.ui.components.common.ideFrame
import com.intellij.driver.sdk.ui.components.elements.checkBoxWithName
import com.intellij.driver.sdk.ui.components.settings.settingsDialog
import com.intellij.ide.starter.config.ConfigurationStorage
import com.intellij.ide.starter.config.splitMode
import com.intellij.ide.starter.driver.engine.runIdeWithDriver
import com.intellij.ide.starter.ide.IdeProductProvider
import com.intellij.ide.starter.junit5.hyphenateWithClass
import com.intellij.ide.starter.models.TestCase
import com.intellij.ide.starter.project.GitHubProject
import com.intellij.ide.starter.runner.CurrentTestMethod
import com.intellij.ide.starter.runner.Starter
import com.intellij.ide.starter.sdk.JdkDownloaderFacade.jdk21
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.time.Duration.Companion.minutes

class UiSettingsTestWithDriver {

    /**
     * Opens Settings, chooses "Version Control" and then "Changelists",
     * selects the checkbox that is called "Create changelists automatically",
     * asserts that it is selected and clicks on the OK button.
     *
     * @param splitMode Determines the mode of execution. If true, the test runs on a remote development environment
     * with IDE backend and frontend running on the same host.
     */
    @ParameterizedTest(name = "split-mode={0}")
    @ValueSource(booleans = [false, true])
    fun openSettingsSelectCreateChangelistsAutomatically(splitMode: Boolean) {
        ConfigurationStorage.splitMode(splitMode)

        val testContext = Starter
            .newContext(CurrentTestMethod.hyphenateWithClass(), TestCase(IdeProductProvider.IU, GitHubProject.fromGithub(
                branchName = "master",
                repoRelativeUrl = "LuisJoseSanchez/hello-world-java.git",
                commitHash = "4947e1e9e32f20606f186e5f7267286b848beb3e"
            )))
            .setupSdk(jdk21.toSdk())
            .setLicense(System.getenv("LICENSE_KEY"))
            .prepareProjectCleanImport()

        testContext.runIdeWithDriver().useDriverAndCloseIde {
            ideFrame {
                waitForIndicators(5.minutes)
                openSettingsDialog()
                settingsDialog {
                    waitFound()

                    openTreeSettingsSection("Version Control", "Changelists")

                    val checkbox = checkBoxWithName("Create changelists automatically").waitFound()
                    checkbox.check()

                    assertTrue(checkbox.isSelected()) { "Checkbox should be selected" }

                    okButton.click()
                }
            }
        }
    }
}