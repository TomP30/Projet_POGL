package model.tests;

import junit.framework.*;
import model.*;
import org.junit.*;
import org.junit.Test;

public class GrilleTest extends TestCase{
    @Test
    public void Constructor_Test() throws Exception {
        Coord[] arts = new Coord[4];
        arts[0] = new Coord(1,3);
        arts[1] = new Coord(2,3);
        arts[2] = new Coord(3,3);
        arts[3] = new Coord(4,3);
        Grille g = new Grille(new Coord(1,1),arts);
        assertTrue(g.getCase(1,3).hasArtifact());
    }
}

