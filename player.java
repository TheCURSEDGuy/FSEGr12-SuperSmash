import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class player {
        public boolean jumped = false;
        public int x,y,xVel,yVel;
        private int player;
        private boolean left,right,up,down;
        public final int P1 = 0, P2 = 1;
        public final int LEFT = 0, RIGHT = 1, UP = 2, NONE = 3;

        public player(int x, int y, int player){
                this.x = x;
                this.y = y;
                this.player = player;
        }

        public void move(boolean keys[]){
                if(keys[KeyEvent.VK_D]){
                        xVel += 2;
                }
                if(keys[KeyEvent.VK_A]){
                        xVel -= 2;
                }

                if(keys[KeyEvent.VK_W] && jumped == false){
                        jumped = true;
                        yVel -= 15;
                }
                
                if(xVel > 10){xVel = 10;}
                if(xVel < -10){xVel = -10;}
                
                x += xVel;
                y += yVel;
        }
        
        public void friction(){
                if(xVel > 0){
                        xVel -= 1;
                }
                else if(xVel < 0){
                        xVel += 1;
                }
        }

        public void update(){
                x += xVel;
                y += yVel;
        }

        public Rectangle getRect(){
                return new Rectangle(x,y,40,80);
        }
        
        public void draw(Graphics g){
                g.setColor(Color.red);
                g.fillRect(x, y, 40, 80);
        }
}
