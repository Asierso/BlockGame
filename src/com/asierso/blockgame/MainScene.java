/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asierso.blockgame;

import com.asierso.blockgame.world.Skybox;
import com.asierso.blockgame.world.Generation;
import com.asierso.blockgame.gameobjects.Block;
import com.asierso.blockgame.gameobjects.Grid;
import com.asierso.blockgame.gameobjects.ui.Inventory;
import com.asierso.blockgame.gameobjects.ui.SplashScreen;
import com.asierso.blockgame.misc.FlagSettings;
import com.asierso.blockgame.misc.Global;
import com.asierso.vortexengine.components.physics.Rigibody;
import com.asierso.vortexengine.objects.ParticleSystem;
import com.asierso.vortexengine.window.Layer;
import com.asierso.vortexengine.window.Scene;
import com.asierso.vortexengine.window.Window;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Image;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

/**
 *
 * @author sobblaas
 */
public class MainScene implements Scene {

    private final Grid grid = new Grid();
    private final Skybox sky = new Skybox();
    private final Layer blocks = new Layer();
    private final Layer entities = new Layer();
    private final Inventory inventory = new Inventory();
    private final SplashScreen splash = new SplashScreen();
    private final TextureLoader blockTilesTexture = new TextureLoader("res/tiles.png");
    private final ParticleSystem destroyBlockPS = new ParticleSystem();
    private final Block destroyBlockPSModel = new Block();
    private boolean isAwaked = false;
    private boolean isInventoryOpened = false;
    private boolean isMultiplayerOpened = false;
    private boolean isIconSetted = false;
    private boolean isSplashOpened = true;
    private int selectedId = 1;
    private float cooldown = 0f;
    private final float COOLDOWN_TIME = 0.1f;

    @Override
    public void start() {
        //UI rescale
        inventory.setPosition(10, 10);
        inventory.setBoxSize(20, 15);

        splash.setPosition(10, 10);
        splash.setBoxSize(20, 13);

        //Destroy block particle system initialize
        destroyBlockPSModel.setBoxSize(.5f, .5f);
        destroyBlockPSModel.setPosition(0, 0);
        destroyBlockPSModel.setIsParticle(true);
        destroyBlockPSModel.setTexture(blockTilesTexture.getTexture());
        destroyBlockPS.setParticleModel(destroyBlockPSModel);
        destroyBlockPS.setBoxSize(10, 10);
        destroyBlockPS.setLifetime(.1f);
        destroyBlockPS.setMaxParticles(5);
        destroyBlockPS.setModifier(ParticleSystem.ParticleModifiers.POSITION, new Vector2f(0, .5f));
    }

    private void awake(Window window) {
        //Set window icon (only once)
        setWindowIcon(window);

        var wgen = new Generation(0, window.getSize().width / 10, 2940);
        isAwaked = true;
        blocks.clear();
        entities.clear();
        Block handle;
        for (int i = 0; i < window.getSize().width; i += 10) {
            for (int j = 0; j < window.getSize().width; j += 10) {
                handle = new Block();
                handle.setPosition(j, i);
                handle.setVisible(false);
                handle.setTexture(blockTilesTexture.getTexture());
                blocks.add(handle);
            }
        }

        wgen.generateWorld(blocks);

        //Inventory adjustment
        inventory.setPosition(window.getSize().width / 2 - (inventory.getBoxSize().x * 8), window.getSize().height / 2 - (inventory.getBoxSize().y * 9));

        //Splash screen adjustment
        splash.setPosition(window.getSize().width / 2 - (splash.getBoxSize().x * 8), window.getSize().height / 2 - (splash.getBoxSize().y * 9));
    }

