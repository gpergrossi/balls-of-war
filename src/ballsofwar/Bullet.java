package ballsofwar;

import java.awt.Color;

public class Bullet {

	public static Bullet[] bullets = new Bullet[1024];
	Point2D position = new Point2D();
	Point2D target = new Point2D();
	Soldier targetSoldier = null;
	Soldier parentSoldier = null;
	double movex, movey;
	double damage = 20;
	long life = (long) (Main.oneSecond*20);
	int bulletType = 1;
	int virulence;
	double pulse = 0;
	Army team = null;
	int bulletID = 0;
	
	public Bullet clone() {
		Bullet ret = new Bullet();
		ret.position = this.position.clone();
		ret.target = this.target.clone();
		ret.targetSoldier = this.targetSoldier;
		ret.parentSoldier = this.parentSoldier;
		ret.movex = this.movex;
		ret.movey = this.movey;
		ret.damage = this.damage;
		ret.life = this.life;
		ret.bulletType = this.bulletType;
		ret.virulence = this.virulence;
		ret.team = this.team;
		return ret;
	}
	
	public static void addBullet(Soldier s, Soldier t, double offset) {
		addBullet(new Bullet(s, t, offset));
	}
	
	public static void addBullet(Bullet bill) {
		int openIndex = 0;
		while(bullets[openIndex] != null) {
			openIndex++;
			if(openIndex == bullets.length) {
				bullets = lengthenArray(bullets);
			}
		}
		bullets[openIndex] = bill;
		bullets[openIndex].bulletID = openIndex;
	}
	
	public static void clearBullets() {
		bullets = new Bullet[1024];
	}
	
	public static void removeBullet(int index) {
		bullets[index] = null;
	}
	
	public static void handleAllBullets() {
		for(int c = 0; c < bullets.length; c++) {
			if(bullets[c] != null) {
				bullets[c].draw();
				bullets[c].moveBullet();
				if(bullets[c] != null) bullets[c].checkBulletCollision(Soldier.getListOfAllPlayers());
			}
		}
	}
	
	public static Bullet[] lengthenArray(Bullet[] array) {
		Bullet[] ret = new Bullet[array.length*2];
		for(int c = 0; c < array.length; c++) {
			ret[c] = array[c];
		}
		return ret;
	}
	
	public Bullet(Soldier s, Soldier t, double o) {
		double a = s.position.angleTo(t.position); 
		this.position.x = s.position.x+Math.cos(a+o)*5;
		this.position.y = s.position.y+Math.sin(a+o)*5;
		this.target.x = t.position.x;
		this.target.y = t.position.y;
		double spread = Math.toRadians(Main.random(-s.spread,s.spread));
		double speed = 5-Math.random();
		this.movex = Math.cos(a+spread+o)*speed;
		this.movey = Math.sin(a+spread+o)*speed;
		this.damage = s.strength;
		this.team = s.team;
		this.bulletType = s.weapon.shotType;
		if(this.bulletType == 4) {
			this.virulence = 3;
			this.life = Main.oneSecond / 2;
		}
		this.targetSoldier = t;
		this.parentSoldier = s;
	}
	
	public Bullet() {} // Mostly unused, except Bullet.copy()

