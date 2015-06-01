package blockoid.game.being;

import blockoid.Assets;
import blockoid.Game;
import blockoid.game.World;
import blockoid.game.being.behavior.AttackBeing;
import blockoid.game.being.behavior.AttackEnemy;
import blockoid.game.being.behavior.AttackedByBeing;
import blockoid.game.being.behavior.AttackedByEnemy;
import blockoid.game.being.behavior.Behavior;
import blockoid.game.being.behavior.FollowEnemy;
import blockoid.game.being.behavior.LayEgg;
import blockoid.game.being.behavior.LocateNearestBeing;
import blockoid.game.being.behavior.LocateNearestEnemy;
import blockoid.game.being.behavior.Perceive;
import blockoid.game.being.behavior.Rest;
import blockoid.game.being.behavior.RunFromEnemy;
import blockoid.game.being.behavior.Wander;
import blockoid.game.being.behavior.primitive.Case;
import blockoid.game.being.behavior.primitive.Loop;
import blockoid.game.being.behavior.primitive.Parallel;
import blockoid.game.being.behavior.primitive.RepeatUntilFailure;
import blockoid.game.being.behavior.primitive.Selector;
import blockoid.game.being.behavior.primitive.Sequence;
import blockoid.game.being.behavior.primitive.When;

public class Spider extends Being {
	public Spider(Game game) {
		super(game);
//		behavior = new Loop(new Parallel(new Behavior[]{
//			new Perceive(),
//			new Selector(new Behavior[]{
//				new Sequence(new Behavior[]{
//					new LocateNearestBeing(),
//					new AttackedByBeing(),
//					new RepeatUntilFailure(new AttackBeing())
//				}),
//				new When(
//					new LocateNearestEnemy(),
//					new RunFromEnemy(),
//					new Sequence(new Behavior[]{
//						new Wander(),
//						new Rest(Rest.SECOND*2),
//						new LayEgg()
//					})
//				)
//			})
//		}));
		
		behavior = new Loop(new Parallel(new Behavior[]{
			new Perceive(),
			new Case(new Behavior[][]{
					new Behavior[]{
						new Sequence(new Behavior[]{
							new LocateNearestBeing(),
							new AttackedByBeing()
						}), 
						new AttackBeing()
					},
					new Behavior[]{
						new LocateNearestEnemy(),
						new RunFromEnemy()
					}
				},
				new Sequence(new Behavior[]{
					new Wander(),
					new Rest(World.SECOND*2),
					new LayEgg()
				})
			)
		}));
		
		sprite = Assets.getSpriteSheet("characters/character", width, height);
		minSpeed = 0.2;
		maxSpeed = 0.5;
		speed = maxSpeed;
		maxDamage = 2;
	}

	public void act(World world, long elapsedTime) {
		behavior.act(this, world, elapsedTime);
	}
	
	public Being getNewInstance() {
		return new Spider(game);
	}
}
