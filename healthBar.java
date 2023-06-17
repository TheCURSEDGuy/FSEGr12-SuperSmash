import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;

import javax.swing.ImageIcon;

public class healthBar {
        Font fontSys = new Font("Arial", Font.BOLD, 50);
        Image heart = new ImageIcon("Pics/hearts.png").getImage();
        public final int LEFT = -1, RIGHT = 1;
        private int dir;
        public int hearts = 3;
        private int x,y;
        private int xPer;
        public int xHearts;
        public int healthNum = 100;
        private Color healthColor = Color.green;
        private int xhealth;
        private int xImage;
        private Image person;
        Image health;

        public healthBar(int dir, Image person){
                System.out.println(dir);
                heart = heart.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                this.dir = dir;
                y = 700;
                
                health = dir == RIGHT ? new ImageIcon("Pics/healthLeft.png").getImage() : new ImageIcon("Pics/healthRight.png").getImage();
                xPer = dir == RIGHT ? 250 : 1180;
                x = dir == RIGHT ? 0 : 1600-health.getWidth(null);
                xHearts = dir == RIGHT ? 150 : 1600-health.getWidth(null)+200;
                healthColor = dir == RIGHT ? Color.blue : Color.red;
                xhealth = dir == RIGHT ? 180 : 1125;
                xImage = dir == RIGHT ? 25 : 1375;
                this.person = person;

        }

        public void update(int health){
                this.healthNum = health;
        }

        public void healthDown(int health){
                healthNum -= health;
                if(healthNum < 0){
                        healthNum = 0;
                }
        }

        public void heartDecrease(){
                hearts--;
        }

        public void draw(Graphics g){
                g.setColor(healthColor);
                if(dir == LEFT){
                        g.fillRect(xhealth+(300-3*healthNum), y+175, 300-(300-3*healthNum), 10);
                }
                else{
                        g.fillRect(xhealth, y+175, 3*healthNum, 10);

                }
                g.setColor(Color.red);
                g.drawImage(health, x, y, null);
                g.setFont(fontSys);
                for(int i = 0; i < hearts; i++){
                        g.drawImage(heart, xHearts+i*40, 922, null);
                }
                g.drawImage(person, xImage, y, null);
        }
}