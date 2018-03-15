package ballsofwar;

public class Weapon {

	public static final int BasicGun = 1;
	public static final int OrbitalMissiles = 2;
	public static final int GravitonRifle = 3;
	public static final int FractalineSlug = 4;
	public static final int ReflectivePulse = 5;
	
	int shotType;
	
	public Weapon(int shotType) {
		this.shotType = shotType;
	}
	
}
