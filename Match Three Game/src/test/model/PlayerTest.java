package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest extends UtilityTest {
    Player player;

    @BeforeEach
    public void setup() {
        player = new Player();
        setupNoMatchThreeGrid();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, player.getPoints());
    }

    @Test
    public void testGetPointsAndSetPoints() {
        player.setPoints(20);
        assertEquals(20, player.getPoints());

        player.setPoints(player.getPoints() + 30);
        assertEquals(50, player.getPoints());
    }

    @Test
    public void testRemoveRowNotEnoughPoints() {
        player.setPoints(Player.PTS_FOR_REMOVE_ROW - 1);
        assertFalse(player.removeRow(noMatchThreeGrid, 2));
        Gem[][] expectedGrid = new Gem[][]{
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM)},
                {new Gem(Gem.BRONZE), new Gem(Gem.BRONZE), new Gem(Gem.ALUMINIUM),
                        new Gem(Gem.BRONZE), new Gem(Gem.FELDSPAR)},
                {new Gem(Gem.CHROMIUM), new Gem(Gem.CHROMIUM), new Gem(Gem.DIAMOND),
                        new Gem(Gem.FELDSPAR), new Gem(Gem.CHROMIUM)},
                {new Gem(Gem.DIAMOND), new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD),
                        new Gem(Gem.DIAMOND), new Gem(Gem.DIAMOND)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM)}};
        checkGridsSame(expectedGrid, noMatchThreeGrid);
        assertEquals(Player.PTS_FOR_REMOVE_ROW - 1, player.getPoints());
    }

    @Test
    public void testRemoveRowEnoughPoints() {
        player.setPoints(Player.PTS_FOR_REMOVE_ROW);
        assertTrue(player.removeRow(noMatchThreeGrid, 2));
        Gem[][] expectedGrid = new Gem[][]{
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM)},
                {new Gem(Gem.BRONZE), new Gem(Gem.BRONZE), new Gem(Gem.ALUMINIUM),
                        new Gem(Gem.BRONZE), new Gem(Gem.FELDSPAR)},
                {null, null, null, null, null},
                {new Gem(Gem.DIAMOND), new Gem(Gem.DIAMOND), new Gem(Gem.EMERALD),
                        new Gem(Gem.DIAMOND), new Gem(Gem.DIAMOND)},
                {new Gem(Gem.ALUMINIUM), new Gem(Gem.FELDSPAR), new Gem(Gem.BRONZE),
                        new Gem(Gem.ALUMINIUM), new Gem(Gem.ALUMINIUM)}};
        checkGridsSame(expectedGrid, noMatchThreeGrid);
        assertEquals(0, player.getPoints());
    }
}
