# Chess System — Java

A fully functional, two-player chess game running in the terminal. Built in Java 21 with ANSI color rendering, complete move validation, and all standard chess rules including special moves.

---

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Package Structure](#package-structure)
- [Class Diagram](#class-diagram)
- [How to Run](#how-to-run)
- [How to Play](#how-to-play)
- [Special Moves](#special-moves)
- [Known Bugs](#known-bugs)
- [Suggestions for Improvement](#suggestions-for-improvement)

---

## Features

- Two-player local match (White vs Black)
- Full move validation (no illegal moves allowed)
- Check and Checkmate detection
- Highlighted possible moves on the board
- Captured pieces displayed per player
- Turn counter
- Special moves: **Castling**, **En Passant**, **Promotion**

---

## Architecture

The project is structured in two independent layers, following a clean separation of concerns:

### Layer 1 — `boardgame` (Generic Engine)

Contains the abstract, game-agnostic board logic. This layer has no knowledge of chess rules. It could theoretically be reused for any board game (checkers, draughts, etc.).

| Class | Responsibility |
|---|---|
| `Board` | Manages the 8×8 grid, piece placement, and removal |
| `Piece` | Abstract base for any board piece; defines the `possibleMoves()` contract |
| `Position` | Represents a matrix coordinate `(row, column)` |
| `BoardException` | Runtime exception for invalid board operations |

### Layer 2 — `chess` (Chess Domain)

Implements all chess-specific rules on top of the generic board engine.

| Class | Responsibility |
|---|---|
| `ChessMatch` | Central game controller — turn management, move execution, check/checkmate logic, special moves |
| `ChessPiece` | Abstract chess piece; extends `Piece` with color, move counter, and opponent detection |
| `ChessPosition` | Chess notation (`a1`–`h8`); converts to/from matrix `Position` |
| `Color` | Enum: `WHITE` / `BLACK` |
| `ChessException` | Chess-specific runtime exceptions |

### Layer 3 — `chess.pieces` (Piece Implementations)

Each piece encapsulates its own movement rules via `possibleMoves()`.

| Piece | Symbol | Movement |
|---|---|---|
| `King` | `K` | One square in any direction + Castling |
| `Queen` | `Q` | Any number of squares in any direction |
| `Rook` | `R` | Any number of squares horizontally or vertically |
| `Bishop` | `B` | Any number of squares diagonally |
| `Knight` | `N` | L-shape (2+1 squares) |
| `Pawn` | `P` | Forward 1 (or 2 from start), captures diagonally + En Passant |

### Layer 4 — `application` (UI & Entry Point)

| Class | Responsibility |
|---|---|
| `Program` | Main loop: reads input, triggers moves, handles promotion prompt |
| `UI` | Renders board, captured pieces, and match state using ANSI colors |

---

## Package Structure

```
src/
├── application/
│   ├── Program.java          # Entry point and game loop
│   └── UI.java               # Terminal rendering with ANSI colors
├── boardgame/
│   ├── Board.java            # Generic 8x8 board
│   ├── Piece.java            # Abstract piece
│   ├── Position.java         # Matrix coordinate (row, column)
│   └── BoardException.java   # Board-level runtime exception
└── chess/
    ├── ChessMatch.java       # Game controller (core logic)
    ├── ChessPiece.java       # Abstract chess piece
    ├── ChessPosition.java    # Chess notation (a1-h8)
    ├── Color.java            # WHITE / BLACK enum
    ├── ChessException.java   # Chess-level runtime exception
    └── pieces/
        ├── King.java
        ├── Queen.java
        ├── Rook.java
        ├── Bishop.java
        ├── Knight.java
        └── Pawn.java
```

---

## Class Diagram

```
boardgame
┌─────────────┐       ┌──────────────┐       ┌───────────────┐
│   Board     │◄──────│    Piece     │       │   Position    │
│  rows       │       │  position    │       │  row          │
│  columns    │       │  board       │       │  column       │
│  pieces[][] │       │─────────────-│       └───────────────┘
└─────────────┘       │ possibleMoves│ (abstract)
                      └──────────────┘
                             ▲
chess                        │ extends
              ┌──────────────────────────┐
              │        ChessPiece        │
              │  color                   │
              │  moveCount               │
              └──────────────────────────┘
                             ▲
              ┌──────────────┼──────────────────────┐
              │              │                       │
           King           Queen/Rook/           Pawn/Knight/
           (+ ChessMatch)  Bishop               Bishop
```

---

## How to Run

### Prerequisites

- Java 21+

### Compile

```bash
javac -d bin src/boardgame/*.java src/chess/*.java src/chess/pieces/*.java src/application/*.java
```

### Run

```bash
java -cp bin application.Program
```

> **Note:** ANSI colors require a terminal that supports escape codes (Linux, macOS, Windows Terminal, Git Bash). The default Windows `cmd.exe` may not render colors correctly.

---

## How to Play

The game is text-based and uses standard chess notation.

### Board Display

```
8 R N B Q K B N R
7 P P P P P P P P
6 - - - - - - - -
5 - - - - - - - -
4 - - - - - - - -
3 - - - - - - - -
2 P P P P P P P P
1 R N B Q K B N R
  a b c d e f g h
```

- **White pieces** are displayed in white
- **Black pieces** are displayed in yellow
- **Blue highlighted squares** show valid moves for the selected piece
- `-` represents an empty square

### Making a Move

1. When prompted, type the **source** position (e.g., `e2`) and press Enter
2. The board will highlight all valid target squares in blue
3. Type the **target** position (e.g., `e4`) and press Enter

### Piece Notation

| Symbol | Piece |
|--------|-------|
| `K` | King |
| `Q` | Queen |
| `R` | Rook |
| `B` | Bishop |
| `N` | Knight |
| `P` | Pawn |

### Example Turn

```
Turn : 1
Waiting player : WHITE

Source: e2
(board highlights e3, e4)

Target: e4
```

---

## Special Moves

### Castling
Move the King two squares toward a Rook. The Rook jumps to the other side of the King automatically.

- **Kingside:** `e1` → `g1` (White) / `e8` → `g8` (Black)
- **Queenside:** `e1` → `c1` (White) / `e8` → `c8` (Black)

**Conditions:** Neither the King nor the Rook may have moved previously, and the squares between them must be empty. The King cannot be in check.

### En Passant
A Pawn that just advanced two squares can be captured "in passing" by an opponent Pawn on an adjacent file, as if it had only moved one square.

**Condition:** Must be performed immediately on the next turn after the two-square advance.

### Promotion
When a Pawn reaches the last rank (row 8 for White, row 1 for Black), it is automatically promoted. You will be prompted to choose:

```
Enter piece for promotion (B/N/R/Q):
```

| Input | Piece |
|-------|-------|
| `Q` | Queen |
| `R` | Rook |
| `B` | Bishop |
| `N` | Knight |

---

## Known Bugs

No known bugs at this time.

---

## Suggestions for Improvement

### Features

1. **Draw conditions** — Implement the 50-move rule and threefold repetition detection.

2. **Move history / notation** — Record each move in standard algebraic notation (SAN) and display it alongside the board.

3. **Undo move** — Allow players to undo the last move (the `undoMove` method already exists internally — expose it as a player action).

4. **Save and load game** — Serialize the match state to a file using FEN (Forsyth–Edwards Notation) for persistence.

8. **AI opponent** — Implement a basic Minimax algorithm with alpha-beta pruning for a single-player mode.

### Code Quality

9. **Extract `boardgame` to a separate module** — The generic board engine is fully decoupled and could be extracted into a reusable library.

10. **Add unit tests** — The pure logic in `ChessMatch`, piece `possibleMoves()` methods, and `ChessPosition` coordinate conversion are ideal candidates for JUnit 5 tests.

11. **Replace `boolean[][]` with a richer type** — A dedicated `MoveSet` or `BitBoard` type would make move representation more expressive and testable.

12. **Dependency injection for `ChessMatch`** — `King` and `Pawn` hold a reference to `ChessMatch` (a circular dependency). Consider introducing an interface (e.g., `IMatchState`) to invert the dependency and improve testability.

---

## Technologies

- **Java 21**
- **ANSI escape codes** for terminal colors
- Originally developed in **Eclipse IDE** (`.classpath`, `.project` present)
- IntelliJ IDEA project files added (`.idea/`, `.iml`)
