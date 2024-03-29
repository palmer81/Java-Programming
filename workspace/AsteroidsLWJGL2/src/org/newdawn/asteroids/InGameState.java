package org.newdawn.asteroids;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.asteroids.entity.Entity;
import org.newdawn.asteroids.entity.EntityManager;
import org.newdawn.asteroids.entity.Player;
import org.newdawn.asteroids.entity.Rock;
import org.newdawn.asteroids.gui.BitmapFont;
import org.newdawn.asteroids.model.ObjLoader;
import org.newdawn.asteroids.model.ObjModel;
import org.newdawn.asteroids.sound.Sound;
import org.newdawn.asteroids.sound.SoundLoader;
import org.newdawn.spaceinvaders.lwjgl.Texture;
import org.newdawn.spaceinvaders.lwjgl.TextureLoader;

/**
 * This state is responsible for rendering the game world and handling
 * the mechanics of game play.
 * 
 * @author Kevin Glass
 */
public class InGameState implements GameState, EntityManager {
	/** The unique name of this state */
	public static final String NAME = "ingame";
	
	/** The texture for the back drop */
	private Texture background;
	/** The texture for the particles in the shot */
	private Texture shotTexture;
	/** The texture for the ship */
	private Texture shipTexture;
	/** The model of the player's ship */
	private ObjModel shipModel;
	/** The texture applied to the asteroids */
	private Texture rockTexture;
	/** The model rendered for the asteroids */
	private ObjModel rockModel;
	
	private ObjModel crystalModel;
	
	/** The font used to draw the text to the screen */
	private BitmapFont font;
	
	/** The entity representing the player */
	private Player player;
	/** The entities in the game */
	private ArrayList entities = new ArrayList();
	/** The list of entities to be added at the next opportunity */
	private ArrayList addList = new ArrayList();
	/** The list of entities to be removed at the next opportunity */
	private ArrayList removeList = new ArrayList();

	/** The OpenGL material properties applied to everything in the game */
	private FloatBuffer material;
	
	/** The current score */
	private int score;
	/** The number of lives left */
	private int lives = 4;
	/** True if the game is over */
	private boolean gameOver;
	
	/** The sound effect to play when shooting */
	private Sound shoot;
	/** The sound effect to play when rocks split apart */
	private Sound split;
	
	/** The current level of play */
	private int level;
	/** The timeout for the game over message before resetting to the menu */
	private int gameOverTimeout;
	
	/**
	 * Create a new game state
	 */
	public InGameState() {
	}

	/**
	 * @see org.newdawn.asteroids.GameState#getName()
	 */
	public String getName() {
		return NAME;
	}
	
	/**
	 * Defint the light setup to view the scene
	 */
	private void defineLight() {
		FloatBuffer buffer;
		
		buffer = BufferUtils.createFloatBuffer(4);
		buffer.put(1).put(1).put(1).put(1); 
		buffer.flip();
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, buffer);
		
