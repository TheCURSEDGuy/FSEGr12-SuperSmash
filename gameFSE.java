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
		pack();  // set the size of my Frame exactly big enough to hold the contents
		setVisible(true);
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
	Font fontLocal=null, fontSys=null;
	
	private boolean []keys;
	Timer timer;
	Image back;
	
	public GamePanel(){
		back = new ImageIcon("OuterSpace.jpg").getImage();
		keys = new boolean[KeyEvent.KEY_LAST+1];

        String fName = "TITLEFONT.ttf";
    	InputStream is = GamePanel.class.getResourceAsStream(fName);
    	try{
    		fontLocal = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(32f);
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
			
		}

	}
	
	@Override
	public void actionPerformed(ActionEvent e){
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
		System.out.println(key);
		keys[key] = true;
	}
	
	@Override
	public void keyTyped(KeyEvent ke){}
	@Override
	public void	mouseClicked(MouseEvent e){}

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
            g.setColor(Color.BLACK);  
		    g.setFont(fontLocal);
		    FontMetrics fm = g.getFontMetrics(fontSys);
		    int wid = fm.stringWidth("Font From System: ");
            g.drawString("Font From System: ",500-wid,100);
            if(text1){
                
            }
        }
    }
}