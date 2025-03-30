package com.jetbrains.oa30051

import com.intellij.driver.sdk.ui.Finder
import com.intellij.driver.sdk.ui.components.UiComponent
import com.intellij.driver.sdk.ui.components.common.ideFrame
import com.intellij.driver.sdk.waitForIndicators
import com.intellij.ide.starter.ide.IdeProductProvider
import com.intellij.ide.starter.junit5.hyphenateWithClass
import com.intellij.ide.starter.models.TestCase
import com.intellij.ide.starter.project.GitHubProject
import com.intellij.ide.starter.runner.CurrentTestMethod
import com.intellij.ide.starter.runner.Starter
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.minutes

/**
 * Test class that demonstrates opening the Settings dialog via keyboard shortcut,
 * navigating to "Version Control -> Changelists", enabling the "Create changelists automatically"
 * checkbox, and verifying it's selected.
 */
class OpenChangelistsSettingsTest {

    @Test
    fun testEnableCreateChangelistsAutomatically() {
        // Create test context with JetBrains/Exposed project
        val testContext = Starter
            .newContext(
                testName = CurrentTestMethod.hyphenateWithClass(),
                testCase = TestCase(
                    ideInfo = IdeProductProvider.IU,
                    projectInfo = GitHubProject.fromGithub(
                        repoRelativeUrl = "JetBrains/Exposed.git",
                        branchName = "main"
                    )
                )
            )
            .prepareProjectCleanImport()

        // Start IDE with driver and execute test
        testContext.runIdeWithDriver().useDriverAndCloseIde {
            // Wait for project to finish loading
            ideFrame {
                waitForIndicators(5.minutes)

                // Open settings using keyboard shortcut (Command+,) - im on a mac
                keyboard {
                    // macOS: press("meta comma")
                    // Windows/Linux: press("ctrl alt s")
                    press("meta comma")
                }

                // Interact with the settings dialog
                ui.settingsDialog {
                    // Navigate to Version Control > Changelists
                    waitForLoadingComplete()
                    selectVersionControl()
                    selectChangelists()

                    // Check/tick "Create changelists automatically"
                    createChangelistsAutomaticallyCheckbox.apply {
                        waitFound()
                        if (!isSelected()) {
                            click()
                        }

                        // Verify the checkbox is selected
                        assertTrue(isSelected()) {
                            "The 'Create changelists automatically' checkbox should be selected"
                        }
                    }

                    // Hit Ok
                    okButton.click()
                }
            }
        }
    }
}

/**
 * Extension function that provides access to the Settings dialog UI components.
 */
fun Finder.settingsDialog(action: SettingsDialogUI.() -> Unit) {
    // Find the Settings dialog
    x("//div[@title='Settings']", SettingsDialogUI::class.java).action()
}

/**
 * Page Object class representing (some) of the Settings dialog components and actions.
 */
class SettingsDialogUI(data: UiComponent.ComponentData) : UiComponent(data) {

    /**
     * Waits for the settings dialog to fully load its content.
     */
    fun waitForLoadingComplete() {
        x("//div[contains(@class, 'SearchField')]").waitFound()
    }

    /**
     * Selects the Version Control item in the settings tree.
     */
    fun selectVersionControl() {
        // Find and click on the Version Control (dropdown) header in the settings tree.
        x("//div[contains(@class, 'TreeNode') and contains(@text, 'Version Control')]").click()
    }

    /**
     * Selects the Changelists item under Version Control.
     */
    fun selectChangelists() {
        // Find and click on the Changelists item within the expanded Version Control epxandable section.
        x("//div[contains(@class, 'TreeNode') and contains(@text, 'Changelists')]").click()
    }

    /**
     * The "Create changelists automatically" checkbox component.
     */
    val createChangelistsAutomaticallyCheckbox: UiComponent
        get() = x("//div[contains(@class, 'JCheckBox') and contains(@text, 'Create changelists automatically')]")

    /**
     * The OK button in the Settings dialog.
     */
    val okButton: UiComponent
        get() = x("//div[@text='OK' and @class='JButton']")
}

/**
 * Extension function to check if a UI component is selected.
 */
fun UiComponent.isSelected(): Boolean {
    return getAttribute("selected").toBoolean()
}
