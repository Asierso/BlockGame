/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asierso.blockgame.world;

import com.asierso.blockgame.gameobjects.Block;
import com.asierso.vortexengine.window.Layer;

/**
 *
 * @author asier
 */
public class Structure {

    private int[][] idMap;
    private int pos = 0;
    private int width = 0;

    public Structure(int pos, int width) {
        this.pos = pos;
        this.width = width;
    }

    public Structure(int pos, int width, int[][] idMap) {
        this.idMap = idMap;
        this.pos = pos;
        this.width = width;
    }

    public void setIdMap(int[][] idMap) {
        this.idMap = idMap;
    }

    public void addStructure(Layer blocks) {
        for (int i = 0; i < idMap.length; i++) {
            for (int j = 0; j < idMap[i].length; j++) {
                Block handle = (Block) blocks.get(pos + j + ((i-idMap.length) * width));
                handle.setId(idMap[i][j]);
                if (idMap[i][j] == 0) {
                    handle.setVisible(false);
                } else {
                    handle.setVisible(true);
                }
            }
        }
    }
}
