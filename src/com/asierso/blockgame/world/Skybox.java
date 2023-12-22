/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asierso.blockgame.world;

import com.asierso.vortexengine.miscellaneous.ColorModifier;
import com.asierso.vortexengine.objects.GameObject;
import com.asierso.vortexengine.window.Window;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

/**
 *
 * @author asier
 */
public class Skybox extends GameObject {
    private final ColorModifier defaultAmbient = new ColorModifier(135, 206, 235, 255);
    private ColorModifier ambient = new ColorModifier(135, 206, 235, 255);
    
    //Flag of day sky shinning
    private boolean lightFlag = true;
    
    //Daylight cicle control
    private int dayNightTime = 0;
    private int lightDelta = 0;
    private float sunPos = 100;

    @Override
    protected void render(Window win) {
        //Sky shape
        RectangleShape sky = new RectangleShape();
        sky.setFillColor(new Color(ambient.r, ambient.g, ambient.b));
        sky.setPosition(0, 0);
        sky.setSize(new Vector2f(win.getSize().width, win.getSize().height));
        win.getRender().draw(sky);

        //Sun shape
        RectangleShape sun = new RectangleShape();
        sun.setFillColor(new Color(255,238,131,200));
        
        //Sun position calculation
        if (dayNightTime < 4000 || dayNightTime > 6000) {
            sun.setSize(new Vector2f(50, 50));
            if (dayNightTime % 5 == 0) {
                sunPos++;
            }
            if (130 - (float) Math.sin(sunPos / 180) * 100 != 0) { //Morning and afternoon
                sun.setPosition(sunPos - sun.getSize().x, 130 - (float) Math.sin(sunPos / 180) * 100);
            }
            win.getRender().draw(sun);
        } else {
            sunPos = 0;
        }

        
        //Sky color calculation update
        updateAmbient();
    }

    private void updateAmbient() {
        lightDelta++;
        dayNightTime++;
        if (lightFlag) {
            if (lightDelta % 5 == 0) {
                if (dayNightTime < 5000) {
                    ambient = new ColorModifier(ambient.r + 1, ambient.g + 1, ambient.b + 1, 255);
                } else {
                    ambient = new ColorModifier(ambient.r - 1, ambient.g - 1, ambient.b - 1, 255);
                }
            }
        } else {
            if (lightDelta % 5 == 0) {
                if (dayNightTime < 2000) { //Day
                    ambient = new ColorModifier(ambient.r - 1, ambient.g - 1, ambient.b - 1, 255);
                } else if (dayNightTime < 5000 && ambient.r > 10 && ambient.g > 10 && ambient.b > 10) { //Afternoon
                    ambient = new ColorModifier(ambient.r - 3, ambient.g - 3, ambient.b - 3, 255);
                } else if (dayNightTime > 5000 && ambient.r < defaultAmbient.r && ambient.g < defaultAmbient.g && ambient.b < defaultAmbient.b) { //Morning
                    ambient = new ColorModifier(ambient.r + 3, ambient.g + 3, ambient.b + 3, 255);
                } else if (ambient.r >= defaultAmbient.r && ambient.g >= defaultAmbient.g && ambient.b >= defaultAmbient.b) { //Reset daynight
                    dayNightTime = 0;
                }
            }
        }
        if (lightDelta > 55) {
            lightFlag = !lightFlag;
            lightDelta = 0;
        }
    }
}
