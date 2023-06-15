import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;

public class gameFSE extends JFrame{
	GamePanel game= new GamePanel();
		
    public gameFSE() {
		super("Move the Box");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(game);
		setVisible(true);
		setSize(1600, 1000);
		
    }
    
    public static void main(String[] arguments) {
		gameFSE frame = new gameFSE();	
    }
}

class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener{
    	private final int INTRO = 0, CHARSELECT = 1, GAME = 2, END = 3;
	private int screen = INTRO;
    	private boolean text1 = true;
    	private boolean text2 = false;
    	int frameCnt = 0;
	Font fontLocal=null, fontSys=null, fontLocal2 = null;

	// INTRO
	int txtX = -400;
	int txtTransparency = 0;
	int imgtransparency = 0;
	boolean introOver = false;
	boolean START = false;
	int polX = 0;
	int polY = 0;
	int polTransparency = 0;

	// CHARSELECT
	int fadeLIGHT = 255;
	int fadeINCHAR = 0;
	int charX = 0;
	int charY = 0;
	int charTransparency = 0;
	boolean charSel = false;
	boolean char1 = false;
	boolean char2 = false;
	int mx, my;

	//OBJECTS
	player p1 = new player();
	player p2 = new player();
	Rectangle plat;
	Rectangle[] plats;
	hand h = new hand();
	circle c1 = new circle(1);
	circle c2 = new circle(2);
	
	// KEYS
	private boolean []keys;
	Timer timer;
	Image back;
	Image platf;

	// IMAGES
	Image luffy = new ImageIcon("Pics/luffy/stand/0.png").getImage();
	Image ichigo = new ImageIcon("Pics/ichigo/pic.png").getImage();
	Image kakashi = new ImageIcon("Pics/kakashi/stand/0.png").getImage();
	Image hand = new ImageIcon("Pics/hand.png").getImage();
	Image p1Circle = new ImageIcon("Pics/p1Circle.png").getImage();
	Image p2Circle = new ImageIcon("Pics/p2Circle.png").getImage();
	
	
	public GamePanel(){
		plats = new Rectangle[]{new Rectangle(300,400,400,50),new Rectangle(900,400,400,50)};
		back = new ImageIcon("Pics/background.gif").getImage();
		platf = new ImageIcon("Pics/platform.png").getImage();
		plat = new Rectangle(100,600,1400,200);
		keys = new boolean[KeyEvent.KEY_LAST+1];

        String fName = "TITLEFONT.ttf";
	String fName2 = "SUBTITLEFONT.otf";
    	InputStream is = GamePanel.class.getResourceAsStream(fName);
		InputStream is2 = GamePanel.class.getResourceAsStream(fName2);
    	try{
    		fontLocal = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(200f);
		fontLocal2 = Font.createFont(Font.TRUETYPE_FONT, is2).deriveFont(30f);
    	}
    	catch(IOException ex){
    		System.out.println(ex);	
    	}
    	catch(FontFormatException ex){
    		System.out.println(ex);	
    	}
	
    		fontSys = new Font("Comic Sans MS",Font.PLAIN,32);
		setSize(800,600);
		
		setPreferredSize(new Dimension(800, 600));
		
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		addMouseListener(this);
		timer = new Timer(40, this);
		timer.start();
	}

