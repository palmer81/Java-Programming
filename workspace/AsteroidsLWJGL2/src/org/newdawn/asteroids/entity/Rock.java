package org.newdawn.asteroids.entity;

import org.lwjgl.opengl.GL11;
import org.newdawn.asteroids.model.ObjModel;
import org.newdawn.spaceinvaders.lwjgl.Texture;

/**
 * The entity representing a rock floating round in the game play
 * area. Rocks move around until they hit something. They then either
 * change direction (if they hit another rock), split into smaller
 * rocks (if they're big enough) or disappear (if they're small) 
 * 
 * @author Kevin Glass
 */
public class Rock extends AbstractEntity {
	/** The texture to be applied to this entity */
	private Texture texture;
	/** The model to be rendered for this entity */
	private ObjModel model;
	/** The size of this rock */
	private int size;
	/** The speed of rotate */
	private float rotateSpeed = (float) (Math.random() * 0.5f) + 1;
	
	/**
	 * Create a rock at a specifed location with a random velocity. This
	 * should be used when spawning rocks at the begining of the game.
	 * 
	 * @param texture The texture to apply to the rock
	 * @param model The model to be rendered for the rock
	 * @param x The initial x position of the rock
	 * @param y The initial y position of the rock 
	 * @param size The size of the rock (3 - big rock, 2 - medium , 1 - small)
	 */
	public Rock(Texture texture, ObjModel model, float x, float y, int size) {
		// we're simply going to call the other constructor with some
		// random values for the velocity
		this(texture, model, x, y, size, 
				(float) (-4 + (Math.random() * 8)), 
				(float) (-4 + (Math.random() * 8)));
	}
	
	/**
	 * Create a rock at a specified location with a given velocity. This
	 * should be used when rocks as an effect of splitting another
	 * rock.
	 * 
	 * @param texture The texture to apply to the rock model
	 * @param model The model to be rendered for this rock
	 * @param x The initial x position of this rock
	 * @param y The initial y position of the rock 
	 * @param size The size of the rock (3 - big rock, 2 - medium , 1 - small)
	 * @param vx The x component of the initial velocity
	 * @param vy The y component of the initial velocity
	 */
	public Rock(Texture texture, ObjModel model, float x, float y, int size, float vx, float vy) {
		this.texture = texture;
		this.model = model;
		
		velocityX = vx;
		velocityY = vy;
		positionX = x;
		positionY = y;

		this.size = size;
	}

	/**
	 * @see org.newdawn.asteroids.entity.Entity#update(org.newdawn.asteroids.entity.EntityManager, int)
	 */
	public void update(EntityManager manager, int delta) {
		// call the abstract entitie's update method to cause the
		// rock to move based on its current settings
		super.update(manager, delta);
		
		// the rocks just spin round all the time, so adjust
		// the rotation of the rock based on the amount of time
		// that has passed
		rotationZ += (delta / 10.0f) * rotateSpeed;
	}

	/**
	 * @see org.newdawn.asteroids.entity.Entity#render()
	 */
	public void render() {
		// enable lighting over the rock model
		GL11.glEnable(GL11.GL_LIGHTING);
		
		// store the original matrix setup so we can modify it
		// without worrying about effecting things outside of this 
		// class
		GL11.glPushMatrix();

		// position the model based on the players currently game
		// location
		GL11.glTranslatef(positionX,positionY,0);

		// rotate the rock round to its current Z axis rotate
		GL11.glRotatef(rotationZ,0,0,1);
		
		// scale the model based on the size of rock we're representing
		GL11.glScalef(size, size, size);
		
		// bind the texture we want to apply to our rock and then
		// draw the model 
		texture.bind();
		model.render();
		
		// restore the model matrix so we leave this method
		// in the same state we entered
		GL11.glPopMatrix();
	}

	/**
	 * @see org.newdawn.asteroids.Entity#getSize()
	 */
	public float getSize() {
		return size * 0.5f;
	}

	/**
	 * Cause this rock to split apart into two smaller rocks
	 * or to disappear if its too small to spliy
	 * 
	 * @param manager The callback to the class managing the list
	 * of entities in the game
	 * @param reason The entity which was the reason for this split to
	 * occur (either the player's ship or a shot hitting the rock)
	 */
	void split(EntityManager manager, Entity reason) {
		// remove this rock (since its about to split) and notify
		// the manager that a rock has been destroyed (just in case
		// it needs to take an action)
		manager.removeEntity(this);
		manager.rockDestroyed(size);
		
		// if the rock isn't the smallest we'll need to create
		// two smaller rocks which the rock destroyed have "split"
		// into 
		if (size > 1) {
			// work out the vector at which the reason for this rock
			// split hit the rock in question
			float dx = getX() - reason.getX();
			float dy = getY() - reason.getY();
			
			// scale it down a bit since we're about to use it
			// for position the split out rocks. The smaller the original
			// rock the closer the resulting rocks need to be to each other
			dx *= (size * 0.2f);
			dy *= (size * 0.2f);
			
			// the speed that the rocks splitting out are going to be sent
			// out at. This value has no units and is just a scalar used
			// to tune the game
			float speed = 2;
			
			// create and add the two new rocks based on the direction of
			// impact and the size of the new rocks.
			Rock rock1 = new Rock(texture, model, getX() + dy, getY() - dx, size - 1, dy * speed, -dx * speed);
			Rock rock2 = new Rock(texture, model, getX() - dy, getY() + dx, size - 1, -dy * speed, dx * speed);
			
			manager.addEntity(rock1);
			manager.addEntity(rock2);
		}
	}
	
	/**
	 * @see org.newdawn.asteroids.entity.Entity#collide(org.newdawn.asteroids.entity.EntityManager, org.newdawn.asteroids.entity.Entity)
	 */
	public void collide(EntityManager manager, Entity other) {
		// if anything collides with a rock its direction must change 
		// (to prevent rocks intersecting with each other). For effect
		// we'll also change the direction of rotation
		velocityX = (getX() - other.getX());
		velocityY = (getY() - other.getY());
		
		rotateSpeed = -(rotateSpeed + 3);
	}
}
