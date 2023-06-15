// import java.awt.Rectangle;

// public class powerUps {
//     public int width, height, player;
//     public boolean waterhit;
//     public timer tW = new timer(105);

//     public powerUps(int ww, int hh, int pp){
//         width = ww;
//         height = hh;
//         player = pp;
//     }

//     public void update(){
//         if(waterhit){
//             tW.update();
//             if(tW.getTime() > 100){
//                 waterhit = false;
//             }
//         }
//     }

    

//     public void multiHit(player bigSpoon, player littleSpoon){
//         Rectangle swordieSlash = new Rectangle(bigSpoon.getRect().x - (int)bigSpoon.getRect().getHeight(), bigSpoon.getRect().y, 2*bigSpoon.getRect().width, bigSpoon.getRect().height);
//         if(swordieSlash.intersects(littleSpoon.getRect())){
//             littleSpoon.punchedHarder(bigSpoon.dir);
//         }
//     }

//     public void hardAttack(player bigSpoon, player littleSpoon){
//         Rectangle player = bigSpoon.dir == bigSpoon.RIGHT ? new Rectangle(bigSpoon.getRect().x, bigSpoon.getRect().y, 2*bigSpoon.getRect().width, bigSpoon.getRect().height) : new Rectangle(bigSpoon.getRect().x - bigSpoon.getRect().width, bigSpoon.getRect().y, 2*bigSpoon.getRect().width, bigSpoon.getRect().height);
//         if(player.intersects(littleSpoon.getRect())){
//             littleSpoon.punchedHarder(bigSpoon.dir);
//         }
//         littleSpoon.isPunched = false;
//     }


//     public void kickUp(player kakashi, player victim){
//         Rectangle player = kakashi.dir == kakashi.RIGHT ? new Rectangle(kakashi.getRect().x, kakashi.getRect().y, 2*kakashi.getRect().width, kakashi.getRect().height) : new Rectangle(kakashi.getRect().x - kakashi.getRect().width, kakashi.getRect().y, 2*kakashi.getRect().width, kakashi.getRect().height);
//         if(player.intersects(victim.getRect())){
//             victim.kick();
//         }
//         victim.isPunched = false;
//     }

//     public void luffyBoulder(player luffy, player victim){
//         Rectangle player = luffy.dir == luffy.RIGHT ? new Rectangle(luffy.getRect().x, luffy.getRect().y, 2*luffy.getRect().width, luffy.getRect().height) : new Rectangle(luffy.getRect().x - luffy.getRect().width, luffy.getRect().y, 2*luffy.getRect().width, luffy.getRect().height);
//         if(player.intersects(victim.getRect())){
//             victim.punchedHarder(luffy.dir);
//         }
//     }

//     public void ichigoDash(player ichigo){
//         ichigo.dash();
//     }

//     public void waterAttack(player aang, player victim){
//         Rectangle player = aang.dir == aang.RIGHT ? new Rectangle(aang.getRect().x, aang.getRect().y, 3*aang.getRect().width, aang.getRect().height) : new Rectangle(aang.getRect().x - aang.getRect().width, aang.getRect().y, 2*aang.getRect().width, aang.getRect().height);
//         if(player.intersects(victim.getRect())){
//             victim.waterAttacked(aang.dir);
//         }
//     }








//     public void kakashiUlt(player victim){
//         victim.kakashiUltHit();
//     }


//     public void luffyUlt(player victim){
//         victim.luffyUltPunch();
//     }

//     public void aangUlt(player victim){
//         victim.aangUltHit();
//     }

//     public void ichigoUlt(player ichigo, player victim){
//         int ogX = ichigo.x;
//         //timer
//         ichigo.x = victim.x - victim.getRect().width;
//         victim.ichigoPeePeeUlt();
//         //animation
//         //timer
//         ichigo.x = ogX;
//     }
// }