    @Override
    public void update(Window window, Iterable<Event> events) {
        if (!isAwaked) {
            awake(window);
        }

        if (Mouse.isButtonPressed(Mouse.Button.LEFT) && !isInventoryOpened && !isSplashOpened && !isMultiplayerOpened) {
            var dx = Mouse.getPosition().x - window.getRender().getPosition().x;
            var dy = Mouse.getPosition().y - window.getRender().getPosition().y - 30;
            if (dx > 0 && dy > 0 && dx < window.getSize().width && dy < window.getSize().height) {
                //Place block
                placeBlock((dy / 10) * (window.getSize().width / 10) + (dx / 10));
            }
        }

        if (Mouse.isButtonPressed(Mouse.Button.RIGHT) && !isInventoryOpened && !isSplashOpened && !isMultiplayerOpened) {
            var dx = Mouse.getPosition().x - window.getRender().getPosition().x;
            var dy = Mouse.getPosition().y - window.getRender().getPosition().y - 30;
            if (dx > 0 && dy > 0 && dx < window.getSize().width && dy < window.getSize().height) {
                //Create destroy particles
                destroyBlockPS.stop();
                destroyBlockPS.setPosition(dx, dy);
                destroyBlockPSModel.setId(((Block) blocks.get((dy / 10) * (window.getSize().width / 10) + (dx / 10))).getId());
                destroyBlockPS.start();

                //Place air
                placeAir((dy / 10) * (window.getSize().width / 10) + (dx / 10));
            }
        }

        keyboardEvents();

        //Render sky
        sky.instantiate(window);

        //Render grid
        grid.setPosition(0, 0);
        grid.setBoxSize(window.getSize().width, window.getSize().height);
        grid.instantiate(window);

        //Render blocks and entities
        blocks.instantiate(window);
        entities.instantiate(window);

        //Render Inventory
        if (isInventoryOpened) {
            selectedId = inventory.getSelectedBlock();
            inventory.instantiate(window);
        }

        if (isSplashOpened) {
            splash.instantiate(window);
        }

        destroyBlockPS.instantiate(window);
        window.setFrameRate(120);
        window.setTitle("BlockGame - " + (int) window.getFramesPerSecond() + " fps");
    }

    @Override
    public void close() {
        System.exit(0);
    }

    private void loadHosts() {
        Path path = Paths.get("hosts.txt");
        try {
            byte[] bytes = Files.readAllBytes(path);
            List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for(String host : allLines){
                Global.flags.add(new FlagSettings("host_" + host.trim(),false,"Host " + host.trim()));
            }
        } catch (Exception e) {
            
        }
    }

    private void keyboardEvents() {
        //Scene functions
        if (Keyboard.isKeyPressed(Keyboard.Key.E) && cooldown <= 0 && !isSplashOpened && !isMultiplayerOpened) {
            isInventoryOpened = !isInventoryOpened;
            cooldown = COOLDOWN_TIME;
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.M) && cooldown <= 0 && !isSplashOpened && !isInventoryOpened) {
            isMultiplayerOpened = !isMultiplayerOpened;
            cooldown = COOLDOWN_TIME;
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.R) && cooldown <= 0) {
            isAwaked = false;
            cooldown = COOLDOWN_TIME;
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) {
            isInventoryOpened = false;
            isSplashOpened = false;
            cooldown = COOLDOWN_TIME;
        }

        if (cooldown > 0f) {
            cooldown -= 0.01f;
        }
    }

    public void setBlock(int pos, int id, boolean makeCooldown) {
        Block handle;
        Block duper;
        switch (id) {
            case 0 -> {
                handle = (Block) blocks.get(pos);
                if (handle.getId() != 0) {
                    entities.forEach(obj -> {
                        obj.<Rigibody>getComponent(Rigibody.class).getCollisionalObjectList().remove(handle);
                    });
                }
            }
            case 3, 5 -> { //Gravity blocks
                handle = new Block();
                if (cooldown <= 0 || !makeCooldown) {
                    duper = (Block) blocks.get(pos);
                    handle.setTexture(blockTilesTexture.getTexture());
                    handle.setPosition(duper.getPosition());
                    handle.addComponent(new Rigibody());
                    blocks.stream().filter(obj -> ((Block) obj).getId() != 0).forEach(handle.<Rigibody>getComponent(Rigibody.class).getCollisionalObjectList()::add);
                    entities.forEach(handle.<Rigibody>getComponent(Rigibody.class).getCollisionalObjectList()::add);
                    entities.add(handle);
                    if (makeCooldown) {
                        cooldown = COOLDOWN_TIME;
                    }
                }
            }
            default -> {
                handle = (Block) blocks.get(pos);
            }
        }
        if (handle != null) {
            handle.setId(id);
            handle.setVisible(true);
        }
    }

    private void placeBlock(int pos) {
        setBlock(pos, selectedId, true);
    }

    private void placeAir(int pos) {
        setBlock(pos, 0, true);
    }

    private void setWindowIcon(Window window) {
        if (isIconSetted == true) {
            return;
        }

        var img = new Image();
        try {
            img.loadFromFile(Paths.get("res/icon.png"));
            window.getRender().setIcon(img);
        } catch (IOException ex) {
        }

        isIconSetted = true;
    }
}
