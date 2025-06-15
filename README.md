# 🐦 Flappy Bird – Java Project

Welcome to our remake of the classic **Flappy Bird** game, developed in pure Java. The goal is simple: fly through pipes, avoid obstacles, and collect as many points as possible.

<img width="512" alt="Screenshot 2025-06-15 at 19 07 40" src="https://github.com/user-attachments/assets/5674178b-a698-468f-99d2-ae4407389922" />

## 🎮 Gameplay

- Control the bird by pressing `SPACE` to jump.
- Avoid hitting the pipes or falling to the ground.
- You start with **3 lives** – losing all ends the game.
- Three difficulty modes:
  - 🟢 **Classic Mode** – slow pipes, wide gaps
  - 🟠 **Hard Mode** – faster pipes, tighter spaces
  - 🔴 **Extreme Mode** – very fast pipes and tiny gaps
- Pause with `P` and return to menu with `ESC`.

## 💡 Features

- ✅ Three unique game modes
- ✅ Custom graphics and animations
- ✅ Sound effects for jump, hit, and scoring
- ✅ High score saving per mode (`scores.txt`)
- ✅ Leaderboard with name input
- ✅ Smooth UI and game restart options

## 📂 Project Structure

```
FlappyBirdProject/
│
├── flappybird/              # All game classes
│   ├── Bird.java
│   ├── ClassicModePanel.java
│   ├── ClassicModePipe.java
│   ├── ExtremeModePanel.java
│   ├── ExtremeModePipe.java
│   ├── GameFrame.java
│   ├── GamePanel.java
│   ├── HardModePanel.java
│   ├── HardModePipe.java
│   ├── LeaderboardPanel.java
│   ├── Main.java
│   ├── Pipe.java
│   ├── ScoreEntry.java
│   ├── SoundPlayer.java
│   └── StartPanel.java
│
├── media/                   # Images
│   ├── bird.png
│   └── heart.png
│
├── sounds/                  # Sounds
│   ├── die.wav
│   ├── hit.wav
│   ├── jump.wav
│   ├── point.wav
│   └── swoosh.wav
│
└── scores.txt               # Stored high scores
```

## ▶️ How to Run

1. **Requirements:**
   - Java 17 or later
   - IntelliJ IDEA / VS Code / any Java IDE

2. **Run Instructions:**
   - Open the project in your IDE.
   - Run `Main.java`.

## 👤 Authors

Developed by:

- **Neža Zamolo**  
- **Žiga Hlastec**
