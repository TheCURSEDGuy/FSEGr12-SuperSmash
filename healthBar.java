import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class healthBar {
        Image heart = new ImageIcon("Pics/hearts.png").getImage();
        public final int LEFT = 0, RIGHT = 1;
        private int dir;
        private int percentage;
        private int hearts;
        private int x,y;
        private int xHearts;
        Image health;

        public healthBar(int dir, int percentage, int hearts){
                heart = heart.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                this.dir = dir;
                this.percentage = percentage;
                this.hearts = hearts;
                y = 700;
                
                health = dir == LEFT ? new ImageIcon("Pics/healthLeft.png").getImage() : new ImageIcon("Pics/healthRight.png").getImage();
                x = dir == LEFT ? 0 : 1600-health.getWidth(null)-15;
                xHearts = dir == LEFT ? 150 : 1600-health.getWidth(null)+200;


        }

        public void update(int percentage){
                this.percentage = percentage;
        }

        public void draw(Graphics g){
                g.setColor(Color.red);
                g.drawImage(health, x, y, null);
                for(int i = 0; i < hearts; i++){
                        g.drawImage(heart, xHearts+i*40, 922, null);
                        
                }
        }
}
