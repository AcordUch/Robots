package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import java.awt.geom.Point2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel {
    private final Timer timer = initTimer();

    private static Timer initTimer() {
        return new Timer("events generator", true);
    }

    private volatile Point2D.Double robotPosition = new Point2D.Double(100, 100);
    private volatile double robotDirection = 0;

    private volatile Point2D.Double targetPosition = new Point2D.Double(150, 100);

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;

    public GameVisualizer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 50);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setTargetPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void setTargetPosition(Point p) {
        targetPosition.setLocation(p);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    protected void onModelUpdateEvent() {
        double distance = distance(targetPosition.x, targetPosition.y,
                robotPosition.x, robotPosition.y);
        if (distance < 0.5) {
            return;
        }
        double angleToTarget = angleTo(robotPosition.x, robotPosition.y, targetPosition.x, targetPosition.y);
        double angularVelocity = 0;
        if (angleToTarget > robotDirection) {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < robotDirection) {
            angularVelocity = -maxAngularVelocity;
        }

        moveRobot(maxVelocity, angularVelocity, 10);
    }

    private static double applyLimits(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = robotPosition.x + velocity / angularVelocity *
                (Math.sin(robotDirection + angularVelocity * duration) -
                        Math.sin(robotDirection));
        if (!Double.isFinite(newX)) {
            newX = robotPosition.x + velocity * duration * Math.cos(robotDirection);
        }
        double newY = robotPosition.y - velocity / angularVelocity *
                (Math.cos(robotDirection + angularVelocity * duration) -
                        Math.cos(robotDirection));
        if (!Double.isFinite(newY)) {
            newY = robotPosition.y + velocity * duration * Math.sin(robotDirection);
        }

        robotPosition.setLocation(keepInsideWindow(newX, newY));
        double newDirection = asNormalizedRadians(robotDirection + angularVelocity * duration);
        robotDirection = newDirection;
    }

    private Point2D.Double keepInsideWindow(double X, double Y){
        return new Point2D.Double(
                applyLimits(X, 0, getWidth()),
                applyLimits(Y, 0, getHeight())
        );
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, round(robotPosition.x), round(robotPosition.y), robotDirection);
        drawTarget(g2d, round(targetPosition.x), round(targetPosition.y));
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void drawRobot(Graphics2D g, int robotX, int robotY, double direction) {
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotX, robotY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotX, robotY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotX, robotY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotX + 10, robotY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotX + 10, robotY, 5, 5);
    }

    private void drawTarget(Graphics2D g, int targetX, int targetY) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, targetX, targetY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, targetX, targetY, 5, 5);
    }
}
