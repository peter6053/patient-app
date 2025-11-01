
A mobile app that shows a allow you to register patient, add vitals


## Tech Stack
Tech-stack

Kotlin - a modern, cross-platform, statically typed, general-purpose programming language with type inference.

Coroutines - lightweight threads to perform asynchronous tasks.

Flow - a stream of data that emits multiple values sequentially.

StateFlow - Flow APIs that enable flows to emit updated state and emit values to multiple consumers optimally.

Dagger Hilt - a dependency injection library for Android built on top of Dagger that reduces the boilerplate of doing manual injection.

Jetpack
Jetpack Compose - A modern toolkit for building native Android UI

Lifecycle - perform actions in response to a change in the lifecycle state.

ViewModel - store and manage UI-related data lifecycle conscious manner and survives configuration change.
Room db - store data locally


Timber - a highly extensible Android logger.
Architecture

MVVM - Model View View Model
Gradle

Gradle Kotlin DSL - An alternative syntax for writing Gradle build scripts using Koltin.
Version Catalogs - A scalable way of maintaining dependencies and plugins in a multi-module project.
Convention Plugins - A way to encapsulate and reuse common build configuration in Gradle, see here

## Dependencies
All the dependencies (external libraries) are managed using version catalogs and defined in a single place gradle/libs.versions.toml file. This is a scalable approach to manage dependencies and use the same dependency version across all modules.
## Screenshots

<p float="left">
  <img src="https://github.com/peter6053/patient-app/blob/main/app/src/main/java/com/patientmanagementapp/Utils/screenshots/WhatsApp%20Image%202025-11-01%20at%204.16.12%20PM.jpeg" width="200" />
  <img src="https://github.com/peter6053/patient-app/blob/main/app/src/main/java/com/patientmanagementapp/Utils/screenshots/WhatsApp%20Image%202025-11-01%20at%204.04.15%20PM.jpeg" width="200" />
  <img src="https://github.com/peter6053/patient-app/blob/main/app/src/main/java/com/patientmanagementapp/Utils/screenshots/WhatsApp%20Image%202025-11-01%20at%204.05.22%20PM.jpeg" width="200" />
</p>
