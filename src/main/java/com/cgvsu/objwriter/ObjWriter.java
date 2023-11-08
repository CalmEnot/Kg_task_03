package com.cgvsu.objwriter;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для записи модели в файл в формате .obj
 * @version 2.0
 */
public class ObjWriter {
    /**
     * Основно метод, который пытается записать передаваемую модель. Если не получается, выводит ошибку.
     * @param model - модель, которую надо записать
     * @param path - путь, по которому нужно записать, формат String
     * @return Возвращает boolean значение. true - удалось записать в файл, false - не удалось
     */
    public static boolean write(Model model, String path) {
        // Формирование строки
        String output = makeOutputString(model.vertices, model.textureVertices, model.normals, model.polygons);
        // Попытка записи в файл
        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write(output);
        }
        catch (IOException ex) {
            System.out.println("Process failed! Error: " + ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Метод для формирования строки из данных модели
     * @param vertices - вершины модели
     * @param textureVertices - текстурные вершины модели
     * @param normals - нормали модели
     * @param polygons - полигон модели
     * @return - сформированная строка
     */
    protected static String makeOutputString(final ArrayList<Vector3f> vertices, final ArrayList<Vector2f> textureVertices,
             final ArrayList<Vector3f> normals, final ArrayList<Polygon> polygons) {
        StringBuilder output = new StringBuilder();
        // Проверки на пустоту полей модели
        if (vertices.size() > 0) {
            output.append(outputVertices(vertices));
            output.append("# ").append(vertices.size()).append(" vertices").append("\n\n");
        }
        if (textureVertices.size() > 0) {
            output.append(outputTextureVertices(textureVertices));
            output.append("# ").append(textureVertices.size()).append(" texture coords").append("\n\n");
        }
        if (normals.size() > 0) {
            output.append(outputNormals(normals));
            output.append("# ").append(normals.size()).append(" vertex normals").append("\n\n");
        }
        if (polygons.size() > 0) {
            output.append(outputPolygons(polygons));
            output.append("# ").append(polygons.size()).append(" polygons").append("\n\n");
        }
        return output.toString();
    }

    /**
     * Метод для формирования строк вершин
     * @param vertices - массив вершин
     * @return - строка с данными о вершинах
     */
    protected static String outputVertices(final List<Vector3f> vertices) {
        StringBuilder output = new StringBuilder();
        String token = "v";
        for (int i = 0; i < vertices.size(); i++) {
            output.append(token).append(" ").append(vertices.get(i).x).append(" ")
                    .append(vertices.get(i).y).append(" ").append(vertices.get(i).z).append("\n");
        }
        return output.toString();
    }

    /**
     * Метод для формирования строк текстурных вершин
     * @param textureVertices - массив вершин
     * @return - строка с данными о вершинах
     */
    protected static String outputTextureVertices(final List<Vector2f> textureVertices) {
        StringBuilder output = new StringBuilder();
        String token = "vt";
        for (int i = 0; i < textureVertices.size(); i++) {
            output.append(token).append(" ").append(textureVertices.get(i).x).append(" ")
                    .append(textureVertices.get(i).y).append("\n");
        }
        return output.toString();
    }

    /**
     * Метод для формирования строк нормалей
     * @param normals - массив вершин
     * @return - строка с данными о вершинах
     */
    protected static String outputNormals(final List<Vector3f> normals) {
        StringBuilder output = new StringBuilder();
        String token = "vn";
        for (int i = 0; i < normals.size(); i++) {
            output.append(token).append(" ").append(normals.get(i).x).append(" ")
                    .append(normals.get(i).y).append(" ").append(normals.get(i).z).append("\n");
        }
        return output.toString();
    }

    /**
     * Метод для формирования строк полигона
     * @param polygons - массив вершин
     * @return - строка с данными о вершинах
     */
    protected static String outputPolygons(final List<Polygon> polygons) {
        StringBuilder output = new StringBuilder();
        String token = "f";
        for (int i = 0; i < polygons.size(); i++) {
            output.append(token).append(editOutputPolygon(polygons.get(i))).append("\n");
        }
        return output.toString();
    }

    /**
     * Формирование строки для одного полигона, с обработкой условий, есть ли текстурные вершины или нормали
     * @param polygon - полигон, который надо обработать
     * @return - сформированная строка для полигона
     */
    protected static String editOutputPolygon(final Polygon polygon) {
        StringBuilder output = new StringBuilder();
        List<Integer> vertexIndices = polygon.getVertexIndices();
        List<Integer> textureVertexIndices = polygon.getTextureVertexIndices();
        List<Integer> normalIndices = polygon.getNormalIndices();
        final int size = Math.max(Math.max(vertexIndices.size(), textureVertexIndices.size()), normalIndices.size());
        // Проверка на наличие текстурных вершин и нормалей
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
