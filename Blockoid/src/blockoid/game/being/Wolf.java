package blockoid.game.being;

import blockoid.Assets;
import blockoid.Game;
import blockoid.game.World;
import blockoid.game.being.behavior.Attack;
import blockoid.game.being.behavior.Behavior;
import blockoid.game.being.behavior.FollowEnemy;
import blockoid.game.being.behavior.LocateNearestEnemy;
import blockoid.game.being.behavior.Loop;
import blockoid.game.being.behavior.Perceive;
import blockoid.game.being.behavior.Rest;
import blockoid.game.being.behavior.Selector;
import blockoid.game.being.behavior.Sequence;
import blockoid.game.being.behavior.Wander;

public class Wolf extends Dog {

	public Wolf(Game game) {
		super(game);
		behavior = new Loop(new Sequence(new Behavior[]{
			new Perceive(),
			new Selector(new Behavior[]{
				new Sequence(new Behavior[]{
						new LocateNearestEnemy(),
						new FollowEnemy(),
						new Attack()
				}),
				new Sequence(new Behavior[]{
					new Wander(),
					new Rest(Rest.SECOND*2)
				})
			})
		}));

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
		behavior.act(this, world, elapsedTime);
	}
}
