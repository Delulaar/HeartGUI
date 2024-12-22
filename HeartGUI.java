import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.Timer;
import java.util.ArrayList;

public class HeartGUI extends JFrame {
    private JPanel drawingPanel;
    private Timer drawTimer;
    private Timer eraseTimer;
    private ArrayList<Point2D> heartPoints;
    private double progress = 0.0;
    private boolean isDrawing = false;
    private boolean isErasing = false;
    private static final int ANIMATION_DELAY = 50; // milliseconds
    private static final double STEP = 0.02; // Progress step for animation

    public HeartGUI() {
        setTitle("Animated Heart Drawing");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize points array
        heartPoints = new ArrayList<>();

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create button panel
        JPanel buttonPanel = new JPanel();
        JButton createButton = new JButton("Create Heart");
        JButton removeButton = new JButton("Remove Heart");
        JButton quitButton = new JButton("Quit");

        buttonPanel.add(createButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(quitButton);

        // Create drawing panel
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAnimatedHeart(g);
            }
        };
        drawingPanel.setBackground(Color.WHITE);

        // Add panels to main panel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(drawingPanel, BorderLayout.CENTER);

        // Initialize drawing timer
        drawTimer = new Timer(ANIMATION_DELAY, e -> {
            if (progress < 1.0) {
                progress += STEP;
                drawingPanel.repaint();
            } else {
                progress = 1.0;
                isDrawing = false;
                ((Timer)e.getSource()).stop();
            }
        });

        // Initialize erasing timer
        eraseTimer = new Timer(ANIMATION_DELAY, e -> {
            if (progress > 0.0) {
                progress -= STEP;
                drawingPanel.repaint();
            } else {
                progress = 0.0;
                isErasing = false;
                ((Timer)e.getSource()).stop();
            }
        });

        // Add action listeners
        createButton.addActionListener(e -> {
            if (!isDrawing && !isErasing) {
                isDrawing = true;
                progress = 0.0;
                generateHeartPoints();
                drawTimer.start();
            }
        });

        removeButton.addActionListener(e -> {
            if (!isDrawing && !isErasing && progress > 0.0) {
                isErasing = true;
                eraseTimer.start();
            }
        });

        quitButton.addActionListener(e -> System.exit(0));

        // Add main panel to frame
        add(mainPanel);
    }

    private void generateHeartPoints() {
        heartPoints.clear();
        int width = drawingPanel.getWidth();
        int height = drawingPanel.getHeight();
        int size = Math.min(width, height) / 2;
        int centerX = width / 2;
        int centerY = height / 2;

        // Generate points for the heart shape
        for (double t = 0; t <= 2 * Math.PI; t += 0.1) {
            double x = centerX + size * (16 * Math.pow(Math.sin(t), 3)) / 16;
            double y = centerY - size * (13 * Math.cos(t) - 5 * Math.cos(2*t) - 2 * Math.cos(3*t) - Math.cos(4*t)) / 16;
            heartPoints.add(new Point2D.Double(x, y));
        }
    }

    private void drawAnimatedHeart(Graphics g) {
        if (heartPoints.isEmpty() || progress == 0.0) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2));

        int pointsToDraw = (int)(heartPoints.size() * progress);
        Path2D path = new Path2D.Double();

        if (pointsToDraw > 0) {
            path.moveTo(heartPoints.get(0).getX(), heartPoints.get(0).getY());
            for (int i = 1; i < pointsToDraw; i++) {
                path.lineTo(heartPoints.get(i).getX(), heartPoints.get(i).getY());
            }

            // Close the path if we're at the end
            if (progress == 1.0) {
                path.closePath();
                g2d.fill(path);
            } else {
                g2d.draw(path);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HeartGUI gui = new HeartGUI();
            gui.setVisible(true);
        });
    }
}