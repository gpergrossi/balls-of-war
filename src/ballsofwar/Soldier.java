package ballsofwar;

public class Soldier {

	public static final Soldier nullSoldier = new Soldier(null, new Point2D(-1000,1000), 0, 0, 0, 0, 0, 0, 0, 0, 0); 
	public static Soldier[] allSoldiers = getListOfAllPlayers();
	
	public Point2D position;
	public Army team;
	public double maxHealth, health;
	public long cooldown;
	public double vitality, strength, agility, speed, spread, multi;
	public double damageTaken;
	public double size = 5;
	public double moveX = 0, moveY = 0;
	public int idNumber = 0;
	public Weapon weapon = new Weapon(Weapon.OrbitalMissiles);
	
	public Soldier(Army army, Point2D position, int id, double health, double vit, 
			       double str, double agil, double speed, double spread, double multi, int weapon) {
		this.team = army;
		this.position = position;
		this.maxHealth = health;
		this.health = health;
		this.vitality = vit;
		this.strength = str;
		this.agility = agil;
		this.speed = speed;
		this.spread = spread;
		this.multi = multi;
		this.weapon = new Weapon(weapon);
		this.idNumber = id;
		this.cooldown = (long) Main.random(0, (1000000000.0/this.speed));
	}
	
	public void draw() {
		Draw.setColor(team.color);
		Draw.circle(position.x, position.y, size);
		Draw.line(position.x - 5.0, position.y - 7.0, position.x - 5.0 + 10.0*(health/maxHealth), position.y - 7.0);
	}
	
	public void runAI() {
		this.cooldown -= Main.AverageFrameTime;
		if(cooldown < 0) {
			//shoot
			Soldier target = getClosestEnemy();
			if(target != null) {
				this.shoot(target);
				this.cooldown = (long)(1000000000.0/this.speed);
			}
			//move
			this.position.x += this.moveX;
			this.position.y += this.moveY;
			if(Math.abs(this.moveX)+Math.abs(this.moveY) > 0) {
				allSoldiers = getListOfAllPlayers();
				for(int c = 0; c < allSoldiers.length; c++) {
					if(allSoldiers[c] != null && allSoldiers[c] != this) {
						double d = this.position.distanceTo(allSoldiers[c].position);
						if(d < 10) {
							double a = allSoldiers[c].position.angleTo(this.position);
							this.position.x += Math.cos(a)*(10-d);
							this.position.y += Math.sin(a)*(10-d);
						}
					}
				}
				if(this.position.x < 0) this.position.x = 0;
				if(this.position.y < 0) this.position.y = 0;
				if(this.position.x > 800) this.position.x = 800;
				if(this.position.y > 600) this.position.y = 600;
			}
			this.moveX = 0;
			this.moveY = 0;
			this.health += this.vitality/60.0;
			if(this.health > this.maxHealth) this.health = this.maxHealth;
		}
	}
	
	public void shoot(Soldier t) {
		double a = (Math.PI/6)/(this.multi+1);
		for(int i = 0; i < this.multi; i++) {
			Bullet.addBullet(this, t, a*(i+1)-(Math.PI/12));
		}
	}
	
	public void takeDamage(double amount) {
		this.health -= amount;
		this.moveX *= 0.95;
		this.moveY *= 0.95;
		if(health < 0) {
			this.team.players[this.idNumber] = null;
			Explosion.create((int)this.position.x, (int)this.position.y);
			this.team.soldiersAlive -= 1;
		}
	}

	public Soldier getClosestEnemy() {
		Soldier[] a = getListOfEnemyPlayers();
		double minDist = Double.MAX_VALUE;
		double distance = 0;
		Soldier winner = null;
		for(int c = 0; c < a.length; c++) {
			if(a[c] != null) {
				distance = this.position.distanceTo(a[c].position); 
				if(distance < minDist) {
					minDist = distance;
					winner = a[c];
				}
			}
		}
		return winner;
	}

	public static Soldier getClosestEnemy(Army team, Point2D position) {
		Soldier[] a = getListOfEnemyPlayers(team);
		double minDist = Double.MAX_VALUE;
		double distance = 0;
		Soldier winner = null;
		for(int c = 0; c < a.length; c++) {
			if(a[c] != null) {
				distance = position.distanceTo(a[c].position); 
				if(distance < minDist) {
					minDist = distance;
					winner = a[c];
				}
			}
		}
		return winner;
	}
	
	public Soldier[] getListOfEnemyPlayers() {
		Soldier[] ret = new Soldier[Army.totalPlayers-team.players.length];
		int index = 0;
		Army[] check = Army.listOfArmies;
		for(int c = 0; c < check.length; c++) {
			if(check[c] != this.team && check[c] != null) {
				for(int d = 0; d < check[c].players.length; d++) {
					if(check[c].players[d] != null) {
						ret[index++] = check[c].players[d];
					}
				}
			}
		}
		return ret;
	}
	
	public static Soldier[] getListOfEnemyPlayers(Army team) {
		Soldier[] ret = new Soldier[Army.totalPlayers-team.players.length];
		int index = 0;
		Army[] check = Army.listOfArmies;
		for(int c = 0; c < check.length; c++) {
			if(check[c] != team && check[c] != null) {
				for(int d = 0; d < check[c].players.length; d++) {
					if(check[c].players[d] != null) {
						ret[index++] = check[c].players[d];
					}
				}
			}
		}
		return ret;
	}
	
	public Soldier[] getListOfTeamPlayers() {
		Soldier[] ret = new Soldier[team.players.length];
		int index = 0;
		Army[] check = Army.listOfArmies;
		for(int c = 0; c < check.length; c++) {
			if(check[c] == this.team && check[c] != null) {
				for(int d = 0; d < check[c].players.length; d++) {
					if(check[c].players[d] != null) {
						ret[index++] = check[c].players[d];
					}
				}
			}
		}
		return ret;
	}
	public static Soldier[] getListOfAllPlayers() {
		Soldier[] ret = new Soldier[Army.totalPlayers];
		int index = 0;
		Army[] check = Army.listOfArmies;
		for(int c = 0; c < check.length; c++) {
			if(check[c] != null) {
				for(int d = 0; d < check[c].players.length; d++) {
					if(check[c].players[d] != null) {
						ret[index++] = check[c].players[d];
					}
				}
			}
		}
		return ret;
	}

	public void notify(Bullet bullet) {
		double a = bullet.position.angleTo(this.position);
		this.moveX = (this.moveX + Math.cos(a))/2.0;
		this.moveY = (this.moveY + Math.sin(a))/2.0;
	}

}
