package WizardTD;

import processing.core.PImage;

import java.util.ArrayList;

public class Monster {

    private PImage type;
    private double hp;
    private double speed;
    private double armour;
    private int manaGainedOnkill;
    private ArrayList<Point> path;
    private double accumulatedPause;
    private int count;
    private int currentX, currentY;
    private int targetX, targetY;
    private String moveDirection;
    private boolean isFired;
    private double fullHp;
    private boolean isDie;
    private App app;
    private PImage[] deathAnimationImages = new PImage[6];
    private int deathAnimationFrame = 0;
    private double originalSpeed;
    private double freezeTime;
    private boolean isFrozen;


    /**
     * Constructor without parameters used by testing.
     */
    public Monster(){}

    /**
     * Constructor of Monster.
     * @param type
     * @param hp
     * @param speed
     * @param armour
     * @param manaGainedOnkill
     * @param path
     * @param accumulatedPause
     * @param app
     */
    public Monster(PImage type, double hp, double speed, double armour, int manaGainedOnkill, ArrayList<Point> path, double accumulatedPause, App app){
        this.type = type;
        this.hp = hp;
        this.speed = speed;
        this.armour = armour;
        this.manaGainedOnkill = manaGainedOnkill;
        this.path = path;
        this.accumulatedPause = accumulatedPause;
        this.count = path.size() - 1;
        Point start = path.get(count);
        this.currentX = start.getCol() * 32;
        this.currentY = start.getRow() * 32 + 40;
        updateTarget();
        this.fullHp = hp;
        this.isFired = false;
        this.isDie = false;
        this.app = app;
        deathAnimationImages[0] = app.loadImage("src/main/resources/WizardTD/gremlin.png");
        deathAnimationImages[1] = app.loadImage("src/main/resources/WizardTD/gremlin1.png");
        deathAnimationImages[2] = app.loadImage("src/main/resources/WizardTD/gremlin2.png");
        deathAnimationImages[3] = app.loadImage("src/main/resources/WizardTD/gremlin3.png");
        deathAnimationImages[4] = app.loadImage("src/main/resources/WizardTD/gremlin4.png");
        deathAnimationImages[5] = app.loadImage("src/main/resources/WizardTD/gremlin5.png");
        this.originalSpeed = speed;
        this.freezeTime = 0;
        this.isFrozen = false;
    }

    public void setIsFired(boolean isFired){
        this.isFired = isFired;
    }

    public boolean getIsFired(){
        return isFired;
    }

    public int getX(){
        return this.currentX;
    }
    public int getY(){
        return this.currentY;
    }
    public double getHp(){
        return this.hp;
    }

    /**
     * Gain the responding mana when kill each monster and there is a liminted largest mana value.
     * @param hp
     */
    public void setHp(double hp){
        this.hp = hp;

        if (this.hp <= 0 && !this.isDie) {
            this.hp = 0;
            this.isDie = true;
            int currentMana = app.getMana();
            app.setMana(currentMana + this.manaGainedOnkill * (int)app.getManaGainedMultiplier());
            if(app.getMana() >= app.getManaCap()){
                app.setMana(app.getManaCap());
            }
        }
    }

    public boolean getIsDie(){
        return this.isDie;
    }
    public PImage[] getDeathAnimationImages(){
        return deathAnimationImages;
    }
    public int getDeathAnimationFrame(){
        return deathAnimationFrame;
    }

    /**
     * Check if the fireball collided with the monster.
     * @param fireball
     * @return
     */
    public boolean collide(Fireball fireball){
        double distance = Math.sqrt((currentX - fireball.getX()) * (currentX - fireball.getX()) + (currentY - fireball.getY()) * (currentY - fireball.getY()));
        double touchDist = 1;
        return distance <= touchDist;
    }

    /**
     * The monster can be frozen by the fireball of frozenTower.
     * @param freezeTime
     */
    public void freeze(double freezeTime){
        if(!isFrozen){
            this.speed = 0;
            isFrozen = true;
        }
        this.freezeTime = freezeTime;
    }

