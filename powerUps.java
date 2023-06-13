import java.awt.Rectangle;
<<<<<<< HEAD
=======

>>>>>>> d8e4458115f04d9dd08a76acd327a2d04c45e39e


public class powerUps {
    public int width, height, player;
    public boolean waterhit;
    public timer tW;

    public powerUps(int ww, int hh, int pp){
        width = ww;
        height = hh;
        player = pp;
    }

    public void update(){
        if(waterhit){
            tW.update();
            if(tW.getTime() > 100){
                waterhit = false;
            }
        }
    }

    public void multiHit(player bigSpoon, player littleSpoon){
        Rectangle swordieSlash = new Rectangle(bigSpoon.getRect().x - (int)bigSpoon.getRect().getHeight(), bigSpoon.getRect().y, 2*bigSpoon.getRect().width, bigSpoon.getRect().height);
        if(swordieSlash.intersects(littleSpoon.getRect())){

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

    }

    public void ichigoDash(player ichigo){
        ichigo.dash();
    }

    public void waterAttack(player aang, player victim){
        
    }
}
