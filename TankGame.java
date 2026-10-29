import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class TankGame extends JPanel implements ActionListener {
    private Timer timer;
    private Tank player1;
    private Tank player2;
    private ArrayList<Bullet> bullets;
    private boolean[] keys;

    // Canvas configuration
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    public TankGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(20, 20, 25));
        setFocusable(true);

        // Initialize players at opposite ends facing each other
        player1 = new Tank(100, HEIGHT / 2, 0, Color.GREEN, "Player 1");
        player2 = new Tank(WIDTH - 100, HEIGHT / 2, Math.PI, Color.RED, "Player 2");
        bullets = new ArrayList<>();
        keys = new boolean[256];

        // Capture user input
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code < keys.length) keys[code] = true;
                
                // Single-press action for shooting
                if (code == KeyEvent.VK_SPACE) {
                    player1.shoot(bullets);
                }
                if (code == KeyEvent.VK_ENTER) {
                    player2.shoot(bullets);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                if (code < keys.length) keys[code] = false;
            }
        });

        // 60 FPS Game Loop
        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame();
        repaint();
    }

    private void updateGame() {
        // Player 1 Input handling
        double p1Move = 0, p1Turn = 0;
        if (keys[KeyEvent.VK_W]) p1Move = 3.0;
        if (keys[KeyEvent.VK_S]) p1Move = -2.0;
        if (keys[KeyEvent.VK_A]) p1Turn = -0.05;
        if (keys[KeyEvent.VK_D]) p1Turn = 0.05;
        player1.update(p1Move, p1Turn, WIDTH, HEIGHT);

        // Player 2 Input handling
        double p2Move = 0, p2Turn = 0;
        if (keys[KeyEvent.VK_UP]) p2Move = 3.0;
        if (keys[KeyEvent.VK_DOWN]) p2Move = -2.0;
        if (keys[KeyEvent.VK_LEFT]) p2Turn = -0.05;
        if (keys[KeyEvent.VK_RIGHT]) p2Turn = 0.05;
        player2.update(p2Move, p2Turn, WIDTH, HEIGHT);

        // Handle bouncy physics for active bullets
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet b = bullets.get(i);
            b.update(WIDTH, HEIGHT);

            // Eliminate expired projectiles
            if (b.isExpired()) {
                bullets.remove(i);
                continue;
            }

            // Hit collision detection
            if (player1.checkHit(b)) {
                player2.incrementScore();
                resetPositions();
                break;
            }
            if (player2.checkHit(b)) {
                player1.incrementScore();
                resetPositions();
                break;
            }
        }
    }

    private void resetPositions() {
        bullets.clear();
        player1.reset(100, HEIGHT / 2, 0);
        player2.reset(WIDTH - 100, HEIGHT / 2, Math.PI);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Render projectiles
        for (Bullet b : bullets) {
            b.draw(g2d);
        }

        // Render structural units
        player1.draw(g2d);
        player2.draw(g2d);

        // Draw Scoreboard UI
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 22));
        g2d.drawString(player1.getName() + ": " + player1.getScore(), 50, 40);
        g2d.drawString(player2.getName() + ": " + player2.getScore(), WIDTH - 250, 40);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("2D Bouncy Ball Tank Arena");
        TankGame game = new TankGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class Tank {
    private double x, y, angle;
    private Color color;
    private String name;
    private int score;
    private static final int SIZE = 40;

    public Tank(double x, double y, double angle, Color color, String name) {
        this.color = color;
        this.name = name;
        this.score = 0;
        reset(x, y, angle);
    }

    public void reset(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public void update(double move, double turn, int width, int height) {
        angle += turn;
        x += Math.cos(angle) * move;
        y += Math.sin(angle) * move;

        // Keep tanks constrained inside game map boundaries
        x = Math.max(SIZE, Math.min(width - SIZE, x));
        y = Math.max(SIZE, Math.min(height - SIZE, y));
    }

    public void shoot(ArrayList<Bullet> bullets) {
        // Spawn ball at the tip of the tank turret barrel
        double barrelLength = SIZE * 0.8;
        double bx = x + Math.cos(angle) * barrelLength;
        double by = y + Math.sin(angle) * barrelLength;
        bullets.add(new Bullet(bx, by, angle));
    }

    public boolean checkHit(Bullet b) {
        double dist = Math.hypot(b.getX() - x, b.getY() - y);
        return dist < (SIZE / 2.0 + b.getRadius());
    }

    public void draw(Graphics2D g) {
        AffineTransform old = g.getTransform();
        g.translate(x, y);
        g.rotate(angle);

        // Core Chassis Frame
        g.setColor(color);
        g.fillRect(-SIZE / 2, -SIZE / 2, SIZE, SIZE);

        // Visual Treads orientation
        g.setColor(Color.DARK_GRAY);
        g.fillRect(-SIZE / 2 - 4, -SIZE / 2, 8, SIZE);
        g.fillRect(SIZE / 2 - 4, -SIZE / 2, 8, SIZE);

        // Rotatable Top Turret & Aiming Line
        g.setColor(color.darker());
        g.fillOval(-SIZE / 4, -SIZE / 4, SIZE / 2, SIZE / 2);
        g.setColor(Color.LIGHT_GRAY);
        g.setStroke(new BasicStroke(5));
        g.drawLine(0, 0, (int) (SIZE * 0.8), 0);

        g.setTransform(old);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public String getName() { return name; }
    public int getScore() { return score; }
    public void incrementScore() { score++; }
}

class Bullet {
    private double x, y, dx, dy;
    private int bouncesLeft;
    private boolean expired;
    private static final int RADIUS = 8;
    private static final double SPEED = 7.0;
    private static final int MAX_BOUNCES = 4;

    public Bullet(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.dx = Math.cos(angle) * SPEED;
        this.dy = Math.sin(angle) * SPEED;
        this.bouncesLeft = MAX_BOUNCES;
        this.expired = false;
    }

    public void update(int width, int height) {
        x += dx;
        y += dy;

        // Bouncy border collisions
        if (x - RADIUS < 0) {
            x = RADIUS;
            dx = -dx;
            bouncesLeft--;
        } else if (x + RADIUS > width) {
            x = width - RADIUS;
            dx = -dx;
            bouncesLeft--;
        }

        if (y - RADIUS < 0) {
            y = RADIUS;
            dy = -dy;
            bouncesLeft--;
        } else if (y + RADIUS > height) {
            y = height - RADIUS;
            dy = -dy;
            bouncesLeft--;
        }

        if (bouncesLeft < 0) {
            expired = true;
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.fillOval((int) (x - RADIUS), (int) (y - RADIUS), RADIUS * 2, RADIUS * 2);
        
        // Energy glow pulse ring
        g.setColor(new Color(255, 255, 0, 100));
        g.setStroke(new BasicStroke(2));
        g.drawOval((int) (x - RADIUS - 2), (int) (y - RADIUS - 2), RADIUS * 2 + 4, RADIUS * 2 + 4);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getRadius() { return RADIUS; }
    public boolean isExpired() { return expired; }
}