	public void move(){
		mx = MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x;
		my = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y;
		if(screen == INTRO){
			
		}
		else if(screen == CHARSELECT){
			if(new Rectangle(75,550,300,300).contains(c1.x,c1.y)){
				char1 = true;
				p1 = new player(1, "luffy");
			}
			else if(new Rectangle(475,550,300,300).contains(c1.x,c1.y)){
				System.out.println("ichigo");
				char1 = true;
				p1 = new player(1, "ichigo");
			}
			else if(new Rectangle(875,550,300,300).contains(c1.x,c1.y)){
				char1 = true;
				p1 = new player(1, "kakashi");
			}
			else if(new Rectangle(1275,550,300,300).contains(c1.x,c1.y)){
				char1 = true;
				p1 = new player(1, "aang");
			}
			else{
				char1 = false;
			}
			if(new Rectangle(75,550,300,300).contains(c2.x,c2.y)){
				char2 = true;
				p2 = new player(-1, "luffy");
			}
			else if(new Rectangle(475,550,300,300).contains(c2.x,c2.y)){
				char2 = true;
				p2 = new player(-1, "ichigo");
			}
			else if(new Rectangle(875,550,300,300).contains(c2.x,c2.y)){
				char2 = true;
				p2 = new player(-1, "kakashi");
			}
			else if(new Rectangle(1275,550,300,300).contains(c2.x,c2.y)){
				char2 = true;
				p2 = new player(-1, "aang");
			}
			else{
				char2 = false;
			}
					
		}
		else if(screen == GAME){
			p1.gravity(plat,plats);
			p1.move(keys, p1, p2);
			p1.friction();
			p1.update();

			p2.gravity(plat,plats);
			p2.move(keys, p1, p2);
			p2.friction();
			p2.update();

			
		}

	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		// Point mouse = MouseInfo.getPointerInfo().getLocation();
		// Point offset = getLocationOnScreen();
		move(); // never draw in move
		repaint(); // only draw
		//Graphics gg = getGraphics();
	}
	
	@Override
	public void keyReleased(KeyEvent ke){
		int key = ke.getKeyCode();
		keys[key] = false;
		
	}	
	
	@Override
	public void keyPressed(KeyEvent ke){
		int key = ke.getKeyCode();
		keys[key] = true;
		if(screen == INTRO && introOver){
			START = true;
		}
		if(screen == CHARSELECT){
			if(key == KeyEvent.VK_ESCAPE){
				screen = INTRO;
			}
			
		}
		if(screen == GAME){
			if(key == KeyEvent.VK_Q){
				p1.punch(p2);
			}
			if(key == KeyEvent.VK_COMMA){
				p2.punch(p1);
			}
			if(key == KeyEvent.VK_E){
				if(p1.playerName == "ichigo"){
					p1.dash();
				}
				else if(p1.playerName == "luffy"){
					p1.hardAttack(p1, p2);
				}
				else if(p1.playerName == "aang"){
					p1.waterAttack(p1, p2);
				}
				else if(p1.playerName == "kakashi"){
					p1.hardAttack(p1, p2);
				}
			}
			if(key == KeyEvent.VK_PERIOD){
				if(p2.playerName == "ichigo"){
					p2.dash();
				}
				else if(p2.playerName == "luffy"){
					p2.hardAttack(p2, p1);
				}
				else if(p1.playerName == "aang"){
					p2.waterAttack(p2, p1);
				}
				else if(p1.playerName == "kakashi"){
					p2.hardAttack(p2, p1);
				}
			}
			if(key == KeyEvent.VK_R){
				if(p1.playerName == "ichigo"){
					p1.multiHit(p1,p2);
				}
				else if(p1.playerName == "luffy"){
					p1.multiHit(p1, p2);
				}
				else if(p1.playerName == "aang"){
					p1.multiHit(p1, p2);
				}
				else if(p1.playerName == "kakashi"){
					p1.kickUp(p1, p2);
				}
			}
			if(key == KeyEvent.VK_SLASH){
				if(p2.playerName == "ichigo"){
					p2.multiHit(p2,p1);
				}
				else if(p1.playerName == "luffy"){
					p2.multiHit(p2, p1);
				}
				else if(p1.playerName == "aang"){
					p2.multiHit(p2, p1);
				}
				else if(p1.playerName == "kakashi"){
					p2.kickUp(p2, p1);
				}
			}
			if(key == KeyEvent.VK_S){
				if(p1.playerName == "ichigo"){
					p1.ichigoUlt(p1, p2);
				}
				else if(p1.playerName == "luffy"){
					p1.luffyUlt(p2);
				}
				else if(p1.playerName == "aang"){
					p1.aangUlt(p2);
				}
				else if(p1.playerName == "kakashi"){
					p1.kakashiUlt(p2);
				}
			}
			if(key == KeyEvent.VK_DOWN){
				if(p2.playerName == "ichigo"){
					p2.ichigoUlt(p2, p1);
				}
				else if(p2.playerName == "luffy"){
					p2.luffyUlt(p1);
				}
				else if(p2.playerName == "aang"){
					p2.aangUlt(p1);
				}
				else if(p1.playerName == "kakashi"){
					p2.kakashiUlt(p1);
				}
			}
			
		}
	}
	
	
	@Override
	public void keyTyped(KeyEvent ke){}
	@Override
	public void mouseClicked(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		if(charSel){
			// if(mx > 100 && mx < 300 && my > 600 && my < 800){
			// 	if(!char1){
			// 		char1 = true;
			// 		p1 = new player(1, "luffy");
			// 	}
			// 	else if(!char2 && p1.playerName != "luffy"){
			// 		char2 = true;
			// 		p2 = new player(-1, "luffy");
			// 	}
			// }
			// if(mx > 400 && mx < 600 && my > 600 && my < 800){
			// 	if(!char1){
			// 		char1 = true;
			// 		p1 = new player(1, "ichigo");
			// 	}
			// 	else if(!char2 && p1.playerName != "ichigo"){
			// 		System.out.println("ichigo2");
			// 		char2 = true;
			// 		p2 = new player(-1, "ichigo");
			// 	}
			// }
			if(char1 && char2 && new Rectangle(getWidth()-200, 0, 200, 100).contains(mx, my)){
				screen = GAME;
			}	

			
		}
	}

