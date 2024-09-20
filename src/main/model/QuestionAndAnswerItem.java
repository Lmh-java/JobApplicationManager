package model;

import org.json.JSONObject;
import persistence.JsonConvertible;

import java.util.UUID;

/**
 * QuestionAndAnswerPair represents a question and an answer item.
 *  The applicants always need to answer various questions to submit valid job applications.
 *  Therefore, this program also allows them
 */
public class QuestionAndAnswerItem implements JsonConvertible {

    // id
    private String id;

    // application question
    private String question;

    // answer to the question
    private String answer;

    // EFFECTS: create an instance of this class with given question and answer. At the same time,
    //          a random id is generated.
    public QuestionAndAnswerItem(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.id = UUID.randomUUID().toString();
    }

    // EFFECTS: returns answer to the question
    public String getAnswer() {
        return answer;
    }

    // EFFECTS: returns the application question
    public String getQuestion() {
        return question;
    }

    // MODIFIES: this
    // EFFECTS: updates the answer to the question
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    // MODIFIES: this
    // EFFECTS: updates the question
    public void setQuestion(String question) {
        this.question = question;
    }

    // EFFECTS: returns the id of the item
    public String getId() {
        return this.id;
    }

    // REQUIRES: id != null
    // MODIFIES: this
    // EFFECTS: set the id of the question and answer item to the given id
    public void setId(String id) {
        this.id = id;
    }

    // EFFECTS: returns the json object for this class
    @Override
    public JSONObject toJson() {
        // CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", getId());
        jsonObject.put("question", getQuestion());
        jsonObject.put("answer", getAnswer());
        return jsonObject;
    }
}
