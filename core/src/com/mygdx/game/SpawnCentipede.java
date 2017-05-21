package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;

/**
 * Created by sretcher on 4/29/2017.
 */

public class SpawnCentipede {

    LinkedList<CentipedeHead> centipedeList = new LinkedList<CentipedeHead>();
    TextureRegion[] head, segment;

    //Constructor takes in the animation arrays needed for head/segment

    public SpawnCentipede(TextureRegion[] segment, TextureRegion[] head) {

        this.head = head;
        this.segment = segment;
    }

    //Adds a segment with the segment animation to the list

    public void fill() {
        centipedeList.add(new CentipedeHead(segment, new Vector2(19, 24), new Vector2(1, 1), centipedeList));
    }

    //Adds a segment with the head animation to the list

    public void fillHead() {
        centipedeList.add(new CentipedeHead(head, new Vector2(18, 24), new Vector2(1, 1), centipedeList));
    }


    public LinkedList<CentipedeHead> getCentipedeList() {
        return centipedeList;
    }

    public void LinkedList(LinkedList<CentipedeHead> centipedeList) {
        this.centipedeList = centipedeList;
    }
}


