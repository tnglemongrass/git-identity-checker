# NoCommitsAfterSix

This is an intellij plugin that checks that `user.name` and `user.mail` are defined as part of the repo's git config.
It also has an option to prevents any git commits after 18:00.
In runs as part of the standard 'commit' button action and shows an error message if the two conditions are not
fulfilled.

## Requirements
- IntellijIDEA 2023.2.4 or later
- Kotlin, Gradle, ...

## Development

Where is the code?
  - All the logic is in `src/main/kotlin/com/yourplugin/TimeCheckinHandlerFactory.kt`
  - The plugin components are registered in `src/main/resources/META-INF/plugin.xml`

## Debugging

- Open the project in Intellij, set breakpoints and click the debug icon. A new IntelliJ Community Edition window will
  open and stop on breakpoints.
- There is also a log available, menu 'Help', 'Show log in files' (old UI also allows to open log in IDE), look for
  lines containing "NoCommitsAfterSix".