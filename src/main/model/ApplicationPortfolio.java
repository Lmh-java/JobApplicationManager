package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonConvertible;

import java.util.ArrayList;
import java.util.List;


/**
 * ApplicationPortfolio class represents a collection of different applications
 */
public class ApplicationPortfolio implements Containable<Application>, JsonConvertible {
    // A list of job applications
    private final List<Application> applicationList;

    // portfolio name
    private String name;

    // event logger
    private final EventLog eventLog;

    // EFFECTS: create a portfolio with given name and an empty application list
    public ApplicationPortfolio(String name) {
        this.name = name;
        this.applicationList = new ArrayList<>();
        eventLog = EventLog.getInstance();
    }

    // REQUIRES: searchApplicationById(application.getId()) == null
    // MODIFIES: this
    // EFFECTS: adds a new application to the portfolio
    public void add(Application application) {
        applicationList.add(application);
        eventLog.logEvent(new Event(String.format("[App] %s is added to app portfolio", application.getId())));
    }

    // EFFECTS: returns the application in the portfolio with the given id. Returns null if not found
    public Application search(String id) {
        for (Application app : this.applicationList) {
            if (app.getId().equals(id)) {
                eventLog.logEvent(
                        new Event(String.format(
                                "[App] %s is found in app portfolio by searching %s",
                                app.getId(),
                                id)));
                return app;
            }
        }
        eventLog.logEvent(new Event(String.format("[App] Nothing is found in app portfolio by searching %s", id)));
        return null;
    }

    // REQUIRES: id != null
    // MODIFIES: this
    // EFFECTS: delete application in the portfolio.
    //          If the application is not in the portfolio, returns false. If the application is successfully deleted,
    //          return true.
    public boolean delete(String id) {
        if (this.applicationList.removeIf((app) -> id.equals(app.getId()))) {
            eventLog.logEvent(new Event(String.format("[App] %s is removed", id)));
            return true;
        }
        return false;
    }

    // EFFECTS: get list of all applications in the portfolio
    public List<Application> getList() {
        return applicationList;
    }

    // EFFECTS: returns the portfolio name
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: change the portfolio name to the provided new name
    public void setName(String newName) {
        this.name = newName;
    }

    // EFFECTS: returns the application whose id starting with prefix
    @Override
    public Application searchByIdPrefix(String prefix) {
        for (Application app : this.applicationList) {
            if (app.getId().startsWith(prefix)) {
                eventLog.logEvent(
                        new Event(String.format(
                                "[App] %s is found in app portfolio by searching %s",
                                app.getId(),
                                prefix))
                );
                return app;
            }
        }
        eventLog.logEvent(new Event(String.format("[App] Nothing is found in app portfolio by searching %s", prefix)));
        return null;
    }

    // EFFECTS: returns the number of applications in the portfolio
    @Override
    public int getNum() {
        return this.applicationList.size();
    }

    // EFFECTS: returns list of display string of items
    @Override
    public List<String> getDisplayString() {
        List<String> displayNames = new ArrayList<>();
        for (Application app : this.applicationList) {
            displayNames.add(app.getDisplayName());
        }
        return displayNames;
    }

    // REQUIRES: app.getId() != null && app is in the portfolio
    // EFFECTS: modifies application with the given id in the container
    @Override
    public void modify(Application app) {
        PositionApplication papp = (PositionApplication) app;
        PositionApplication targetApp = (PositionApplication) search(app.getId());
        if (targetApp != null) {
            targetApp.setFollowUpDate(papp.getFollowUpDate());
            targetApp.setStatus(papp.getStatus());
            targetApp.setInfoLink(papp.getInfoLink());
            targetApp.setSubmitDate(papp.getSubmitDate());
            targetApp.setIsResearchApplication(papp.getIsResearchApplication());
            targetApp.setOrganization(papp.getOrganization());
            targetApp.setPosition(papp.getPosition());
            eventLog.logEvent(new Event(String.format("[App] %s is updated to %s",
                    targetApp.getId(),
                    papp.getDisplayName())));
        }
    }

    // EFFECTS: returns a json object representing the current instance
    @Override
    public JSONObject toJson() {
        // CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", getName());
        jsonObject.put("applications", applicationsToJson());
        return jsonObject;
    }

    // EFFECTS: returns a json array representing the list of Applications
    public JSONArray applicationsToJson() {
        // CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        JSONArray jsonArray = new JSONArray();
        for (Application item : getList()) {
            jsonArray.put(((PositionApplication) item).toJson());
        }
        return jsonArray;
    }
}
