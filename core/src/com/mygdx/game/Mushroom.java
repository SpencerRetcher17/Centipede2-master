package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by sretcher on 4/19/2017.
 */

public class Mushroom extends Sprite {

    //Image,size,position,heath of Mushroom

    Vector2 position, size;
    Texture tilesImage = new Texture(Gdx.files.internal("tile.png"));
    TextureRegion[][] splitTiles = TextureRegion.split(tilesImage, 8, 8);
    TextureRegion image;
    int heath;

    //Stores Mushrooms

    ArrayList<Mushroom> mushrooms = new ArrayList<Mushroom>();
    TiledMap map;

    //Constructor used only if placing mushrooms

    public Mushroom(TiledMap map) {
        this.map = map;
    }


    //Gives a mushroom an image,position,size,heath and takes in the player object

    public Mushroom(TextureRegion image, Vector2 position, Vector2 size, int heath) {
        super(new TextureRegion(image));
        this.image = image;
        setPosition(position.x, position.y);
        this.position = position;
        this.size = size;
        this.heath = heath;


    }

    public void spawnMushrooms(TiledMap map) {


        //Loop through all most of the cells in the map, randomly placing mushrooms and adding them to the list.
        //Place key "mushroom" in those cells

        for (int i = 0; i < 20; i++) {
            for (int j = 2; j < 19; j++) {
                TiledMapTileLayer cur = (TiledMapTileLayer) map.getLayers().get(2);
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                int R = (int) ((Math.random() * (10 - 0) + 0));
                if (cur.getCell(i, j) != null) {
                    if (R == 0) {
                        cell = cur.getCell(i, j);
                        cur.setCell(i * 8, j * 8, cell);

                        Mushroom cur4 = new Mushroom(splitTiles[0][8], new Vector2(i, j), new Vector2(1, 1), 3);

                        cell.setTile(new StaticTiledMapTile(splitTiles[0][8]));
                        cell.getTile().getProperties().put("mushroom", true);
                        mushrooms.add(cur4);
                    }

                }
            }
        }
    }


    public ArrayList<Mushroom> getMushrooms() {
        return mushrooms;
    }

    public void setMushrooms(ArrayList<Mushroom> mushrooms) {
        this.mushrooms = mushrooms;
    }

    public int getHeath() {
        return heath;
    }

    public void setHeath(int heath) {
        this.heath = heath;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }


    public TextureRegion[][] getSplitTiles() {
        return splitTiles;
    }

    public void setSplitTiles(TextureRegion[][] splitTiles) {
        this.splitTiles = splitTiles;
    }

    public TextureRegion getImage() {
        return image;
    }

    public void setImage(TextureRegion image) {
        this.image = image;
    }
}
