package WizardTD;

import processing.core.PImage;

public abstract class Board {

    protected int x;
    protected int y;
    protected PImage sprite;

    /**
     * Constructor for all layout element
     * @param sprite
     * @param x
     * @param y
     */
    public Board(PImage sprite, int x, int y){
        this.sprite = sprite;
        this.x = x;
        this.y = y;
    }

    /**
     * Draw all elements in the game by current frame.
     */
    public void draw(App app){
        app.image(sprite, x, y);
    }
}
