#Contacts app

A simple contacts application.

###Description
- Added smooth Material animations
- Created custom Views for managing multiple inputs of the same type in a form.
- Implemented a CRUD and search using Realm as a database
- Coded using Clean Architecture principles
- Followed Material Design guidelines
- Implemented efficient branded launch screen

###Screenshots
![Intro animation](screenshots/intro_animation.gif?raw=true)
![Search bar animation](screenshots/search_bar_animation.gif?raw=true)
![Save animation](screenshots/save_animation_v2.gif?raw=true)
![BottomSheet](screenshots/bottom_sheet.gif?raw=true)
![Add contact screen](screenshots/add_contact_scren.png?raw=true) 

###Behavior
The app shows basic CRUD functionality and search implementations according to this set of
rules:

- Must store a first name
- Must store a last name
- An optional date of birth
- Zero or more addresses
- One or more phone numbers
- One or more emails.

### Technologies and libraries
- [Kotlin](https://kotlinlang.org/): as preferred programming language.
- [RxJava](https://github.com/ReactiveX/RxJava): for composing asynchronous calls.
- [JavaFaker](https://github.com/DiUS/java-faker): used to fill the database of fake user data.
- [Realm](https://realm.io/): as preferred database.