	@Override
	public void	mouseEntered(MouseEvent e){}

	@Override
	public void	mouseExited(MouseEvent e){}

	@Override
	public void	mousePressed(MouseEvent e){
		if(screen == INTRO){
            		repaint();
		}
		if(screen == CHARSELECT){
			moveCircle();
		}
        	if(screen == GAME){
            		move();
            		repaint();
        	}	
	}

	@Override
	public void	mouseReleased(MouseEvent e){
		if(c1.isTouched){
			c1.isNotTouched(mx, my);
			h.switchMode(0);
		}
		if(c2.isTouched){
			c2.isNotTouched(mx,my);
			h.switchMode(0);
		}
	}

	public void moveHand(Graphics g){
		int x = mx-hand.getWidth(null)/2;
		int y = my-hand.getHeight(null)/2;
		h.move(x, y);
	}

	public void moveCircle(){
		if(h.getRect().contains(c1.x,c1.y)){
			c1.isTouched();
			h.switchMode(1);
		}
		else if(h.getRect().contains(c2.x,c2.y)){
			c2.isTouched();
			h.switchMode(2);
		}
	}

	@Override
	public void paint(Graphics g){
		if(screen == INTRO){
			if(txtX < 400){
				txtX += 50;	
			}
			else if(txtTransparency < 255){
				txtTransparency += 5;
			}
			else{
				introOver = true;
			}
			if(START && imgtransparency < 255){
				imgtransparency += 15;
			}
			else if(START && imgtransparency >= 255 && polX <= getWidth() && polY <= getHeight()){
				polTransparency = 255;
				polX += 50;
				polY += 50;
			}
			else if(START){
				txtX = -400;
				txtTransparency = 0;
				imgtransparency = 0;
				polX = 0;
				polY = 0;
				polTransparency = 0;
				introOver = false;
				START = false;
				fadeLIGHT = 255;
				fadeINCHAR = 0;
				charX = 0;
				charY = 0;
				charTransparency = 0;
				charSel = false;

				screen = CHARSELECT;
			}
			g.fillRect(0, 0, getWidth(), getHeight());
            		g.setColor(Color.red);  
	    		g.setFont(fontLocal);
	    		g.drawString("SMASH", txtX, 400);
			g.setColor(new Color(255, 255, 255, txtTransparency));
			g.setFont(fontLocal2);
			g.drawString("Press sumn to start", txtX, 450);
			g.setColor(new Color(255, 255, 255, imgtransparency));
			g.drawLine(0, 0, getWidth(), getHeight());
			g.setColor(new Color(255, 255, 255, polTransparency));
			g.fillPolygon(new int[]{0,polX,getWidth(),getWidth(),getWidth()-polX,0}, new int[]{0,0,getHeight()-polX,getHeight(),getHeight(),polY}, 6);
        	}
		if(screen == CHARSELECT){
			if(fadeLIGHT > 0){
				fadeLIGHT -= 5;
			}
			if(fadeINCHAR < 255){
				fadeINCHAR += 5;
			}
			// else if(charTransparency < 200){
			// 	charTransparency += 10;
			// 	charX += 5;
			// 	charY += 20;
			// 	if(charTransparency > 200){
			// 		charTransparency = 200;
			// 	}
			// }
			charSel = true;
			g.setColor(new Color(255, 255, 255, fadeLIGHT));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(150, 0, 0, fadeINCHAR));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(10, 10, 10, 100));
			g.fillRect(50,500,1500,400);

