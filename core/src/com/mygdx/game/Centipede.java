package com.mygdx.game;
import com.badlogic.gdx.Game;


//Credit given to Hello World Tutorial, dermetfan, and LibGDX's Github page
//Credit given to Stack Overflow users Sagar Balyan and  Abhishek Aryan



public class Centipede extends Game {


	//Object that allows us to switch screens, but must be passed into contructors

	Game game;

	@Override
	public void create() {

		//Initializes the game object, switches screen to MainMenu

		game=this;
		setScreen(new MainMenu(game));

	}


	//Renders screen

	@Override
	public void render() {

		super.render();


	}


	@Override
	public void dispose() {


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


}
