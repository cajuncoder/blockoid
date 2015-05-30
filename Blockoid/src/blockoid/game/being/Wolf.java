package blockoid.game.being;

import blockoid.Assets;
import blockoid.Game;
import blockoid.game.World;
import blockoid.game.being.behavior.AttackBehavior;

public class Wolf extends Dog {

	public Wolf(Game game) {
		super(game);
		behavior = new AttackBehavior(this);
		maxSpeed = 0.9;
		
		sprite = Assets.getSpriteSheet("characters/wolf20x14", 20, 14);
		height = 14;
		idleRight = new int[]{5};
		idleLeft = new int[]{0};
		walkRight = new int[]{2+5, 6, 4+5};
		walkLeft = new int[]{2, 1, 4};
		jumpRight = new int[]{9};
		jumpLeft = new int[]{4};
		//minSpeed = 0.66;
		//maxSpeed = 1.0;
	}

	public void act(World world, long elapsedTime) {
		Being enemy = world.nearestEnemy(this);
		if (enemy == null)
			return;
		((AttackBehavior)behavior).setTarget(enemy);
		behavior.act(world, elapsedTime);
	}
}
