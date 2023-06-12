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
        public int status;


        // Timers
        timer pT = new timer(500);
        timer nPunch;
        timer cDown = new timer(1000);

        // finals
        public final int LEFT = -1, RIGHT = 1, UP = 2, NONE = 3;
        public final int IDLE = 0, WALK = 1, JUMP = 2, PUNCH = 3, HIT = 4;
        public final boolean P1 = true, P2 = false;
        private final int normalP = 3, poweredP = 10;
        private final int WAIT = 8;

        // attacks
        private int typePunch = normalP;
        private int numPunches = 0;
        boolean isPunched = false;

        // cooldowns
        private timer cooldownP = new timer(1000);
        private timer cooldownBet = new timer(1000);
        private int cooldown = 0;

        // sprites
        Image[] attack1;
        Image[] attack2;
        Image[] jump;
        Image[] punch;
        Image[] run;
        Image[] stand;
        Image[] ult;
        Image[] hit;

        


        public player(int player, String playerName){
                attack1 = new Image[new File("Pics/" + playerName + "/attack1").listFiles().length];
                attack2 = new Image[new File("Pics/" + playerName + "/attack2").listFiles().length];
                jump = new Image[new File("Pics/" + playerName + "/jump").listFiles().length];
                punch = new Image[new File("Pics/" + playerName + "/punch").listFiles().length];
                run = new Image[new File("Pics/" + playerName + "/run").listFiles().length];
                stand = new Image[new File("Pics/" + playerName + "/stand").listFiles().length];
                ult = new Image[new File("Pics/" + playerName + "/ult").listFiles().length];
                hit = new Image[new File("Pics/" + playerName + "/hit").listFiles().length];

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
                for(int i = 0; i < hit.length; i++){
                        hit[i] = new ImageIcon("Pics/" + playerName + "/hit/" + i + ".png").getImage();
                }


                
                this.playerName = playerName;

                x = player == 1 ? 200 : 1300;
                y = 700;
                playerRect = new Rectangle(x,y,stand[0].getWidth(null),stand[0].getHeight(null));
                this.player = player;
                dir = player == 1 ? RIGHT : LEFT;

        }

        public void move(boolean keys[], player p1, player p2){
                pT.update();
                cooldownP.update();
                cooldownBet.update();
                if(pT.getTime() > 10 && isPunched){isPunched = false;}
                if(player == 1 && status != HIT){
                        if(keys[KeyEvent.VK_D]){
                                dir = RIGHT;
                                xVel += 2;
                        }
                        if(keys[KeyEvent.VK_A]){
                                dir = LEFT;
                                xVel -= 30;
                        }
        
                        if(keys[KeyEvent.VK_W] && status != JUMP){
                                frame = 0;
                                status = JUMP;
                                yVel -= 15;
                        }
                }
                if(player == -1 && status != HIT){
                        if(keys[KeyEvent.VK_RIGHT]){
                                dir = RIGHT;
                                xVel += 2;
                        }
                        if(keys[KeyEvent.VK_LEFT]){
                                dir = LEFT;
                                xVel -= 2;
                        }
        
                        if(keys[KeyEvent.VK_UP] && status != JUMP){
                                frame = 0;
                                status = JUMP;
                                yVel -= 15;
                        }
                }
                if(xVel > 10 && status != HIT){xVel = 10;}
                if(xVel < -10 && !isPunched){xVel = -10;}
                if(pT.getTime() > 10 && isPunched){isPunched = false;}
                if(xVel == 0 && yVel == 0 && !p2.isPunched && status != PUNCH && status != JUMP){status = IDLE; frame = 0;}
                else if(status != PUNCH && status != JUMP && status != PUNCH){status = WALK; frame = 0;}
                if(xVel%1 != 0 && xVel > -1 && xVel < 1){xVel = 0;}
                x += xVel;
                y += yVel;
                playerRect = new Rectangle(x,y,stand[0].getWidth(null),stand[0].getHeight(null));

        }

        public void punch(player p){
                if(cooldownBet.getTime() > 10){
                        frame = 0;
                        status = PUNCH;
                        cooldownBet.reset();
                }
                
                if(p.getRect().intersects(getRect()) && status != HIT){
                        System.out.println("PUNCH");
                        if(cDown.getTime() >= 30){
                                cDown.reset();
                                numPunches = 0;
                        }
                        if(numPunches == 9){
                                p.punched(dir, poweredP, numPunches+1);
                                typePunch = poweredP;
                                numPunches = 0;
                                cooldownP.reset(); 
                                cooldownBet.reset();
                       }
                        else if(cooldownP.getTime() > 30 && cooldownBet.getTime() > 10){
                                cDown.reset();
                                p.punched(dir, 1,numPunches+1);
                                cooldownBet.reset();                                
                        }
                        numPunches++;
                        
                }
        }

        public void punched(double dir, double dist, int numPunches) {
                System.out.println("WAS PUNCHED");
                double yDist = 0;
                double knockbackScaling = 0.03; // Adjust this value to control knockback scaling
            
                if (player == 1) {
                    pT.reset();
                } else {
                    pT.reset();
                }
                
                if(numPunches == 1){

                        yDist = 5;
                }
                if (numPunches > 1 && numPunches < 9) {
                    dist = 1;
                    yDist = 5;
                } 
                else if(numPunches == 10){
                    yDist = 10;
                    dist = 10;
                }
            
                double knockback = (dist * (percentage / 100.0) * dir) * knockbackScaling;
                xVel += knockback;
                yVel -= yDist;
                isPunched = true;
                percentage += 1 * dist;
                // Increase percentage based on the magnitude of the knockback
                double percentageIncrement = Math.abs(knockback) * 0.1; // Adjust the scaling factor as needed
                percentage += percentageIncrement;
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
                        y = plat.y - getRect().height+5;
                        status = status != PUNCH ? IDLE : PUNCH;

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

        public void move(Image[] move){
                if(cooldown % WAIT == 0){
                        frame++;
                        if(frame >= move.length){
                                frame = 0;
                        }
                }
        }
        
        public void hit(){
                if(cooldown % WAIT == 0){
                        frame++;
                        if(frame >= hit.length){
                                frame = hit.length-1;
                        }
                }
        }

        public void punch(){
                if(cooldown % WAIT == 0){
                        frame++;
                        if(frame >= punch.length){
                                status = IDLE;
                        }
                }
        }

        public void draw(Graphics g) {
                g.setColor(Color.red);
                cooldown++;
                if (status == IDLE) {
                        move(stand);
                        if (dir == RIGHT){
                                g.drawImage(stand[frame], x, y, null);
                        } 
                        else{
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.drawImage(stand[frame], x + stand[frame].getWidth(null), y, -stand[frame].getWidth(null), stand[frame].getHeight(null), null);
                        }
                }
                else if(status == PUNCH){
                        punch();
                        if (dir == RIGHT){
                                g.drawImage(punch[frame], x, y, null);
                        } 
                        else{
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.drawImage(punch[frame], x + punch[frame].getWidth(null), y, -punch[frame].getWidth(null), punch[frame].getHeight(null), null);
                        }
                }
                else if(isPunched){
                        hit();
                        if (dir == RIGHT){
                                g.drawImage(hit[frame], x, y, null);
                        } 
                        else{
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.drawImage(hit[frame], x + hit[frame].getWidth(null), y, -hit[frame].getWidth(null), hit[frame].getHeight(null), null);
                        }
                }
                else if(status == JUMP){
                        move(jump);
                        System.out.println(frame + " " + jump.length);
                        if (dir == RIGHT){
                                g.drawImage(jump[frame], x, y, null);
                        } 
                        else{
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.drawImage(jump[frame], x + jump[frame].getWidth(null), y, -jump[frame].getWidth(null), jump[frame].getHeight(null), null);
                        }
                }
                else if(status == WALK){
                        move(run);
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
