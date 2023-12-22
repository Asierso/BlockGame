/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asierso.blockgame.gameobjects.ui;

import com.asierso.vortexengine.window.Window;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;

/**
 *
 * @author asier
 */
public class SplashScreen extends UILayout {

    private final IntRect[] splitedTiles = this.getSplitedTiles();

    @Override
    public void render(Window win) {
        super.render(win);
        Text tx = new Text();
        tx.setFont(this.getFont());

        tx.setCharacterSize(50);
        tx.setPosition(this.getPosition().x + 90, this.getPosition().y + 10);
        tx.setColor(Color.BLACK);
        tx.setString("BlockGame");
        win.getRender().draw(tx);

        tx.setCharacterSize(18);
        tx.setPosition(this.getPosition().x + 90, this.getPosition().y + 60);
        tx.setColor(Color.BLACK);
        tx.setString("Made with love by Asierso");
        win.getRender().draw(tx);

        String[] keys = {"- (Key E) Open inventory","- (Key R) Reset world","- (Key ESC) Closes menu","- (Mouse Left) Places block","- (Mouse Right) Destroy block"};

        for (int i = 0; i < keys.length; i++) {
            tx.setCharacterSize(18);
            tx.setPosition(this.getPosition().x + 60, this.getPosition().y + 80 + (15*i));
            tx.setColor(Color.YELLOW);
            tx.setString(keys[i]);
            win.getRender().draw(tx);
        }
    }
}
