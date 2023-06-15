import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class hand {
        int x,y;
        Rectangle handRect;
        Image hand = new ImageIcon("Pics/hand.png").getImage();

        public hand(){
                x = 0;
                y = 0;
        }

        public void move(int x, int y){
                this.x = x;
                this.y = y;
                handRect = new Rectangle(x, y, hand.getWidth(null), hand.getHeight(null));
        }

        public Rectangle getRect(){
                return handRect;
        }

        public void switchMode(int player){
                if(player == 1){
                        hand = new ImageIcon("Pics/hand1.png").getImage();
                }
                else if(player == 2){
                        hand = new ImageIcon("Pics/hand2.png").getImage();
                }
                else{
                        hand = new ImageIcon("Pics/hand.png").getImage();
                }
        }

        public void draw(Graphics g){
                g.drawImage(hand, x, y, null);
        }
}
