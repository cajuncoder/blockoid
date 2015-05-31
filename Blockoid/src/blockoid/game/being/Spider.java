package blockoid.game.being;

import blockoid.Assets;
import blockoid.Game;
import blockoid.game.World;
import blockoid.game.being.behavior.Attack;
import blockoid.game.being.behavior.Behavior;
import blockoid.game.being.behavior.FollowEnemy;
import blockoid.game.being.behavior.LayEgg;
import blockoid.game.being.behavior.LocateNearestEnemy;
import blockoid.game.being.behavior.Loop;
import blockoid.game.being.behavior.Parallel;
import blockoid.game.being.behavior.Perceive;
import blockoid.game.being.behavior.Rest;
import blockoid.game.being.behavior.Selector;
import blockoid.game.being.behavior.Sequence;
import blockoid.game.being.behavior.Wander;
import blockoid.game.being.behavior.When;

public class Spider extends Being {
	public Spider(Game game) {
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
					new Rest(Rest.SECOND*2),
					new LayEgg()
				})
			)
		}));
		
		sprite = Assets.getSpriteSheet("characters/character", width, height);
		minSpeed = 0.2;
		maxSpeed = 0.5;
		speed = maxSpeed;
	}

	public void act(World world, long elapsedTime) {
		behavior.act(this, world, elapsedTime);
	}
	
	public Being getNewInstance() {
		return new Spider(game);
	}
}
