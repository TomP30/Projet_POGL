package model.tests;
import junit.framework.*;
import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class JoueurTest {
    Joueur J1 = new Joueur(new Coord(1,3),4);
    Joueur J2 = new Joueur(new Coord(4,3),6);

    @Test
    public void constructorTest(){
        Joueur J = new Joueur(new Coord(2,2),3);
        assertFalse(J.hasInventoryFull());
        assertTrue(J.getCoord().equals(new Coord(2,2)));
        assertEquals(J.getActions(),3);
    }

    @Test
    public void InventoryTest(){
        for (int i = 0;i<3;i++){
            J1.addKey(Type.Wind);
        }
        assertFalse(J1.hasInventoryFull());
        J1.addKey(Type.Fire);
        assertTrue(J1.hasInventoryFull());
        J1.donnerClef(J2,Type.Fire);
        assertFalse(J1.hasInventoryFull());
        assertEquals(J2.getKeys()[0],Type.Fire);
    }
}
