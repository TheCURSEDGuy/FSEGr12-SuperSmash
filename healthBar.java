import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class healthBar {
        Font fontSys = new Font("Arial", Font.BOLD, 50);
        Image heart = new ImageIcon("Pics/hearts.png").getImage();
        public final int LEFT = -1, RIGHT = 1;
        private int dir;
        private int hearts = 3;
        private int x,y;
        private int xPer;
        private int xHearts;
        private int healthNum = 100;
        private Color healthColor = Color.green;
        private int xhealth;
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

        }

        public void update(int health){
                this.healthNum = health;
        }

        public void heartDecrease(){
                hearts--;
        }

        public void draw(Graphics g){
                g.setColor(healthColor);
                g.fillRect(xhealth, y+175, healthNum*3, 10);
                g.setColor(Color.red);
                g.drawImage(health, x, y, null);
                g.setFont(fontSys);
                for(int i = 0; i < hearts; i++){
                        g.drawImage(heart, xHearts+i*40, 922, null);
                }
        }
}