	public void moveBullet() {
		this.life -= Main.AverageFrameTime;
		this.pulse += Math.PI/8;
		//Normal
		if(this.bulletType == 1) {
			this.position.x += this.movex;
			this.position.y += this.movey;
			if(position.x < 0 || position.x > Main.screenWidth || position.y < 0|| position.y > Main.screenHeight) {
				this.destroy();
			}
		}
		//Orbital
		if(this.bulletType == 2) {
			this.position.x += this.movex;
			this.position.y += this.movey;
			double a = this.position.angleTo(this.targetSoldier.position);
			if(this.targetSoldier.health > 0) {
				this.movex = this.movex*0.98 + Math.cos(a+Math.PI/2-Math.PI/2.1)*0.1;
				this.movey = this.movey*0.98 + Math.sin(a+Math.PI/2-Math.PI/2.1)*0.1;
			} else {
				this.movex = this.movex*1.01;
				this.movey = this.movey*1.01;
			}
			if(position.x < -100 || position.x > Main.screenWidth+100 || position.y < -100|| position.y > Main.screenHeight+100) {
				this.destroy();
			}
		}
		//Gravitational
		if(this.bulletType == 3) {
			this.position.x += this.movex;
			this.position.y += this.movey;
			Soldier target = Soldier.getClosestEnemy(this.parentSoldier.team, this.position);
			if(target != null) {
				double a = this.position.angleTo(target.position);
				double d = this.position.distanceTo(target.position);
				this.movex += Math.cos(a)*(14/(d+1));
				this.movey += Math.sin(a)*(14/(d+1));
				this.movex *= 0.99;
				this.movey *= 0.99;
			} else {
				this.movex *= 1.03;
				this.movey *= 1.03;
			}
			if(position.x < -100 || position.x > Main.screenWidth+100 || position.y < -100|| position.y > Main.screenHeight+100) {
				this.destroy();
			}
		}
		//Fractaline
		if(this.bulletType == 4) {
			this.position.x += this.movex;
			this.position.y += this.movey;
			if(this.virulence > 0 && this.life <= 0) { 
				this.virulence -= 1;
				this.life = Main.oneSecond / 2;
				if(this.virulence == 0) this.life = Main.oneSecond * 19;
				Bullet offspring = this.clone();
				Bullet.addBullet(offspring);
				this.rotateMovement(2.0 * (virulence + 1));
				offspring.rotateMovement(-2.0 * (virulence + 1));
			}
			if(position.x < -100 || position.x > Main.screenWidth+100 || position.y < -100|| position.y > Main.screenHeight+100) {
				this.destroy();
			}
		}//Reflective Pulse
		if(this.bulletType == 5) {
			this.position.x += this.movex;
			this.position.y += this.movey;
			if(position.x < 0) {
				this.movex = Math.abs(this.movex);
				this.life /= 2;
				if(this.life < Main.oneSecond * 2) this.life = 0;
			}
			if(position.x > Main.screenWidth) {
				this.movex = -Math.abs(this.movex);
				this.life /= 2;
				if(this.life < Main.oneSecond * 2) this.life = 0;
			}
			if(position.y < 0) {
				this.movey = Math.abs(this.movey);
				this.life /= 2;
				if(this.life < Main.oneSecond * 2) this.life = 0;
			} 
			if(position.y > Main.screenHeight) {
				this.movey = -Math.abs(this.movey);
				this.life /= 2;
				if(this.life < Main.oneSecond * 2) this.life = 0;
			}
		} //Drain Bullets
		if(this.bulletType == 6) {
			this.position.x += this.movex;
			this.position.y += this.movey;
			if(position.x < 0 || position.x > Main.screenWidth || position.y < 0|| position.y > Main.screenHeight) {
				this.destroy();
			}
		}
		if(this.life <= 0) {
			this.destroy();
		}
	}

	private void rotateMovement(double deg) {
		Vector move = Vector.gridVector(this.movex, this.movey);
		move = move.setA(move.getA() + Math.PI / 180.0 * deg);
		this.movex = move.getX();
		this.movey = move.getY();
	}

	public void checkBulletCollision(Soldier[] s) {
		for(int c = 0; c < s.length; c++) {
			if(s[c] != null) {
				if(Game.friendlyFire == true || s[c].team != this.team) {
					double d = s[c].position.distanceTo(this.position);
					if(d < s[c].size*1.25 + s[c].agility) {
						s[c].notify(this);
					}
					if(d < s[c].size*1.25) {
						s[c].takeDamage(this.damage);
						if(this.bulletType == 6) {
							this.parentSoldier.takeDamage(-this.damage);
						}
						this.destroy();
					}
				}
			}
		}
	}
	
	public void destroy() {
		Bullet.bullets[this.bulletID] = null;
	}
	
	public void draw() {
		if(this.bulletType != 6) {
			Draw.setColor(this.team.color);
			Draw.line(position.x, position.y, position.x+movex, position.y+movey);
		} else {
			Draw.setColor(this.team.color);
			Draw.line(position.x, position.y, position.x+movex, position.y+movey);
			Draw.line(position.x+1, position.y, position.x+movex, position.y+movey);
			Draw.line(position.x-1, position.y, position.x+movex, position.y+movey);
			Draw.line(position.x, position.y+1, position.x+movex, position.y+movey);
			Draw.line(position.x, position.y-1, position.x+movex, position.y+movey);
		}
	}
	
	private static Color transparify(Color c, int d) {
		return new Color(Math.min(c.getRed()+d,255), Math.min(c.getGreen()+d,255), Math.min(c.getBlue()+d,255), d);
	}
	
}
