[![Build Status](https://travis-ci.org/PromasterGuru/nairobi-java-developers.svg?branch=develop)](https://travis-ci.org/PromasterGuru/nairobi-java-developers)
[![CircleCI](https://circleci.com/gh/PromasterGuru/nairobi-java-developers.svg?style=svg)](https://circleci.com/gh/PromasterGuru/nairobi-java-developers)
[![codecov](https://codecov.io/gh/PromasterGuru/nairobi-java-developers/branch/develop/graph/badge.svg)](https://codecov.io/gh/PromasterGuru/nairobi-java-developers)
[![Maintainability](https://api.codeclimate.com/v1/badges/a141aa73b0bf051ca102/maintainability)](https://codeclimate.com/github/PromasterGuru/nairobi-java-developers/maintainability)

# ConvergeCodelab

Android Application that displays a list Nairobi Java Developers

## Wireframes

All the wireframes are designed using **[Figma](https://www.figma.com/)** wireframing tool. The main reason for choosing this tool is that it is easy to learn and allow developers to share wireframes and collect feedback, in real time or asynchronously, by simply sharing a link.

| ![image](https://user-images.githubusercontent.com/39240075/54424140-519a3980-4723-11e9-8a63-5d36a2e7e861.png) | ![image](https://user-images.githubusercontent.com/39240075/54424143-5363fd00-4723-11e9-9ddc-b0289609f89c.png) | ![image](https://user-images.githubusercontent.com/39240075/54424132-4e06b280-4723-11e9-9640-ecef45054495.png) |

## App color palettes

| Color Code           | Image                                                                                                          |
| -------------------- | -------------------------------------------------------------------------------------------------------------- |
| **Activity Title**   | ![image](https://user-images.githubusercontent.com/39240075/53101612-2f096c00-353b-11e9-86c5-9c8b02120318.png) |
| **App Title**        | ![image](https://user-images.githubusercontent.com/39240075/53101631-392b6a80-353b-11e9-8b15-cf1da2a7f325.png) |
| **Item list layout** | ![image](https://user-images.githubusercontent.com/39240075/53282218-c0e4c500-3745-11e9-82f9-7653b68eee81.png) |
| **Toolbar Title**    | ![image](https://user-images.githubusercontent.com/39240075/53101664-46485980-353b-11e9-808f-09e57982b8d1.png) |
| **Usernames**        | ![image](https://user-images.githubusercontent.com/39240075/53101695-58c29300-353b-11e9-98f8-bba4fbec5630.png) |

## Getting Started

Deploy the project on your local machine.

### Prerequisites

Download and install **[Android Studio](https://developer.android.com/studio)**

### Installing

1. Get a copy of the project on your machine by running the following command on the terminal:
   ```git clone https://github.com/PromasterGuru/nairobi-java-developers.git````

2. Open the project on Android Studio by clicking on:
   `File -> New -> Import Project... -> <Project folder>`

3. Create a `.env.secret` file in the Fastlane directory and add the following `ENV` variables:

```
  SLACK_URL=[URL to your slack workspace]
  SLACK_API_TOKEN=[slack API token]
  SLACK_CHANNEL="name of channel you want to share the apk file "
```

4. Build the project:
   `./gradlew build`

## Running the tests

First launch the emulator or connect your android device via a USB cable in debugging mode.
Run both UI and Unit tests with the following command:
`./gradlew JacocoTestReport`

## Deploy

You can either run the project on emulator or your android mobile device. To run the app on your device, connect the device to your machine via a USB cable with debugging mode.
Deploy the app by clicking on the `Run` button on the Toolbar

## Built With

1. **[Android Studio](https://developer.android.com/studio)** - IDE.
2. **[Java](https://www.oracle.com/java/)** - Programming Language for backend.
3. **[XML](https://www.xml.com/)** - Markup Language for the frontend.
4. **[Gradle](https://gradle.org/)** - Used to manage dependencies.

## Authors

**[Promaster Guru](https://github.com/PromasterGuru/)**

## License

This project is licensed under the **[MIT](https://badges.mit-license.org/)** License.

## Acknowledgments

My special thanks and gratitude goes to

1. Andela Converge App collaborators
2. Github Community
3. Stackoverflow Contributors
