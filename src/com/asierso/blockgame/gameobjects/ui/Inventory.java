/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asierso.blockgame.gameobjects.ui;

import com.asierso.blockgame.TextureLoader;
import com.asierso.vortexengine.window.Window;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Mouse;

/**
 *
 * @author asier
 */
public class Inventory extends UILayout {

    private final IntRect[] splitedTiles = this.getSplitedTiles();
    private final Texture blocksTex = new TextureLoader("res/tiles.png").getTexture();
    private final int BLOCK_IDS = 60;
    private int selectedBlock = 1;
    private Vector2f[] blockButton = new Vector2f[BLOCK_IDS];

    @Override
    public void render(Window win) {
        //UI elements drawer
        Sprite spr = new Sprite(this.uiTexture);

        //UI blocks drawer
        Sprite bk = new Sprite(blocksTex);

        //Font renderer
        Text tx = new Text();
        tx.setFont(this.getFont());
        
        //Draw inventory tag
        for (int i = 5; i > 0; i--) {
            spr.setPosition(this.getPosition().x + (18 * i), this.getPosition().y - 17);
            spr.setScale(new Vector2f(this.LAYOUT_SCALE, this.LAYOUT_SCALE));
            if (i == 5) {
                spr.setTextureRect(splitedTiles[11]);
            } else {
                spr.setTextureRect(splitedTiles[10]);
            }
            win.getRender().draw(spr);
        }
        //Draw inventory icon
        spr.setTextureRect(splitedTiles[13]);
        spr.setPosition(this.getPosition().x + 25, this.getPosition().y - 14);
        spr.setScale(new Vector2f(this.LAYOUT_SCALE * .55f, this.LAYOUT_SCALE * .55f));
        win.getRender().draw(spr);
        
        //Draw inventory name
        tx.setCharacterSize(18);
        tx.setPosition(this.getPosition().x+40,this.getPosition().y-22);
        tx.setColor(Color.BLACK);
        tx.setString("Inventory");
        
        win.getRender().draw(tx);
        
        super.render(win);

        //Draw up to the body
        int xpos = 0;
        int ypos = 0;
        
        for (int i = 0; i < BLOCK_IDS; i++) {
            spr = new Sprite(this.uiTexture);
            spr.setScale(new Vector2f(this.LAYOUT_SCALE, this.LAYOUT_SCALE));
            spr.setPosition(this.getPosition().x + 22 + (10 * (xpos * this.LAYOUT_SCALE)), this.getPosition().y + 20 + (10 * (ypos * this.LAYOUT_SCALE)));
            blockButton[i] = spr.getPosition();
            if (selectedBlock == i + 1) {
                spr.setTextureRect(splitedTiles[12]);
            } else {
                spr.setTextureRect(splitedTiles[9]);
            }

            bk.setTextureRect(new IntRect(8 * (i + 1), 0, 8, 8));
            bk.setScale(new Vector2f(this.LAYOUT_SCALE - .5f, this.LAYOUT_SCALE - .5f));
            bk.setPosition(new Vector2f(spr.getPosition().x + 2, spr.getPosition().y + 2));

            //Draw all modified components in lap
            win.getRender().draw(spr);
            win.getRender().draw(bk);
            xpos++;
            
            //Next row (xpos overflow)
            if(xpos > 10){
                ypos++;
                xpos=0;
            }
        }
        
        if (Mouse.isButtonPressed(Mouse.Button.LEFT)) {
            var dx = Mouse.getPosition().x - win.getRender().getPosition().x;
            var dy = Mouse.getPosition().y - win.getRender().getPosition().y-30;
            if (dx > 0 && dy > 0 && dx < win.getSize().width && dy < win.getSize().height) {
                for(int i= 0;i<blockButton.length;i++){
                    if(dx >= blockButton[i].x && dx <= blockButton[i].x + 25 && dy >= blockButton[i].y && dy <= blockButton[i].y + 25){    
                        selectedBlock = i+1;
                    }
                }
            }
        }
    }

    public int getSelectedBlock() {
        return selectedBlock;
    }
}
