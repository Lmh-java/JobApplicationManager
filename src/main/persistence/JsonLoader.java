package persistence;

import model.ApplicationPortfolio;
import model.PositionApplication;
import model.QuestionAndAnswerItem;
import model.QuestionTemplatePortfolio;
import org.json.JSONObject;

import java.io.*;
import java.util.Date;

/**
 * JsonLoader loads the saved data to the program
 * CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class JsonLoader {
    private final String loadPath;

    // REQUIRES: loadPath != null
    // EFFECTS: initialize an instance of JsonLoader with given load path.
    public JsonLoader(String loadPath) {
        this.loadPath = loadPath;
    }

    // EFFECTS: loads in the json object from loadPath
    //          FileNotFoundException is thrown if the file is not located at loadPath
    public JSONObject loadJsonObject() throws IOException {
        // CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        StringBuilder jsonString = new StringBuilder();
        try (FileReader fileReader = new FileReader(this.loadPath);
                        BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            bufferedReader.lines().forEach(jsonString::append);
        }
        return convertStringToJsonObject(jsonString.toString());
    }

    // REQUIRES: jsonString is a json-like string
    // EFFECTS: converts the provided string to a json object
    private JSONObject convertStringToJsonObject(String jsonString) {
        return new JSONObject(jsonString);
    }

    // EFFECTS: converts the provided json object to an ApplicationPortfolio object
    public ApplicationPortfolio parseApplicationPortfolio(JSONObject object) {
        // CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        ApplicationPortfolio portfolio = new ApplicationPortfolio(object.getString("name"));
        for (Object applicationJson : object.getJSONArray("applications")) {
            addApplication(portfolio, (JSONObject) applicationJson);
        }
        return portfolio;
    }

    // EFFECTS: converts the provided json object to a QuestionTemplatePortfolio object
    public QuestionTemplatePortfolio parseQuestionTemplatePortfolio(JSONObject object) {
        // CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        QuestionTemplatePortfolio portfolio = new QuestionTemplatePortfolio(object.getString("name"));
        for (Object itemJson : object.getJSONArray("QAs")) {
            addQuestionItem(portfolio, (JSONObject) itemJson);
        }
        return portfolio;
    }

    // REQUIRES: portfolio != null
    // MODIFIES: portfolio
    // EFFECTS: parses the question and answer item from jsonObject and adds it to portfolio
    public void addQuestionItem(QuestionTemplatePortfolio portfolio, JSONObject jsonObject) {
        QuestionAndAnswerItem item = new QuestionAndAnswerItem(
                jsonObject.getString("question"),
                jsonObject.getString("answer"));
        item.setId(jsonObject.getString("id"));
        portfolio.add(item);
    }

    // REQUIRES: portfolio != null
    // MODIFIES: portfolio
    // EFFECTS: parses the application from jsonObject and adds it to portfolio
    public void addApplication(ApplicationPortfolio portfolio, JSONObject jsonObject) {
        PositionApplication application = new PositionApplication(jsonObject.getString("organization"),
                jsonObject.getString("position"),
                new Date(jsonObject.getLong("submitDate")),
                jsonObject.getString("infoLink"),
                jsonObject.getBoolean("isResearchApplication"));
        application.setId(jsonObject.getString("id"));
        application.setStatus(jsonObject.getInt("status"));
        application.setFollowUpDate(new Date(jsonObject.getLong("followUpDate")));
        portfolio.add(application);
    }
}
