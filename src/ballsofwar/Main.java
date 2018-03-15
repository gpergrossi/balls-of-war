 package ballsofwar;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JApplet implements MouseListener, MouseMotionListener, KeyListener {

	public static void main(String[] args) {
		  JFrame frame = new JFrame("test");
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setTitle("Balls of War");

		  JPanel panel = new JPanel(new BorderLayout());
		  panel.setMinimumSize(new Dimension(800, 600));
		  panel.setMaximumSize(new Dimension(800, 600));
		  panel.setPreferredSize(new Dimension(800, 600));
		  frame.add(panel);

		  JApplet applet = new Main();
		  panel.add(applet, BorderLayout.CENTER);
		  applet.init();
		  
		  frame.setResizable(false);
		  frame.pack();
		  
		  frame.setVisible(true);
	}
	
	
	
	
	private static final long serialVersionUID = (long) 1.0;
	
	//Mouse
	public static Point2D mouse = new Point2D();
	public static Point2D mouseDrag = new Point2D();
	public static int mouseClick = 0;
	public static Interface mouseFocus = null;
	
	//Clock
	public static long startTime = System.nanoTime();
	public static long frameTime = 0;
	public static int frameOn = 0;
	public static final long oneSecond = 1000000000;
	
	//Display
	public static int screenWidth, screenHeight;
	public static double FPS = 40;
	public static long AverageFrameTime = (long)(oneSecond/FPS);
	public static Image backbufferImage;
	public static Graphics backbuffer;
	public static boolean loadingMessageDisplayed = false;
	
	//text
    public static final Font font = new Font("Courier", Font.PLAIN, 12);
    public static final int fontWidth = 7;
    public static final int fontHeight = 12;
	
	public void init() {
		screenWidth = 800;
	    screenHeight = 600;
	    setBackground(new Color(0,0,0,32));
	    backbufferImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
	    backbuffer = backbufferImage.getGraphics();
	    
	    addMouseListener(this);
	    addMouseMotionListener(this);
	    addKeyListener(this);
	}
	
	public void stop() { }
	
	public void displayLoader(Graphics g) {
		loadingMessageDisplayed = true;
		g.drawString("Loading...", 350, 300);
	}
 
	public void paint(Graphics appletGraphics) {
		backbuffer.setColor(this.getBackground());
		backbuffer.fillRect(0, 0, screenWidth, screenHeight);
		Explosion.drawExplosions((Graphics2D) backbuffer);
		if(!loadingMessageDisplayed) { 
			displayLoader(backbuffer);
		} else {
			Game.run(backbuffer);
		}
		while((System.nanoTime()-startTime) < (1000000000.0/FPS)) {
		}
		frameTime = (System.nanoTime()-startTime);
		startTime = System.nanoTime();
		appletGraphics.drawImage(backbufferImage, 0, 0, this);
		repaint();
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public static int mouseClick() { 
		return mouseClick;
	}
	
	public static int mouseX() { 
		return (int)mouse.x;
	}
	
	public static int mouseY() { 
		return (int)mouse.y;
	}
	
	public static int mouseDragX() { 
		return (int)(mouse.x-mouseDrag.x);
	}
	
	public static int mouseDragY() { 
		return (int)(mouse.y-mouseDrag.y);
	}
	
	public static int getFPS() {
		return (int) (1000000000.0/frameTime);
	}
	
	public static int random(int min, int max) {
		return (int)((max-min)*Math.random()+min);
	}
	
	public static double random(double min, double max) {
		return ((max-min)*Math.random()+min);
	}
	
	public int mouseToBinary(int mouseclick) {
		if(mouseclick == 1) return 1;
		if(mouseclick == 2) return 4;
		if(mouseclick == 3) return 2;
		return 0;
	}
	
	//-----------------Handle Events-----------------------
	
	public void mouseEntered(MouseEvent e) { 
	}
	
	public void mouseExited(MouseEvent e) { 
	}
	
	public void mouseDragged(MouseEvent e) { 	
		if(e.getX() > 0 && e.getX() < screenWidth && e.getY() > 0 && e.getY() < screenHeight) {
			if(mouseFocus == null) {
				mouse.x = e.getX();
				mouse.y = e.getY();
			} else {
				mouse.x = e.getX();
				mouse.y = e.getY();
			}
		}
	}
	
	public void mouseClicked(MouseEvent e) { 
	}
	
	public void mousePressed(MouseEvent e) {
		mouseClick += mouseToBinary(e.getButton());
		Interface.registerClick(mouseClick, mouse);
	}
	
	public void mouseReleased(MouseEvent e) {
		mouseClick -= mouseToBinary(e.getButton());
		Interface.registerClick(mouseClick, mouse);
		mouseFocus = null; 
	}
	
	public void mouseMoved(MouseEvent e) { 
		mouseDrag.x = e.getX();
		mouseDrag.y = e.getY();
		mouse.x = e.getX();
		mouse.y = e.getY();
	}

	public void keyPressed(KeyEvent arg0) {
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}
	
}
