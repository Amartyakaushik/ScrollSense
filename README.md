# ScrollSense

## Overview
ScrollSense is an Android application that showcases a dynamic list of items with search functionality and pagination. The app utilizes a mock API to simulate data fetching, providing a smooth user experience with a shimmer loading effect and a splash screen.

## Features
- **Dynamic Item List**: Displays a list of items fetched from a mock API.
- **Search Functionality**: Users can search for items, with highlighted search terms in the results.
- **Pagination**: Supports loading more items as the user scrolls up or down.
- **Shimmer Effect**: Provides a visual loading indicator while data is being fetched.
- **Splash Screen**: Displays an introductory screen before launching the main activity.

## APK Download
You can download the APK for ScrollSense from the following link:
- https://github.com/Amartyakaushik/ScrollSense/releases/tag/v1.0.0

## Technologies Used
- **Programming Languages**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Libraries**:
    - RecyclerView for displaying lists
    - Shimmer for loading animations
    - LiveData and ViewModel for managing UI-related data

## Setup and Running Instructions

1. **Clone the Repository**:
   Open your terminal and run the following command to clone the repository:
   ```bash
   git clone https://github.com/Amartyakaushik/ScrollSense.git
   ```

2. **Open the Project**:
    - Launch Android Studio.
    - Click on "Open an existing Android Studio project."
    - Navigate to the cloned repository folder and select it.

3. **Install Dependencies**:
    - Ensure you have the latest version of Android Studio and the necessary SDKs installed.
    - Open the `build.gradle` files (both project-level and app-level) to check for any dependencies that need to be synced.
    - Click on "Sync Now" if prompted.

4. **Build the Project**:
    - Click on "Build" in the top menu and select "Rebuild Project" to ensure everything is set up correctly.

5. **Run the Application**:
    - Connect an Android device or start an emulator.
    - Click on the green "Run" button (or press Shift + F10) to launch the application.

6. **Usage**:
    - Upon launching the app, you will see a splash screen for 1.7 seconds.
    - After the splash screen, the main activity will display a list of items.
    - Use the search bar at the top to filter items based on the search query.
    - Scroll down to load more items dynamically.

## Code Structure
- **MainActivity.kt**: The main entry point of the application, responsible for setting up the UI and handling user interactions.
- **SplashScreen.kt**: Displays an introductory screen before launching the main activity.
- **ItemRepository.kt**: Manages data operations and acts as a bridge between the mock API and the app.
- **MockApi.kt**: Simulates fetching data asynchronously, generating mock data with artificial delays.
- **ItemAdapter.kt**: Adapter class for displaying items in a RecyclerView, supporting search query highlighting.
- **ItemViewModel.kt**: Manages the items and their filtered state, handling data loading and filtering.
- **ViewModelFactory.kt**: Custom factory for creating ViewModel instances with the repository.

## Contributing
Contributions are welcome! Please feel free to submit a pull request or open an issue for any suggestions or improvements.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