		buffer = BufferUtils.createFloatBuffer(4);
		buffer.put(1).put(1).put(1).put(1);
		buffer.flip();
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, buffer);
		
		// setup the ambient light 
		buffer = BufferUtils.createFloatBuffer(4);
		buffer.put(0.8f).put(0.8f).put(0.8f).put(0.8f);
		buffer.flip();
		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, buffer);
		GL11.glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE, GL11.GL_TRUE);
		
		// set up the position of the light
		buffer = BufferUtils.createFloatBuffer(4);
		buffer.put(10).put(10).put(5).put(0);
		buffer.flip();
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, buffer);
		
		GL11.glEnable(GL11.GL_LIGHT0);
		
		material = BufferUtils.createFloatBuffer(4);
	}

	/**
	 * @see org.newdawn.asteroids.GameState#init(org.newdawn.asteroids.GameWindow)
	 */
	public void init(GameWindow window) throws IOException {
		defineLight();
		
		TextureLoader loader = new TextureLoader();
		background = loader.getTexture("res/bg.jpg");
		shotTexture = loader.getTexture("res/shot.png");
		
		shipTexture = loader.getTexture("res/ship.jpg");
		shipModel = ObjLoader.loadObj("res/ship.obj");
		rockTexture = loader.getTexture("res/rock.jpg");
		rockModel = ObjLoader.loadObj("res/rock.obj");
		
		Texture fontTexture = loader.getTexture("res/font.png");
		font = new BitmapFont(fontTexture, 32, 32);
		
		shoot = SoundLoader.get().getOgg("res/hit.ogg");
		split = SoundLoader.get().getOgg("res/bush.ogg");
	}

	/**
	 * @see org.newdawn.asteroids.GameState#render(org.newdawn.asteroids.GameWindow, int)
	 */
	public void render(GameWindow window, int delta) {
		// reset the view transformation matrix back to the empty
		// state. 
		GL11.glLoadIdentity();

		material.put(1).put(1).put(1).put(1); 
		material.flip();
		GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, material);
		GL11.glMaterial(GL11.GL_BACK, GL11.GL_DIFFUSE, material);
		
		// draw our background image
		GL11.glDisable(GL11.GL_LIGHTING);
		drawBackground(window);
		
		// position the view a way back from the models so we
		// can see them
		GL11.glTranslatef(0,0,-50);

		// loop through all entities in the game rendering them
		for (int i=0;i<entities.size();i++) {
			Entity entity = (Entity) entities.get(i);
			
			entity.render();
		}
		
		drawGUI(window);
	}

	/**
	 * Draw the overlay for score and lives
	 * 
	 * @param window The window in which the GUI is displayed 
	 */
	private void drawGUI(GameWindow window) {
		window.enterOrtho();
		
		GL11.glColor3f(1,1,0);
		font.drawString(1, "SCORE:" + score, 5, 5);
		
		GL11.glColor3f(1,0,0);
		String numLives = "";
		for (int i = 0; i < lives; i++) {
			numLives += "O";
		}
		font.drawString(0, numLives, 795 - (numLives.length() * 27), 5);

		GL11.glColor3f(1,1,1);

		if (gameOver) {
			font.drawString(1, "GAME OVER", 280, 286);
		}
		window.leaveOrtho();
	}
	
	/**
	 * Draw the background image
	 * 
	 * @param window The window to display the background in 
	 */
	private void drawBackground(GameWindow window) {
		window.enterOrtho();
		
		background.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2i(0,0);
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2i(0,600);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2i(800,600);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2i(800,0);
		GL11.glEnd();
		
		window.leaveOrtho();
	}
	
	/**
	 * @see org.newdawn.asteroids.GameState#update(org.newdawn.asteroids.GameWindow, int)
	 */
	public void update(GameWindow window, int delta) {
		if (gameOver) {
			gameOverTimeout -= delta;
			if (gameOverTimeout < 0) {
				window.changeToState(MenuState.NAME);
			}
		}
		
		for (int i=0;i<entities.size();i++) {
			Entity entity = (Entity) entities.get(i);
			float firstSize = entity.getSize();
			
			for (int j=i+1;j<entities.size();j++) {
				Entity other = (Entity) entities.get(j);
				
				if (entity.collides(other)) {
					entity.collide(this, other);
					other.collide(this, entity);
				}
			}
		}
		
		entities.removeAll(removeList);
		entities.addAll(addList);
		
		removeList.clear();
		addList.clear();
		
		// loop through all the entities in the game causing them
		// to update (i.e. move, shoot, etc)
		int rockCount = 0;
		
		for (int i=0;i<entities.size();i++) {
			Entity entity = (Entity) entities.get(i);
			
			entity.update(this, delta);
			
			if (entity instanceof Rock) {
				rockCount++;
			}
		}
		
		if (rockCount == 0) {
			level++;
			spawnRocks(level);
		}
	}

	/**
	 * @see org.newdawn.asteroids.GameState#enter(org.newdawn.asteroids.GameWindow)
	 */
	public void enter(GameWindow window) {
		entities.clear();
		
		player = new Player(shipTexture, shipModel, shotTexture);
		entities.add(player);
		
		lives = 4;
		score = 0;
		level = 5;
		gameOver = false;
		
		spawnRocks(level);
	}

	/**
	 * Spawn some asteroids into the game world
	 * 
	 * @param count The number of rocks to be spawned
	 */
	private void spawnRocks(int count) {
		// spawn some rocks
		int fails = 0;
		
		for (int i=0;i<count;i++) {
			float xp = (float) (-20 + (Math.random() * 40));
			float yp = (float) (-20 + (Math.random() * 40));
			
			Rock rock = new Rock(rockTexture, rockModel, xp, yp, 3);
			if (!rock.collides(player)) {
				entities.add(rock);
			} else {
				i--;
				fails++;
			}
			
			if (fails > 5) {
				return;
			}
		}
	}
	
	/**
	 * @see org.newdawn.asteroids.GameState#leave(org.newdawn.asteroids.GameWindow)
	 */
	public void leave(GameWindow window) {
	}

	/**
	 * @see org.newdawn.asteroids.entity.EntityManager#removeEntity(org.newdawn.asteroids.entity.Entity)
	 */
	public void removeEntity(Entity entity) {
		removeList.add(entity);
	}

	/**
	 * @see org.newdawn.asteroids.entity.EntityManager#addEntity(org.newdawn.asteroids.entity.Entity)
	 */
	public void addEntity(Entity entity) {
		addList.add(entity);
	}

	/**
	 * @see org.newdawn.asteroids.entity.EntityManager#rockDestroyed(int)
	 */
	public void rockDestroyed(int size) {
		split.play(1.0f,1.0f);
		score += (4 - size) * 100;
	}

	/**
	 * @see org.newdawn.asteroids.entity.EntityManager#playerHit()
	 */
	public void playerHit() {
		lives--;
		if (lives < 0) {
			gameOver = true;
			gameOverTimeout = 6000;
			removeEntity(player);
		}
	}

	/**
	 * @see org.newdawn.asteroids.entity.EntityManager#shotFired()
	 */
	public void shotFired() {
		shoot.play(1.0f,0.5f);
	}
}
