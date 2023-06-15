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
        public int speedX = 2;
        public double xVel,yVel;
        private int player;
        public int dir;
        Rectangle playerRect;
        String playerName = "";
        int frame = 0;
        public int status;
        public boolean isStunned = false;

        // booleans
        boolean jumped = false;


        // Timers
        timer pT = new timer(500);
        timer nPunch;
        timer cDown = new timer(1000);

        // finals
        public final int LEFT = -1, RIGHT = 1, UP = 2, NONE = 3;
        public final int IDLE = 0, WALK = 1, JUMP = 2, PUNCH = 3, HIT = 4;
        public final boolean P1 = true, P2 = false;
        private final int normalP = 3, poweredP = 10;
        private final int WAIT = 4;

        // attacks
        private int typePunch = normalP;
        private int numPunches = 0;
        boolean isPunched = false;

        // cooldowns
        private timer cooldownP = new timer(1000);
        private timer cooldownBet = new timer(1000);
        private int cooldown = 0;

        // health
        healthBar health;

        // sprites
        Image[] attack1;
        Image[] attack2;
        Image[] jump;
        Image[] punch;
        Image[] run;
        Image[] stand;
        Image[] ult;
        Image[] hit;

        // powerUps pU;

        public player(){
                playerName = "";
        }


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
                System.out.println(dir);
                // pU = new powerUps(playerRect.width, playerRect.height, player);
                health = new healthBar(dir, new ImageIcon("Pics/" + playerName + "/pic.png").getImage());


        }

        public void move(boolean keys[], player p1, player p2){
                // pU.update();
                pT.update();
                cooldownP.update();
                cooldownBet.update();
                if(isStunned){
                        xVel = 0;
                        yVel = 0;
                        // timer
                }
                if(pT.getTime() > 10 && isPunched){isPunched = false;}
                if(player == 1 && status != HIT){
                        if(keys[KeyEvent.VK_D]){
                                dir = RIGHT;
                                status = status != JUMP && status != HIT ? WALK : JUMP;
                                xVel += speedX;
                        }
                        if(keys[KeyEvent.VK_A]){
                                dir = LEFT;
                                xVel -= speedX;
                                status = status != JUMP && status != HIT ? WALK : JUMP;

                        }
        
                        if(keys[KeyEvent.VK_W] && !jumped){
                                frame = 0;
                                status = JUMP;
                                jumped = true;
                                yVel -= 15;
                        }
                }
                if(player == -1 && status != HIT){
                        if(keys[KeyEvent.VK_RIGHT]){
                                dir = RIGHT;
                                status = status != JUMP && status != HIT ? WALK : JUMP;
                                xVel += speedX;
                        }
                        if(keys[KeyEvent.VK_LEFT]){
                                dir = LEFT;
                                xVel -= speedX;
                                status = status != JUMP && status != HIT ? WALK : JUMP;
                        }
        
                        if(keys[KeyEvent.VK_UP] && !jumped){
                                frame = 0;
                                status = JUMP;
                                jumped = true;
                                yVel -= 15;
                        }
                }
                if(xVel > 10 && status != HIT){xVel = 10;}
                if(xVel < -10 && status != HIT){xVel = -10;}
                if(pT.getTime() > 10 && status == HIT){status = IDLE; frame = 0;}
                if(xVel == 0 && yVel == 0 &&  status != HIT && status != PUNCH && status != JUMP && status != IDLE){status = IDLE; frame = 0;}
                if(xVel%1 != 0 && xVel > -1 && xVel < 1){xVel = 0;}
                x += xVel;
                y += yVel;
                if(x > 1600 && x < 0){respawn();}
                if(y > 1000){respawn();}
                playerRect = new Rectangle(x,y,stand[0].getWidth(null),stand[0].getHeight(null));

        }

        public void punch(player p){
                if(cooldownBet.getTime() > 30){
                        frame = 0;
                        status = PUNCH;
                        cooldownBet.reset();
                }
                if(cDown.getTime() >= 30){
                        cDown.reset();
                        numPunches = 0;
                }
                
                if(p.getRect().intersects(getRect()) && status != HIT){
                        if(numPunches == 9){
                                p.punched(dir, poweredP, numPunches+1);
                                typePunch = poweredP;
                                numPunches = 0;
                                cooldownP.reset(); 
                                cooldownBet.reset();
                       }
                        else if(cooldownP.getTime() > 30){
                                cooldownP.reset();
                                p.punched(dir, 1,numPunches+1);
                                cooldownBet.reset();                                
                        }
                        numPunches++;
                        
                }
        }

        public void punched(double dir, double dist, int numPunches) {
                double yDist = 0;
                double knockbackScaling = 300.0; // Adjust this value to control knockback scaling
            
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

                xVel += 10;
                yVel -= yDist;
                frame = 0;
                status = HIT;
        }

        public void respawn(){
                health.heartDecrease();
                x = player == 1 ? 200 : 1300;
                y = 700;
                playerRect = new Rectangle(x,y,stand[0].getWidth(null),stand[0].getHeight(null));
                status = IDLE;
                frame = 0;
                cooldownP.reset();
                cooldownBet.reset();
                cDown.reset();
                pT.reset();
        }
        
        public void punchedHarder(double dir){
            
                if (player == 1) {
                    pT.reset();
                } else {
                    pT.reset();
                }
                
                double knockback = 15;
                xVel += knockback;
                isPunched = true;
        }
        public void waterAttacked(double dir){
            
                if (player == 1) {
                    pT.reset();
                } else {
                    pT.reset();
                }
                
                double knockback = 15;
                xVel += knockback;
                isPunched = true;

                speedX = 1;
                //timer needed
        }

        public void multiHit(){
                if (player == 1) {
                        pT.reset();
                    } else {
                        pT.reset();
                    }

                    isPunched = true;
                    speedX = 0;
                    //timer needed
        }
        public void kick(){
                if (player == 1) {
                        pT.reset();
                } else {
                        pT.reset();
                }

                yVel -= 15;
                isPunched = true;
        }

        public void dash(){
                if (player == 1) {
                        pT.reset();
                } else {
                        pT.reset();
                }

                if(dir == RIGHT){
                        x += 8;
                }
                else{
                        x -= 8;
                }
        }

        public void boulder(){
                if (player == 1) {
                        pT.reset();
                } else {
                        pT.reset();
                }

                if(dir == RIGHT){
                        xVel += 5;
                }
                else{
                        xVel -= 5;
                }

                // make so he can cancel
        }


        public void kakashiUltHit(){
                isStunned = true;
                //different sprite called kakashis "ult hit"
        }

        public void luffyUltPunch(){
                isStunned = true;
                //different sprite
        }

        public void aangUltHit(){
                isStunned = true;
                //different sprite
        }

        public void ichigoPeePeeUlt(){
                isStunned = true;
        }

    public void multiHit(player bigSpoon, player littleSpoon){
        Rectangle swordieSlash = new Rectangle(bigSpoon.getRect().x - (int)bigSpoon.getRect().getHeight(), bigSpoon.getRect().y, 2*bigSpoon.getRect().width, bigSpoon.getRect().height);
        if(swordieSlash.intersects(littleSpoon.getRect())){
            littleSpoon.punchedHarder(bigSpoon.dir);
        }
    }

    public void hardAttack(player bigSpoon, player littleSpoon){
        Rectangle player = bigSpoon.dir == bigSpoon.RIGHT ? new Rectangle(bigSpoon.getRect().x, bigSpoon.getRect().y, 2*bigSpoon.getRect().width, bigSpoon.getRect().height) : new Rectangle(bigSpoon.getRect().x - bigSpoon.getRect().width, bigSpoon.getRect().y, 2*bigSpoon.getRect().width, bigSpoon.getRect().height);
        if(player.intersects(littleSpoon.getRect())){
            littleSpoon.punchedHarder(bigSpoon.dir);
        }
        littleSpoon.isPunched = false;
    }


    public void kickUp(player kakashi, player victim){
        Rectangle player = kakashi.dir == kakashi.RIGHT ? new Rectangle(kakashi.getRect().x, kakashi.getRect().y, 2*kakashi.getRect().width, kakashi.getRect().height) : new Rectangle(kakashi.getRect().x - kakashi.getRect().width, kakashi.getRect().y, 2*kakashi.getRect().width, kakashi.getRect().height);
        if(player.intersects(victim.getRect())){
            victim.kick();
        }
        victim.isPunched = false;
    }

    public void luffyBoulder(player luffy, player victim){
        Rectangle player = luffy.dir == luffy.RIGHT ? new Rectangle(luffy.getRect().x, luffy.getRect().y, 2*luffy.getRect().width, luffy.getRect().height) : new Rectangle(luffy.getRect().x - luffy.getRect().width, luffy.getRect().y, 2*luffy.getRect().width, luffy.getRect().height);
        if(player.intersects(victim.getRect())){
            victim.punchedHarder(luffy.dir);
        }
    }

