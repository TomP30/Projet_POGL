package model.tests;

import junit.framework.*;
import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GrilleTest  {
    Coord[] arts = {new Coord(1, 3), new Coord(2,3),new Coord(3,3), new Coord(4,3)};
    Grille g = new Grille(new Coord(1,1),arts);

    public GrilleTest() throws  Exception{}
    @Test
    public void Constructor_Test() throws Exception {

        assertTrue(g.getCase(1,3).hasArtifact());
        assertTrue(g.getCase(2,3).hasArtifact());
        assertTrue(g.getCase(3,3).hasArtifact());
        assertTrue(g.getCase(4,3).hasArtifact());

        assertEquals(g.getHeliport().getCoord().get_x(), 1);
        assertEquals(g.getHeliport().getCoord().get_y(), 1);
    }
    @Test
    public void Test_validCoord() throws Exception {
        assertFalse(g.isValidCoord(new Coord(0,0)));
        assertFalse(g.isValidCoord(new Coord(1,0)));
        assertFalse(g.isValidCoord(new Coord(0,1)));

        assertFalse(g.isValidCoord(new Coord(5,5)));
        assertFalse(g.isValidCoord(new Coord(5,4)));
        assertFalse(g.isValidCoord(new Coord(4,5)));

        assertFalse(g.isValidCoord(new Coord(5,0)));
        assertFalse(g.isValidCoord(new Coord(5,1)));
        assertFalse(g.isValidCoord(new Coord(4,0)));

        assertFalse(g.isValidCoord(new Coord(0,5)));
        assertFalse(g.isValidCoord(new Coord(0,4)));
        assertFalse(g.isValidCoord(new Coord(1,5)));

        for (int i = 1;i<5;i++){
            for (int j = 1;j<5;j++){
                assertTrue(g.isValidCoord(new Coord(i,j)));
            }
        }
        for (int i = 2;i<4;i++){
            assertTrue(g.isValidCoord(new Coord(0,i)));
            assertTrue(g.isValidCoord(new Coord(5,i)));
            assertTrue(g.isValidCoord(new Coord(i,0)));
            assertTrue(g.isValidCoord(new Coord(i,5)));
        }
    }
    @Test
    public void Test_neighbours() throws Exception {
        ArrayList<Case> neighb1 = g.neighbours(g.getCase(3,3),1);
        assertTrue(neighb1.contains(g.getCase(2,3)));
    }
}

