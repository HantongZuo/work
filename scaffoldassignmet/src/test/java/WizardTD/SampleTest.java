package WizardTD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.core.PApplet;
import processing.data.JSONObject;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {
    private static final int BOARD_WIDTH = 20;
    private String configPath = "config.json";
    private char[][] boardlayout = new char[20][20];
    App app = new App();

    // check the setup() method and draw()method
    @Test
    public void checkSetupandDraw() {
        App app = new App();
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);
        app.setup();
    }

    // test the keyPressed of T
    @Test
    public void testTPressed(){
        KeyEvent keyEvent = createKeyEventP('T');
        app.keyPressed(keyEvent);
        assertTrue(app.getIsTpressed());
    }

    // test the keyPressed of 1
    @Test
    public void testU1Pressed(){
        KeyEvent keyEvent = createKeyEventP('1');
        app.keyPressed(keyEvent);
        assertTrue(app.getIsU1pressed());
    }

    // test the keyPressed of 2
    @Test
    public void testU2Pressed(){
        KeyEvent keyEvent = createKeyEventP('2');
        app.keyPressed(keyEvent);
        assertTrue(app.getIsU2pressed());
    }

    // test the keyPressed of 3
    @Test
    public void testU3Pressed(){
        KeyEvent keyEvent = createKeyEventP('3');
        app.keyPressed(keyEvent);
        assertTrue(app.getIsU3pressed());
    }

    // test the keyPressed of P
    @Test
    public void testPPressed(){
        KeyEvent keyEvent = createKeyEventP('P');
        app.keyPressed(keyEvent);
        assertTrue(app.getIsPpressed());
    }

    // test the keyPressed of F
    @Test
    public void testFPressed(){
        KeyEvent keyEvent = createKeyEventP('F');
        app.keyPressed(keyEvent);
        assertTrue(app.getIsFFpressed());
    }

    // test the keyPressed of M
    @Test
    public void testMPressed(){
        KeyEvent keyEvent = createKeyEventP('M');
        app.keyPressed(keyEvent);
        assertTrue(app.getIsMpressed());
    }

    // test the keyPressed of 4
    @Test
    public void testU4Pressed(){
        KeyEvent keyEvent = createKeyEventP('4');
        app.keyPressed(keyEvent);
        assertTrue(app.getIsU4pressed());
    }

    // test the keyPressed of R
    @Test
    public void testRPressed(){
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);
        app.setState(GameState.LOST);
        KeyEvent keyEvent = createKeyEventP('R');
        app.keyPressed(keyEvent);
    }

    // test the keyReleased of T
    @Test
    public void testTRelease(){
        KeyEvent keyEvent = createKeyEventR('T');
        app.keyReleased(keyEvent);
        assertFalse(app.getIsTpressed());
    }

    // test the keyReleased of 1
    @Test
    public void testU1Release(){
        KeyEvent keyEvent = createKeyEventR('1');
        app.keyReleased(keyEvent);
        assertFalse(app.getIsU1pressed());
    }

    // test the keyReleased of 2
    @Test
    public void testU2Release(){
        KeyEvent keyEvent = createKeyEventR('2');
        app.keyReleased(keyEvent);
        assertFalse(app.getIsU2pressed());
    }

    // test the keyReleased of 3
    @Test
    public void testU3Release(){
        KeyEvent keyEvent = createKeyEventR('3');
        app.keyReleased(keyEvent);
        assertFalse(app.getIsU3pressed());
    }

    // test the keyReleased of M
    @Test
    public void testMRelease(){
        KeyEvent keyEvent = createKeyEventR('M');
        app.keyReleased(keyEvent);
        assertFalse(app.getIsMpressed());
    }

    // test the keyReleased of P
    @Test
    public void testPRelease(){
        KeyEvent keyEvent = createKeyEventR('P');
        app.keyReleased(keyEvent);
        assertFalse(app.getIsPpressed());
    }

    // test the keyReleased of F
    @Test
    public void testFRelease(){
        KeyEvent keyEvent = createKeyEventR('F');
        app.keyReleased(keyEvent);
        assertFalse(app.getIsFFpressed());
    }

    // test the keyReleased of 4
    @Test
    public void testU4Release(){
        KeyEvent keyEvent = createKeyEventR('4');
        app.keyReleased(keyEvent);
        assertFalse(app.getIsTpressed());
    }

    //test the mousePressed of T area
    @Test
    public void testMousePressedT(){
        MouseEvent keyEvent = createMouseEventP(670, 225);
        app.mousePressed(keyEvent);
        assertTrue(app.getIsTpressed());
        app.mousePressed(keyEvent);
        assertFalse(app.getIsTpressed());
    }

    //test the mousePressed of U1 area
    @Test
    public void testMousePressedU1(){
        MouseEvent keyEvent = createMouseEventP(670, 270);
        app.mousePressed(keyEvent);
        assertTrue(app.getIsU1pressed());
        app.mousePressed(keyEvent);
        assertFalse(app.getIsU1pressed());
    }

    //test the mousePressed of U2 area
    @Test
    public void testMousePressedU2(){
        MouseEvent keyEvent = createMouseEventP(670, 330);
        app.mousePressed(keyEvent);
        assertTrue(app.getIsU2pressed());
        app.mousePressed(keyEvent);
        assertFalse(app.getIsU2pressed());
    }

    //test the mousePressed of U3 area
    @Test
    public void testMousePressedU3(){
        MouseEvent keyEvent = createMouseEventP(670, 390);
        app.mousePressed(keyEvent);
        assertTrue(app.getIsU3pressed());
        app.mousePressed(keyEvent);
        assertFalse(app.getIsU3pressed());
    }

    //test the mousePressed of M area
    @Test
    public void testMousePressedM(){
        MouseEvent keyEvent = createMouseEventP(670, 460);
        app.mousePressed(keyEvent);
        app.mousePressed(keyEvent);
        assertFalse(app.getIsMpressed());
    }

    //test the mousePressed of P area
    @Test
    public void testMousePressedP(){
        MouseEvent keyEvent = createMouseEventP(670, 150);
        app.mousePressed(keyEvent);
        assertTrue(app.getIsPpressed());
        app.mousePressed(keyEvent);
        assertFalse(app.getIsPpressed());
    }

    //test the mousePressed of FF area
    @Test
    public void testMousePressedFF(){
        MouseEvent keyEvent = createMouseEventP(670, 90);
        app.mousePressed(keyEvent);
        assertTrue(app.getIsFFpressed());
        app.mousePressed(keyEvent);
        assertFalse(app.getIsFFpressed());
    }

    //test the mousePressed of U4 area
    @Test
    public void testMousePressedU4(){
        MouseEvent keyEvent = createMouseEventP(670, 520);
        app.mousePressed(keyEvent);
        assertTrue(app.getIsU4pressed());
        app.mousePressed(keyEvent);
        assertFalse(app.getIsU4pressed());
    }

    // test if we can set a tower with enough mana
    @Test
    public void testSetTowerWithEnoughMana(){
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);
        app.setIsTpressed(true);
        app.setMana(200);
        app.setTowerCost(100);
    }

    //test if we can set a tower without enough mana
    @Test
    public void testSetTowerWithoutEnoughMana(){
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);
        app.setIsTpressed(true);
        app.setMana(200);
        app.setTowerCost(250);
    }

    // test the tower upgrade for each attribute
    @Test
    public void testTowerUpgrade(){
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);

        Tower t = new Tower(96, 1.5, 40,224 , 104, app.loadImage("src/main/resources/WizardTD/tower0.png"), 240);
        app.getTowers().add(t);
        app.setIsU1pressed(true);
        app.setIsU2pressed(true);
        app.setIsU3pressed(true);
        app.setIsU4pressed(true);
        app.setFreezeCost(50);
        app.setMana(200);
        t.setHasBeenFrozen(false);
        MouseEvent keyEvent = createMouseEventP(224,104);
        app.mousePressed(keyEvent);
    }

    // create the keyEvent for test the keyPressed()
    public KeyEvent createKeyEventP(char keyChar) {
        PApplet applet = new App();

        int action = KeyEvent.PRESS;
        int keyCode = keyChar;
        int keyModifiers = 0;

        KeyEvent keyEvent = new KeyEvent(applet,
                System.currentTimeMillis(),
                action,
                keyModifiers,
                keyChar,
                keyCode);

        return keyEvent;
    }

    // create the keyEvent for test the keyRelease()
    public KeyEvent createKeyEventR(char keyChar) {
        PApplet applet = new App();

        int action = KeyEvent.RELEASE;
        int keyCode = keyChar;
        int keyModifiers = 0;

        KeyEvent keyEvent = new KeyEvent(applet,
                System.currentTimeMillis(),
                action,
                keyModifiers,
                keyChar,
                keyCode);

        return keyEvent;
    }

    // create the mouseEvent for test the mousePressed()
    public MouseEvent createMouseEventP( int x, int y) {
        PApplet applet = new App();
        int modifiers = 0;
        int action = MouseEvent.PRESS;
        int button = 1;
        int count = 1;

        MouseEvent mouseEvent = new MouseEvent(applet,
                System.currentTimeMillis(),
                action,
                modifiers,
                x, y,
                button,
                count);

        return mouseEvent;
    }

    //test the getter and setter of attribute towerCost
    @Test
    public void testTowerCost(){
        app.setTowerCost(200);
        assertEquals(200, app.getTowerCost());
    }

    //test the getter and setter of attribute mana
    @Test
    public void testMana(){
        app.setMana(200);
        assertEquals(200.0, app.getMana());
    }

    //test the getter and setter of attribute manaCap
    @Test
    public void testManaCap(){
        app.setManaCap(1000);
        assertEquals(1000, app.getManaCap());
    }

    //test the getter and setter of attribute manaGainedMultiplier
    @Test
    public void testManaGainedMultiplier(){
        app.setManaGainedMultiplier(1.0);
        assertEquals(1.0, app.getManaGainedMultiplier());
    }

    // test if the tower is hovered and isHover
    @Test
    public void testTowerHoverStateWhenMouseMovedOverIt() {
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);
        app.setup();

        Tower t = new Tower(96, 1.5, 40,224 , 104, app.loadImage("src/main/resources/WizardTD/tower0.png"), 240);
        app.getTowers().add(t);

        MouseEvent event1 = createMouseEventM(240, 120);
        app.mouseMoved(event1);
        assertTrue(t.getIsHover());

        MouseEvent event2 = createMouseEventM(220, 100);
        app.mouseMoved(event2);
        assertFalse(t.getIsHover());

        MouseEvent event3 = createMouseEventM(220, 120);
        app.mouseMoved(event3);
        assertFalse(t.getIsHover());

        MouseEvent event4 = createMouseEventM(300, 200);
        app.mouseMoved(event4);
        assertFalse(t.getIsHover());

        MouseEvent event5 = createMouseEventM(300, 120);
        app.mouseMoved(event5);
        assertFalse(t.getIsHover());

        MouseEvent event6 = createMouseEventM(240, 100);
        app.mouseMoved(event6);
        assertFalse(t.getIsHover());

        MouseEvent event7 = createMouseEventM(240, 200);
        app.mouseMoved(event7);
        assertFalse(t.getIsHover());
    }

    // test if the isHoverBuildTower is right
    @Test
    public void testIsHoverBuildTowerWhenMouseMovedOverIt() {
        MouseEvent event = createMouseEventM(650, 225);
        app.mouseMoved(event);
        assertTrue(app.getIsHoverBuildTower());
    }

    // test if the isHoverManaPool is right
    @Test
    public void testIsHoverManaPoolWhenMouseMovedOverIt() {
        MouseEvent event = createMouseEventM(650, 465);
        app.mouseMoved(event);
        assertTrue(app.getIsHoverManaPool());
    }

    // test if the isHoverP is right
    @Test
    public void testIsHoverPWhenMouseMovedOverIt() {
        MouseEvent event = createMouseEventM(650, 165);
        app.mouseMoved(event);
        assertTrue(app.getIsHoverP());
    }

    @Test
    public void testDoubleSpeed(){
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);
        app.setIsFFpressed(true);
    }

    // test if the isHoverFf is right
    @Test
    public void testIsHoverFfWhenMouseMovedOverIt() {
        MouseEvent event = createMouseEventM(650, 120);
        app.mouseMoved(event);
        assertTrue(app.getIsHoverFf());
    }

    // test if the isHoverU1 is right
    @Test
    public void testIsHoverU1WhenMouseMovedOverIt() {
        MouseEvent event = createMouseEventM(650, 270);
        app.mouseMoved(event);
        assertTrue(app.getIsHoverU1());
    }

    // test if the isHoverU2 is right
    @Test
    public void testIsHoverU2WhenMouseMovedOverIt() {
        MouseEvent event = createMouseEventM(650, 350);
        app.mouseMoved(event);
        assertTrue(app.getIsHoverU2());
    }

    // test if the isHoverU3 is right
    @Test
    public void testIsHoverU3WhenMouseMovedOverIt() {
        MouseEvent event = createMouseEventM(650, 400);
        app.mouseMoved(event);
        assertTrue(app.getIsHoverU3());
    }

    // test if the isHoverU4 is right
    @Test
    public void testIsHoverU4WhenMouseMovedOverIt() {
        MouseEvent event = createMouseEventM(650, 530);
        app.mouseMoved(event);
        assertTrue(app.getIsHoverU4());
    }

    // create the mouseEvent for test the mouseMoved()
    public MouseEvent createMouseEventM( int x, int y) {
        PApplet applet = new App();
        int modifiers = 0;
        int action = MouseEvent.MOVE;
        int button = 0;
        int count = 1;

        MouseEvent mouseEvent = new MouseEvent(applet,
                System.currentTimeMillis(),
                action,
                modifiers,
                x, y,
                button,
                count);

        return mouseEvent;
    }

    //set the boardlayout
    @BeforeEach
    public void setupBroadlayout() throws IOException {
        try (FileReader f1 = new FileReader(this.configPath)) {
            JSONObject jsonob = new JSONObject(f1);
            String layout = jsonob.getString("layout");
            File f2 = new File(layout);
            try (BufferedReader br = new BufferedReader(new FileReader(f2))) {
                int count = 0;
                String line;

                while ((line = br.readLine()) != null) {
                    for (int i = 0; i < line.length(); i++) {
                        boardlayout[count][i] = line.charAt(i);
                    }
                    for (int i = line.length(); i < BOARD_WIDTH; i++) {
                        boardlayout[count][i] = ' ';
                    }
                    count++;
                }
            }
        }
    }

    // test the checkValidPlace when we want to set a tower
    @Test
    public void testCheckValidPlace() {
        assertFalse(app.checkValidPlace(boardlayout, -1, 50));
        assertFalse(app.checkValidPlace(boardlayout, 650, 50));
        assertFalse(app.checkValidPlace(boardlayout, 300, -1));
        assertFalse(app.checkValidPlace(boardlayout, 300, 690));

        assertTrue(app.checkValidPlace(boardlayout, 320, 240));
        assertEquals('T', boardlayout[(240 - 40) / 32][320 / 32]);

        assertFalse(app.checkValidPlace(boardlayout, 200, 240));
    }

    // test if the different shape of path is right cognized
    // including intersection, triangle, corner and direction
    @Test
    public void testCheckShapeofPath(){  // Let the method throw exceptions
        assertTrue(app.checkIntersection(boardlayout, 5, 6));
        assertEquals(180.0, app.checkTriangle(boardlayout,5, 9 ));
        assertEquals(90.0, app.checkTriangle(boardlayout,7, 16 ));
        assertEquals(0.0, app.checkCorner(boardlayout, 3 , 4));
        assertEquals(90.0, app.checkCorner(boardlayout, 8 , 16));
        assertEquals(270.0, app.checkCorner(boardlayout, 8 , 10));
        assertEquals(90.0, app.checkDirection(boardlayout, 1 , 9));
        assertEquals(90.0, app.checkDirection(boardlayout, 0 , 9));
        assertEquals(0.0, app.checkDirection(boardlayout, 5 , 11));

        boardlayout[1][2] = 'X';
        boardlayout[1][1] = 'X';
        boardlayout[1][3] = 'X';
        boardlayout[2][2] = 'X';
        assertEquals(0.0, app.checkTriangle(boardlayout,1, 2 ));

        boardlayout[1][2] = 'X';
        boardlayout[1][1] = 'W';
        boardlayout[0][2] = 'X';
        boardlayout[1][3] = 'X';
        boardlayout[2][2] = 'X';
        assertEquals(270.0, app.checkTriangle(boardlayout,1,2 ));

        boardlayout[4][5] = 'X';
        boardlayout[4][6] = 'X';
        boardlayout[3][5] = 'X';
        boardlayout[4][4] = 'W';
        boardlayout[5][5] = 'W';
        assertEquals(180.0, app.checkCorner(boardlayout, 4 , 5));
    }

    // test the manaGained per frame
    @Test
    public void testManaUpdateOnCountFrame() {
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);
        app.setup();
        app.draw();
        assertEquals(200, app.getMana());
    }

    // test the limited of the mana: manaCap
    @Test
    public void testManaDoesNotExceedCap() {
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);
        app.setup();
        app.draw();
        assertEquals(200, app.getMana());
    }

    // test the tooltip of BuildTower
    @Test
    public void testHoverBuildTowerTooltip(){
        app.setIsHoverBuildTower(true);
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);
    }

    // test the tooltip of ManaPool
    @Test
    public void testHoverManaPoolTooltip(){
        app.setIsHoverManaPool(true);
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);
    }

    // test the tooltip of FrozenTower
    @Test
    public void testHoverU4Tooltip(){
        app.setIsHoverU4(true);
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);
    }

    // test the Lost state of the game
    @Test
    public void testLost() {
        PApplet.runSketch(new String[]{"APP"}, app);
        app.delay(200);
        app.setup();
        app.setState(GameState.LOST);
        assertEquals(GameState.LOST, app.getState());
    }

    //test the draw method of Tower class
    @Test
    public void testTower(){
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);

        Tower t = new Tower(96, 1.5, 40,224 , 104, app.loadImage("src/main/resources/WizardTD/tower0.png"), 240);
        app.getTowers().add(t);
        t.setIsHover(true);
        app.setIsU1pressed(true);
        app.setIsU2pressed(true);
        app.setIsU3pressed(true);

        t.setRangeLevel(2);
        t.setSpeedLevel(2);
        t.setDamageLevel(2);
    }

    // test the tower draw with minLevel less than rangeLevel
    @Test
    public void testTowerminLevelLessThanRangeLevel(){
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);

        Tower t = new Tower(96, 1.5, 40,224 , 104, app.loadImage("src/main/resources/WizardTD/tower0.png"), 240);
        app.getTowers().add(t);

        t.setRangeLevel(2);
        t.setSpeedLevel(1);
        t.setDamageLevel(1);
    }

    // test the tower draw with minLevel less than speedLevel
    @Test
    public void testTowerminLevelLessThanSpeedLevel(){
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);

        Tower t = new Tower(96, 1.5, 40,224 , 104, app.loadImage("src/main/resources/WizardTD/tower0.png"), 240);
        app.getTowers().add(t);

        t.setRangeLevel(1);
        t.setSpeedLevel(2);
        t.setDamageLevel(2);
    }

    // test the tower draw with minLevel less than damageLevel
    @Test
    public void testTowerminLevelLessThanDamageLevel(){
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);

        Tower t = new Tower(96, 1.5, 40,224 , 104, app.loadImage("src/main/resources/WizardTD/tower0.png"), 240);
        app.getTowers().add(t);

        t.setRangeLevel(1);
        t.setSpeedLevel(1);
        t.setDamageLevel(2);
    }

    // test the DeathAnimation of Monster
    @Test
    public void testMonsterDeathAnimation(){
        PApplet.runSketch(new String[] {"APP"}, app);
        app.delay(200);
        Monster m = new Monster();
        assertEquals(6, m.getDeathAnimationImages().length);
        assertNotEquals(23, m.getDeathAnimationFrame());
    }
}



