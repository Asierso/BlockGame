/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asierso.blockgame.world;

import com.asierso.blockgame.gameobjects.Block;
import com.asierso.vortexengine.window.Layer;
import java.util.Random;

/**
 *
 * @author asier
 */
public class Generation {

    private final Random random;
    private int ystart = 0;
    private int width = 0;
    private int generationTry = 0;

    private enum StructureName {
        NONE, TREE, BIGGER_TREE, BIGGER_BUST
    };

    public Generation(int seed, int width, int ystart) {
        this.ystart = ystart;
        this.width = width;
        if (seed == 0) {
            random = new Random();
        } else {
            random = new Random(seed);
        }
    }

    public void generateWorld(Layer blocks) {
        try {
            int[] zdeep = new int[width];
            int height = 20;
            int[] varietyRanges = new int[10];
            for (int i = 0; i < 10; i++) {
                varietyRanges[i] = random.nextInt(random.nextInt(-1, 1), random.nextInt(1, 3));
            }
            for (int i = 0; i < width; i++) {
                zdeep[i] = height + random.nextInt(-1, 1);
                height += varietyRanges[random.nextInt(0, 10)];
                if (height < 5) {
                    height = 5;
                }
            }

            Block handle;
            //Chunks and structure generator
            boolean isSnowChunk = false;
            boolean isPlantChunk = false;
            StructureName generateStructureName = StructureName.NONE;

            int chunkBuffer = 0;
            for (int i = 0; i < width; i++) {
                //Chunk generation
                if (random.nextInt(0, 20) == 0) {
                    isSnowChunk = true;
                    chunkBuffer = random.nextInt(7, 19);
                }
                //Plant generation
                if (random.nextInt(0, 20) == 0) {
                    isPlantChunk = true;
                }
                if (random.nextInt(0, 15) == 0 && i < width - 5) {
                    generateStructureName = StructureName.BIGGER_BUST;
                }
                if (random.nextInt(0, 20) == 0 && i < width - 5) {
                    generateStructureName = StructureName.TREE;
                }
                if (random.nextInt(0, 100) == 0 && i < width - 10) {
                    generateStructureName = StructureName.BIGGER_TREE;
                }
                //Block placement generation
                for (int j = 0; j < zdeep[i] - 1; j++) {
                    if (ystart - (width * j) + i < 0) {
                        continue;
                    }
                    handle = (Block) blocks.get(ystart - (width * j) + i);
                    if (j == zdeep[i] - 2 && ((Block) blocks.get(ystart - (width * (j - 1)) + i)).getId() == 4) {
                        if (isSnowChunk) {
                            handle.setId(9);
                            chunkBuffer--;
                            isSnowChunk = (chunkBuffer != 0);
                        } else {
                            handle.setId(1);
                        }
                        if (j == zdeep[i] - 2 && isPlantChunk && ((Block) blocks.get(ystart - (width * (j - 1)) + i)).getId() == 4) {
                            ((Block) blocks.get(blocks.indexOf(handle) - width)).setId(21);
                            ((Block) blocks.get(blocks.indexOf(handle) - width)).setVisible(true);
                            isPlantChunk = false;
                        } else if (j == zdeep[i] - 2 && ((Block) blocks.get(ystart - (width * (j - 1)) + i)).getId() == 4) {
                            generateStructure(blocks.indexOf(handle) + width, blocks, generateStructureName);
                            generateStructureName = StructureName.NONE;
                        }

                    } else if (j > random.nextInt(5, 9)) {
                        handle.setId(4);
                    } else {
                        switch (random.nextInt(0, 50)) {
                            case 0 ->
                                handle.setId(30);
                            case 1 ->
                                handle.setId(32);
                            default ->
                                handle.setId(2);
                        }

                    }
                    handle.setVisible(true);
                }
            }
            generationTry = 0;
        } catch (Exception e) {
            generationTry++;
            if (generationTry < 3) {
                generateWorld(blocks);
            } else {
                e.printStackTrace();
            }
        }
    }

    private void generateStructure(int pos, Layer blocks, StructureName structure) {
        Structure str = null;
        switch (structure) {
            case TREE -> {
                str = new Structure(pos, width, new int[][]{
                    {0, 6, 0},
                    {6, 6, 6},
                    {0, 20, 0},
                    {0, 20, 0},
                    {0, 20, 0},
                    {0, 20, 0}});
            }
            case BIGGER_TREE -> {
                str = new Structure(pos, width, new int[][]{
                    {0, 6, 0},
                    {6, 6, 6},
                    {6, 20, 6},
                    {0, 20, 0},
                    {0, 20, 0},
                    {0, 20, 0},
                    {0, 20, 0},
                    {0, 20, 0}});
            }
            case BIGGER_BUST -> {
                str = new Structure(pos, width, new int[][]{
                    {0, 6, 0},
                    {0, 6, 21},
                    {6, 6, 6}});
            }
        }
        if (str != null) {
            str.addStructure(blocks);
        }
    }
}
