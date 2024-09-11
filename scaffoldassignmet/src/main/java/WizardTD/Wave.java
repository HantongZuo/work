package WizardTD;

import java.util.ArrayList;

public class Wave {

    private int duration;
    private double pre_wave_pause;
    private ArrayList<Monster> monsters;


    /**
     * Constructor of Wave.
     * @param duration
     * @param pre_wave_pause
     */
    public Wave(int duration, double pre_wave_pause){
        this.duration = duration;
        this.pre_wave_pause = pre_wave_pause;
        this.monsters = new ArrayList<>();
    }

    public int getDuration(){
        return this.duration;
    }
    public double getPre_wave_pause(){
        return this.pre_wave_pause;
    }

    /**
     * Add monster to the wave it belongs to.
     * @param m
     */
    public void addMonster(Monster m) {
        monsters.add(m);
    }

    public ArrayList<Monster> getMonsters(){
        return monsters;
    }


    /**
     * Draw all the alive monster of this wave
     * @param app
     * @param countFrame
     */
    public void draw(App app, int countFrame){
        if(countFrame >= pre_wave_pause * 60) {
            for (Monster m : monsters) {
                if(!m.getIsDie() ||  (m.getIsDie() && m.getDeathAnimationFrame() < m.getDeathAnimationImages().length * 4)) {
                    m.draw(app, countFrame);
                }
            }
        }
    }
}
