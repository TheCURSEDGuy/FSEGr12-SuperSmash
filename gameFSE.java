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
	private int screen = GAME;
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

	//OBJECTS
	player p1 = new player(1);
	player p2 = new player(-1);
	Rectangle plat;
	Rectangle[] plats;
	healthBar h1 = new healthBar(0);
	healthBar h2 = new healthBar(1);
	
	// KEYS
	private boolean []keys;
	Timer timer;
	Image back;
	Image platf;
	
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
		timer = new Timer(20, this);
		timer.start();
	}

	public void move(){
		if(screen == INTRO){
			
		}
		else if(screen == GAME){
			p1.gravity(plat);
			p1.move(keys);
			p1.friction();
			p1.update();

			p2.gravity(plat);
			p2.move(keys);
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
			h1.update(p1.percentage);
			h2.update(p2.percentage);
			
		}
	}
	
	@Override
	public void keyTyped(KeyEvent ke){}
	@Override
	public void mouseClicked(MouseEvent e){
		if(charSel){
			screen = GAME;
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
        	if(screen == GAME){
            		move();
            		repaint();
        	}	
	}

	@Override
	public void	mouseReleased(MouseEvent e){}

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
			else if(charTransparency < 200){
				charTransparency += 10;
				charX += 5;
				charY += 20;
				if(charTransparency > 200){
					charTransparency = 200;
				}
			}
			else{
				charSel = true;
			}
			g.setColor(new Color(255, 255, 255, fadeLIGHT));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(150, 0, 0, fadeINCHAR));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(10, 10, 10, charTransparency));
			g.fillPolygon(new int[]{25,125-charX,1550,1450+charX}, new int[]{900,900-charY,500,500+charY}, 4);
			if(charSel){
				for(int i = 0; i < 4; i++){
					g.setColor(new Color(255, 255, 255, 100));
					g.fillPolygon(new int[]{100+300*i,300+300*i,500+300*i,300+300*i}, new int[]{600,800,800,600}, 4);
				}
				// g.setColor(Color.gray);
				// g.fillPolygon(new int[]{100,300,500,300}, new int[]{100,100,400,400}, 4);
				// g.setColor(Color.black);
				// g.fillPolygon(new int[]{150,250,400,200}, new int[]{100,100,400,400}, 4);

				g.fillRect(400,400,100,100);

			}


		}
	if(screen == GAME){
		g.drawImage(back, -330, 0, back.getWidth(this)*3, back.getHeight(this)*3, this);
		g.drawImage(platf, plat.x-20, plat.y-30, plat.width, plat.height+100, this);
		g.setColor(Color.green);
		// g.fillRect(plat.x, plat.y, plat.width, plat.height);
		p1.draw(g);
		p2.draw(g);
		h1.draw(g);
		h2.draw(g);

		for(Rectangle r:plats){
			g.setColor(Color.green);
			g.fillRect(r.x, r.y, r.width, r.height);
			
		}
		
	}
    }
}