# Tasky - Calendar App

Calendar App intended to portray all the new technologies I learn on a daily basis

## Authentication Screens
Login             |  Registration
:-------------------------:|:-------------------------:
![Login Screen](https://i.imgur.com/aeFMlIs.png) | ![Registration Screen](https://i.imgur.com/qVObVca.png)

## Agenda Screen
![Agenda Screen](https://i.imgur.com/0aZ1JHH.png)

## 🎥 Demo Video
![Demo Video of the App](https://i.imgur.com/PIdcjea.gif)

## 🛠 Tech Stack

- Kotlin
- MVVM/MVI
- Jetpack Compose
- Retrofit
- Room
- Hilt
- Coil
- Moshi
- WorkManager + AlarmManager
- Notifications
- SplashScreen
- Jetpack Security - Encrypted SharedPreferences
- Compose Navigation
- MockWebServer
- Mockk
- Truth
- JUnit
- SOLID
- Clean Architecture

## Features
- Authentication with Login and Registration.
- SplashScreen that redirects based whether the user is logged in or not.
- Home Screen showcasing Events, Reminders and Tasks.
- Event, Task and Reminder create, update, and delete.
- Detail Screen for each Agenda Item Type, each with it's own settings.
- Save on both API and DB.
- Sync to the API in case of Offline usage.
- Sync from the API in case the user was invited to an Event.
- Notifications to remind the user when there's an upcoming Event/Reminder/Task.
- Logout.
- Add Photos to Events.


## Contributing

Contributions are always welcome!

## TODO
- Set notifications from remote events
- Fix notifications on boot
- Update Event (Fix Photo uploading and deleting)
- Sync Deleted, Created, and Updated
- Fix Event Detail screen Editing in case the user isn't a host and such
- Save photos on DB
- Fix Event Detail photos when editing/is not the host, etc
- Error handling in case there's no connection (Show a snack bar or something)
- What happens if the api-key stops working or the user stops being authed? Investigate
- Black Line (Needle) in the Agenda screen to give the impression of past and future items
- Fix the options menu after clicking the 3 dots in an agenda item (It only appears in the middle of the screen)
- Fix the > in the Event Detail Screen - From time
- Agenda screen items sometimes don't update on change visually, the current fix is to change the date and go back to the same day
- Delete visitor from event
- Testing!
