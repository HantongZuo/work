package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.*;

public class App extends PApplet {

    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;
    public static int WIDTH = CELLSIZE * BOARD_WIDTH + SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH * CELLSIZE + TOPBAR;
    public static final int FPS = 60;
    public static String configPath;
    public Random random = new Random();
    public ArrayList<Grass> grasses = new ArrayList<>();
    public ArrayList<Shrub> shrubs = new ArrayList<>();
    public ArrayList<Path> paths = new ArrayList<>();
    public wizardHouse wizardhouse;
    public char[][] boardlayout = new char[20][20];
    public ArrayList<Wave> waves = new ArrayList<>();
    public ArrayList<Point> points = new ArrayList<>();
    private int countFrame = 0;
    public Point end;
    private int towerRange;
    private double fireSpeed;
    private int damage;
    private int mana;
    private int manaCap;
    private int manaGained;
    private int towerCost;
    private boolean isTpressed = false;
    private ArrayList<Tower> towers = new ArrayList<>();
    private int waveNum;
    public GameState state = GameState.PLAYING;
    private boolean isHoverBuildTower = false;
    private boolean isU1pressed = false;
    private boolean isU2pressed = false;
    private boolean isU3pressed = false;
    private int manaPoolCost;
    private int manaPoolCostIncrease;
    private double manaPoolCap;
    private double manaPoolManaGained;
    private double manaGainedMultiplier = 1.0;
    private boolean isMpressed = false;
    private boolean isHoverManaPool = false;
    private boolean isPpressed = false;
    private boolean isFFpressed = false;
    private boolean isHoverFf = false;
    private boolean isHoverU1 = false;
    private boolean isHoverP = false;
    private boolean isHoverU2 = false;
    private boolean isHoverU3 = false;
    private FileReader f1;
    private JSONObject jsonob;
    private boolean isU4pressed = false;
    private boolean isHoverU4 = false;
    private double freezeTime;
    private int freezeCost;
    private int manaCounter = 0;

    // Feel free to add any additional methods or attributes you want. Please put classes in different files.

    /**
     * check if this path tile is the shape of path1 or not, it has four possible cases.
     * @param boardlayout the 2D array to describe the layout of the map
     * @param i the row of layout
     * @param j the col of layout
     * @return return the result
     */
    public double checkCorner(char[][] boardlayout, int i, int j) {
        if (j - 1 >= 0 && i - 1 >= 0) {
            if (boardlayout[i][j - 1] == 'X' && boardlayout[i - 1][j] == 'X') {
                return 90.0;
            }
        }
        if (j + 1 < 20 && i - 1 >= 0) {
            if (boardlayout[i][j + 1] == 'X' && boardlayout[i - 1][j] == 'X') {
                return 180.0;
            }
        }
        if (j - 1 >= 0 && i + 1 < 20) {
            if (boardlayout[i][j - 1] == 'X' && boardlayout[i + 1][j] == 'X') {
                return 0.0;
            }
        }
        if (i + 1 < 20 && j + 1 < 20) {
            if (boardlayout[i][j + 1] == 'X' && boardlayout[i + 1][j] == 'X') {
                return 270.0;
            }
        }
        return -1;
    }

    /**
     * check if this path tile is the shape of path3 or not, it has only one possible case.
     * @param boardlayout the 2D array to describe the layout of the map
     * @param i the row of layout
     * @param j the col of layout
     * @return return the result
     */
    public boolean checkIntersection(char[][] boardlayout, int i, int j) {
        if (i + 1 < 20 && i - 1 >= 0 && j + 1 < 20 && j - 1 >= 0) {
            if (boardlayout[i][j - 1] == 'X' && boardlayout[i - 1][j] == 'X' &&
                    boardlayout[i][j + 1] == 'X' && boardlayout[i + 1][j] == 'X') {
                return true;
            }
        }
        return false;
    }

    /**
     * check if this path tile is the shape of path0 or not, it has two possible cases.
     * @param boardlayout the 2D array to describe the layout of the map
     * @param i the row of layout
     * @param j the col of layout
     * @return return the result
     */
    public double checkDirection(char[][] boardlayout, int i, int j) {
        if (i - 1 >= 0 && boardlayout[i - 1][j] == 'X') {
            return 90.0;
        } else if (i + 1 < 20 && boardlayout[i + 1][j] == 'X') {
            return 90.0;
        } else {
            return 0.0;
        }
    }

