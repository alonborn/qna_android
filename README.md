# QnA Android App

This project demonstrates a simple Android application that verbally asks the user questions in Hebrew, records spoken answers, and emails the transcript.

## Features
- Uses Android TextToSpeech to speak Hebrew questions.
- Uses Android SpeechRecognizer for Hebrew voice input.
- After all questions are answered, the app opens the email client with the transcript addressed to `alonborn@gmail.com`.

## Building
1. Install **Android Studio** (Arctic Fox or later) and ensure the Android SDK and Kotlin support are installed.
2. Clone this repository:
   ```bash
   git clone <repo-url>
   ```
3. Open the project in Android Studio using *File → Open* and selecting this folder.
4. Let Gradle sync and download dependencies. If prompted, install the Android build tools and a device emulator or connect a real device.
5. Run the `app` configuration on an emulator or device.

## Usage
- Grant the microphone permission when requested.
- The app will automatically speak each question in Hebrew and listen for your verbal response.
- After the final question, your default email app opens with the conversation transcript. Review and send the email.

## Future iOS Version
To implement similar functionality on iOS:
- Use `AVSpeechSynthesizer` for speaking Hebrew questions.
- Use `SFSpeechRecognizer` for voice recognition.
- Collect answers and use `MFMailComposeViewController` to compose an email with the transcript.

The code structure will differ, but the overall flow mirrors the Android version.