    /**
     * Determine which direction the monster will go along the path.
     */
    private void updateTarget(){
        if(count > 0){
            Point target = path.get(count - 1);
            targetX = target.getCol() * 32;
            targetY = target.getRow() * 32 + 40;

            if(targetY < currentY){
                moveDirection = "UP";
            }else if(targetY > currentY){
                moveDirection = "DOWN";
            }else if(targetX < currentX){
                moveDirection = "LEFT";
            }else{
                moveDirection = "RIGHT";
            }
        }
    }

    /**
     * Check if this monster are frozen and the state of this game.
     * Draw the monster of each wave.
     * Control how long the monster stays frozen.
     * If the monster is alive, draw it along the path.
     * Display the health bar of each monster.
     * Draw the death animation for monsters with each image lasting 4 frames.
     * If an enemy reaches the wizard’s house, it is banished.
     * And banished monsters respawn correctly and cause a deduction of mana equal to their remaining hp.
     * @param app
     * @param countFrame
     */
    public void draw(App app, int countFrame){
       if(countFrame >= accumulatedPause * 60) {
            if(count >= 0) {

                if (!isFrozen) {
                    if (app.getIsPpressed()) {
                        app.setState(GameState.PAUSED);
                    } else if (app.getIsFFpressed()) {
                        app.setState(GameState.DOUBLE_SPEED);
                    } else {
                        app.setState(GameState.PLAYING);
                    }

                    switch (app.getState()) {
                        case PAUSED:
                            this.speed = 0;
                            break;
                        case DOUBLE_SPEED:
                            this.speed = originalSpeed * 2;
                            break;
                        default:
                            this.speed = originalSpeed;
                            break;
                    }
                }

                if(isFrozen && freezeTime > 0){
                    freezeTime --;
                    if(freezeTime <= 0){
                        this.speed = originalSpeed;
                        isFrozen = false;
                    }
                }

                if(!isDie) {
                    app.image(type, (float) currentX, (float) currentY);
                }
                switch(moveDirection){
                    case "UP":
                        currentY -= speed;
                        break;
                    case "DOWN":
                        currentY += speed;
                        break;
                    case "LEFT":
                        currentX -= speed;
                        break;
                    case "RIGHT":
                        currentX += speed;
                        break;
                }

                if((moveDirection.equals("UP") && currentY <= targetY) || (moveDirection.equals("DOWN") && currentY >= targetY) ||
                        (moveDirection.equals("LEFT") && currentX <= targetX) ||
                        (moveDirection.equals("RIGHT") && currentX >= targetX)) {
                    currentX = targetX;
                    currentY = targetY;
                    count -= 1;
                    updateTarget();
                }

                int totalWidth = 30;
                int w1 = (int)(((float)hp) / fullHp * totalWidth);
                int w2 = totalWidth - w1;
                app.fill(0, 255, 0);
                app.stroke(0, 255, 0);
                app.rect(currentX-5, currentY-10, w1, 5);

                if(hp != fullHp){
                    app.fill(255, 0 , 0);
                    app.stroke(255, 0, 0);
                    app.rect(currentX + w1 - 5, currentY-10, w2, 5);
                }

                if(this.isDie){
                    if(deathAnimationFrame < deathAnimationImages.length * 4){
                        app.image(deathAnimationImages[deathAnimationFrame / 4], (float)currentX, (float)currentY);
                        deathAnimationFrame++;
                    }else{
                        resetDeathAnimation();
                    }
                }

                if(count == -1 && !isDie){
                    app.setMana(app.getMana() - (int) this.hp);
                    respawn();
                }
            }
        }
    }

    /**
     * Respawn the banished monsters with hp > 0;
     */
    private void respawn() {
        this.count = path.size() - 1;
        Point start = path.get(count);
        this.currentX = start.getCol() * 32;
        this.currentY = start.getRow() * 32 + 40;
        this.isDie = false;
        updateTarget();
    }

    /**
     * Initialize the death animation after the animation of last monster has been finished.
     */
    public void resetDeathAnimation() {
        deathAnimationFrame = 0;
    }

    public double getArmour() {
        return this.armour;
    }
}
