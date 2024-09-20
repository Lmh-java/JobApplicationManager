package persistence;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * JsonSaver saves the current state of the program to json file
 */
public class JsonSaver {

    private final String targetPath;

    // REQUIRES: targetPath != null
    // EFFECTS: create an instance of JsonSaver with a given save path
    public JsonSaver(String targetPath) {
        this.targetPath = targetPath;
    }

    // REQUIRES: jsonObject != null
    // EFFECTS: writes the given object to the target path
    //          FileNotFoundException is thrown if file at targetPath is not found
    public void saveToFile(JSONObject jsonObject) throws FileNotFoundException {
        // CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        PrintWriter writer = new PrintWriter(targetPath);
        writer.write(jsonObject.toString(4));
        writer.close();
    }
}
