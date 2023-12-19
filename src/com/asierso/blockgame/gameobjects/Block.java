/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asierso.blockgame.gameobjects;

import com.asierso.vortexengine.objects.GameObject;
import com.asierso.vortexengine.window.Window;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 *
 * @author sobblaas
 */
public class Block extends GameObject {

    private Texture texture = null;
    private int id = 0;
    private boolean isParticle = false;

    public void setIsParticle(boolean isParticle) {
        this.isParticle = isParticle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void render(Window win) {
        if (texture == null) {
            return;
        }
        if (!isParticle) {
            if (id == 3) {
                this.setBoxSize(9.9f, 10f);
            } else {
                this.setBoxSize(10, 10);
            }
        }

        Sprite spr = new Sprite(texture);

        int xid = id * 8;

        IntRect textureRect = new IntRect(xid, 0, 8, 8);
        spr.setTextureRect(textureRect);

        if(!isParticle)
            spr.setScale(new Vector2f(1.3f, 1.3f));
        else
            spr.setScale(this.getBoxSize());

        spr.setPosition(this.getPosition());
        spr.setRotation(this.getRotation());
        win.getRender().draw(spr);
    }
}
