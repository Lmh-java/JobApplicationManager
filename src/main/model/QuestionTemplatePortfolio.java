package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonConvertible;

import java.util.ArrayList;
import java.util.List;

/**
 * QuestionTemplatePortfolio represents a portfolio for application questions and answers
 */
public class QuestionTemplatePortfolio implements Containable<QuestionAndAnswerItem>, JsonConvertible {

    // name of the portfolio
    private String name;

    // list of question and answer
    private final List<QuestionAndAnswerItem> questionList;

    // event logger
    private final EventLog eventLog;

    // REQUIRES: name != null
    // EFFECTS: creates an instance of this class with given portfolio name
    public QuestionTemplatePortfolio(String name) {
        this.name = name;
        this.questionList = new ArrayList<>();
        this.eventLog = EventLog.getInstance();
    }

    // REQUIRES: item != null
    // MODIFIES: this
    // EFFECTS: adds a question and answer pair to the portfolio
    @Override
    public void add(QuestionAndAnswerItem item) {
        questionList.add(item);
        eventLog.logEvent(new Event(String.format("[Question] %s is added to question portfolio", item.getId())));
    }

    // MODIFIES: this
    // EFFECTS: deletes the question with the given id and return whether the item is removed
    @Override
    public boolean delete(String id) {
        if (questionList.removeIf((item) -> item.getId().equals(id))) {
            eventLog.logEvent(new Event(String.format("[Question] %s is removed", id)));
            return true;
        }
        return false;
    }

    // EFFECTS: returns the question item with the given id
    @Override
    public QuestionAndAnswerItem search(String id) {
        for (QuestionAndAnswerItem item : questionList) {
            if (item.getId().equals(id)) {
                eventLog.logEvent(
                        new Event(String.format(
                                "[Question] %s is found in question portfolio by searching %s",
                                item.getId(),
                                id))
                );
                return item;
            }
        }
        eventLog.logEvent(new Event(String.format(
                "[Question] Nothing is found in question portfolio by searching %s",
                id)));
        return null;
    }

    // EFFECTS: returns the list of question and answer items
    @Override
    public List<QuestionAndAnswerItem> getList() {
        return questionList;
    }

    // EFFECTS: returns
    @Override
    public int getNum() {
        return questionList.size();
    }

    // EFFECTS: returns the name of the portfolio
    @Override
    public String getName() {
        return this.name;
    }

    // MODIFIES: this
    // EFFECTS: sets the portfolio name to the given name
    @Override
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: returns the first item in the portfolio whose id starts with prefix
    @Override
    public QuestionAndAnswerItem searchByIdPrefix(String prefix) {
        for (QuestionAndAnswerItem item : questionList) {
            if (item.getId().startsWith(prefix)) {
                new Event(String.format(
                        "[Question] %s is found in question portfolio by searching %s",
                        item.getId(),
                        prefix));
                return item;
            }
        }
        eventLog.logEvent(new Event(String.format(
                "[Question] Nothing is found in question portfolio by searching %s",
                prefix)));
        return null;
    }

    // EFFECTS: returns a list of display string of all items
    @Override
    public List<String> getDisplayString() {
        List<String> resultList = new ArrayList<>();
        for (QuestionAndAnswerItem item : questionList) {
            resultList.add(String.format("[%s]\t Q: %s \n\t\t A: %s", item.getId().substring(0, 5), item.getQuestion(),
                    item.getAnswer()));
        }
        return resultList;
    }

    // REQUIRES: item.getId() != null && item is in the portfolio
    // EFFECTS: modifies item with the given id in the portfolio
    @Override
    public void modify(QuestionAndAnswerItem item) {
        QuestionAndAnswerItem targetItem = search(item.getId());
        if (targetItem != null) {
            targetItem.setQuestion(item.getQuestion());
            targetItem.setAnswer(item.getAnswer());
            eventLog.logEvent(new Event(String.format(
                    "[Question] %s is updated to Q: %s A: %s",
                    targetItem.getId(),
                    item.getQuestion(),
                    item.getAnswer())));
        }
    }

    // EFFECTS: returns a json object representing the current instance
    @Override
    public JSONObject toJson() {
        // CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", getName());
        jsonObject.put("QAs", itemsToJson());
        return jsonObject;
    }

    // EFFECTS: returns a json array representing the list of QuestionAndAnswerItem
    public JSONArray itemsToJson() {
        // CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        JSONArray jsonArray = new JSONArray();
        for (QuestionAndAnswerItem item : getList()) {
            jsonArray.put(item.toJson());
        }
        return jsonArray;
    }
}
