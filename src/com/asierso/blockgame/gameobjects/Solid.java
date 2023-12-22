/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asierso.blockgame.gameobjects;

import com.asierso.vortexengine.objects.GameObject;
import com.asierso.vortexengine.window.Window;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;

/**
 *
 * @author sobblaas
 */
public class Solid extends GameObject{
    @Override
    public void render(Window win){
        RectangleShape rsh = new RectangleShape();
        rsh.setPosition(this.getPosition());
        rsh.setSize(this.getBoxSize());
        rsh.setRotation(this.getRotation());
        rsh.setFillColor(this.getColor());
        
        win.getRender().draw(rsh);
    }
}