//     public void ichigoDash(player ichigo){
//         ichigo.dash();
//     }

    public void waterAttack(player aang, player victim){
        Rectangle player = aang.dir == aang.RIGHT ? new Rectangle(aang.getRect().x, aang.getRect().y, 3*aang.getRect().width, aang.getRect().height) : new Rectangle(aang.getRect().x - aang.getRect().width, aang.getRect().y, 2*aang.getRect().width, aang.getRect().height);
        if(player.intersects(victim.getRect())){
            victim.waterAttacked(aang.dir);
        }
    }








    public void kakashiUlt(player victim){
        victim.kakashiUltHit();
    }


    public void luffyUlt(player victim){
        victim.luffyUltPunch();
    }

    public void aangUlt(player victim){
        victim.aangUltHit();
    }

    public void ichigoUlt(player ichigo, player victim){
        int ogX = ichigo.x;
        //timer
        ichigo.x = victim.x - victim.getRect().width;
        victim.ichigoPeePeeUlt();
        //animation
        //timer
        ichigo.x = ogX;
    }

        
        public void friction(){
                if(xVel > 0){
                        xVel -= 1.5;
                }
                else if(xVel < 0){
                        xVel += 1.5;
                }
        }

        public void gravity(Rectangle plat, Rectangle[] plats){
                if(getRect().intersects(plat)){
                        jumped = false;
                        if(status != HIT){
                                yVel = 0;
                        }
                        y = plat.y - getRect().height+5;
                        if(status != HIT){
                                status = status != PUNCH ? IDLE : PUNCH;
                        }
                        if(status == IDLE){
                                if(frame >= stand.length){
                                        frame = 0;
                                }
                        }
                        else if(status == PUNCH){
                                if(frame >= punch.length){
                                        frame = 0;
                                }
                        }
                }
                else if(intersectList(plats) && yVel >= 0){
                        jumped = false;
                        if(status != HIT){
                                yVel = 0;
                        }
                        y = plats[0].y - getRect().height+1;
                        status = status != PUNCH ? IDLE : PUNCH;
                        if(status == IDLE){
                                if(frame >= stand.length){
                                        frame = 0;
                                }
                        }
                        else if(status == PUNCH){
                                if(frame >= punch.length){
                                        frame = 0;
                                }
                        }
                }
                else{
                        yVel += 1;
                }
        }

        public boolean intersectList(Rectangle[] plats){
                for(Rectangle p:plats){
                        if(playerRect.intersects(p)){
                                return true;
                        }
                }
                return false;
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
                                frame = 0;
                        }
                }
        }

        public void draw(Graphics g) {
                health.draw(g);
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
                        if (dir == RIGHT && status != IDLE){
                                g.drawImage(punch[frame], x, y, null);
                        } 
                        else if(status != IDLE){
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.drawImage(punch[frame], x + punch[frame].getWidth(null), y, -punch[frame].getWidth(null), punch[frame].getHeight(null), null);
                        }
                }
                else if(status == HIT){
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
