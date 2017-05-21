package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by sretcher on 4/21/2017.
 */

public class TablesScreen implements Screen {

    Game game;

    //Screen has is made of a table and buybutton

    Stage stage;
    Table table;
    SpriteBatch batch;
    TextButton buyButton;


  //Initializes game object,necessary for passing screens

    public TablesScreen(Game game)
    {
        this.game=game;
    }





    @Override
    public void show() {
        stage=new Stage();
        batch=new SpriteBatch();
        table=new Table();
        table.setBounds(Gdx.graphics.getWidth()/48,0,Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/24),Gdx.graphics.getHeight());

        table.debug();

        buyButton=new TextButton("Buy?", MainMenu.buttonStyle);
        buyButton.setSize(100,50);

        table.add(new Label("Title", MainMenu.style)).colspan(3);
        table.row();
        table.add(new Label("Item", MainMenu.style)).expandX();
        table.add(new Label("Price", MainMenu.style)).expandX();
        table.add(new Label("Buy", MainMenu.style)).expandX();
        table.row();
        table.add(new Label("Sword", MainMenu.style));
        table.add(new Label("50 bucks", MainMenu.style));
        table.add(buyButton).size(100,50);
        stage.addActor(table);



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(stage);
        stage.act();
        batch.begin();
        stage.draw();
        stage.setDebugAll(true);
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

    @Override
    public void dispose() {

    }
}
