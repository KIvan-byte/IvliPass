
# IvliPass

IvliPass is an Android application designed for secure password management. It allows users to safely store, generate, and manage passwords for different services. The project is built using Android Jetpack and employs cryptographic techniques for enhanced security.

[User Story and Splits](./images/user%20stories.pdf)


## Features

- **Secure Password Storage**: Each password is encrypted using a key stored in the Android KeyStore.
- **Password Generation**: The password generator lets users create strong passwords of various lengths, with optional special characters.
- **Password Strength Check**: Evaluates password strength based on complexity and commonness.
- **User Registration and Login**: Supports encrypted storage of user credentials via EncryptedSharedPreferences.
- **Copy Password**: Easily copy passwords to the clipboard.
- **Account Editing and Deletion**: Manage saved accounts with options to edit or delete entries.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/KIvan-byte/IvliPass
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle and build it.

## Technologies and Libraries Used

- **Android Jetpack**:
  - ViewModel and LiveData for UI state management and data storage.
  - Navigation component for seamless in-app navigation.
- **Security**:
  - Android Keystore for encryption key management.
  - EncryptedSharedPreferences for secure user data storage.
  - Bcrypt for password hashing.
- **Material Design**: UI elements built with Material components for improved user experience.

## Key Components Overview

### Repository (PasswordRepository)

`PasswordRepository` manages password storage and encryption. It leverages `KeyStoreManager` for encryption and `SecurePreferences` for secure data storage.

### Fragments

- **LoginFragment**: Handles user login, verifies credentials, and sets the current user.
- **RegistrationFragment**: Registers a new user with password hashing.
- **GenerationFragment**: Allows users to generate new passwords with custom parameters.
- **CheckFragment**: Checks the strength of a password using commonPasswords to exclude weak choices.
- **SavedFragment**: Displays a list of saved passwords, with options for editing or deletion.

### Model and Adapters

- `Account` — Data model for user accounts, with fields for `service`, `email`, and `password`.
- `AccountAdapter` — RecyclerView adapter for displaying account lists with options to copy or edit passwords.

## Example of work

<img src="./images/example.gif" alt="Example of work" width="250">

## License

This project is licensed under the [Apache License 2.0](LICENSE).
