# HeartGUI

This Java program creates a GUI application to animate the drawing and erasing of a heart shape. Here's a breakdown:

### 1. **Key Classes and Libraries**
   - **Swing Components**: Used for the GUI framework (e.g., `JFrame`, `JPanel`, `JButton`).
   - **AWT Graphics**: Provides the functionality to draw custom shapes (e.g., `Graphics`, `Graphics2D`).
   - **Timers**: Used for the animation, updating the progress over time.

### 2. **Structure**
   - **`HeartGUI` Class**: Main class extending `JFrame`. Contains GUI setup, animation logic, and the main entry point (`main` method).

---

### 3. **Components**
#### **GUI Layout**
   - The layout has two main sections:
     - **`buttonPanel`**: Contains buttons for user actions: *Create Heart*, *Remove Heart*, and *Quit*.
     - **`drawingPanel`**: A custom `JPanel` used to draw and animate the heart shape.

#### **Buttons**
   - **`Create Heart`**: Triggers the animation to draw the heart gradually.
   - **`Remove Heart`**: Triggers the erasing animation (progressively removing the drawn shape).
   - **`Quit`**: Closes the application.

---

### 4. **Drawing Mechanics**
#### **Heart Points Generation**
   - The heart's shape is defined mathematically using parametric equations. 
   - **`generateHeartPoints`** generates points based on these equations, normalizing them to fit the drawing panel size.

#### **Progress-Based Animation**
   - **`progress` Variable**: Tracks how much of the heart is drawn or erased (value between 0.0 and 1.0).
   - The animation steps are controlled using `javax.swing.Timer`.

#### **Graphics Drawing**
   - **Heart Path**: A `Path2D` object is used to join the generated heart points and create a smooth shape.
   - During the animation:
     - The number of points drawn corresponds to the progress.
     - If progress reaches 1.0, the heart is fully drawn, and `g2d.fill` is called to fill the shape.

---

### 5. **Animation Control**
- **Draw Timer** (`drawTimer`): Gradually increases the `progress` to animate drawing the heart.
- **Erase Timer** (`eraseTimer`): Gradually decreases the `progress` to erase the heart.

---

### 6. **Behavior**
   - When the "Create Heart" button is pressed:
     - If no animation is ongoing, it initializes the drawing process and starts `drawTimer`.
   - When the "Remove Heart" button is pressed:
     - If no animation is ongoing and the heart is drawn, it starts `eraseTimer`.
   - The "Quit" button terminates the application. 

---

### 7. **Entry Point**
   - The `main` method runs the GUI using `SwingUtilities.invokeLater`, ensuring thread safety for the Swing components.

This application visually demonstrates an animated drawing and erasing of a heart shape using Swing and AWT.
