package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by sretcher on 4/25/2017.
 */
public class Bullet extends Sprite {


    //Speed,Size,and Position of Bullet

    public static ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
    public float speed;
    Vector2 position, size;
    float delta;

    //Player

    Ship player;

    //Images

    Texture tilesImage = new Texture(Gdx.files.internal("tile.png"));
    TextureRegion[][] splitTiles = TextureRegion.split(tilesImage, 8, 8);
    TextureRegion image;

    //Holds segments

    LinkedList<CentipedeHead> list;

    //Hit sound

    Sound hit = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));

    //Handles Score

    Ui userInterface;


    //Gives a bullet an image, a size, a speed of 30, and position the Bullet above the player.
    //Takes in list of segments and the userInterface

    public Bullet(TextureRegion image1, LinkedList<CentipedeHead> list, Ship player, Vector2 size, Ui userInterface) {
        super(new TextureRegion(image1));
        setX(player.getX());
        setY(player.getY() + 1);
        setSize(size.x, size.y);
        speed = 30f;
        this.player = player;
        image1 = new TextureRegion();
        this.list = list;
        this.userInterface = userInterface;
    }


    //Called constantly to update the bullet's position

    public void update(TiledMap map) {

        //Bullet is constantly traveling up

        delta = Gdx.graphics.getDeltaTime();
        setPosition(getX(), getY() + speed * delta);


        //Loop through bullets, if it has passed the top of the screen, remove it.
        //getY()>24 because that is the max amount of rows

        if (getY() > 24) {
            for (int i = 0; i < player.getBulletList().size(); i++) {
                if (player.getBulletList().get(i).getY() > 24) {
                    player.getBulletList().remove(i);
                }
            }
        }


        TiledMapTileLayer cur = (TiledMapTileLayer) map.getLayers().get(2);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();


        //Loop through bullets, if bullet position is in a cell with a key "mushroom", remove the bullet
        //and change the mushroom image based on the heath value of the reference mushroom

        for (int i = 0; i < player.getBulletList().size(); i++) {

            if (cur.getCell(Math.round(player.getBulletList().get(i).getX()), Math.round(player.getBulletList().get(i).getY())).getTile().getProperties().containsKey("mushroom") == true) {
                {

                    //Saves the bullet's last position and remove it

                    Vector2 position = new Vector2((Math.round(player.getBulletList().get(i).getX())), Math.round(player.getBulletList().get(i).getY()));
                    player.getBulletList().remove(i);


                    //Find the mushroom that the bullet hit and change its image based on heath.
                    //Also adds to score if heath=0

                    for (int index = 0; index < player.getMushroomsList().size(); index++) {
                        if (position.equals(player.getMushroomsList().get(index).getPosition())) {

                            if (player.getMushroomsList().get(index).getHeath() == 3) {

                                cell = cur.getCell(Math.round(player.getMushroomsList().get(index).getX()), Math.round(player.getMushroomsList().get(index).getY()));
                                cur.setCell(Math.round(player.getMushroomsList().get(index).getX()) * 8, Math.round(player.getMushroomsList().get(index).getY()) * 8, cell);

                                cell.setTile(new StaticTiledMapTile(splitTiles[0][9]));
                                cell.getTile().getProperties().put("mushroom", true);

                                player.getMushroomsList().get(index).setHeath(player.getMushroomsList().get(index).getHeath() - 1);
                            } else if (player.getMushroomsList().get(index).getHeath() == 2) {

                                cell = cur.getCell(Math.round(player.getMushroomsList().get(index).getX()), Math.round(player.getMushroomsList().get(index).getY()));
                                cur.setCell(Math.round(player.getMushroomsList().get(index).getX()) * 8, Math.round(player.getMushroomsList().get(index).getY()) * 8, cell);

                                cell.setTile(new StaticTiledMapTile(splitTiles[0][10]));
                                cell.getTile().getProperties().put("mushroom", true);
                                player.getMushroomsList().get(index).setHeath(player.getMushroomsList().get(index).getHeath() - 1);

                            } else if (player.getMushroomsList().get(index).getHeath() == 1) {

                                cell = cur.getCell(Math.round(player.getMushroomsList().get(index).getX()), Math.round(player.getMushroomsList().get(index).getY()));
                                cur.setCell(Math.round(player.getMushroomsList().get(index).getX()) * 8, Math.round(player.getMushroomsList().get(index).getY()) * 8, cell);

                                cell.setTile(new StaticTiledMapTile(splitTiles[0][11]));
                                cell.getTile().getProperties().put("mushroom", true);
                                player.getMushroomsList().get(index).setHeath(player.getMushroomsList().get(index).getHeath() - 1);

                            } else if (player.getMushroomsList().get(index).getHeath() == 0) {

                                cell = cur.getCell(Math.round(player.getMushroomsList().get(index).getX()), Math.round(player.getMushroomsList().get(index).getY()));
                                cur.setCell(Math.round(player.getMushroomsList().get(index).getX()) * 8, Math.round(player.getMushroomsList().get(index).getY()) * 8, cell);

                                cell.setTile(new StaticTiledMapTile(splitTiles[2][11]));
                                cell.getTile().getProperties().remove("mushroom");
                                userInterface.setScore(userInterface.getScore() + 1);

                            }


                        }

                    }


                }

            }
        }


        //If bullet overlaps any segment

        for (int i = 0; i < player.getBulletList().size(); i++) {

            for (int j = 0; j < list.size(); j++) {

                if (player.getBulletList().get(i).getBoundingRectangle().overlaps(list.get(j).getBoundingRectangle())) {

                    //Play a sound

                    hit.play();


                    //Adds a mushroom at the segement's position, removes the segment, add points to the score

                    cell = cur.getCell(Math.round(list.get(j).getX()), Math.round(list.get(j).getY()));
                    cur.setCell(Math.round(list.get(j).getX()) * 8, Math.round(list.get(j).getY() * 8), cell);

                    Mushroom cur4 = new Mushroom(splitTiles[0][8],new Vector2(Math.round(list.get(j).getX()), Math.round(list.get(j).getY())), new Vector2(1, 1), 3);

                    player.getMushroomsList().add(cur4);

                    cell.setTile(new StaticTiledMapTile(splitTiles[0][8]));
                    cell.getTile().getProperties().put("mushroom", true);
                    list.remove(j);
                    userInterface.setScore(userInterface.getScore() + 50);


                }


            }
        }
    }


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public ArrayList<Bullet> getBulletList() {
        return bulletList;
    }

    public void setBulletList(ArrayList<Bullet> bulletList) {
        this.bulletList = bulletList;
    }


    public TextureRegion getImage() {
        return image;
    }

    public void setImage(TextureRegion image) {
        this.image = image;
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


}
