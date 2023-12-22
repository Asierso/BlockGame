/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asierso.blockgame;

import java.io.IOException;
import java.nio.file.Paths;
import org.jsfml.graphics.Texture;

/**
 *
 * @author asier
 */
public class TextureLoader {
    private Texture tex;
    public TextureLoader(String uri){
        try{
            tex = new Texture();
            tex.loadFromFile(Paths.get(uri));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public Texture getTexture(){
        return tex;
    }
}
