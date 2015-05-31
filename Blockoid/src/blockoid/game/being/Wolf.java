package blockoid.game.being;

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
	}

	public void act(World world, long elapsedTime) {
		behavior.act(this, world, elapsedTime);
	}
}