    /**
     * check if this path tile is the shape of path2 or not, it has four possible cases.
     * @param boardlayout the 2D array to describe the layout of the map
     * @param i the row of layout
     * @param j the col of layout
     * @return return the result
     */
    public double checkTriangle(char[][] boardlayout, int i, int j) {
        if (j - 1 >= 0 && i + 1 < 20 && j + 1 < 20) {
            if (boardlayout[i][j - 1] == 'X' && boardlayout[i][j + 1] == 'X' &&
                    boardlayout[i + 1][j] == 'X') {
                return 0.0;
            }
        }
        if (i - 1 >= 0 && j + 1 < 20 && i + 1 < 20) {
            if (boardlayout[i - 1][j] == 'X' && boardlayout[i][j + 1] == 'X' &&
                    boardlayout[i + 1][j] == 'X') {
                return 270.0;
            }
        }
        if (i - 1 >= 0 && i + 1 < 20 && j - 1 >= 0) {
            if (boardlayout[i][j - 1] == 'X' && boardlayout[i + 1][j] == 'X' &&
                    boardlayout[i - 1][j] == 'X') {
                return 90.0;
            }
        }
        if (i - 1 >= 0 && j + 1 < 20 && j - 1 >= 0) {
            if (boardlayout[i][j - 1] == 'X' && boardlayout[i - 1][j] == 'X' &&
                    boardlayout[i][j + 1] == 'X') {
                return 180.0;
            }
        }
        return -1;
    }

    /**
     * Generate monster instance and add it to its attributed wave.
     * @param wave
     * @param jsonObject
     * @param end  the end of the path where monster will go
     * @param duration the time of one wave
     * @param accumulatedPause the time before one wave
     */
    private void generateMonster(Wave wave, JSONObject jsonObject, Point end, int duration, double accumulatedPause) {
        int quantity = jsonObject.getInt("quantity");
        double interval = duration / (double) quantity;

        for (int i = 0; i < quantity; i++) {
            int spawnpoint = random.nextInt(points.size());
            Point start = points.get(spawnpoint);

            ArrayList<Point> path = new ArrayList<>();
            BFSfindPath(path, boardlayout, start, end);

            String type = jsonObject.getString("type"); // w e12
            String imagePath = String.format("src/main/resources/WizardTD/%s.png", type);
            PImage img = loadImage(imagePath);
            double hp = jsonObject.getDouble("hp");
            double speed = jsonObject.getDouble("speed");
            double armour = jsonObject.getDouble(("armour"));
            int mana_gained_on_kill = jsonObject.getInt("mana_gained_on_kill");

            Monster m = new Monster(img, hp, speed, armour, mana_gained_on_kill, path, accumulatedPause, this);
            wave.addMonster(m);
            accumulatedPause += interval;
        }
    }

    /**
     * Use BFS algorithm to get a path of one monster.
     * @param path the path of one monster from its start point to the end point(wizardHouse)
     * @param boardlayout
     * @param start the start point of one monster's path
     * @param end the end point of one monster's path
     */
    private void BFSfindPath(ArrayList<Point> path, char[][] boardlayout, Point start, Point end) {
        Point[][] pre = new Point[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                pre[i][j] = null;
            }
        }

        ArrayList<Point> queue = new ArrayList<>();
        queue.add(start);
        pre[start.getRow()][start.getCol()] = start;

