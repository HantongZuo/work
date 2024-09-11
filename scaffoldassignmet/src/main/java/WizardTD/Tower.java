package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Random;

public class Tower{

    private int range;
    private double fireSpeed;
    private int damage;
    private int x;
    private int y;
    private PImage towerSprite;
    private boolean isHover;
    private ArrayList<Fireball> fireballs;
    private int rangeLevel = 0;
    private int speedLevel = 0;
    private int damageLevel = 0;
    private static final int UPGRADE_COST_INCREMENT = 10;
    private static final int BASE_UPGRADE_COST = 20;
    private int speed;
    private int damageUp;
    private double freezeTime;
    private boolean isFreezeTower;
    private boolean hasBeenFrozen;
    private int fireCounter;


    /**
     * Constructor of Tower
     * @param range
     * @param fireSpeed
     * @param damage
     * @param x
     * @param y
     * @param towerSprite
     * @param freezeTime
     */
    public Tower(int range, double fireSpeed, int damage, int x, int y, PImage towerSprite, double freezeTime){
        this.range = range;
        this.fireSpeed =  fireSpeed;
        this.damage = damage;
        this.x = x;
        this.y = y;
        this.towerSprite = towerSprite;
        this.isHover = false;
        this.fireballs = new ArrayList<>();
        this.damageUp = damage / 2;
        this.speed = (int) (60 / fireSpeed);
        this.freezeTime = freezeTime;
        this.isFreezeTower = false;
        this.hasBeenFrozen = false;
        this.fireCounter = 0;
    }

    /**
     * Used for upgrading the range of the tower.
     */
    public void upgradeRange(){
            rangeLevel++;
            range += 32;
    }

    /**
     * Used for upgrading the speed of the tower.
     */
    public void upgradeSpeed(){
            speedLevel++;
            fireSpeed = fireSpeed + 0.5;
            speed = (int)(60 / fireSpeed);
    }

    /**
     * Used for upgrading the damages of the tower.
     */
    public void upgradeDamage(){
            damageLevel++;
            damage += damageUp;
    }

    public boolean getHasBeenFrozen(){
        return this.hasBeenFrozen;
    }
    public void setHasBeenFrozen(boolean hasBeenFrozen){
        this.hasBeenFrozen = hasBeenFrozen;
    }

    public int getRangeLevel(){
        return this.rangeLevel;
    }
    public int getSpeedLevel(){
        return this.speedLevel;
    }
    public int getDamageLevel(){
        return this.damageLevel;
    }

    public void setIsFreezeTower(boolean isFreezeTower){
        this.isFreezeTower = isFreezeTower;
    }

    /**
     * Calculate the cost of upgrade each time.
     * @param level
     * @return
     */
    public int upgradeCost(int level){
        int upgradeCost = BASE_UPGRADE_COST + (level - 1) * UPGRADE_COST_INCREMENT;
        return upgradeCost;
    }

