# Tic Tac Toe Kata Implementation - 2024-DEV2-020

![home screen shot](./screenshot_home_page.png) ![player vs player screen shot](./screenshot_player_vs_player_page.png)

## Overview
Anonymized Tic Tac Toe [Kata](https://github.com/stephane-genicot/katas/blob/master/TicTacToe.md]) submission for the BNP Paribas Fortis Android role. Emphasizing TDD, clean code, and software craftsmanship.

## Tech Stack
- Language: **Kotlin**
- UI: **Jetpack Compose**, **Compose Navigation**
- Architecture: **Single Activity UI Architecture**, **MVVM pattern**
- Testing: **JUnit**
- Dependency Injection: **Hilt**
- Build System: **Gradle**
- Code Quality: **Ktlint**

## TDD, Clean-Code, Code Quality Approach
- Unit tests for all game logic
- MVVM
- SOLID principles
- Kotlin coding conventions
- Stateless data classes
- Applied Linter (ktlint)


## Building the project
1. Clone the repository:
```bash
git clone https://github.com/2024-DEV2-020/tic-tac-toe-kata.git
```

2. Open the project in Android Studio

3. Build the project via the IDE UI or CLI (if it's not done automatically):
```bash
./gradlew build
```

## Running the App
1. Connect a physical Android device or start an emulator (Android 7.0 up to Android 15)
2. Run the app via the IDE UI green play button or via the CLI:
```bash
./gradlew installDebug
```

Alternatively download and install the debug APK on an Android device/eumlator from the github releases tab.

## Running the Tests
Android tests are in `app/src/androidTest/java/com`, JUnit tests are in `app/src/test/java/com`. Run via UI or:
```bash
./gradlew test
```
