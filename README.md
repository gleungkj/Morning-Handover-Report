# Morning Handover Report

A simple Android application built with Kotlin and Jetpack Compose.

## Prerequisites

Before you begin, ensure you have the following installed:

1.  **Java Development Kit (JDK):** The project is configured to use JDK 11. You can use the one bundled with Android Studio.
2.  **Android Studio:** The latest stable version is recommended for the best experience.

## Getting Started

Follow these instructions to get the project up and running on your local machine.

### 1. Clone the Repository

First, clone the repository to your local machine using Git:

```sh
git clone <repository-url>
cd Morning-Handover-Report
```

### 2. Configure the JAVA_HOME Environment Variable

For the Gradle build tool to work correctly from the command line, you need to set the `JAVA_HOME` environment variable.

1.  **Find your JDK Path:**
    *   Open the project in Android Studio.
    *   Navigate to **File > Settings > Build, Execution, Deployment > Build Tools > Gradle**.
    *   Copy the path listed in the **Gradle JDK** field. It will look something like `/path/to/your/android-studio/jbr`.

2.  **Set the Environment Variable:**
    *   Open your terminal and run the following commands, replacing the placeholder path with the one you copied:

      ```sh
      export JAVA_HOME="/path/to/your/jdk"
      export PATH="$JAVA_HOME/bin:$PATH"
      ```

    *   **To make this change permanent**, add these two `export` lines to your shell's configuration file (e.g., `~/.bashrc` for Bash or `~/.zshrc` for Zsh) and restart your terminal.

### 3. Build the Project

Once `JAVA_HOME` is set, you can build the project from the command line to download all dependencies and compile the application.

*   On macOS or Linux:
    ```sh
    ./gradlew build
    ```
*   On Windows:
    ```sh
    gradlew.bat build
    ```

### 4. Run the App

You can run the application in two main ways:

*   **From Android Studio (Recommended):**
    1.  Open the project folder in Android Studio.
    2.  Allow Gradle to sync and download the project dependencies.
    3.  Select a run configuration (usually `app`).
    4.  Choose a target device (emulator or a physical device).
    5.  Click the **Run** button (green play icon).

*   **From the Command Line:**
    You can install the app directly onto a connected device or a running emulator with the following Gradle command:

    ```sh
    ./gradlew installDebug
    ```