    /**
     * Draw elements related to the tower.
     * Highlight the tower range radius by hovering over it and display the tooltips of upgrading a tower.
     * Tower image changed to orange and red when all of level 1 or level 2 upgrades are done.
     * Show the indicators for different individual upgrades(range, speed, damage).
     * Show the indicator of frozen tower.
     *  Fireballs are generated at regular intervals.
     *  Erase the fireball which finishes its job, either collides a monster or the monster died before it arrives.
     * @param app
     * @param fireSprite
     */
    public void draw(App app, PImage fireSprite){
        if(this.isHover){
            app.stroke(255, 255, 0);
            app.noFill();
            app.ellipse(x + 16, y + 16, 2 * range, 2 * range);

            int costRange = this.upgradeCost(this.getRangeLevel() + 1);
            int costSpeed = this.upgradeCost(this.getSpeedLevel() + 1);
            int costDamage = this.upgradeCost(this.getDamageLevel() + 1);
            boolean isU1pressed = app.getIsU1pressed();
            boolean isU2pressed = app.getIsU2pressed();
            boolean isU3pressed = app.getIsU3pressed();
            int costTotal = 0;

            if(isU1pressed || isU2pressed || isU3pressed){
            app.stroke(0);
            app.fill(255);
            app.rect(642, 565, 105, 20);

            app.stroke(0);
            app.fill(255);
            app.rect(642, 585, 105, 60);

            app.stroke(0);
            app.fill(255);
            app.rect(642, 645, 105, 20);
            }

            if(isU1pressed){
                app.textSize(15);
                app.fill(0);
                app.text(String.format("range: %d", costRange), 645, 600);
                costTotal += costRange;
            }
            if(isU2pressed){
                app.textSize(15);
                app.fill(0);
                app.text(String.format("speed: %d", costSpeed), 645, 620);
                costTotal += costSpeed;
            }
            if(isU3pressed){
                app.textSize(15);
                app.fill(0);
                app.text(String.format("damage: %d", costDamage), 645, 640);
                costTotal += costDamage;
            }

            if(isU1pressed || isU2pressed || isU3pressed) {

                app.textSize(15);
                app.fill(0);
                app.text("Upgrade cost", 645, 580);

                app.textSize(15);
                app.fill(0);
                app.text(String.format("Total: %d", costTotal), 645, 660);
            }
        }

        int minLevel = Math.min(Math.min(rangeLevel, speedLevel), damageLevel);

        if(minLevel == 1){
            this.towerSprite = app.loadImage("src/main/resources/WizardTD/tower1.png");
        }
        if(minLevel == 2){
            this.towerSprite = app.loadImage("src/main/resources/WizardTD/tower2.png");
        }
        app.image(towerSprite, x, y);

        if(rangeLevel > minLevel) {
            app.fill(255, 0, 255);
            app.textSize(10);
            for (int i = 0; i < (rangeLevel - minLevel); i++) {
                app.text('O', x + i * 8, y + 8);
            }
        }

        if(speedLevel > minLevel) {
            app.stroke(135, 206, 235);
            app.strokeWeight((speedLevel - minLevel) * 2);
            app.noFill();
            app.rect(x + 7, y + 7, 18, 18);
            app.strokeWeight(1);
        }

        if(damageLevel > minLevel) {
            app.fill(255, 0, 255);
            app.textSize(10);
            for (int i = 0; i < damageLevel - minLevel; i++) {
                app.text('X', x + i * 5, y + 32);
            }
        }

        if(isFreezeTower) {
            app.stroke(255, 250, 250);
            app.fill(255, 250, 250);
            app.ellipse(x + 16, y + 16, 10, 10);
        }

        if(!app.getIsPpressed()) {
            fireCounter += app.getIsFFpressed() ? 2 : 1;
            if (fireCounter >= speed) {
                Monster target = findTarget(app);
                if (target != null) {
                    Fireball f = new Fireball(x + 16, y + 16, fireSprite, damage, target, freezeTime, isFreezeTower);
                    fireballs.add(f);
                    target.setIsFired(true);
                }
                fireCounter = 0;
            }
            for (Fireball f : fireballs) {
                f.draw(app);
            }
        }

        for(int i = fireballs.size() - 1; i >= 0; i-- ){
            if(fireballs.get(i).getDisAppear()){
                fireballs.remove(i);
            }
        }
    }

    /**
     * Find the monster that meets the criteria to be the target of this tower.
     * @param app
     * @return
     */
    private Monster findTarget(App app){
        // loop all monsters
        ArrayList<Monster> potentialTargets = new ArrayList<>();
        ArrayList<Wave> waves = app.waves;
        for(Wave w : waves){
            for(Monster m : w.getMonsters()){
                if(!m.getIsFired() && inRange(m)){  // inRange dist(m, tower) <= range
                    potentialTargets.add(m);
                }
            }
        }
        if(!potentialTargets.isEmpty()){
            Random random = new Random();
            int i = random.nextInt(potentialTargets.size());
            return potentialTargets.get(i);
        }
        return null;
    }

    /**
     * Check if the monster is in the range of this tower.
     * @param m
     * @return
     */
    private boolean inRange(Monster m){
        double dist =  Math.sqrt((m.getX() - x) * (m.getX() - x) + (m.getY() - y) * (m.getY() - y));
        if(dist <= range){
            return true;
        }
        return false;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setIsHover(boolean isHover){
        this.isHover = isHover;
    }

    public boolean getIsHover() {
        return isHover;
    }

    public void setRangeLevel(int rangeLevel) {
        this.rangeLevel = rangeLevel;
    }

    public void setDamageLevel(int damageLevel) {
        this.damageLevel = damageLevel;
    }

    public void setSpeedLevel(int speedLevel) {
        this.speedLevel = speedLevel;
    }
}
