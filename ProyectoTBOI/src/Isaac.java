import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Isaac {

    public static final int CHARACTER_SPEED = 4;
    private static final int CHARACTER_SIZE = 62;
    private static final int BALL_SPEED = 5;
    private static final int SHOOT_DELAY = 450; // 1000 = 1 segundo

    private int x;
    private int y;
    private int velocityX;
    private int velocityY;

    private BufferedImage isaac;
    private BufferedImage tear;

    public List<Lagrima> lagrimas;
    public long lastShootTime;  // Tiempo del último disparo

    public Isaac(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;

        try {
            isaac = ImageIO.read(new File("resources/isaacola.png"));
            tear = ImageIO.read(new File("resources/normaltear.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        lagrimas = new ArrayList<>();
        lastShootTime = 0;
    }

    public void paint(Graphics g) {
        g.drawImage(isaac, x - CHARACTER_SIZE / 2, y - CHARACTER_SIZE / 2, CHARACTER_SIZE, CHARACTER_SIZE, null);

        for (Lagrima lagrima : lagrimas) {
            lagrima.paint(g);
        }
    }

    public void update(int velocityX, int velocityY) {
        x += velocityX;
        y += velocityY;

        for (Lagrima lagrima : lagrimas) {
            lagrima.update();
        }

        lagrimas.removeIf(lagrima -> lagrima.getX() < 80 || lagrima.getX() > Juego.WIDTH - 110|| lagrima.getY() < 80 || lagrima.getY() > Juego.HEIGHT - 110);
    }

    public void shootUp() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_DELAY) {
            Lagrima lagrima = new Lagrima(x, y, 0, -BALL_SPEED);
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
        }
    }

    public void shootDown() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_DELAY) {
            Lagrima lagrima = new Lagrima(x, y, 0, BALL_SPEED);
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
        }
    }

    public void shootLeft() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_DELAY) {
            Lagrima lagrima = new Lagrima(x, y, -BALL_SPEED, 0);
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
        }
    }

    public void shootRight() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_DELAY) {
            Lagrima lagrima = new Lagrima(x, y, BALL_SPEED, 0);
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public int getCharacterSpeed(){
        return CHARACTER_SPEED;
    }
}