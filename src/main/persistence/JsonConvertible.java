package persistence;

import org.json.JSONObject;

/**
 * JsonConvertible interface describes the ability for a java class to be converted to JSON string
 * Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public interface JsonConvertible {
    // EFFECTS: returns a json object represents the current class
    JSONObject toJson();
}
