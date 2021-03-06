package blockoid.game.being;

import blockoid.Assets;
import blockoid.Game;
import blockoid.game.World;
import blockoid.game.being.behavior.Attack;
import blockoid.game.being.behavior.Behavior;
import blockoid.game.being.behavior.FollowEnemy;
import blockoid.game.being.behavior.LocateNearestEnemy;
import blockoid.game.being.behavior.Loop;
import blockoid.game.being.behavior.Parallel;
import blockoid.game.being.behavior.Perceive;
import blockoid.game.being.behavior.Rest;
import blockoid.game.being.behavior.Selector;
import blockoid.game.being.behavior.Sequence;
import blockoid.game.being.behavior.Wander;
import blockoid.game.being.behavior.When;

public class Wolf extends Dog {

	public Wolf(Game game) {
		super(game);
		behavior = new Loop(new Parallel(new Behavior[]{
			new Perceive(),
			new When(
				new LocateNearestEnemy(),
				new Sequence(new Behavior[]{
					new FollowEnemy(),
					new Attack()
				}),
				new Sequence(new Behavior[]{
					new Wander(),
					new Rest(Rest.SECOND*2)
				})
			)
		}));
		
		sprite = Assets.getSpriteSheet("characters/wolf20x14", 20, 14);
		height = 14;
		idleRight = new int[]{5};
		idleLeft = new int[]{0};
		walkRight = new int[]{2+5, 6, 4+5};
		walkLeft = new int[]{2, 1, 4};
		jumpRight = new int[]{9};
		jumpLeft = new int[]{4};
		maxSpeed = 0.95;
		speed = maxSpeed;
	}

	public void act(World world, long elapsedTime) {
		behavior.act(this, world, elapsedTime);
	}
	
	public Being getNewInstance() {
		return new Wolf(game);
	}
}
