/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asierso.blockgame.misc;

/**
 *
 * @author sobblaas
 */
public class FlagSettings {
    private String name;
    private String showName;
    private Object value;
    public FlagSettings(String name, Object defaultValue,String showName){
        this.name = name;
        this.value = defaultValue;
        this.showName = showName;
    }
    public FlagSettings(String name, Object defaultValue){
        this.name = name;
        this.value = defaultValue;
        this.showName = name;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
}
