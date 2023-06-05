package com.kpi.computergraphics.lab3.service;

import com.kpi.computergraphics.lab3.scene.ReaderOBJ;
import com.kpi.computergraphics.lab3.base.Ray;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.scene.objects.PolygonMesh;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ReaderOBJTest {

    @Test
    void testReadAndIntersect() throws IOException {
        PolygonMesh mesh = ReaderOBJ.readStream(getClass().getClassLoader().getResourceAsStream("cow.obj"));

        Ray ray = new Ray(new Vector3D(0, 0, -2000),
                new Vector3D(0.353308090492502,
                0.4033939117500598,
                0.8440656047698705));
        assertTrue(mesh.findIntersection(ray).isPresent());
    }

}
