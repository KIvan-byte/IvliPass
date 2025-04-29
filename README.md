# IvliPass

<p align="center">
  <b>Professional Password Management Solution for Android</b>
</p>

<p align="center">
  <img alt="API Level" src="https://img.shields.io/badge/API-23%2B-brightgreen.svg"/>
  <img alt="License" src="https://img.shields.io/badge/License-MIT-blue.svg"/>
  <img alt="Version" src="https://img.shields.io/badge/Version-1.0.0-orange"/>
  <img alt="Build Status" src="https://img.shields.io/badge/build-passing-brightgreen"/>
</p>

## Overview

IvliPass is a comprehensive Android password management application designed for individuals seeking robust security combined with intuitive usability. The application provides a secure environment for generating, storing, and managing passwords across multiple platforms and services. Utilizing industry-standard encryption protocols and adhering to modern security practices, IvliPass ensures that sensitive user credentials remain protected while maintaining accessibility.

## Key Features

### Security Infrastructure
- **AES-256 Encryption**: Implementation of Advanced Encryption Standard with 256-bit key length for maximum security
- **Secure Key Derivation**: Utilization of PBKDF2 (Password-Based Key Derivation Function 2) with multiple iterations for enhanced protection against brute force attacks
- **Local Data Storage**: All sensitive information is stored exclusively on the user's device, minimizing exposure to network-based vulnerabilities
- **Keystore Integration**: Leveraging Android Keystore System for hardware-backed security when available on supported devices
- **Biometric Authentication**: Support for fingerprint and other biometric authentication methods for convenient and secure access

### User Interface
- **Material Design Implementation**: Clean, intuitive interface adhering to Google's Material Design guidelines
- **Dark Mode Support**: Full application compatibility with both light and dark themes
- **Responsive Layouts**: Optimized for various screen sizes and orientations
- **Accessibility Features**: Compliance with Android accessibility standards for users with disabilities
- **Customizable Views**: User-defined sorting, filtering, and display preferences

### Password Management
- **Advanced Password Generator**: Configurable generation of secure passwords based on user-defined parameters:
  - Length customization (8-64 characters)
  - Character set inclusion (uppercase, lowercase, numbers, symbols)
  - Exclusion of ambiguous characters
  - Pronounceability options
- **Password Health Assessment**: Evaluation of password strength and identification of duplicate or compromised passwords
- **Categorization System**: Hierarchical organization of entries with custom categories and tags
- **Auto-Fill Service**: Integration with Android's auto-fill framework for seamless credential input
- **Import/Export Functionality**: Secure mechanisms for data migration and backup
- **Password History**: Tracking of previous passwords for each entry

### Application Features
- **Quick Search**: Efficient filtering and location of stored entries
- **Clipboard Management**: Secure copy functionality with automatic clipboard clearing
- **Auto-Lock**: Configurable timeouts for automatic application locking
- **Emergency Access**: Optional trusted contact recovery method
- **Activity Logging**: Detailed history of application usage and changes

## Technical Architecture

IvliPass is built on a robust technical foundation:

### Architecture Pattern
- **MVVM (Model-View-ViewModel)**: Clean separation of concerns for maintainability and testability
- **Repository Pattern**: Abstraction of data sources for flexible data management
- **Dependency Injection**: Modular component design for improved testing and maintainability

### Core Technologies
- **Programming Language**: Java
- **Database**: Room Persistence Library with SQLite
- **Encryption**: Android Security Library with AES/GCM implementations
- **UI Framework**: AndroidX and Material Components
- **Authentication**: BiometricPrompt API and AndroidX Biometric library
- **Concurrency**: Java ExecutorService and Android WorkManager

### Data Structure
The application implements a sophisticated data model with the following key entities:
- **User Profile**: Master account information and application preferences
- **Vault Items**: Password entries with associated metadata
- **Categories**: Organizational structure for vault items
- **Audit Logs**: Security event tracking and monitoring

## Security Best Practices

IvliPass adheres to industry-recognized security standards:

1. **Zero-Knowledge Architecture**: The master password is never stored directly; instead, it generates encryption keys
2. **Memory Protection**: Sensitive information is cleared from memory when no longer needed
3. **Secure Random Generation**: Cryptographically secure random number generation for passwords
4. **Tamper Detection**: Verification of data integrity to prevent unauthorized modifications
5. **No Analytics or Telemetry**: No collection or transmission of user data
6. **Regular Security Audits**: Continuous evaluation of security measures and implementations

## Installation Requirements

- Android 6.0 (Marshmallow, API 23) or higher
- Minimum 50MB of available storage space
- Recommended: Fingerprint sensor or other biometric capability for enhanced security

## Installation Guide

1. Download the latest release APK from the [Releases](https://github.com/KIvan-byte/IvliPass/releases) section
2. Enable installation from unknown sources in your device settings if required
3. Install the application and launch
4. Create a master password and complete the initial setup process
5. Optional: Configure biometric authentication for convenience

## Usage Instructions

### Initial Setup
1. Launch the application and create a strong master password
2. Configure security settings including auto-lock timeout and biometric access
3. Optionally set up a recovery method for emergency access

### Managing Passwords
1. Add new entries via the "+" button on the main screen
2. Generate secure passwords using the built-in generator
3. Organize entries into categories for better management
4. Use the search function to quickly locate specific entries
5. Edit or delete entries via the context menu

### Security Maintenance
1. Regularly perform security audits using the built-in password health checker
2. Update weak passwords with stronger alternatives
3. Enable additional security features as needed
4. Perform periodic backups of your vault

## Screenshots

## Example of work

<img src="./images/example.gif" alt="Example of work" width="250">

## Development Roadmap

- [ ] **Cloud Synchronization**: Optional encrypted sync across multiple devices
- [ ] **Password Sharing**: Secure sharing of credentials with trusted contacts
- [ ] **Advanced Analytics**: Password health dashboard with detailed statistics
- [ ] **Browser Extension**: Companion browser extensions for major platforms
- [ ] **Two-Factor Authentication Support**: Management of 2FA tokens within the app

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

**KIvan-byte** - Lead Developer - [GitHub Profile](https://github.com/KIvan-byte)

## Acknowledgments

- The Android Security team for their comprehensive documentation
- Material Design team for UI/UX guidelines
- Open-source cryptography projects that have inspired secure implementations



## License

This project is licensed under the [Apache License 2.0](LICENSE).