        while (queue.size() > 0) {
            Point p = queue.remove(0);
            if (p.getRow() == end.getRow() && p.getCol() == end.getCol()) {
                break;
            }
            int r = p.getRow();
            int c = p.getCol();
            if (r + 1 < 20 && (boardlayout[r + 1][c] == 'X' || boardlayout[r + 1][c] == 'W')) {
                if (pre[r + 1][c] == null) {  // if used before
                    pre[r + 1][c] = p;   // remember the point before it
                    queue.add(new Point(r + 1, c));
                }
            }
            if (r - 1 >= 0 && (boardlayout[r - 1][c] == 'X' || boardlayout[r - 1][c] == 'W')) {
                if (pre[r - 1][c] == null) {
                    pre[r - 1][c] = p;   // remember the point before it
                    queue.add(new Point(r - 1, c));
                }
            }
            if (c + 1 < 20 && (boardlayout[r][c + 1] == 'X' || boardlayout[r][c + 1] == 'W')) {
                if (pre[r][c + 1] == null) {
                    pre[r][c + 1] = p;   // remember the point before it
                    queue.add(new Point(r, c + 1));
                }
            }
            if (c - 1 >= 0 && (boardlayout[r][c - 1] == 'X' || boardlayout[r][c - 1] == 'W')) {
                if (pre[r][c - 1] == null) {
                    pre[r][c - 1] = p;   // remember the point before it
                    queue.add(new Point(r, c - 1));
                }
            }
        }
        getPath(start, pre, end, path); // Get the path from the pre.
    }

    /**
     * Used to be a recursion to help the BFS algorithm to find the path.
     * @param start
     * @param pre
     * @param end
     * @param path
     */
    private void getPath(Point start, Point[][] pre, Point end, ArrayList<Point> path) {
        if (start.getRow() == end.getRow() && start.getCol() == end.getCol()) {
            path.add(start);
            return;
        }
        path.add(end);
        Point next = pre[end.getRow()][end.getCol()];
        getPath(start, pre, next, path);
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component but no buttons have been pushed.
     * @param e
     */
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for (Tower t : towers) {
            int xBound = t.getX() + CELLSIZE;
            int yBound = t.getY() + CELLSIZE;
            if (x <= xBound && x >= t.getX() && y <= yBound && y >= t.getY()) {
                t.setIsHover(true);
            } else {
                t.setIsHover(false);
            }
        }

        isHoverBuildTower = false;
        isHoverManaPool = false;
        isHoverP = false;
        isHoverFf = false;
        isHoverU1 = false;
        isHoverU2 = false;
        isHoverU3 = false;
        isHoverU4 = false;

        if (x >= 645 && x <= 695) {
            if (y >= 200 && y <= 250) {
                isHoverBuildTower = true;
            } else if (y >= 440 && y <= 490) {
                isHoverManaPool = true;
            } else if (y >= 140 && y <= 190) {
                isHoverP = true;
            } else if (y >= 80 && y <= 130) {
                isHoverFf = true;
            } else if (y >= 260 && y <= 310) {
                isHoverU1 = true;
            } else if (y >= 320 && y <= 370) {
                isHoverU2 = true;
            } else if (y >= 380 && y <= 430) {
                isHoverU3 = true;
            } else if (y >= 500 && y <= 550) {
                isHoverU4 = true;
            }
        }
    }

    /**
     * Check if this tile can be set a tower.
     * @param boardlayout
     * @param x
     * @param y
     * @return
     */
    public boolean checkValidPlace(char[][] boardlayout, int x, int y) {
        if (x < 0 || x >= 640) {
            return false;
        }
        if (y < 40 || y >= 680) {
            return false;
        }
        int row = (y - TOPBAR) / CELLSIZE;
        int col = x / CELLSIZE;
        if (boardlayout[row][col] == ' ') {
            boardlayout[row][col] = 'T';
            return true;
        }
        return false;
    }

    /**
     * Getter of mana.
     * @return
     */
    public int getMana() {
        return this.mana;
    }

    /**
     * Getter of manaCap.
     * @return
     */
    public int getManaCap() {
        return this.manaCap;
    }

    /**
     * Setter of mana.
     * @param mana
     */
    public void setMana(int mana) {
        this.mana = mana;
    }

    /**
     * Getter of isTpressed.
     * @return
     */
    public boolean getIsTpressed() {
        return this.isTpressed;
    }

    /**
     * Getter of isU1pressed.
     * @return
     */
    public boolean getIsU1pressed() {
        return this.isU1pressed;
    }

    /**
     * Getter of isU2pressed.
     * @return
     */
    public boolean getIsU2pressed() {
        return this.isU2pressed;
    }

    /**
     * Getter of isU3pressed.
     * @return
     */
    public boolean getIsU3pressed() {
        return this.isU3pressed;
    }

    /**
     * Getter of isU4pressed.
     * @return
     */
    public boolean getIsU4pressed() {
        return this.isU4pressed;
    }

    /**
     * Getter of manaGainedMultiplier.
     * @return
     */
    public double getManaGainedMultiplier() {
        return this.manaGainedMultiplier;
    }

    /**
     * Getter of towers.
     * @return
     */
    public ArrayList<Tower> getTowers() {
        return this.towers;
    }

    /**
     * Figure out what the next wave of monsters is.
     * @param countFrame
     * @return
     */
    private int findWave(int countFrame) {

        int waveNumber = 1;
        double totalTime = 0;

        for (Wave w : waves) {
            totalTime += w.getPre_wave_pause();

            if (countFrame < totalTime * 60) {
                return waveNumber;
            }
            totalTime += w.getDuration();

            if (countFrame < totalTime * 60) {
                return waveNumber + 1;
            }
            waveNumber++;
        }
        return -1;
    }

    /**
     * Figure out how long until the next monster arrives.
     * @param countFrame
     * @return
     */
    private int findWaveStartTime(int countFrame) {
        int totalTime = 0;

        for (Wave w : waves) {
            totalTime += (int) (w.getPre_wave_pause() * 60);

            if (countFrame < totalTime) {
                return totalTime / 60;
            }

            totalTime += w.getDuration() * 60;
        }
        return -1;
    }

    /**
     * Start the manaPool function.
     */
    private void castManaPoolSpell() {

        double increment = manaPoolManaGained - 1.0;

        if (mana >= manaPoolCost) {
            mana -= manaPoolCost;
            manaCap *= (int) manaPoolCap;
            manaGainedMultiplier += increment;
            manaPoolCost = manaPoolCost + manaPoolCostIncrease;
        }
    }

    /**
     * Draw different color of the key area when this key are pressed or hovered.
     * @param x
     * @param y
     * @param isHover
     * @param isPressed
     */
    private void drawKey(int x, int y, boolean isHover, boolean isPressed) {
        stroke(0);
        if (isPressed) {
            fill(255, 255, 0);
        } else if (isHover) {
            fill(169, 169, 169);
        }
        rect(x, y, 50, 50);
    }

    /**
     * Check if the player wins the game or not by testing if all monsters of all waves are died.
     */
    private void checkWin() {
        if (state == GameState.LOST) {
            return;
        }

        for (Wave w : waves) {
            for (Monster m : w.getMonsters()) {
                if (!m.getIsDie()) {
                    return;
                }
            }
        }
        state = GameState.WON;
    }

    /**
     * Check if the player loses the game or not by testing if the mana is less than zero.
     */
    private void checkLose() {
        if (getMana() < 0) {
            state = GameState.LOST;
        }
    }

    /**
     * Restart the game when losing the game and initialize the game data from the json.
     */
    private void resetGame() {

        state = GameState.PLAYING;

        waves.clear();
        points.clear();
        towers.clear();

        countFrame = 0;

        JSONArray jsonArray;
        jsonArray = (JSONArray) jsonob.get("waves");
        double accumulatedPause = 0;
        double totalDuration = 0;

        for (int k = 0; k < 20; k++) {
            if (boardlayout[0][k] == 'X') {
                points.add(new Point(0, k));
            }
            if (boardlayout[19][k] == 'X') {
                points.add(new Point(19, k));
            }
            if (boardlayout[k][0] == 'X') {
                points.add(new Point(k, 0));
            }
            if (boardlayout[k][19] == 'X') {
                points.add(new Point(k, 19));
            }
        }

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject perwave = (JSONObject) jsonArray.get(i);
            int duration = perwave.getInt("duration");
            double pre_wave_Pause = perwave.getDouble("pre_wave_pause");
            if (i > 0) {
                accumulatedPause += totalDuration;
            }
            accumulatedPause += pre_wave_Pause;
            totalDuration = duration;

            Wave wave = new Wave(duration, pre_wave_Pause);
            JSONArray monsterArray = (JSONArray) perwave.get("monsters");
            for (int j = 0; j < monsterArray.size(); j++) {

                generateMonster(wave, (JSONObject) monsterArray.get(j), end, duration, accumulatedPause);
            }
            waves.add(wave);
        }

        this.towerRange = jsonob.getInt("initial_tower_range");
        this.fireSpeed = jsonob.getDouble("initial_tower_firing_speed");
        this.damage = jsonob.getInt("initial_tower_damage");
        this.mana = jsonob.getInt("initial_mana");
        this.manaCap = jsonob.getInt("initial_mana_cap");
        this.manaGained = jsonob.getInt("initial_mana_gained_per_second");
        this.towerCost = jsonob.getInt("tower_cost");

        this.manaPoolCost = jsonob.getInt("mana_pool_spell_initial_cost");
        this.manaPoolCostIncrease = jsonob.getInt("mana_pool_spell_cost_increase_per_use");
        this.manaPoolCap = jsonob.getDouble("mana_pool_spell_cap_multiplier");
        this.manaPoolManaGained = jsonob.getDouble("mana_pool_spell_mana_gained_multiplier");

        this.freezeTime = jsonob.getDouble("freeze time of frozen tower");
        this.freezeCost = jsonob.getInt("freeze tower upgrade cost");
    }

    /**
     * Constructor of App
     */
    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        size(WIDTH, HEIGHT);

    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
    @Override
    public void setup() {
        frameRate(FPS);

        try {
            f1 = new FileReader(this.configPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        jsonob = new JSONObject(f1);
        String layout = jsonob.getString("layout");
        File f2 = null;
        f2 = new File(layout);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f2));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int count = 0;
        String line;
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < line.length(); i++) {
                boardlayout[count][i] = line.charAt(i);
            }
            for (int i = line.length(); i < BOARD_WIDTH; i++) {
                boardlayout[count][i] = ' ';
            }
            count++;
        }

        double degree;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                char c = boardlayout[i][j];
                if (c == ' ') {
                    grasses.add(new Grass(loadImage("src/main/resources/WizardTD/grass.png"), CELLSIZE * j, TOPBAR + CELLSIZE * i));
                }
                if (c == 'S') {
                    shrubs.add(new Shrub(loadImage("src/main/resources/WizardTD/shrub.png"), CELLSIZE * j, TOPBAR + CELLSIZE * i));
                }
                if (c == 'X') {
                    if (this.checkIntersection(boardlayout, i, j)) {
                        paths.add(new Path(loadImage("src/main/resources/WizardTD/path3.png"), CELLSIZE * j, TOPBAR + CELLSIZE * i));
                    } else if ((degree = checkTriangle(boardlayout, i, j)) >= 0) {
                        paths.add(new Path(rotateImageByDegrees(loadImage("src/main/resources/WizardTD/path2.png"), degree), CELLSIZE * j, TOPBAR + CELLSIZE * i));
                    } else if ((degree = checkCorner(boardlayout, i, j)) >= 0) {
                        paths.add(new Path(rotateImageByDegrees(loadImage("src/main/resources/WizardTD/path1.png"), degree), CELLSIZE * j, TOPBAR + CELLSIZE * i));
                    } else {
                        degree = checkDirection(boardlayout, i, j);
                        paths.add(new Path(rotateImageByDegrees(loadImage("src/main/resources/WizardTD/path0.png"), degree), CELLSIZE * j, TOPBAR + CELLSIZE * i));
                    }
                }
                if (c == 'W') {
                    wizardhouse = new wizardHouse(loadImage("src/main/resources/WizardTD/wizard_house.png"), CELLSIZE * j - 8, TOPBAR + CELLSIZE * i - 8);
                    end = new Point(i, j);
                }

            }
        }
        resetGame();
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 84) {
            isTpressed = true;
        }
        if (key == 49) {
            isU1pressed = true;
        }
        if (key == 50) {
            isU2pressed = true;
        }
        if (key == 51) {
            isU3pressed = true;
        }
        if (key == 77) {
            isMpressed = true;
        }
        if (key == 80) {
            isPpressed = true;
        }
        if (key == 70) {
            isFFpressed = true;
        }
        if (key == 82 && state == GameState.LOST) {
            resume();
            resetGame();
        }
        if (key == 52) {
            isU4pressed = true;
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 84) {
            isTpressed = false;
        }
        if (key == 49) {
            isU1pressed = false;
        }
        if (key == 50) {
            isU2pressed = false;
        }
        if (key == 51) {
            isU3pressed = false;
        }
        if (key == 77) {
            isMpressed = false;
        }
        if (key == 80) {
            isPpressed = false;
        }
        if (key == 70) {
            isFFpressed = false;
        }
        if (key == 52) {
            isU4pressed = false;
        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     * Set a tower in a valid place.
     * Upgrade the range/speed/damage level of one tower or add a freeze function to a tower.
     * Activate the ManaPool Spell function.
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (x >= 645 && x <= 695) {
            if (y >= 200 && y <= 250) {
                isTpressed = !isTpressed;
            } else if (y >= 260 && y <= 310) {
                isU1pressed = !isU1pressed;
            } else if (y >= 320 && y <= 370) {
                isU2pressed = !isU2pressed;
            } else if (y >= 380 && y <= 430) {
                isU3pressed = !isU3pressed;
            } else if (y >= 440 && y <= 490) {
                isMpressed = !isMpressed;
            } else if (y >= 140 && y <= 190) {
                isPpressed = !isPpressed;
            } else if (y >= 80 && y <= 130) {
                isFFpressed = !isFFpressed;
            } else if (y >= 500 && y <= 550) {
                isU4pressed = !isU4pressed;
            }
        }

        if (isTpressed) {
            if (checkValidPlace(boardlayout, x, y)) {
                if (mana >= towerCost) {
                    int row = (y - TOPBAR) / CELLSIZE;
                    int col = x / CELLSIZE;
                    x = CELLSIZE * col;
                    y = CELLSIZE * row + TOPBAR;

                    Tower t = new Tower(towerRange, fireSpeed, damage, x, y, loadImage("src/main/resources/WizardTD/tower0.png"), freezeTime);
                    towers.add(t);

                    this.setMana(mana - towerCost);
                }
            }
        }

        for (Tower t : towers) {
            if (x >= t.getX() && x < t.getX() + CELLSIZE && y >= t.getY() && y <= t.getY() + CELLSIZE) {
                if (isU1pressed) {
                    int currentU1Level = t.getRangeLevel() + 1;
                    int requireMana = t.upgradeCost(currentU1Level);

                    if (mana >= requireMana) {
                        t.upgradeRange();
                        mana = mana - requireMana;
                    }
                }
                if (isU2pressed) {
                    int currentU2Level = t.getSpeedLevel() + 1;
                    int requireMana = t.upgradeCost(currentU2Level);

                    if (mana >= requireMana) {
                        t.upgradeSpeed();
                        mana = mana - requireMana;
                    }
                }
                if (isU3pressed) {
                    int currentU3Level = t.getDamageLevel() + 1;
                    int requireMana = t.upgradeCost(currentU3Level);

                    if (mana >= requireMana) {
                        t.upgradeDamage();
                        mana = mana - requireMana;
                    }
                }
                if (isU4pressed) {
                    if (mana >= freezeCost && !(t.getHasBeenFrozen())) {
                        mana = mana - freezeCost;
                        t.setHasBeenFrozen(true);
                        t.setIsFreezeTower(true);
                    }
                }
                break;
            }
        }

        if (isMpressed) {
            castManaPoolSpell();
        }
    }

    /**
     * Invoked when a mouse button has been released on a component.
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /*@Override
    public void mouseDragged(MouseEvent e) {

    }*/

    /**
     * Draw all elements in the game by current frame.
     * Check win or lose.
     * Set Timer of this game.
     * Set the background color of this game.
     * Draw the map element.
     * Draw each wave with their monsters.
     * Draw the tower which has been set.
     * indicate the time in seconds until the next wave begins with “Wave <x> starts: <s>”. If the last wave has begun, display nothing.
     * Display the mana bar.
     * Calculate the mana with gained per second.
     * Draw the game control action area.
     * Display the tooltip for towerCost.
     * Display the tooltip for ManaPool.
     * Display the tooltip for freeze towerCost.
     */
    @Override
    public void draw() {

        checkLose();
        checkWin();
        if (state == GameState.WON) {
            textSize(100);
            text("YOU WIN", 150, HEIGHT / 2);
            pause();
            return;
        } else if (state == GameState.LOST) {
            textSize(80);
            text("YOU LOST", 160, HEIGHT / 2);
            textSize(60);
            text("Press 'r' to restart", 100, HEIGHT / 2 + 80);
            pause();
            return;
        }

        if(!isPpressed) {
            if (isFFpressed) {
                countFrame += 2;
            } else {
                countFrame += 1;
            }
        }

        background(139, 121, 94);

        for (int i = 0; i < grasses.size(); i++) {
            grasses.get(i).draw(this);
        }
        for (int i = 0; i < shrubs.size(); i++) {
            shrubs.get(i).draw(this);
        }
        for (int i = 0; i < paths.size(); i++) {
            paths.get(i).draw(this);
        }
        wizardhouse.draw(this);

        for (Wave w : waves) {
            w.draw(this, countFrame);
        }

        for (Tower t : towers) {
            t.draw(this, loadImage("src/main/resources/WizardTD/fireball.png"));
        }

        waveNum = findWave(countFrame);
        int nextWaveStartTime = findWaveStartTime(countFrame);
        int currentTime = countFrame / 60;
        int timeRemain = nextWaveStartTime - currentTime;

        if (nextWaveStartTime != -1 && timeRemain >= 0) {
            fill(0);
            textSize(30);
            text(String.format("Wave %d starts: %d", waveNum, timeRemain), 5, 30);
        }

        fill(0);
        textSize(20);
        text("MANA:", 300, 30);

        int totalWidth = 350;
        int w1 = (int) (((float) mana) / manaCap * totalWidth);
        int w2 = totalWidth - w1;

        stroke(0);
        fill(0, 191, 255);
        this.rect(370, 11, w1, 20);

        if (mana != manaCap) {
            stroke(0);
            fill(255, 250, 250);
            this.rect(370 + w1, 11, w2, 20);
        }

        fill(0);
        textSize(16);
        text(String.format(" %d / %d", mana, manaCap), 500, 28);

        if(!isPpressed) {
            manaCounter += isFFpressed ? 2 : 1;
            if (manaCounter >= 60) {
                this.mana += (int) (manaGained * manaGainedMultiplier);
                if (this.mana >= manaCap) {
                    this.mana = manaCap;
                }
                manaCounter -= 60;
            }
        }

        fill(0);
        stroke(0);
        fill(139, 121, 94);
        rect(645, 80, 50, 50);
        drawKey(645, 80, isHoverFf, isFFpressed);

        fill(0);
        textSize(30);
        text("FF", 655, 110);

        fill(0);
        textSize(12);
        text("2*speed", 700, 95);

        stroke(0);
        fill(139, 121, 94);
        rect(645, 140, 50, 50);
        drawKey(645, 140, isHoverP, isPpressed);

        fill(0);
        textSize(30);
        text("P", 655, 170);

        fill(0);
        textSize(12);
        text("PAUSE", 700, 155);

        stroke(0);
        fill(139, 121, 94);
        rect(645, 200, 50, 50);
        drawKey(645, 200, isHoverBuildTower, isTpressed);

        fill(0);
        textSize(30);
        text("T", 655, 230);

        fill(0);
        textSize(12);
        text("Build", 700, 215);

        fill(0);
        textSize(12);
        text("tower", 700, 240);

        stroke(0);
        fill(139, 121, 94);
        rect(645, 260, 50, 50);
        drawKey(645, 260, isHoverU1, isU1pressed);

        fill(0);
        textSize(30);
        text("U1", 655, 290);

        fill(0);
        textSize(12);
        text("Upgrade", 700, 280);

        fill(0);
        textSize(12);
        text("range", 700, 295);

        stroke(0);
        fill(139, 121, 94);
        rect(645, 320, 50, 50);
        drawKey(645, 320, isHoverU2, isU2pressed);

        fill(0);
        textSize(30);
        text("U2", 655, 350);

        fill(0);
        textSize(12);
        text("Upgrade", 700, 340);

        fill(0);
        textSize(12);
        text("speed", 700, 355);

        stroke(0);
        fill(139, 121, 94);
        rect(645, 380, 50, 50);
        drawKey(645, 380, isHoverU3, isU3pressed);

        fill(0);
        textSize(30);
        text("U3", 655, 410);

        fill(0);
        textSize(12);
        text("Upgrade", 700, 400);

        fill(0);
        textSize(12);
        text("damage", 700, 415);

        stroke(0);
        fill(139, 121, 94);
        rect(645, 440, 50, 50);
        drawKey(645, 440, isHoverManaPool, isMpressed);

        fill(0);
        textSize(30);
        text("M", 655, 470);

        fill(0);
        textSize(12);
        text("Mana pool", 700, 455);

        fill(0);
        textSize(12);
        text(String.format("cost: %d", manaPoolCost), 700, 470);

        stroke(0);
        fill(139, 121, 94);
        rect(645, 500, 50, 50);
        drawKey(645, 500, isHoverU4, isU4pressed);

        fill(0);
        textSize(30);
        text("U4", 655, 530);

        fill(0);
        textSize(12);
        text("Freeze", 700, 515);

        fill(0);
        textSize(12);
        text("tower", 700, 530);

        if (isHoverBuildTower) {
            stroke(0);
            fill(255);
            rect(560, 190, 70, 25);
            fill(0);
            textSize(12);
            text(String.format("Cost: %d", towerCost), 568, 207);
        }

        if (isHoverManaPool) {
            stroke(0);
            fill(255);
            rect(560, 420, 70, 25);
            fill(0);
            textSize(12);
            text(String.format("Cost: %d", manaPoolCost), 568, 440);
        }

        if (isHoverU4) {
            stroke(0);
            fill(255);  // 白色背景
            rect(560, 495, 70, 25);
            fill(0);  // 黑色文字
            textSize(12);
            text(String.format("Cost: %d", freezeCost), 568, 513);  // 显示花费
        }
    }

    public static void main(String[] args) {
        PApplet.main("WizardTD.App");
    }

    /**
     * Source: https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
     *
     * @param pimg  The image to be rotated
     * @param angle between 0 and 360 degrees
     * @return the new rotated image
     */
    public PImage rotateImageByDegrees(PImage pimg, double angle) {
        BufferedImage img = (BufferedImage) pimg.getNative();
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        PImage result = this.createImage(newWidth, newHeight, ARGB);
        //BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotated = (BufferedImage) result.getNative();
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                result.set(i, j, rotated.getRGB(i, j));
            }
        }
        return result;
    }

    public boolean getIsPpressed() {
        return isPpressed;
    }


    public boolean getIsFFpressed() {
        return isFFpressed;
    }


    public boolean getIsMpressed() {
        return isMpressed;
    }


    public void setManaCap(int manaCap) {
        this.manaCap = manaCap;
    }

    public void setManaGainedMultiplier(double manaGainedMultiplier) {
        this.manaGainedMultiplier = manaGainedMultiplier;
    }

    public boolean getIsHoverBuildTower() {
        return isHoverBuildTower;
    }

    public boolean getIsHoverManaPool() {
        return isHoverManaPool;
    }

    public boolean getIsHoverP() {
        return isHoverP;
    }

    public boolean getIsHoverFf() {
        return isHoverFf;
    }

    public boolean getIsHoverU1() {
        return isHoverU1;
    }


    public boolean getIsHoverU2() {
        return isHoverU2;
    }


    public boolean getIsHoverU3() {
        return isHoverU3;
    }

    public boolean getIsHoverU4() {
        return isHoverU4;
    }


    public int getTowerCost() {
        return towerCost;
    }

    public void setTowerCost(int towerCost) {
        this.towerCost = towerCost;
    }

    public void setIsHoverBuildTower(boolean isHoverBuildTower) {
        this.isHoverBuildTower = isHoverBuildTower;
    }


    public void setIsHoverManaPool(boolean isHoverManaPool) {
        this.isHoverManaPool = isHoverManaPool;
    }


    public void setIsHoverU4(boolean isHoverU4) {
        this.isHoverU4 = isHoverU4;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public GameState getState() {
        return this.state;
    }

    public void setIsTpressed(boolean isTpressed) {
        this.isTpressed = isTpressed;
    }

    public void setIsU1pressed(boolean isU1pressed) {
        this.isU1pressed = isU1pressed;
    }


    public void setIsU3pressed(boolean isU3pressed) {
        this.isU3pressed = isU3pressed;
    }


    public void setIsU2pressed(boolean isU2pressed) {
        this.isU2pressed = isU2pressed;
    }

    public void setIsU4pressed(boolean isU4pressed) {
        this.isU4pressed = isU4pressed;
    }

    public void setFreezeCost(int freezeCost) {
        this.freezeCost = freezeCost;
    }

    public void setIsFFpressed(boolean isFFpressed) {
        this.isFFpressed = isFFpressed;
    }

}
