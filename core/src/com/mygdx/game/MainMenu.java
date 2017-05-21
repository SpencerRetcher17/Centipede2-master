package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.steer.behaviors.Alignment;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

/**
 * Created by sretcher on 4/21/2017.
 */

public class MainMenu implements Screen {


    //Sets up the menu with a label and two buttons

    Stage stage;
    Label label;
    public static Label.LabelStyle style;
    BitmapFont font;
    TextureAtlas buttonAtlas;
    public static TextButton.TextButtonStyle buttonStyle;
    // TextButton button, tableButton;
    Label Play, HighScore, Credits;
    Skin skin;
    Image title;

    //Batch is used for drawing

    SpriteBatch batch;
    Game game;

    //Takes in game object, necessary for switching screens

    public MainMenu(Game game) {
        this.game = game;
    }

    public MainMenu() {
    }

    @Override
    public void show() {

        //Menu are created by adding components(actors) to a stage. The stage takes
        //up the whole screen. Before adding the actors, we first set their text,position,image etc
        //Example-we set the font and style for a label, then add the label to the stage

        stage = new Stage();


        title = new Image(new Texture(Gdx.files.internal("title.jpg")));
        title.setPosition((Gdx.graphics.getWidth() / 2) - (title.getWidth() / 2), Gdx.graphics.getHeight() - title.getHeight()-100);
        title.setAlign(Align.center);
        font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        style = new Label.LabelStyle(font, com.badlogic.gdx.graphics.Color.RED);

        stage.addActor(title);

        Play = new Label("Play", style);
        Play.setFontScale(2);
        Play.setPosition(Gdx.graphics.getWidth() / 2 - Play.getWidth(), title.getY() - 130);
        Play.setSize(100, 100);
        Play.setAlignment(Align.center);
        stage.addActor(Play);

        HighScore = new Label("High Scores", style);
        HighScore.setFontScale(2);
        HighScore.setPosition(Gdx.graphics.getWidth() / 2 - HighScore.getWidth() / 3, Play.getY() - 90);
        HighScore.setSize(100, 100);
        HighScore.setAlignment(Align.center);
        stage.addActor(HighScore);


        Credits = new Label("Credits", style);
        Credits.setFontScale(2);
        Credits.setPosition(Gdx.graphics.getWidth() / 2 - Credits.getWidth() / 2 - 10, HighScore.getY() - 90);
        Credits.setSize(100, 100);
        Credits.setAlignment(Align.center);
        stage.addActor(Credits);


        Gdx.input.setInputProcessor(stage);

        Play.addListener(new InputListener() {


            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.log("Clicked", "Button");
                game.setScreen(new PlayScreen(game));
                return super.touchDown(event, x, y, pointer, button);
            }
        });


        //Object used to draw

        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {


        //Sets the background color, draws/updates the screen

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        batch.begin();
        stage.draw();
        batch.end();


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


    //Makes sure input doesn't transfer to other screen

    @Override
    public void dispose() {
        //  Gdx.input.setInputProcessor(null);

    }
}
