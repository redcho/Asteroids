package com.redcho.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by jagcurasur on 11/12/15.
 */
public class Sensor extends SpaceObject {

    private float radius;

    public Sensor(Player player){
        this.x = player.x + 100;
        this.y = player.y;
        radius = 40;
    }

    public void update(Player player){
        this.x = player.x;
        this.y = player.y + 100;
        this.radians = 0;
        wrap();
    }

    public void draw(ShapeRenderer sr){
        sr.setColor(Color.WHITE);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.circle(x,y,radius);
        sr.end();
    }

    public float getRadians(){
        return radians;
    }

    public boolean inside(Asteroid a){
        float x1 = a.getx();
        float y1 = a.gety();
        float r1 = a.getRadius();

        if(Math.pow((x-x1),2) + Math.pow((y1-y),2) <= Math.pow((r1+radius),2)){
            return true;
        }

        return false;

    }

}
