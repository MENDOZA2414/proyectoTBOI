import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Lagrima {

    private static final int BALL_SIZE = 30;

    private int x;
    private int y;
    private int velocityX;
    private int velocityY;
    private BufferedImage tear;

    public Lagrima(int initialX, int initialY, int velocityX, int velocityY) {
        x = initialX;
        y = initialY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;

        try {
            tear = ImageIO.read(new File("resources/normaltear.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        x += velocityX;
        y += velocityY;
    }

    public void paint(Graphics g) {
        g.drawImage(tear, x - BALL_SIZE / 2, y - BALL_SIZE / 2, BALL_SIZE, BALL_SIZE, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}