package WizardTD;

import processing.core.PImage;

public class Fireball {

    private int x;
    private int y;
    private PImage fireSprite;
    private int damage;
    private Monster target;
    private boolean disAppear;

    private double freezeTime;
    private boolean isFreezeTower;

    /**
     * Construtor of Fireball.
     * @param x
     * @param y
     * @param fireSprite
     * @param damage
     * @param target
     * @param freezeTime
     * @param isFreezeTower
     */
    public Fireball(int x, int y, PImage fireSprite, int damage, Monster target, double freezeTime, boolean isFreezeTower){
        this.x = x;
        this.y = y;
        this.fireSprite = fireSprite;
        this.damage = damage;
        this.target = target;
        this.disAppear = false;
        this.freezeTime = freezeTime;
        this.isFreezeTower = isFreezeTower;
    }

    public boolean getDisAppear(){
        return this.disAppear;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    /**
     * Draw the fireballs from towers to monsters.
     * Check if at least one monster is in the range of this tower.
     * Make the fireball from the center of one tower to collide one monster.
     * Make sure that fireballs are displayed correctly and move at the correct speed.
     * @param app
     */
    public void draw(App app){

        if (!hasValidTarget()) {
            this.disAppear = true;
            return;
        }
        app.image(fireSprite, x, y);

        if(!target.collide(this) && !disAppear){
            double distance = Math.sqrt((target.getX() - x) * (target.getX() - x) + (target.getY() - y) * (target.getY() - y));
            if(distance > 5.0){
                double cos = Math.abs(target.getX() - x) / distance;
                double sin = Math.abs(target.getY() - y) / distance;
                if(target.getX() > x){
                    x += (int)((Math.min(5, distance)) * cos);
                }else{
                    x -= (int)((Math.min(5, distance)) * cos);
                }
                if(target.getY() > y){
                    y += (int)((Math.min(5, distance)) * sin);
                }else{
                    y -= (int)((Math.min(5, distance)) * sin);
                }
            }else{
                x = target.getX();
                y = target.getY();
                this.shoot(target);
            }
        }
    }

    /**
     * Fireballs cause loss of health to enemies, and when the monster’s health reaches 0, it dies.
     * And if the fireball is from a freezeTower, it will freeze enemies.
     * @param target
     */
    private void shoot(Monster target){
        target.setHp(target.getHp() - damage * (target.getArmour()));
        if(isFreezeTower){
            target.freeze(freezeTime);
        }
            target.resetDeathAnimation();
        this.disAppear = true;
        target.setIsFired(false);
    }


    /**
     * Check if there is at least one alive target in the range of this tower.
     * @return
     */
    public boolean hasValidTarget() {
        return target != null && !target.getIsDie();
    }

}



