# Smart Pantry Manager

A Java Android app that reduces food waste. The user records the ingredients
they already have, and the app suggests only the recipes they can make right now.

## Features
- Add, edit and delete pantry items (name, quantity, unit, optional expiry date)
- 20 pre-loaded recipes
- Strict matching: a recipe is suggested only if every ingredient is available
  in the required quantity (handles plurals and unit differences)
- Recipe detail screen, plus settings (expiry highlight, default unit)

## Database: SQLite (SQLiteOpenHelper)
It runs on the device, needs no server or internet, and keeps data between
sessions. The data is small and relational (recipes have many ingredients).

## Setup
1. Clone the repository
2. Open it in Android Studio and let Gradle sync
3. Run on an emulator or phone (min SDK 24)

Built with Java only. No maps or location features.
