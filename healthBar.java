import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class healthBar {
        Font fontSys = new Font("Arial", Font.BOLD, 50);
        Image heart = new ImageIcon("Pics/hearts.png").getImage();
        public final int LEFT = 0, RIGHT = 1;
        private int dir;
        private double percentage = 0;
        private int hearts = 3;
        private int x,y;
        private int xPer;
        private int xHearts;
        Image health;

        public healthBar(int dir){
                heart = heart.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                this.dir = dir;
                y = 700;
                
                health = dir == LEFT ? new ImageIcon("Pics/healthLeft.png").getImage() : new ImageIcon("Pics/healthRight.png").getImage();
                xPer = dir == LEFT ? 250 : 1180;
                x = dir == LEFT ? 0 : 1600-health.getWidth(null);
                xHearts = dir == LEFT ? 150 : 1600-health.getWidth(null)+200;


        }

        public void update(int percentage){
                this.percentage = percentage;
        }

        public void draw(Graphics g){
                g.setColor(Color.red);
                g.drawImage(health, x, y, null);
                g.setFont(fontSys);
                g.setColor(new Color((int)(255-255*(percentage/1000)),0,0));
                g.drawString(percentage+"%", xPer, 850);
                for(int i = 0; i < hearts; i++){
                        g.drawImage(heart, xHearts+i*40, 922, null);
                        
                }
        }
}
