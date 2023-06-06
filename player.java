import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class player {
        public boolean jumped = false;
        public int x,y;
        public double xVel,yVel;
        private int player;
        public int percentage;
        private int dir;
        private boolean cooldownPunch = false;
        Rectangle playerRect;

        // Timers
        timer pT1 = new timer(500);
        timer pT2 = new timer(500);
        timer nPunch;

        // finals
        public final int LEFT = -1, RIGHT = 1, UP = 2, NONE = 3;
        public final boolean P1 = true, P2 = false;
        private final int normalP = 1, poweredP = 5;

        // attacks
        private int punch = normalP;
        private int numPunches = 0;
        boolean isPunched = false;




        public player(int player){
                x = player == 1 ? 200 : 1300;
                y = 700;
                playerRect = new Rectangle(x,y,40,80);
                this.player = player;
                dir = player == 1 ? RIGHT : LEFT;

        }

        public void move(boolean keys[]){
                if(player == 1){
                        pT1.update();
                        if(keys[KeyEvent.VK_D]){
                                dir = RIGHT;
                                xVel += 2;
                        }
                        if(keys[KeyEvent.VK_A]){
                                dir = LEFT;
                                xVel -= 2;
                        }
        
                        if(keys[KeyEvent.VK_W] && jumped == false){
                                
                                jumped = true;
                                yVel -= 10;
                        }
                        if(xVel > 10 && !isPunched){xVel = 10;}
                        if(xVel < -10 && !isPunched){xVel = -10;}
                        if(pT1.getTime() > 10 && isPunched){isPunched = false;}

                        x += xVel;
                        y += yVel;
                }
                if(player == -1){
                        pT2.update();
                        if(keys[KeyEvent.VK_RIGHT]){
                                dir = RIGHT;
                                xVel += 2;
                        }
                        if(keys[KeyEvent.VK_LEFT]){
                                dir = LEFT;
                                xVel -= 2;
                        }
        
                        if(keys[KeyEvent.VK_UP] && jumped == false){
                                jumped = true;
                                yVel -= 10;
                        }

                        if(xVel > 10 && !isPunched){xVel = 10;}
                        if(xVel < -10 && !isPunched){xVel = -10;}
                        if(pT2.getTime() > 10 && isPunched){isPunched = false;}
                
                        x += xVel;
                        y += yVel;
                }
                playerRect = new Rectangle(x,y,40,80);
        }

        public void punch(player p){
                if(p.getRect().intersects(getRect())){
                        p.punched(dir, 10);
                        numPunches++;
                        if(numPunches == 5){
                                punch = poweredP;
                                numPunches = 0;
                        }
                }
        }

        public void punched(int dir, int dist){
                if(player == 1){
                        pT1.reset();
                }
                else{
                        pT2.reset();
                }
                percentage+=1;
                System.out.println(dist*dir*percentage/50);
                xVel += dist*dir*percentage/50;
                yVel -= 10;
                isPunched = true;
                percentage+=10;
        }
        
        public void friction(){
                if(xVel > 0){
                        xVel -= 1;
                }
                else if(xVel < 0){
                        xVel += 1;
                }
        }

        public void gravity(Rectangle plat){
                if(getRect().intersects(plat) && !isPunched){
                        if(!isPunched){
                                yVel = 0;
                        }
                        y = plat.y - getRect().height+1;
                        jumped = false;

                }
                else{
                        yVel += 1;
                }
        }

        public void update(){
                x += xVel;
                y += yVel;
        }

        public Rectangle getRect(){
                return playerRect;
        }
        
        public void draw(Graphics g){
                g.setColor(Color.red);
                g.fillRect(x, y, 40, 80);
        }

        
}
