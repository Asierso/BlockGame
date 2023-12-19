/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.asierso.blockgame;

import com.asierso.blockgame.misc.FlagSettings;
import com.asierso.blockgame.misc.Global;
import com.asierso.vortexengine.window.Window;

/**
 *
 * @author sobblaas
 */
public class BlockGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        var win = new Window(600,500);
        win.setTitle("BlockGame");
        win.setScene(new MainScene());
        win.instantiate();
    }
    
}
