package org.newdawn.asteroids.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.asteroids.model.ObjModel;
import org.newdawn.asteroids.particles.ParticleGroup;
import org.newdawn.spaceinvaders.lwjgl.Texture;

/**
 * The entity representing the player. This entity is responsible for
 * displaying a model, a particle system for the player's engine and
 * for creating shot entities based on player input.
 * 
 * @author Kevin Glass
 */
public class Player extends AbstractEntity {
	/** The texture to apply to the model */
	private Texture texture;
	/** The model to be displayed for the player */
	private ObjModel model;
	
	/** The X component of the forward vector - used to place shots */
	private float forwardX = 0;
	/** The Y component of the forward vector - used to place shots */
	private float forwardY = 1;
	
	/** The timeout that counts down until the player can shoot again */
	private int shotTimeout;
	/** The interval in milliseconds between player shots */
	private int shotInterval = 300;
	
	/** The texture applied to the particles that build up the player's shots */
	private Texture shotTexture;
	/** The particle engine used to produce the ship's engine effect */
	private ParticleGroup engine;
	
	/**
	 * Create a new Player entity
	 * 
	 * @param texture The texture to apply to the player's model
	 * @param model The model to display for the player
	 * @param shotTexture The texture to apply to the shot's created when
	 * the player fires
	 */
	public Player(Texture texture, ObjModel model, Texture shotTexture) {
		this.texture = texture;
		this.model = model;
		this.shotTexture = shotTexture;
		
		// create a new particle group to use for the engine 
		// trail from the ship (100 particles, 200 milliseconds
		// fade out, red = 0, green = 1, blue = 0)
		engine = new ParticleGroup(100,200,0,1,0);
	}
	
	/**
	 * @see org.newdawn.asteroids.entity.Entity#update(org.newdawn.asteroids.entity.EntityManager, int)
	 */
	public void update(EntityManager manager, int delta) {
		// if the player is pushing left or right then rotate the
		// ship. Note that the amount rotated is scaled by delta, the 
		// amount of time that has passed. This means that rotation
		// stays framerate independent
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			rotationZ += (delta / 5.0f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			rotationZ -= (delta / 5.0f);
		}

		// recalculate the forward vector based on the current
		// ship rotation
		forwardX = (float) Math.sin(Math.toRadians(rotationZ));
		forwardY = (float) -Math.cos(Math.toRadians(rotationZ));
		
		// count down the timer until a shot can be taken again
		// if the timeout has run out (<= 0) then check if the player
		// wants to fire. If so, fire and then reset the timeout
		shotTimeout -= delta;
		if (shotTimeout <= 0) {
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				fire(manager);
				shotTimeout = shotInterval;
			}
		}
		
		// if the player is pushing the thrust key (up) then
		// increase the velocity in the direction we're currently
		// facing
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			// increase the velocity based on the current forward
			// vector (note again that this is scaled by delta to 
			// keep us framerate independent)
			velocityX += (forwardX * delta) / 50.0f;
			velocityY += (forwardY * delta) / 50.0f;
			
			
			// since we're thrusting now we need to add some effect
			// to our engine engine trail - add a particle to the
			// engine positioning it just behind the ship
			float flameOffset = 1.1f;
			engine.addParticle(positionX-(forwardX*flameOffset), 
					   positionY-(forwardY*flameOffset), 
					   0.6f, 150);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			// increase the velocity based on the current forward
			// vector (note again that this is scaled by delta to 
			// keep us frame-rate independent)
			velocityX -= (forwardX * delta) / 50.0f;
			velocityY -= (forwardY * delta) / 50.0f;
			
			
			// since we're thrusting now we need to add some effect
			// to our engine engine trail - add a particle to the
			// engine positioning it just behind the ship
			float flameOffset = 1.1f;
			engine.addParticle(positionX-(forwardX*flameOffset), 
					   positionY-(forwardY*flameOffset), 
					   0.6f, 150);
		}
		
		// call the update the abstract class to cause our generic
		// movement and anything else the abstract implementation 
		// provides for us
		super.update(manager, delta);
		
		// update the particle engine to cause the particles to fade
		// out over their lifespan.
		engine.update(delta);
	}
	
	/**
	 * Fire a shot - this consists of creating a new shot entity
	 * at the correct location and adding it to the game
	 * 
	 * @param manager The manager holding the list of entities
	 * to which we'll add our new shot
	 */
	private void fire(EntityManager manager) {
		// create a new shot based on our current settings
		Shot shot = new Shot(shotTexture, 
							 getX() + forwardX, 
							 getY() + forwardY, 
							 forwardX * 30, 
							 forwardY * 30);
		
		// add the entity to the game and notify it that a 
		// shot has been fired (just in case it needs to do 
		// anything)
		manager.addEntity(shot);
		manager.shotFired();
	}
	
	/**
	 * @see org.newdawn.asteroids.entity.Entity#render()
	 */
	public void render() {
		// enable lighting for the player's model
		GL11.glEnable(GL11.GL_LIGHTING);
		
		// store the original matrix setup so we can modify it
		// without worrying about effecting things outside of this 
		// class
		GL11.glPushMatrix();

		// position the model based on the players currently game
		// location
		GL11.glTranslatef(positionX,positionY,0);
		
		// rotate the ship round to our current orientation for shooting
		GL11.glRotatef(rotationZ,0,0,1);
		
		// setup the matrix to draw the model for our player
		// rotate the ship to the right orientation for rendering. Our
		// ship model is modelled on a different axis to the one we're
		// using so we'd like to rotate it round
		GL11.glRotatef(90,1,0,0);
		
		// scale the model down because its way to big by default
		GL11.glScalef(0.01f,0.01f,0.01f);
		
		// bind to the texture for our model then render it. This 
		// actually draws the geometry to the screen
		texture.bind();
		model.render();
		
		// restore the model matrix so we leave this method
		// in the same state we entered
		GL11.glPopMatrix();
		
		// render the particle engine that presents our ship's engine
		// out
		renderEngine();
	}

	/**
	 * Render the particle effect thats used to represent our
	 * ship's engine.
	 */
	private void renderEngine() {
		// disable lighting for the particles, we want them to 
		// be very glowy
		GL11.glDisable(GL11.GL_LIGHTING);
		
		// When rendering particles we want to blend them together
		// to give that smoothed look. Note that we're setting the
		// blend function to GL_ONE which causes the final alpha value
		// rendered to be ramped up making everything look glowing
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		
		// bind to our texture then ask the particle engine to render
		// all its particles
		shotTexture.bind();
		engine.render();
		
		// turn blending back off, since we're done now
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	/**
	 * @see org.newdawn.asteroids.Entity#getSize()
	 */
	public float getSize() {
		// the size of the player
		return 2;
	}

	/**
	 * @see org.newdawn.asteroids.entity.Entity#collide(org.newdawn.asteroids.entity.EntityManager, org.newdawn.asteroids.entity.Entity)
	 */
	public void collide(EntityManager manager, Entity other) {
		// if we've collide with a rock then the rock must split apart,
		// and our velocity needs to be changed to push us away from the
		// rock
		if (other instanceof Rock) {
			velocityX = (getX() - other.getX());
			velocityY = (getY() - other.getY());
			
			((Rock) other).split(manager, this);
			
			// notify the class manging the entities that the player
			// has been hit, just in case anything needs doing
			manager.playerHit();
		}
	}
}
