# GitIdentityChecker

This is an intellij plugin that checks that `user.name` and `user.mail` are defined as part of the repo's git config.
It also has an option to prevents any git commits after 18:00.
In runs as part of the standard 'commit' button action and shows an error message if the two conditions are not
fulfilled.

Motivation is to prevent commits under the wrong mail address when working on multiple repositories (work, github, private bitbucket,...).

## Usage options
- Manual check via "Tools" menu and "Check Local Git Config...".
- Automatic check before each commit, shows yes/no dialog if no local name/mail are found.

## Development
### ## Requirements
- IntellijIDEA 2023.2.4 or later
- written in Kotlin, Gradle, ...

### Where is the code?
  - All the logic is in `src/main/kotlin/com/github/TimeCheckinHandlerFactory.kt`
  - The plugin components are registered in `src/main/resources/META-INF/plugin.xml`

### Debugging

- Open the project in Intellij, set breakpoints and click the debug icon. A new IntelliJ Community Edition window will
  open and stop on breakpoints.
- There is also a log available, menu 'Help', 'Show log in files' (old UI also allows to open log in IDE), look for
  lines containing "GitIdentityChecker".

## Installation
- Invoke the `buildPlugin` Gradle task to create the plugin distribution
- The resulting ZIP file is located in `build/distributions` and can be installed via "Install Plugin from Disk..." 
- read more: https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html#building-distribution

## Release Process
- increase version in `build.gradle.kts`
- build a zip, appears in [build/distributions](build/distributions)
- upload manually via https://plugins.jetbrains.com/plugin/add#intellij


