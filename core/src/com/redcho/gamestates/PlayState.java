package com.redcho.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.redcho.asteroid.GameStateManager;
import com.redcho.asteroid.ZMainGame;
import com.redcho.entities.*;

import java.util.ArrayList;

/**
 * Created by jagcurasur on 11/8/15.
 */
public class PlayState extends GameState {

    private ShapeRenderer sr;

    private Player player;
    private ArrayList<Bullet> bullets;
    private ArrayList<Asteroid> asteroids;

    private ArrayList<Particle> particles;

    private int level;
    private int totalAsteroids;
    private int numAsteroidsLeft;

    public PlayState(GameStateManager gsm){
        super(gsm);
    }

    public void init(){
        sr = new ShapeRenderer();
        bullets = new ArrayList<Bullet>();
        player = new Player(bullets);
        asteroids = new ArrayList<Asteroid>();
        particles = new ArrayList<Particle>();

        level = 1;
        spawnAsteroids();

    }

    private void createParticles(float x, float y){
        for(int i=0; i<6; i++){
            particles.add(new Particle(x,y));
        }
    }

    private void splitAsteroids(Asteroid a){
        createParticles(a.getx(), a.gety());
        numAsteroidsLeft--;
        if(a.getType() == Asteroid.LARGE){
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.MEDIUM));
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.MEDIUM));
        }else if(a.getType() == Asteroid.MEDIUM){
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.SMALL));
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.SMALL));
        }
    }

    private void spawnAsteroids(){
        asteroids.clear();

        //int numToSpawn = 4 + level - 1;
        int numToSpawn = 1;
        totalAsteroids = numToSpawn * 7;
        numAsteroidsLeft = totalAsteroids;

        for(int i=0; i<numToSpawn; i++){
            float x = MathUtils.random(ZMainGame.WIDTH);
            float y = MathUtils.random(ZMainGame.HEIGHT);

            float dx = x - player.getx();
            float dy = y - player.gety();
            float dist = (float)Math.sqrt(dx*dx + dy*dy);

            while(dist < 100){
                x = MathUtils.random(ZMainGame.WIDTH);
                y = MathUtils.random(ZMainGame.HEIGHT);

                dx = x - player.getx();
                dy = y - player.gety();
                dist = (float)Math.sqrt(dx*dx + dy*dy);
            }

            asteroids.add(new Asteroid(x, y, Asteroid.LARGE));
        }
    }

    public void update(float dt){

        handleInput();

        // Next Level
        if(asteroids.size() == 0){
            level ++;
            spawnAsteroids();
        }

        player.update(dt);
        if(player.isDead()){
            player.reset();
            return;
        }

        for(int i=0; i< bullets.size(); i++){
            bullets.get(i).update(dt);
            if(bullets.get(i).shouldRemove()){
                bullets.remove(i);
                i--;
            }
        }

        for(int i=0; i<asteroids.size(); i++){
            asteroids.get(i).update(dt);
            if(asteroids.get(i).shouldRemove()){
                asteroids.remove(i);
                i--;
            }
        }

        for(int i=0; i<particles.size(); i++){
            particles.get(i).update(dt);
            if(particles.get(i).shouldRemove()){
                particles.remove(i);
                i--;
            }
        }

        checkCollision();
    }

    private void checkCollision(){

        //Player-Asteroid
        if(!player.isHit()) {
            for (int i = 0; i < asteroids.size(); i++) {
                Asteroid a = asteroids.get(i);
                if (a.intersects(player)) {
                    player.hit();
                    asteroids.remove(i);
                    i--;
                    splitAsteroids(a);
                    break;
                }
            }
        }

        // Bullet-Asteroid
        for(int i=0; i<bullets.size(); i++){
            Bullet b = bullets.get(i);
            for(int j=0; j<asteroids.size(); j++){
                Asteroid a = asteroids.get(j);
                if(a.contains(b.getx(), b.gety())){
                    bullets.remove(i);
                    i--;
                    asteroids.remove(j);
                    j--;
                    splitAsteroids(a);
                    break;
                }
            }
        }

        // Asteroid - Sensor
        Sensor s = player.getSensor();
        for(int i =0; i<asteroids.size(); i++){
            Asteroid a = asteroids.get(i);
            if(s.inside(a)){
                player.decide(a);
            }
        }

    }

    public void draw(){

        player.draw(sr);

        for(int i=0; i<bullets.size(); i++){
            bullets.get(i).draw(sr);
        }

        for(int i=0; i<asteroids.size(); i++){
            asteroids.get(i).draw(sr);
        }

        for(int i=0; i<particles.size(); i++){
            particles.get(i).draw(sr);
        }

    }
    public void handleInput(){

        player.setLeft(Gdx.input.isKeyPressed(Input.Keys.A));
        player.setRight(Gdx.input.isKeyPressed(Input.Keys.D));
        player.setUp(Gdx.input.isKeyPressed(Input.Keys.W));
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            player.shoot();
        }
    }
    public void dispose(){ }
}
