 # Flappy Bird - Multi-Level Java Game  

This project is a fully functional multi-level version of the popular Flappy Bird game, built in Java using Swing components.  
Players can choose between three difficulty modes, each with its own physics and obstacle behavior.

The game features real-time collision detection, dynamic animations, and persistent high score tracking through a simple leaderboard system.

Users interact via a graphical interface, and the application manages game logic, rendering, and data persistence seamlessly.

---

## Features

- Playable Flappy Bird clone written entirely in Java (no external libraries)
- Three difficulty levels:
  - **Classic Mode**: Default speed and pipe gaps
  - **Hard Mode**: Faster movement and narrower gaps
  - **Extreme Mode**: Unforgiving challenge with tighter timing
- Real-time bird physics and collision detection
- Pipe spawning and scrolling animation
- Score tracking and persistent leaderboard
- GUI menu for mode selection and results display
- Ability to restart the game on game over
- Simple persistent storage for saving high scores

---

## Usage
Once launched, the application displays a menu allowing the player to:

Select a game mode (Classic, Hard, Extreme)

Play the game using spacebar to flap the bird

View their current score in real time

Restart after crashing

View top scores on the leaderboard

All high scores are stored in a local file and displayed by mode.

---

## Notes
The game is fully offline and does not require internet access.

Leaderboards are stored locally (as plain text files or Java-serialized objects, depending on implementation).

The game window is resizable but optimized for default screen dimensions.

No external dependencies or libraries are required — everything runs with built-in Java libraries.
