package com.redcho.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by jagcurasur on 11/8/15.
 */
public class Bullet extends SpaceObject {

    private float lifeTime;
    private float lifeTimer;

    private boolean remove;

    public Bullet(float x, float y, float radians){
        this.x = x;
        this.y = y;
        this.radians = radians;

        float speed = 350;
        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;

        width = height = 2;

        lifeTimer = 0;
        lifeTime = 1;

    }

    public boolean shouldRemove(){ return remove; }

    public void update(float dt){

        x += dx*dt;
        y += dy*dt;

        wrap();

        lifeTimer += dt;
        if(lifeTimer > lifeTime){
            remove = true;
        }
    }

    public void draw(ShapeRenderer sr){
        sr.setColor(Color.WHITE);
        sr.begin(ShapeRenderer.ShapeType.Filled);
          sr.circle(x - width / 2, y - height / 2, width / 2);
        sr.end();
    }

}
