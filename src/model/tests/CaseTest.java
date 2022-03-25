package model.tests;
import static org.junit.Assert.*;

import junit.framework.*;
import model.*;
import org.junit.*;
import org.junit.Test;

public class CaseTest extends TestCase{

    @Test
    public void constructWithoutArtifacts() throws Exception{
        Case acase = new Case(new Coord(0,0));
        assertEquals(acase.getCoord().get_x(),0);
        assertEquals(acase.getCoord().get_y(),0);
        assertEquals(acase.getInnondation(),0);
    }


}