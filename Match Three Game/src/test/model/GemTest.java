package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GemTest {
    // constructor and getType have been tested as part of helpers of other class' methods

    Gem aluminium;
    Gem chromium;
    Gem feldspar;

    @BeforeEach
    public void setup() {
        aluminium = new Gem(Gem.ALUMINIUM);
        chromium = new Gem(Gem.CHROMIUM);
        feldspar = new Gem(Gem.FELDSPAR);
    }

    @Test
    public void testSetType() {
        aluminium.setType(Gem.BRONZE);
        assertEquals(Gem.BRONZE, aluminium.getType());
    }
}
