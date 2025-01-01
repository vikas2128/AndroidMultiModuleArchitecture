# AndroidMultiModuleArchitecture
![Screenshot Description](list.png)
![Screenshot Description](detail.png)
## About

This project demonstrates a multi-module architecture for Android applications. It showcases how to organize code into separate modules for improved maintainability, scalability, and testability.

## Features

- **Modular Structure:** The project is divided into distinct modules, each responsible for a specific functionality (e.g., data, domain, feature).
- **Dependency Injection:** Utilizes dependency injection (e.g., Hilt) to manage dependencies between modules.
- **Clean Architecture:** Follows clean architecture principles to separate concerns and improve code organization.
- **Testing:** Includes unit and integration tests to ensure code quality and functionality.

## Getting Started

### Prerequisites

- Android Studio (latest stable version)
- Android SDK
- Kotlin plugin enabled in Android Studio

### Installation

1. Clone the repository: `git clone https://github.com/vikas2128/AndroidMultiModuleArchitecture.git`
2. Open the project in Android Studio.
3. Build the project: `Build` -> `Make Project`

## Modules

- **app:** The main application module that integrates other modules.
- **data:** Responsible for data access and persistence (e.g., network calls, database interactions).
- **domain:** Defines business logic and use cases.
- **featere:** Implements the user interface and presentation logic.

## Architecture

The project follows a clean architecture approach, with the following layers:

- **Presentation:** UI components and view models.
- **Domain:** Use cases and business logic.
- **Data:** Data sources and repositories.

## Testing

- **Unit Tests:** Located in the `src/test` directory of each module.

## Contributing

Contributions are welcome! Please open an issue or pull request to discuss any changes or improvements.
