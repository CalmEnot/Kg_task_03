package com.cgvsu.objwriter;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class ObjWriterTest {

    @Test
    public void testFile1() throws IOException {
        String input = "SimpleModelsForReaderTests/Test02.obj";
        String output = "testWriter.obj";

        Path fileNameInput = Path.of(input);
        String fileContentInput = Files.readString(fileNameInput);
        Model modelInput = ObjReader.read(fileContentInput);

        ObjWriter.write(modelInput, output);

        Path fileNameOutput = Path.of(output);
        String fileContentOutput = Files.readString(fileNameOutput);
        Model modelOutput = ObjReader.read(fileContentOutput);
        Assertions.assertTrue(equalsModels(modelInput, modelOutput));
    }

    @Test
    public void testFile2() throws IOException {
        String input = "Faceform/WrapBody.obj";
        String output = "testWriter.obj";

        Path fileNameInput = Path.of(input);
        String fileContentInput = Files.readString(fileNameInput);
        Model modelInput = ObjReader.read(fileContentInput);

        ObjWriter.write(modelInput, output);

        Path fileNameOutput = Path.of(output);
        String fileContentOutput = Files.readString(fileNameOutput);
        Model modelOutput = ObjReader.read(fileContentOutput);
        Assertions.assertTrue(equalsModels(modelInput, modelOutput));
    }

    private static boolean equalsModels(Model input, Model output) throws IOException {
        if (input.normals.size() != output.normals.size()) {
            return false;
        }
        if (input.polygons.size() != output.polygons.size()) {
            return false;
        }
        if (input.textureVertices.size() != output.textureVertices.size()) {
            return false;
        }
        if (input.vertices.size() != output.vertices.size()) {
            return false;
        }
        return true;
    }
}
