package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by sretcher on 4/21/2017.
 */

public class PlayScreen implements Screen {

    //Used for drawing to the screen

    SpriteBatch batch;

    Game game;

    //Objects used to create the map, also references to cell

    OrthographicCamera cam;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    TiledMapTileLayer cur;
    TiledMapTileLayer.Cell cell;


    //Images that are split so that they are 8x8 pixels

    Texture tilesImage = new Texture(Gdx.files.internal("tile.png"));
    TextureRegion[][] splitTiles = TextureRegion.split(tilesImage, 8, 8);
    Texture tilesImage2 = new Texture(Gdx.files.internal("tile.png"));
    TextureRegion[][] splitTiles2 = TextureRegion.split(tilesImage2, 8, 8);


    //Player object

    Ship pilot;

    //Used to spawn Mushrooms

    Mushroom cur2;

    //Animation arrays

    TextureRegion[] frames, frames1;

    //Spawns Centipede

    SpawnCentipede centipede;

    //Delay for spawning, isSpawned checks if all segments are on the map, userInterface tracks score

    float delay = 0;
    boolean isSpawned;
    Ui userInterface;


    //Initializes game object, necessary when changing screens

    public PlayScreen(Game game) {
        this.game = game;
    }


    @Override
    public void show() {

        userInterface = new Ui(game);
        batch = new SpriteBatch();
        cur2 = new Mushroom(map);
        frames = new TextureRegion[16];
        frames1 = new TextureRegion[16];


        //Puts images into animation array-probably could make this more efficient

        for (int i = 0; i < 4; i++) {
            frames[i] = splitTiles[2][i];
            frames[i + 4] = splitTiles[3][i];

        }

        for (int i = 8; i < 12; i++) {

            frames[i] = splitTiles2[2][i - 8];
            frames[i].flip(true, false);
            frames[i + 4] = splitTiles2[3][i - 8];
            frames[i + 4].flip(true, false);


        }
        for (int i = 0; i < 4; i++) {

            frames1[i] = splitTiles[0][i];
            frames1[i + 4] = splitTiles[1][i];
        }

        for (int i = 8; i < 12; i++) {

            frames1[i] = splitTiles2[0][i - 8];
            frames1[i].flip(true, false);
            frames1[i + 4] = splitTiles2[1][i - 8];
            frames1[i + 4].flip(true, false);

        }


        //Passes animation arrays into centipede

        centipede = new SpawnCentipede(frames, frames1);

        //Ship will be at cell 5,0 8x8 pixels, takes in segemnt list from

        pilot = new Ship(cur2.getMushrooms(), centipede.getCentipedeList(), splitTiles[10][0], new Vector2(5, 0), new Vector2(1, 1), userInterface);

        //Camera is set for screen and for 20 columns and 25 rows
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.setToOrtho(false, 20, 25);

        //Loads the map, makes every eight pixels equal one unit

        map = new TmxMapLoader().load("centipedeMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / 8f);
        cur = (TiledMapTileLayer) map.getLayers().get(2);
        cell = new TiledMapTileLayer.Cell();


        //Spawns Mushrooms

        cur2.spawnMushrooms(map);

    }


    //Constantly called to update the screen

    @Override
    public void render(float delta) {


        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(cam);
        renderer.render();

        //Updates the score/stage

        userInterface.action();
        userInterface.updateScore();

        //Draw object according to camera-every eight pixel is a cell
        //Updates camera
        batch.setProjectionMatrix(cam.combined);
        cam.update();


        //Spawns a segment every 0.1 segments-stops after list has 12 elements

        delay += Gdx.graphics.getDeltaTime();

        if (delay >= 0.1) {

            if (isSpawned == false) {


                if (centipede.getCentipedeList().size() == 12) {
                    isSpawned = true;
                } else {

                    centipede.fill();
                    delay = 0;

                }
            }


        }

        //Starts the drawing

        batch.begin();


        //Loop through segments, draw them according to their given arguments

        for (int i = 0; i < centipede.getCentipedeList().size(); i++) {


            batch.draw(centipede.getCentipedeList().get(i).getCurrentFrame(), centipede.getCentipedeList().get(i).getX(), centipede.getCentipedeList().get(i).getY(), centipede.getCentipedeList().get(i).getWidth(), centipede.getCentipedeList().get(i).getHeight());
        }

        //Draw the ship based on its arguments

        batch.draw(pilot, pilot.getX(), pilot.getY(), pilot.getWidth(), pilot.getHeight());


        //Loop through bullets, draw them according to their given arguments

        for (int i = 0; i < pilot.getBulletList().size(); i++) {
            batch.draw(pilot.getBulletList().get(i), pilot.getBulletList().get(i).getX(), pilot.getBulletList().get(i).getY(), pilot.getBulletList().get(i).getWidth(), pilot.getBulletList().get(i).getHeight());

        }


        //Ends drawing

        batch.end();


        //Updates the ship

        pilot.update(map, pilot);


        //Loop through bullets and update their position


        for (int i = 0; i < pilot.getBulletList().size(); i++) {
            pilot.getBulletList().get(i).update(map);

        }


        //Loop through segments and update their position


        for (int i = 0; i < centipede.getCentipedeList().size(); i++) {


            centipede.getCentipedeList().get(i).update(map);

        }


        if (centipede.getCentipedeList().size() == 0) {

            isSpawned=false;
            delay += Gdx.graphics.getDeltaTime();

            if (delay >= 0.1) {

                if (isSpawned == false) {


                    if (centipede.getCentipedeList().size() == 12) {
                        isSpawned = true;
                    } else {

                        centipede.fill();
                        delay = 0;

                    }
                }


            }
        }


        //Draw the UI

        if(userInterface.getLives()==0)
        {

            game.setScreen(new MainMenu(game));


        }


        userInterface.draw();

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

