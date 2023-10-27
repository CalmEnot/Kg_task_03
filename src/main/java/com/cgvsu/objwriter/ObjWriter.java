package com.cgvsu.objwriter;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ObjWriter {

    public static boolean write(Model model, String path) throws IOException {
        if (writeInfo(model, path)) {
            return true;
        } else {
            return false;
        }
    }

    protected static boolean writeInfo(Model model, String path) {
        StringBuilder output = new StringBuilder();
        output.append(outputVertices(model));
        output.append(outputTextureVertices(model));
        output.append(outputNormals(model));
        output.append(outputPolygons(model));
        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write(output.toString());
        }
        catch (IOException ex) {
            System.out.println("Process failed! Error: " + ex.getMessage());
            return false;
        }
        return true;
    }

    protected static String outputVertices(final Model model) {
        StringBuilder output = new StringBuilder();
        List<Vector3f> vertices = model.vertices;
        String token = "v";
        for (int i = 0; i < vertices.size(); i++) {
            output.append(token).append(" ").append(vertices.get(i).x).append(" ")
                    .append(vertices.get(i).y).append(" ").append(vertices.get(i).z).append("\n");
        }
        return output.toString();
    }

    protected static String outputTextureVertices(final Model model) {
        StringBuilder output = new StringBuilder();
        List<Vector2f> textureVertices = model.textureVertices;
        String token = "vt";
        for (int i = 0; i < textureVertices.size(); i++) {
            output.append(token).append(" ").append(textureVertices.get(i).x).append(" ")
                    .append(textureVertices.get(i).y).append("\n");
        }
        return output.toString();
    }

    protected static String outputNormals(final Model model) {
        StringBuilder output = new StringBuilder();
        List<Vector3f> normals = model.normals;
        String token = "vn";
        for (int i = 0; i < normals.size(); i++) {
            output.append(token).append(" ").append(normals.get(i).x).append(" ")
                    .append(normals.get(i).y).append(" ").append(normals.get(i).z).append("\n");
        }
        return output.toString();
    }

    protected static String outputPolygons(final Model model) {
        StringBuilder output = new StringBuilder();
        List<Polygon> polygons = model.polygons;
        String token = "f";
        for (int i = 0; i < polygons.size(); i++) {
            output.append(token).append(editOutputPolygon(polygons.get(i))).append("\n");
        }
        return output.toString();
    }

    protected static String editOutputPolygon(final Polygon polygon) {
        StringBuilder output = new StringBuilder();
        List<Integer> vertexIndices = polygon.getVertexIndices();
        List<Integer> textureVertexIndices = polygon.getTextureVertexIndices();
        List<Integer> normalIndices = polygon.getNormalIndices();
        final int size = Math.max(Math.max(vertexIndices.size(), textureVertexIndices.size()), normalIndices.size());
        if (textureVertexIndices.size() == 0) {
            if (normalIndices.size() == 0) {
                for (int i = 0; i < size; i++) {
                    output.append(" ").append(vertexIndices.get(i));
                }
            } else {
                for (int i = 0; i < size; i++) {
                    output.append(" ").append(vertexIndices.get(i)).append("//").append(normalIndices.get(i));
                }
            }
        } else {
            if (normalIndices.size() == 0) {
                for (int i = 0; i < size; i++) {
                    output.append(" ").append(vertexIndices.get(i)).append("/").append(textureVertexIndices.get(i));
                }
            } else {
                for (int i = 0; i < size; i++) {
                    output.append(" ").append(vertexIndices.get(i)).append("/").append(textureVertexIndices.get(i))
                            .append("/").append(normalIndices.get(i));
                }
            }
        }
        return output.toString();
    }
}
