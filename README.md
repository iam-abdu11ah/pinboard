# PinBoard: a vector drawing editor in Java / JavaFX

A small vector drawing editor, in the spirit of Inkscape or Illustrator, built during my **L3 Computer Science** year at **Sorbonne Université** (course *LU3IN002 – Programmation par objets*, 2024–25).

The editor itself is deliberately simple. The point of the project is the **architecture**: an extensible design built on delegation, programming against interfaces, and classic **object-oriented design patterns**.


---

## What it does

PinBoard lets you place graphical elements (*clips*) on a drawing surface (a *board*) and manipulate them:

- **Draw shapes:** filled rectangles and ellipses, drawn by click and drag with a live outline preview.
- **Insert images:** choose an image file and place it on the board.
- **Pick colours** from a colour palette in the toolbar.
- **Select:** click to select one element, or Shift + click to select several. Selected elements get a blue bounding box.
- **Move** the selected elements by dragging them.
- **Copy, paste and delete** through a clipboard that is **shared between all open windows**, so you can copy from one drawing to another.
- Open **several editor windows** at once, each with its own drawing (File → New / Close).
- **Group and ungroup** elements so they behave as a single shape *(in progress)*.
- **Undo / redo** without limit *(planned)*.

---

## Design patterns

| Pattern | Where | Why |
|---|---|---|
| **Strategy** | `editor.tools.Tool`, implemented by `ToolRect`, `ToolEllipse`, `ToolImage` and `ToolSelection` | `EditorWindow` doesn't handle mouse events itself. It delegates `press` / `drag` / `release` / `drawFeedback` to the *current tool*. Adding a tool means adding a class, not editing a long `if/else` block. |
| **Singleton** | `editor.Clipboard` | There is one clipboard for the whole application, shared by every window. It has a private constructor and a static `getInstance()`. |
| **Observer** | `Clipboard` (subject) and `ClipboardListener`, implemented by `EditorWindow` | When the clipboard changes, every open window is notified and enables or disables its *Paste* menu item. Windows unregister when they close so the clipboard doesn't keep references to dead windows. |
| **Composite** | `document.Composite`, implemented by `ClipGroup` | A group of clips is itself a `Clip`. It can be drawn, moved, copied (deep copy) and selected like any single shape, and groups can contain other groups. |
| **Command** | `editor.commands.Command` (`CommandAdd`, `CommandMove`, `CommandGroup`, `CommandUngroup`) and `CommandStack` | Each action that changes the board becomes an object with `execute()` and `undo()`. Two stacks (undo and redo) make undo/redo possible to any depth. *(in progress)* |
| **Prototype** | `Clip.copy()` | Copy/paste works by asking each clip to clone itself, without knowing its concrete class. |

Other object-oriented principles used throughout:

- **Programming against interfaces:** `Board` and `Selection` only know about `Clip`, never about `ClipRect` or `ClipEllipse`.
- **Abstract class for shared code:** `AbstractClip` holds the geometry and colour logic shared by every shape. Subclasses only provide `draw` and `copy`, and override `isSelected` where needed (an ellipse uses its equation).
- **Low coupling through `EditorInterface`:** tools see only what the editor chooses to expose (board, selection, command stack), not the whole window.
- **Separation of model and view:** the `document` package (the drawing model) has no dependency on the `editor` package (the GUI).
- **Mock objects for testing:** the tool tests use a `MockEditor` instead of a real JavaFX window.

---

## Project structure

```
src/pobj/pinboard/
├── document/              # Model: what a drawing is
│   ├── Clip               # interface for every graphical element
│   ├── AbstractClip       # shared geometry and colour
│   ├── ClipRect, ClipEllipse, ClipImage
│   ├── Composite, ClipGroup   # Composite pattern
│   └── Board              # the drawing surface (list of clips)
├── editor/                # View / controller: the JavaFX editor
│   ├── EditorMain         # entry point
│   ├── EditorWindow       # one editor window
│   ├── EditorInterface    # what tools may access
│   ├── Selection
│   ├── Clipboard, ClipboardListener   # Singleton + Observer
│   ├── CommandStack       # undo/redo stacks
│   ├── tools/             # Strategy: Tool, ToolRect, ToolEllipse, ToolImage, ToolSelection
│   └── commands/          # Command: Command, CommandAdd, CommandMove, CommandGroup, CommandUngroup
└── test/                  # TestDocument (visual check of the model)
```

Unit tests (JUnit 5) are in the `test` sub-package next to each package.

---


## Running it

**Requirements:** JDK 17+ (developed with JDK 21) and the [JavaFX SDK](https://gluonhq.com/products/javafx/) for your OS and CPU (e.g. macOS aarch64 for Apple Silicon).

The project is a Java module (`src/module-info.java`), so JavaFX and JUnit must be on the **module path**.

**IntelliJ IDEA**
1. Open the project folder.
2. *Project Structure → Libraries:* add the JavaFX SDK's `lib` folder and JUnit 5.
3. Run `pobj.pinboard.editor.EditorMain`.

**Command line**
```bash
FX=/path/to/javafx-sdk/lib
javac -d out --module-path "$FX:/path/to/junit-jars" $(find src -name "*.java")
java --module-path "$FX:out" -m pobj.pinboard/pobj.pinboard.editor.EditorMain
```

---

## Credits

Project specification, the provided interfaces (`Clip`, `Tool`, `EditorInterface`, `Composite`, `Command`, …) and the unit tests come from the *LU3IN002* teaching team at Sorbonne Université. The implementation is my own.
