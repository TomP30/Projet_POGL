package model.tests;
import static org.junit.Assert.*;

import junit.framework.TestCase;
import model.*;
import org.junit.*;
public class CaseTest extends TestCase{

    
    public void constructWithoutArtifacts() throws Exception{
        Case acase = new Case(new Coord(0,0));
        assertEquals(acase.getCoord().get_x(),0);
        assertEquals(acase.getCoord().get_y(),0);
        assertEquals(acase.getInnondation(),0);
    }


}