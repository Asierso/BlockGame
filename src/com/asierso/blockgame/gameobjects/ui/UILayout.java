/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asierso.blockgame.gameobjects.ui;

import com.asierso.blockgame.TextureLoader;
import com.asierso.vortexengine.objects.GameObject;
import com.asierso.vortexengine.window.Window;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 *
 * @author asier
 */
public class UILayout extends GameObject {

    private final IntRect[] inventorySplitedTiles = new IntRect[17];
    private final Font font = new Font();
    protected final Texture uiTexture;
    protected final float LAYOUT_SCALE = 1.3f * 2;

    public UILayout() {
        uiTexture = new TextureLoader("res/ui.png").getTexture();
        
        try {
            font.loadFromFile(Paths.get("res/pixelfont.ttf"));
        } catch (IOException ex) {
            Logger.getLogger(UILayout.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int i = 0; i < inventorySplitedTiles.length; i++) {
            inventorySplitedTiles[i] = new IntRect(i * 8, 0, 8, 8);
        }
    }
    
    protected IntRect[] getSplitedTiles(){
        return inventorySplitedTiles;
    }
    
     protected Font getFont(){
        return font;
    }

    @Override
    public void render(Window win) {
        for (int j = 0; j < this.getBoxSize().y; j++) {
            for (int i = 0; i < this.getBoxSize().x; i++) {
                Sprite spr = new Sprite(uiTexture);
                if (j == 0) { //Top border
                    if (i == 0) {
                        spr.setTextureRect(inventorySplitedTiles[0]);
                    } else if (i == this.getBoxSize().x - 1) {
                        spr.setTextureRect(inventorySplitedTiles[2]);
                    } else {
                        spr.setTextureRect(inventorySplitedTiles[1]);
                    }
                }
                else if(j == this.getBoxSize().y - 1){ //Center
                    if (i == 0) {
                        spr.setTextureRect(inventorySplitedTiles[6]);
                    } else if (i == this.getBoxSize().x - 1) {
                        spr.setTextureRect(inventorySplitedTiles[4]);
                    } else {
                        spr.setTextureRect(inventorySplitedTiles[5]);
                    }
                }
                else{ //Button border
                    if (i == 0) {
                        spr.setTextureRect(inventorySplitedTiles[7]);
                    } else if (i == this.getBoxSize().x - 1) {
                        spr.setTextureRect(inventorySplitedTiles[3]);
                    } else {
                        spr.setTextureRect(inventorySplitedTiles[8]);
                    }
                }
                spr.setScale(new Vector2f(LAYOUT_SCALE,LAYOUT_SCALE));
                spr.setPosition(this.getPosition().x + (i * 16), this.getPosition().y + (j * 16));
                spr.setRotation(this.getRotation());
                win.getRender().draw(spr);
            }
        }
    }
}
