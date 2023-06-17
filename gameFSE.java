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
    	private final int INTRO = 0, CHARSELECT = 1, GAME = 2, MAPSELECT = 3, END = 4;
	int winner = 0;
	private int screen = INTRO;
    	int frameCnt = 0;
	Font fontLocal=null, fontSys=null, fontLocal2 = null, fontLocal3 = null;


	// AUDIO
	


	
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
	boolean mapSelect = false;
	int mapSelected = 5;
	boolean backSelected = false;
	timer backTimer = new timer(10);

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
	Image[] backgrounds = new Image[]{new ImageIcon("Pics/backgrounds/0.gif").getImage(), new ImageIcon("Pics/backgrounds/1.gif").getImage(), new ImageIcon("Pics/backgrounds/2.gif").getImage(), new ImageIcon("Pics/backgrounds/3.gif").getImage()};
	Image[] backgroundPNG = new Image[]{new ImageIcon("Pics/backgrounds/0.png").getImage(), new ImageIcon("Pics/backgrounds/1.png").getImage(), new ImageIcon("Pics/backgrounds/2.png").getImage(), new ImageIcon("Pics/backgrounds/3.png").getImage()};
	Image smallPlatform = new ImageIcon("Pics/platform/smallPlatform.png").getImage();
	Image luffy = new ImageIcon("Pics/luffy/stand/0.png").getImage();
 	Image ichigo = new ImageIcon("Pics/ichigo/pic.png").getImage();
	Image kakashi = new ImageIcon("Pics/kakashi/stand/0.png").getImage();
	Image aang = new ImageIcon("Pics/aang/stand/0.png").getImage();
	Image hand = new ImageIcon("Pics/hand.png").getImage();
	Image p1Circle = new ImageIcon("Pics/p1Circle.png").getImage();
	Image p2Circle = new ImageIcon("Pics/p2Circle.png").getImage();
	Image p1Menu = new ImageIcon("Pics/player1Menu.png").getImage();
	Image p2Menu = new ImageIcon("Pics/player2Menu.png").getImage();
	Image mcKenzie = new ImageIcon("Pics/mckenzie.png").getImage();
	
	public GamePanel(){
		plats = new Rectangle[]{new Rectangle(300,400,400,50),new Rectangle(900,400,400,50)};
		back = new ImageIcon("Pics/background.gif").getImage();
		platf = new ImageIcon("Pics/platform.png").getImage();
		plat = new Rectangle(150,600,1200,50);
		keys = new boolean[KeyEvent.KEY_LAST+1];

		// create an image
      		Image image = Toolkit.getDefaultToolkit().createImage("");

        	// create a transparent cursor using the image
	      	Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "invisiblecursor");

      		setCursor(cursor);

        	String fName = "TITLEFONT.ttf";
		String fName2 = "SUBTITLEFONT.otf";
		String fName3 = "supersmash.ttf";
    		InputStream is = GamePanel.class.getResourceAsStream(fName);
		InputStream is2 = GamePanel.class.getResourceAsStream(fName2);
		InputStream is3 = GamePanel.class.getResourceAsStream(fName3);
    		try{
    			fontLocal = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(200f);
			fontLocal2 = Font.createFont(Font.TRUETYPE_FONT, is2).deriveFont(30f);
			fontLocal3 = Font.createFont(Font.TRUETYPE_FONT, is3).deriveFont(60f);
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
		if(screen == INTRO){
			
		}
		else if(screen == CHARSELECT){
			backTimer.update();
			mx = MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x;
			my = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y;
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
				p1 = new player();
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
				p2 = new player();
			}
					
		}
		else if(screen == MAPSELECT){
			mx = MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x;
			my = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y;
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

			if(p1.health.hearts <= 0 || p2.health.hearts <= 0){

				screen = END;
			}
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
					p1.hardAttack(p1, p2);
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
				else if(p2.playerName == "aang"){
					p2.hardAttack(p2, p1);
				}
				else if(p2.playerName == "kakashi"){
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
				else if(p2.playerName == "luffy"){
					p2.multiHit(p2, p1);
				}
				else if(p2.playerName == "aang"){
					p2.multiHit(p2, p1);
				}
				else if(p2.playerName == "kakashi"){
					p2.kickUp(p2, p1);
				}
			}
			if(key == KeyEvent.VK_S){
				if(p1.playerName == "ichigo"){
					p1.ichigoUlt(p1, p2);
				}
				else if(p1.playerName == "luffy"){
					if(p1.status == p1.ULT){
						p1.status = p1.IDLE;
					}
					else{
						p1.luffyUlt(p1, p2);
					}
				}
				else if(p1.playerName == "aang"){
					p1.aangUlt(p1, p2);
				}
				else if(p1.playerName == "kakashi"){
					p1.kakashiUlt(p1, p2);
				}
			}
			if(key == KeyEvent.VK_DOWN){
				if(p2.playerName == "ichigo"){
					p2.ichigoUlt(p2, p1);
				}
				else if(p2.playerName == "luffy"){
					if(p2.status == p2.ULT){
						p2.status = p2.IDLE;
					}
					else{
						p2.luffyUlt(p2, p1);
					}
				}
				else if(p2.playerName == "aang"){
					p2.aangUlt(p2, p1);
				}
				else if(p2.playerName == "kakashi"){
					p2.kakashiUlt(p2, p1);
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
		
		if(screen == MAPSELECT){
			System.out.println("MAPSELECT");
			if(new Rectangle(0, 0, 200, 100).contains(mx, my)){
				screen = CHARSELECT;
				backTimer.reset();
				System.out.println(backTimer.getTime());
			}
			if(new Rectangle(75,550,300,300).contains(mx,my)){
				mapSelected = 0;
			 	mapSelect = true;
			}
			if(new Rectangle(475,550,300,300).contains(mx,my)){
				mapSelected = 1;
			 	mapSelect = true;
			}
			if(new Rectangle(875,550,300,300).contains(mx,my)){
				mapSelected = 2;
			 	mapSelect = true;
			}
			if(new Rectangle(1275,550,300,300).contains(mx,my)){
				mapSelected = 3;
			 	mapSelect = true;
			}
			if(mapSelect && new Rectangle(getWidth()-200, 0, 200, 100).contains(mx, my)){
				screen = GAME;
				back = new ImageIcon("Pics/backgrounds/" + mapSelected + ".gif").getImage();
			}

		}
	}

	@Override
	public void	mouseEntered(MouseEvent e){
		
	}

	@Override
	public void	mouseExited(MouseEvent e){}

	@Override
	public void	mousePressed(MouseEvent e){
		if(screen == INTRO){
            		repaint();
		}
		if(screen == CHARSELECT){
			if(char1 && char2 && new Rectangle(getWidth()-200, 0, 200, 100).contains(mx, my)){
				System.out.println("GAME");
				screen = MAPSELECT;
			}
			if(new Rectangle(0, 0, 200, 100).contains(mx, my) && backTimer.getTime() > 200){
				System.out.println(backTimer.getTime());
				screen = INTRO;
			}
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

	public void drawString(Graphics g, String text, int x, int y){
		for(String line : text.split("\n")){
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
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
			g.setColor(new Color(200, 0, 0, fadeINCHAR));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(10, 10, 10, 100));
			g.fillRect(50,500,1500,400);
			g.drawImage(p1Menu, 100, 150, 600, 300, null);
			g.drawImage(p2Menu, 900, 150, 600, 300, null);
			g.setFont(fontLocal2);
			g.setColor(Color.white);
			drawString(g, p1.playerName, 500, 200);
			drawString(g, p2.playerName, 1300, 200);
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
					else if(i == 2){
						g.drawImage(new ImageIcon("Pics/kakashi/pic.png").getImage(), 375*i+100, 550, 275, 275, null);
					}
					else if(i == 3){
						g.drawImage(new ImageIcon("Pics/aang/pic.png").getImage(), 375*i+100, 550, 275, 275, null);
					}
				}
				if(p1.playerName != ""){
					if(p1.playerName == "luffy"){
						System.out.println("luffy");
						g.drawImage(luffy, 200, 225, luffy.getWidth(null)*3, luffy.getHeight(null)*3, null);
					}
					else if(p1.playerName == "ichigo"){
						g.drawImage(ichigo, 150, 200, ichigo.getWidth(null), ichigo.getHeight(null), null);
					}
					else if(p1.playerName == "kakashi"){
						g.drawImage(kakashi, 200, 200, kakashi.getWidth(null)*5, kakashi.getHeight(null)*5, null);
					}
					else if(p1.playerName == "aang"){
						g.drawImage(aang, 200, 200, aang.getWidth(null)*5, aang.getHeight(null)*5, null);
					}
				}
				if(p2.playerName != ""){
					if(p2.playerName == "luffy"){
						g.drawImage(luffy, 950, 225, (luffy.getWidth(null))*3, luffy.getHeight(null)*3, null);
					}
					else if(p2.playerName == "ichigo"){
						g.drawImage(ichigo, 950, 200, (ichigo.getWidth(null)), ichigo.getHeight(null), null);
					}
					else if(p2.playerName == "kakashi"){
						g.drawImage(kakashi, 950, 200, (kakashi.getWidth(null))*5, kakashi.getHeight(null)*5, null);
					}
					else if(p2.playerName == "aang"){
						g.drawImage(aang, 950, 200, (aang.getWidth(null))*5, aang.getHeight(null)*5, null);
					}
				}
				g.setColor(new Color(0, 0, 0, 200));
				g.fillRect(200,0,getWidth(),100);
				g.fillRect(0,100,50,getHeight());
				g.fillRect(getWidth()-50,100,50,getHeight());
				g.fillRect(50,getHeight()-60,getWidth(),60);
				g.setColor(new Color(255, 255, 255, 200));
				g.fillRect(0,0,200,100);
				g.fillRect(getWidth()-200, 0, 200, 100);
				g.setFont(fontLocal3);
				g.setColor(Color.black);
				drawString(g, "NEXT", getWidth()-200, 0);
				drawString(g, "BACK", 0, 0);
				// g.setColor(Color.gray);
				// g.fillPolygon(new int[]{100,300,500,300}, new int[]{100,100,400,400}, 4);
				// g.setColor(Color.black);
				// g.fillPolygon(new int[]{150,250,400,200}, new int[]{100,100,400,400}, 4);
			}
			moveHand(g);
			c1.draw(g);
			c2.draw(g);
			h.draw(g);

		}
		else if(screen == MAPSELECT){
			g.setColor(Color.red);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(10, 10, 10, 100));
			g.fillRect(50,500,1480,400);
			for(int i = 0; i < 4; i++){
				g.setColor(new Color(255, 255, 255, 200));
				g.fillRect(375*i+85,550,300,300);
				g.drawImage(backgroundPNG[i], 375*i+85, 550, 300, 300, null);
				if(i == mapSelected){
					g.setColor(Color.WHITE);
					g.fillRect(375 * i + 85, 550, 300, 5);
					g.fillRect(375 * i + 85, 550, 5, 300);
					g.fillRect(375 * i + 85, 550 + 300 - 5, 300, 5);
					g.fillRect(375 * i + 85 + 300 - 5, 550, 5, 300);
				}
			}
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(200,0,getWidth(),100);
			g.fillRect(0,100,50,getHeight());
			g.fillRect(getWidth()-50,100,50,getHeight());
			g.fillRect(50,getHeight()-60,getWidth(),60);
			g.setColor(new Color(255, 255, 255, 200));
			g.fillRect(0,0,200,100);
			g.fillRect(getWidth()-200, 0, 200, 100);
			g.setFont(fontLocal3);
			g.setColor(Color.black);
			drawString(g, "NEXT", getWidth()-200, 0);
			drawString(g, "BACK", 0, 0);
			moveHand(g);
			h.draw(g);
		}
	if(screen == GAME){
		g.setColor(Color.WHITE);
		g.drawImage(backgroundPNG[mapSelected], 0, 0, backgroundPNG[mapSelected].getWidth(this)*3,backgroundPNG[mapSelected].getHeight(this)*3 , null);
		g.drawImage(back, -330, 0, back.getWidth(this)*3, back.getHeight(this)*3, this);
		g.drawImage(platf, plat.x, plat.y, plat.width, 400, this);
		p1.draw(g);
		p2.draw(g);

		for(Rectangle r:plats){
			g.drawImage(smallPlatform, r.x, r.y, r.width, r.height, this);
		}
		

	}
	if(screen == END){
		g.setColor(Color.WHITE);
		g.drawImage(mcKenzie,0,0,mcKenzie.getWidth(this),mcKenzie.getHeight(this),null);
		g.setFont(fontLocal3);
		drawString(g, "Thanks for playing, Mr. McKenzie!", 0, 0);
	}
    }
}
			
