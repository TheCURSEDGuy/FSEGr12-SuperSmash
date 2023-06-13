import java.awt.Rectangle;


public class powerUps {
    public int width, height, player;

    public powerUps(int ww, int hh, int pp){
        width = ww;
        height = hh;
        player = pp;
    }

    public void multiHit(player bigSpoon, player littleSpoon){
        //check if sword rectangle intersects player 


        Rectangle swordieSlash = new Rectangle(bigSpoon.getRect().x, bigSpoon.getRect().y, 2*bigSpoon.getRect().x, bigSpoon.getRect().y);
        
    }

}
