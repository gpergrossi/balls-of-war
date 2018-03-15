package ballsofwar;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Explosion {


	public static ArrayList<Explosion> aliveExplosions = new ArrayList<Explosion>();
	public static ArrayList<Explosion> deadExplosions = new ArrayList<Explosion>();
	public static final BufferedImage[] frames = new BufferedImage[16];
	public static final int fps = 16;
	
	static {
		for(int i = 0; i < 16; i++) {
			try {
				frames[i] = ImageIO.read(new File("media/Explosion"+i+".png"));
			} catch(IOException e) {
				System.err.println("Explosion frame "+i+" failed to load.");
			}
		}
	}
	
	public static void create(int x, int y, double size) {
		Explosion e = new Explosion(x, y, size);
		aliveExplosions.add(e);
	}
	
	public static void create(int x, int y) {
		Explosion e = new Explosion(x, y);
		aliveExplosions.add(e);
	}
	
	public static void drawExplosions(Graphics2D g) {
		deadExplosions.clear();
		for(Explosion explosion : aliveExplosions) {
			explosion.tick();
			explosion.draw(g);
			if(explosion.alive == false) {
				deadExplosions.add(explosion);
			}
		}
		for(Explosion explosion : deadExplosions) {
			aliveExplosions.remove(explosion);
		}
	}
	
	int x, y;
	int life = 0;
	long lastTick;
	boolean alive;
	boolean flipped = false;
	double size = 1.0;
	
	public Explosion(int x, int y, double size) {
		this(x, y);
		this.size = (size / 55.0);
	}
	
	public Explosion(int x, int y) {
		this.x = x;
		this.y = y;
		this.lastTick = System.currentTimeMillis();
		this.life = 0;
		this.alive = true;
		if(Math.random() > 0.5) this.flipped = true;
		this.size = (Math.random()*40.0+20.0) / 55.0;
	}

	public void tick() {
		life += (System.currentTimeMillis()-lastTick);
		lastTick = System.currentTimeMillis();
	}
	
	public void draw(Graphics2D g) {
		if(!alive) return;
		int frame = (life*fps/1000);
		if(frame < 16) {
			AffineTransform xform = AffineTransform.getTranslateInstance(x, y);
			if(flipped) {
				xform.scale(-1.0, 1.0);
			}
			xform.scale(size, size);
			xform.translate(-34.0, -55.0);
			g.drawRenderedImage(frames[frame], xform);
		} else {
			alive = false;
		}
	}

	public void restart() {
		this.lastTick = System.currentTimeMillis();
		this.life = 0;
		this.alive = true;
	}
	
}
