package com.cgvsu.objwriter;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.objreader.ObjReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class ObjWriterTest {

    @Test
    public void testOutputVertices() {
        List<Vector3f> input = new ArrayList<>();
        input.add(new Vector3f(1, 0.2f, 1000));
        input.add(new Vector3f(-200, 344, 88));
        String output = ObjWriter.outputVertices(input);
        Assertions.assertTrue(output.equals("v 1.0 0.2 1000.0\nv -200.0 344.0 88.0\n"));
    }

    @Test
    public void testOutputTextureVertices() {
        List<Vector2f> input = new ArrayList<>();
        input.add(new Vector2f(0, 0));
        input.add(new Vector2f(300, -300));
        String output = ObjWriter.outputTextureVertices(input);
        Assertions.assertTrue(output.equals("vt 0.0 0.0\nvt 300.0 -300.0\n"));
    }

    @Test
    public void testOutputNormals() {
        List<Vector3f> input = new ArrayList<>();
        input.add(new Vector3f(200, 80, 3000));
        input.add(new Vector3f(-200, 344, 88));
        String output = ObjWriter.outputNormals(input);
        Assertions.assertTrue(output.equals("vn 200.0 80.0 3000.0\nvn -200.0 344.0 88.0\n"));
    }

    @Test
    public void testOutputPolygons() {
        List<Polygon> input = new ArrayList<>();
        Polygon p1 = new Polygon();
        Polygon p2 = new Polygon();
        Polygon p3 = new Polygon();
        Polygon p4 = new Polygon();
        ArrayList<Integer> vI = new ArrayList<>();
        vI.add(3);
        vI.add(5);
        vI.add(6);
        ArrayList<Integer> vtI = new ArrayList<>();
        vtI.add(3);
        vtI.add(5);
        vtI.add(6);
        ArrayList<Integer> vnI = new ArrayList<>();
        vnI.add(3);
        vnI.add(5);
        vnI.add(6);
        p1.setVertexIndices(vI);

        p2.setVertexIndices(vI);
        p2.setTextureVertexIndices(vtI);

        p3.setVertexIndices(vI);
        p3.setNormalIndices(vnI);

        p4.setVertexIndices(vI);
        p4.setTextureVertexIndices(vtI);
        p4.setNormalIndices(vnI);

        input.add(p1);
        input.add(p2);
        input.add(p3);
        input.add(p4);
        String output = ObjWriter.outputPolygons(input);
        Assertions.assertTrue(output.equals("f 3 5 6\nf 3/3 5/5 6/6\n" +
                "f 3//3 5//5 6//6\nf 3/3/3 5/5/5 6/6/6\n"));
    }
}
