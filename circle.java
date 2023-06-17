import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class circle {
        int x = 0,y = 0;
        Image circle;
        int radius;
        int player;
        boolean isTouched = false;
        boolean defaulCircle = false;

        public circle(){
                defaulCircle = true;
                radius = circle.getWidth(null);
                y = 200;
                x = 200;
        }
        

        public circle(int player){
                this.player = player;
                if(player == 1){
                        circle = new ImageIcon("Pics/p1Circle.png").getImage();
                }
                else if(player == 2){
                        circle = new ImageIcon("Pics/p2Circle.png").getImage();
                }
                radius = circle.getWidth(null)/2;
                y = 200;
                x = player == 1 ? 200 : 1400;
        }

        public void isTouched(){
                isTouched = true;
        }

        public void isNotTouched(int x, int y){
                isTouched = false;
                this.x = x;
                this.y = y;
        }

        public void draw(Graphics g){
                if(!isTouched && !defaulCircle){
                        g.drawImage(circle, x-radius, y-radius, null);
                }
                if(defaulCircle){
                        g.drawOval(x, y, radius, radius);
                }
        }
}
