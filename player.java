import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;

public class player {
        public int x,y;
        public double xVel,yVel;
        private int player;
        public int percentage;
        private int dir;
        Rectangle playerRect;
        String playerName;
        int frame = 0;

        // Booleans
        boolean combo = false;
        boolean idle = true;
        public boolean jumped = false;
        public boolean walk;


        // Timers
        timer pT = new timer(500);
        timer nPunch;

        // finals
        public final int LEFT = -1, RIGHT = 1, UP = 2, NONE = 3;
        public final boolean P1 = true, P2 = false;
        private final int normalP = 3, poweredP = 10;

        // attacks
        private int typePunch = normalP;
        private int numPunches = 0;
        boolean isPunched = false;

        // cooldowns
        private timer cooldownP = new timer(1000);
        private timer cooldownBet = new timer(1000);

        // sprites
        Image[] attack1;
        Image[] attack2;
        Image[] jump;
        Image[] punch;
        Image[] run;
        Image[] stand;
        Image[] ult;

        


        public player(int player, String playerName){
                attack1 = new Image[new File("Pics/" + playerName + "/attack1").listFiles().length];
                attack2 = new Image[new File("Pics/" + playerName + "/attack2").listFiles().length];
                jump = new Image[new File("Pics/" + playerName + "/jump").listFiles().length];
                punch = new Image[new File("Pics/" + playerName + "/punch").listFiles().length];
                run = new Image[new File("Pics/" + playerName + "/run").listFiles().length];
                stand = new Image[new File("Pics/" + playerName + "/stand").listFiles().length];
                ult = new Image[new File("Pics/" + playerName + "/ult").listFiles().length];

                for(int i = 0; i < attack1.length; i++){
                        attack1[i] = new ImageIcon("Pics/" + playerName + "/attack1/" + i + ".png").getImage();
                }
                for(int i = 0; i < attack2.length; i++){
                        attack2[i] = new ImageIcon("Pics/" + playerName + "/attack2/" + i + ".png").getImage();
                }
                for(int i = 0; i < jump.length; i++){
                        jump[i] = new ImageIcon("Pics/" + playerName + "/jump/" + i + ".png").getImage();
                }
                for(int i = 0; i < punch.length; i++){
                        punch[i] = new ImageIcon("Pics/" + playerName + "/punch/" + i + ".png").getImage();
                }
                for(int i = 0; i < run.length; i++){
                        run[i] = new ImageIcon("Pics/" + playerName + "/run/" + i + ".png").getImage();
                }
                for(int i = 0; i < stand.length; i++){
                        stand[i] = new ImageIcon("Pics/" + playerName + "/stand/" + i + ".png").getImage();
                }
                for(int i = 0; i < ult.length; i++){
                        ult[i] = new ImageIcon("Pics/" + playerName + "/ult/" + i + ".png").getImage();
                }


                
                this.playerName = playerName;

                x = player == 1 ? 200 : 1300;
                y = 700;
                playerRect = new Rectangle(x,y,40,80);
                this.player = player;
                dir = player == 1 ? RIGHT : LEFT;

        }

        public void move(boolean keys[], player p1, player p2){
                pT.update();
                cooldownP.update();
                cooldownBet.update();
                if(pT.getTime() > 10 && isPunched){isPunched = false;}
                if(player == 1){
                        if(keys[KeyEvent.VK_D]){
                                dir = RIGHT;
                                xVel += 2;
                                walk = true;
                        }
                        if(keys[KeyEvent.VK_A]){
                                dir = LEFT;
                                xVel -= 2;
                                walk = true;
                        }
        
                        if(keys[KeyEvent.VK_W] && jumped == false){
                                jumped = true;
                                yVel -= 15;
                        }
                        if(xVel > 10 && !isPunched){xVel = 10;}
                        if(xVel < -10 && !isPunched){xVel = -10;}
                        if(pT.getTime() > 10 && isPunched){isPunched = false;}
                        if(xVel == 0 && yVel == 0 && !p2.isPunched){idle = true; walk = false; frame = 0;}
                        else{idle = false; walk = true; frame = 0;}
                        x += xVel;
                        y += yVel;
                }
                if(player == -1){
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
                                yVel -= 15;
                        }

                        if(xVel > 10 && !isPunched){xVel = 10;}
                        if(xVel < -10 && !isPunched){xVel = -10;}
                        if(pT.getTime() > 10 && isPunched){isPunched = false;}
                        if(xVel == 0 && yVel == 0 && !p2.isPunched){idle = true; walk = false; frame = 0;}
                        else{idle = false; walk = true; frame = 0;}

                        x += xVel;
                        y += yVel;
                }
                playerRect = new Rectangle(x,y,40,80);
        }

        public void punch(player p){
                if(p.getRect().intersects(getRect()) && !isPunched){
                        if(numPunches == 10){
                                p.punched(dir, poweredP, numPunches);
                                typePunch = poweredP;
                                numPunches = 0;
                                cooldownP.reset(); 
                                cooldownBet.reset();
                       }
                        else if(cooldownP.getTime() > 30 && cooldownBet.getTime() > 10){
                                p.punched(dir, 1,numPunches);
                                cooldownBet.reset();                                
                        }
                        numPunches++;
                        
                }
        }

        public void punched(double dir, double dist, int numPunches) {
                double yDist = 0;
                double knockbackScaling = 0.08; // Adjust this value to control knockback scaling
            
                if (player == 1) {
                    pT.reset();
                } else {
                    pT.reset();
                }
            
                if (numPunches > 3 && x < 9) {
                    dist = 1;
                    yDist = 0.01;
                } else {
                    yDist = 10;
                }
            
                percentage += 1;
                double knockback = (dist * (percentage / 100.0) * dir) * knockbackScaling;
                xVel += knockback;
                yVel -= yDist;
                isPunched = true;
                percentage += 1 * dist;
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
                if(getRect().intersects(plat)){
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

        public void moveIdle(){
                if(frame > stand.length-2){frame = 0;}
                else{frame++;}
        }

        public void moveRun(){
                if(frame > run.length-1){frame = 0;}
                else{frame++;}
        }
        
        public void draw(Graphics g) {
                System.out.println(walk + " " + playerName);
                g.setColor(Color.red);
                if (idle) {
                        moveIdle();
                        if (dir == RIGHT){
                        g.drawImage(stand[frame], x, y, null);
                        } 
                        else{
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.drawImage(stand[frame], x + stand[frame].getWidth(null), y, -stand[frame].getWidth(null), stand[frame].getHeight(null), null);
                        }
                }
                else if(jumped){
                        if (dir == RIGHT){
                                g.drawImage(jump[0], x, y, null);
                        } 
                        else{
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.drawImage(jump[0], x + jump[0].getWidth(null), y, -jump[0].getWidth(null), jump[0].getHeight(null), null);
                        }
                }
                else if(walk){
                        moveRun();
                        if (dir == RIGHT){
                                g.drawImage(run[frame], x, y, null);
                        } 
                        else{
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.drawImage(run[frame], x + run[frame].getWidth(null), y, -run[frame].getWidth(null), run[frame].getHeight(null), null);
                        }
                }
                
        }

        
}
