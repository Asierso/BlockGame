/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asierso.blockgame.gameobjects;

import com.asierso.vortexengine.objects.GameObject;
import com.asierso.vortexengine.window.Window;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

/**
 *
 * @author sobblaas
 */
public class Grid extends GameObject {
    private int celSize = 10;
    private final Color COLORFG = new Color(255,255,255,100);
    public int getCelSize() {
        return celSize;
    }

    public void setCelSize(int celSize) {
        this.celSize = celSize;
    }
    
    @Override
    public void render(Window win){
        RectangleShape rect;
        for(int i = 0;i<this.getBoxSize().x;i+=celSize){
            rect = new RectangleShape();
            rect.setFillColor(COLORFG);
            rect.setPosition(i,this.getPosition().y);
            rect.setSize(new Vector2f(1,this.getBoxSize().y));
            win.getRender().draw(rect);
        }
        for(int i = 0;i<this.getBoxSize().y;i+=celSize){
            rect = new RectangleShape();
            rect.setFillColor(COLORFG);
            rect.setPosition(this.getPosition().x,i);
            rect.setSize(new Vector2f(this.getBoxSize().x,1));
            win.getRender().draw(rect);
        }
    }
}
