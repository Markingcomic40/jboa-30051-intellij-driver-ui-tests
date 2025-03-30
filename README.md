# JetBrains OA 30051 – UI Driver Test - Luis Fernandez Gu

This repository contains a UI test for the JetBrains IDEs (specifically verified on IntelliJ) using the IntelliJ IDE Starter framework.

## Test Scenario

- Open IntelliJ IDE with the [JetBrains/Exposed](https://github.com/JetBrains/Exposed) project
- Open Settings
- Navigate to **Version Control > Changelists**
- Enable the **"Create changelists automatically"** checkbox
- Confirm the checkbox is selected
- Click **OK** to apply the setting

## Repo structure:

The test file is located in: [`src/test/kotlin/com/jetbrains/oa30051/OpenChangelistsSettingsTest.kt`](src/test/kotlin/com/jetbrains/oa30051/OpenChangelistsSettingsTest.kt) within:

```python
.
├── README.md
├── build.gradle.kts
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradle.properties
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
└── src
    ├── main
    │   ├── kotlin
    │   │   └── Main.kt
    │   └── resources
    └── test
        ├── kotlin
        │   └── com
        │       └── jetbrains
        │           └── oa30051
        │               └── OpenChangelistsSettingsTest.kt
        └── resources
```

Note that due to internal dependency requirements, this project won't run as is. The code is written following convetions used and stated in the [intellij-ide-starter](https://github.com/JetBrains/intellij-ide-starter) project.
