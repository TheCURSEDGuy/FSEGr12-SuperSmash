import java.awt.Rectangle;



public class powerUps {
    public int width, height, player;

    public powerUps(int ww, int hh, int pp){
        width = ww;
        height = hh;
        player = pp;
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

    public void luffyThrow(player luffy, player victim){

    }

    public void ichigoDash(player ichigo){
        ichigo.dash();
    }
}