			if(charSel){
				for(int i = 0; i < 4; i++){
					g.setColor(new Color(255, 255, 255, 200));
					g.fillRect(375*i+85,550,300,300);
					if(i == 0){
						g.drawImage(new ImageIcon("Pics/luffy/pic.png").getImage(), 375*i+100, 550, 275, 275, null);
					}
					else if(i == 1){
						g.drawImage(new ImageIcon("Pics/ichigo/pic.png").getImage(), 375*i+100, 550, 275, 275, null);
					}
				}
				if(p1.playerName != ""){
					if(p1.playerName == "luffy"){
						System.out.println("luffy");
						g.drawImage(luffy, 500, 225, luffy.getWidth(null)*3, luffy.getHeight(null)*3, null);
					}
					else if(p1.playerName == "ichigo"){
						g.drawImage(ichigo, 500, 200, ichigo.getWidth(null), ichigo.getHeight(null), null);
					}
					else if(p1.playerName == "kakashi"){
						g.drawImage(kakashi, 500, 200, kakashi.getWidth(null)*5, kakashi.getHeight(null)*5, null);
					}
				}
				if(p2.playerName != ""){
					if(p2.playerName == "luffy"){
						g.drawImage(luffy, 1100, 225, -(luffy.getWidth(null))*3, luffy.getHeight(null)*3, null);
					}
					else if(p2.playerName == "ichigo"){
						g.drawImage(ichigo, 1100, 200, -(ichigo.getWidth(null)), ichigo.getHeight(null), null);
					}
					else if(p2.playerName == "kakashi"){
						g.drawImage(kakashi, 1100, 200, -(kakashi.getWidth(null))*5, kakashi.getHeight(null)*5, null);
					}
				}
				g.drawRect(getWidth()-200, 0, 200, 100);
				// g.setColor(Color.gray);
				// g.fillPolygon(new int[]{100,300,500,300}, new int[]{100,100,400,400}, 4);
				// g.setColor(Color.black);
				// g.fillPolygon(new int[]{150,250,400,200}, new int[]{100,100,400,400}, 4);
			}
			moveHand(g);
			h.draw(g);
			c1.draw(g);
			c2.draw(g);




		}
	if(screen == GAME){
		
		g.drawImage(back, -330, 0, back.getWidth(this)*3, back.getHeight(this)*3, this);
		g.drawImage(platf, plat.x-20, plat.y-30, plat.width, plat.height+100, this);
		p1.draw(g);
		p2.draw(g);

		for(Rectangle r:plats){
			g.setColor(Color.green);
			g.fillRect(r.x, r.y, r.width, r.height);
			
		}
		

	}
    }
}
			
