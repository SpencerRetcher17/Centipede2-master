package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import java.util.LinkedList;

/**
 * Created by sretcher on 4/25/2017.
 */
public class CentipedeHead extends Sprite {


    //Gamestates that control centipede direction/movement

    enum State {
        LEFT, RIGHT, DOWN
    }

    //Saves current and previous segment

    State currentState, previousState;

    //Speed, iamge, position, size, animation, and list of segments

    public float speed;
    public float stateTime;
    TextureRegion image;
    Vector2 position, size;
    Animation<TextureRegion> animation;
    TextureRegion currentFrame;
    TextureRegion[] frames, frames2;
    LinkedList<CentipedeHead> list;
    float delay = 0;


    //Gives segment an animation, position, size, and speed. Segment's starting direction is left and also takes the segemtn list.

    public CentipedeHead(TextureRegion[] frames, Vector2 position, Vector2 size, LinkedList<CentipedeHead> list) {
        super(new TextureRegion(frames[0]));
        this.frames = frames;
        this.list = list;
        setPosition(position.x, position.y);
        setSize(size.x, size.y);
        currentState = State.LEFT;
        previousState = State.LEFT;
        speed = 10f;

        animation = new Animation(1, frames);
        stateTime = 0f;
        currentFrame = animation.getKeyFrame(0);

    }

    public void update(TiledMap map) {


        //Remove segment if off screen
        
        for (int i = 0; i < list.size(); i++)
        {

            if(list.get(i).getY()<0)
            {

                list.remove(i);

            }


        }
        
        
        
        
        float delta = Gdx.graphics.getDeltaTime();

        //Controls which frame to display

        stateTime += Gdx.graphics.getDeltaTime() * 15;


        //First eight elements in animation array face the left, last eight face right. Resets the animation.

        if (stateTime > 7) {
            stateTime = 0;
        }


        if (currentState == State.LEFT) {


            //Makes the segment move left

            setPosition(getX() - speed * delta, getY());


            currentFrame = animation.getKeyFrame(stateTime);

            //If it hits the left edge, go down

            if (getX() < 0) {
                previousState = currentState;
                currentState = State.DOWN;
            }
        }

        if (currentState == State.RIGHT) {

            //Moves centipede to the right

            setPosition(getX() + speed * delta, getY());

            //Statetime + 8 because last eight face the right

            currentFrame = animation.getKeyFrame(stateTime + 8);


            //If segment hits right edge, go down

            if (getX() > 19) {
                previousState = currentState;
                currentState = State.DOWN;
            }
        }


        if (currentState == State.DOWN) {

            //Segment goes down one row

            setPosition(getX(), getY() - 1);

            //If statement that decides new direction based on previousState

            currentState = previousState == State.LEFT ? State.RIGHT : State.LEFT;
            delay = 0;
        }


        TiledMapTileLayer cur = (TiledMapTileLayer) map.getLayers().get(2);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();


        //Set state to down if the segments hit a cell with key "mushroom"

        if (cur.getCell(Math.round(getX()), Math.round(getY())) != null) {

            if (cur.getCell(Math.round(getX()), Math.round(getY())).getTile().getProperties().containsKey("mushroom")) {

                if (cur.getCell(Math.round(getX()), Math.round(getY() - 1)).getTile().getProperties().containsKey("mushroom")) {
                    previousState = currentState;
                    currentState = State.DOWN;
                } else {
                    previousState = currentState;
                    currentState = State.DOWN;
                }


            }
        }
    }


    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(TextureRegion currentFrame) {
        this.currentFrame = currentFrame;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }


    public State getPreviousState() {
        return previousState;
    }

    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
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
