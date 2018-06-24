# Notes example app

<img hspace="20" height="400" src="github/screenshot_home.png" alt="Screenshot of home activity"/><img  hspace="20" height="400" src="github/screenshot_note_detail.png" alt="Screenshot of note detail"/><img hspace="20" height="400" src="github/screenshot_edit_note.png" alt="Screenshot of edit note"/>

This repository contains the source code for the Notes example app.

You will need JDK 1.7+ installed to work with it.
Gradle, Android SDK and project dependencies will be downloaded automatically.

## Building the app

#### 1. Set API base URL. (optional)

I have created a default API for this application on http://bsc-notes.duke-hq.net/api/. If you wish to provide a custom one, you need to edit `NOTES_BASEURL_API` variable in `app/build.gradle`.

```
$ vim app/build.gradle
```
```
buildConfigField "String", "NOTES_BASEURL_API", "\"URL"\"
```

#### 2. Build the application using command line or using GUI.

To build a debug APK, run:

```
$ ./gradlew clean assembleDebug
```
  
This creates an APK named `app-debug.apk` in `project_name/module_name/build/outputs/apk/`. The file is already signed with the debug key and aligned with `zipalign`, so you can immediately install it on a device.

#### 3. Or to build the APK and immediately install it on a running emulator or connected device, instead invoke `installDebug`:

```
$ gradlew installDebug
```

To see all the build and install tasks available for each variant (including uninstall tasks), run the `tasks` task.

```
$ ./gradlew tasks
```

## Running unit tests

You can run tests targeting specific build variant using following syntax:

```
$ ./gradlew testVariantNameUnitTest
```

To run the tests targeting debug variant, run:

```
$ ./gradlew testDebugUnitTest
```
