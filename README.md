# 📦 EchoBox

**EchoBox** is a lightweight Android Jetpack Compose library for collecting user feedback inside your app with zero friction.

<img width="270" height="600" alt="Image" src="https://github.com/user-attachments/assets/1eb72ce8-5ddb-44be-86e1-111e45c85bbb" />

## ✨ Why use EchoBox?

Many users never leave Play Store reviews or open external support channels. EchoBox makes it easy to:

- Collect feedback **without leaving the app**
- Capture **emoji-based sentiment** (like/dislike)
- Let users write short feedback messages
- Automatically attach helpful context like app version and device info
- Send feedback via **email** no backend required

All in under a minute.  
No setup, no log collection, no complexity.


## 💡 Use Cases

- Quickly test how users feel about a new feature
- Let beta testers report issues without screenshots/logs
- Gather lightweight feedback on design changes
- Provide a fallback feedback option when no backend exists

## ⚙️ Installation

EchoBox is available via [JitPack](https://jitpack.io).  

Add it to your project in two simple steps:

### 1. Add JitPack repository in your settings.gradle file:
```
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}
```
### 2. Add the library dependency in your app/build.gradle:
```
dependencies {
    implementation "com.github.amitcodr:EchoBox:1.0.0"
}
```
(Replace 1.0.0 with the latest version from JitPack)

[![](https://jitpack.io/v/amitcodr/EchoBox.svg)](https://jitpack.io/#amitcodr/EchoBox)

## 📷 Demo

<img src="https://github.com/user-attachments/assets/92f8baeb-892e-4cb8-8d15-3fe247e926f6" width="250">


## 🚧 What's next?

This is just the beginning. Planned features:

- Optional screenshot support
- API endpoint support (upload instead of email)
- Theme customization
- GitHub integration (open issues directly?)

Pull requests, feedback, and suggestions are welcome!



## 🙌 Built by @amitcodr

EchoBox is an open-source passion project focused on improving developer & user communication without bloat.

If you use it, star the repo and spread the word! ⭐


## 📄 License

EchoBox is licensed under the [MIT License](LICENSE).  
You can freely use, modify, and distribute it in personal or commercial projects.
