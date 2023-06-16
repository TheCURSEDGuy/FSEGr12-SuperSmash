import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;

public class player {
        player thisPlayer;
        player otherPlayer;
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
        boolean topPlat = false;


        // Timers
        timer pT = new timer(500);
        timer nPunch;
        timer cDown = new timer(1000);

        // finals
        public final int LEFT = -1, RIGHT = 1, UP = 2, NONE = 3;
        public final int IDLE = 0, WALK = 1, JUMP = 2, PUNCH = 3, HIT = 4,  ATTACK1 = 5, ATTACK2 = 6, ULT = 7;
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

        private timer cooldownDash = new timer(2000);
        private timer cooldownKick = new timer(2000);
        private timer cooldownWaterAttack = new timer(2000);
        private timer cooldownHardAttack = new timer(2000);
        private timer cooldownMultiHit = new timer(2000);
        private timer cooldownMH = new timer(2000);

        private timer cooldownUltKak = new timer(3000);
        private timer cooldownUltAang = new timer(3000);
        private timer cooldownUltIchigo = new timer(3000);
        private timer cooldownUltLuffy = new timer(3000);

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
                y = 600;
                playerRect = new Rectangle(x,y,stand[0].getWidth(null),stand[0].getHeight(null));
                this.player = player;
                dir = player == 1 ? RIGHT : LEFT;
                // pU = new powerUps(playerRect.width, playerRect.height, player);
                health = new healthBar(dir, new ImageIcon("Pics/" + playerName + "/pic.png").getImage());


        }

        public void move(boolean keys[], player p1, player p2){
                // pU.update();
                pT.update();
                cooldownP.update();
                cooldownBet.update();
                cooldownDash.update();
                cooldownKick.update();
                cooldownHardAttack.update();
                cooldownMultiHit.update();
                cooldownWaterAttack.update();
                cooldownMH.update();
                

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
                if(xVel == 0 && yVel == 0 &&  status != HIT && status != PUNCH && status != JUMP && status != IDLE && status != ATTACK1 && status != ATTACK2 && status != ULT){status = IDLE; frame = 0;}
                if(xVel%1 != 0 && xVel > -1 && xVel < 1){xVel = 0;}
                
                x += xVel;
                y += yVel;
                if(x > 1600 && x < 0){respawn();}
                if(y > 1000){respawn();}
                playerRect = new Rectangle(x,y,stand[0].getWidth(null),stand[0].getHeight(null));
                if(health.healthNum <= 0){
                        respawn();
                }
                if(status == ATTACK2 && (playerName == "ichigo" || playerName == "luffy" || playerName == "aang")){
                        multiHit(thisPlayer, otherPlayer);
                }
                if(status == ULT && playerName == "luffy"){
                        xVel = dir*50;
                        luffyUlt(thisPlayer, otherPlayer);
                }
                

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

                health.healthDown((int)dist);
                xVel += 10*dir;
                yVel -= yDist;
                frame = 0;
                status = HIT;
        }

        public void respawn(){
                health.heartDecrease();
                health.healthNum = 100;
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
        
        
        

        
        

        public void dash(){
                if(cooldownDash.getTime() > 50){
                        frame = 0;
                        status = ATTACK1;
                        cooldownDash.reset();
                        if(dir == RIGHT){
                                x += 150;
                        }
                        else{
                                x -= 150;
                        }
                }
        }


    public void multiHit(player attacker, player victim){
        Rectangle player = new Rectangle(attacker.getRect().x - (int)attacker.getRect().getHeight(), attacker.getRect().y, 2*attacker.getRect().width, attacker.getRect().height);
        if(attacker.status == ATTACK2 && player.intersects(victim.getRect())){                
                victim.health.healthDown(2);
                victim.frame = 0;
                victim.status = HIT;
                victim.pT.reset();
                victim.yVel -= 5;
                victim.isPunched = true; 

        }
        otherPlayer = victim;
        thisPlayer = attacker;
        if(cooldownMultiHit.getTime() > 50){
                attacker.frame = 0;
                cooldownMultiHit.reset();
                attacker.status = ATTACK2;
                
                if(attacker.status == IDLE){
                        cooldownMultiHit.reset();
                }
        }

        victim.isPunched = false;
    }

    public void hardAttack(player attacker, player victim){
        Rectangle player = attacker.dir == attacker.RIGHT ? new Rectangle(attacker.getRect().x, attacker.getRect().y, 2*attacker.getRect().width, attacker.getRect().height) : new Rectangle(attacker.getRect().x - attacker.getRect().width, attacker.getRect().y, 2*attacker.getRect().width, attacker.getRect().height);
        if(cooldownHardAttack.getTime() > 50){
                attacker.frame = 0;
                attacker.status = ATTACK1;
                cooldownHardAttack.reset();
                if(player.intersects(victim.getRect())){
                        if(attacker.dir == RIGHT){
                                victim.xVel += 15;
                        }
                        else{
                                victim.xVel -= 15;
                        }
                        victim.yVel -= 8;
                        victim.health.healthDown(5);
                        victim.isPunched = true;
                        victim.pT.reset();
                        victim.frame = 0;
                        victim.status = HIT;
                }
        }
        victim.isPunched = false;
    }


    public void kickUp(player kakashi, player victim){
        Rectangle player = kakashi.dir == kakashi.RIGHT ? new Rectangle(kakashi.getRect().x, kakashi.getRect().y, 2*kakashi.getRect().width, kakashi.getRect().height) : new Rectangle(kakashi.getRect().x - kakashi.getRect().width, kakashi.getRect().y, 2*kakashi.getRect().width, kakashi.getRect().height);
        if(cooldownKick.getTime() > 50){
                kakashi.frame = 0;
                kakashi.status = ATTACK2;
                cooldownKick.reset();
                if(player.intersects(victim.getRect())){
                    victim.frame = 0;
                    victim.pT.reset();
                    victim.status = HIT;
                    victim.yVel -= 10;
                    victim.isPunched = true;
                    victim.health.healthDown(5);
                }
        }
        victim.isPunched = false;
}

    

    





    
    public void kakashiUlt(player kakashi, player victim){
        if(cooldownUltKak.getTime() > 200){

                kakashi.frame = 0;
                kakashi.status = ULT;
                kakashi.pT.reset();
                cooldownUltKak.reset();
                //sprite under victim
                victim.health.healthDown(15);
        }
    }

    public void aangUlt(player aang, player victim){
        if(cooldownUltAang.getTime() > 200){
                aang.frame = 0;
                aang.status = ULT;
                aang.pT.reset();
                cooldownUltAang.reset();
                //sprite under victim
                victim.health.healthDown(15);
        }
    }

    public void luffyUlt(player luffy, player victim){
        otherPlayer = victim;
        thisPlayer = luffy;
        Rectangle player = luffy.dir == luffy.RIGHT ? new Rectangle(luffy.getRect().x, luffy.getRect().y, 2*luffy.getRect().width, luffy.getRect().height) : new Rectangle(luffy.getRect().x - luffy.getRect().width, luffy.getRect().y, 2*luffy.getRect().width, luffy.getRect().height);
        if(luffy.status == ULT && player.intersects(victim.getRect())){
            if(luffy.dir == RIGHT){
                        victim.xVel += 50;
                }
                else{
                        victim.xVel -= 50;
                }
                victim.yVel -= 10;
                victim.isPunched = true;
                victim.health.healthDown(15);
        }
        if(cooldownUltLuffy.getTime() > 100){
                luffy.frame = 0;
                luffy.status = ULT;
                luffy.pT.reset();
                cooldownUltLuffy.reset();
                
        }
        victim.isPunched = false;

    }


    public void ichigoUlt(player ichigo, player victim){
        if(cooldownUltIchigo.getTime() > 200){
                ichigo.frame = 0;
                ichigo.status = ULT;
                ichigo.pT.reset();
                cooldownUltIchigo.reset();
        
                int originalgX = ichigo.x;

                //timer
                ichigo.x = victim.x - victim.getRect().width;
                ichigo.dir = RIGHT;
        
                //animation
                //timer
                ichigo.x = originalgX;
        
                victim.health.healthDown(15);
        }
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
                // System.out.println(y + getRect().height+40 + " " + plat.y);
                // if(y+getRect().height< plat.y){
                //         topPlat = true;
                // }

                // else{
                //         topPlat = false;
                // }
                
                if(getRect().intersects(plat)){
                        jumped = false;
                        
                        if(status != HIT){
                                yVel = 0;
                        }
                        y = plat.y - getRect().height+5;
                        if(status != HIT && status != ATTACK1 && status != ATTACK2 && status != ULT){
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
                if(!getRect().intersects(plat) && !intersectList(plats) && status != HIT){
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

        public void punch(Image[] punch){
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
                        punch(punch);
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
                else if(status == ATTACK1){
                        punch(attack1);
                        if (dir == RIGHT){
                                g.drawImage(attack1[frame], x, y, null);
                        } 
                        else{
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.drawImage(attack1[frame], x + attack1[frame].getWidth(null), y, -attack1[frame].getWidth(null), attack1[frame].getHeight(null), null);
                        }
                }
                else if(status == ATTACK2){
                        punch(attack2);
                        if (dir == RIGHT){
                                g.drawImage(attack2[frame], x, y, null);
                        } 
                        else{
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.drawImage(attack2[frame], x + attack2[frame].getWidth(null), y, -attack2[frame].getWidth(null), attack2[frame].getHeight(null), null);
                        }
                }
                else if(status == ULT){
                        punch(ult);
                        if (dir == RIGHT){
                                g.drawImage(ult[frame], x, y, null);
                        } 
                        else{
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.drawImage(ult[frame], x + ult[frame].getWidth(null), y, -ult[frame].getWidth(null), ult[frame].getHeight(null), null);
                        }
                }
        }
}
