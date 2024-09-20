package model;

import org.json.JSONObject;
import persistence.JsonConvertible;
import util.DateUtils;

import java.util.Date;


/**
 * PositionApplication class represents a position application that needs to be tracked by our program
 */
public class PositionApplication extends Application implements JsonConvertible {

    // employer organization applied to
    private String organization;

    // job position applied to
    private String position;

    // number of days between each follow up
    public static final int FOLLOW_UP_INTERVAL = 7;

    // is this application a research application
    private boolean isResearchApplication;

    // EFFECTS: create an application with given organization name, position name, submit date and job info link
    public PositionApplication(String organization, String position, Date submitDate,
                               String infoLink, boolean isResearchApplication) {
        super(submitDate, infoLink);
        this.organization = organization;
        this.position = position;
        this.isResearchApplication = isResearchApplication;
    }

    // MODIFIES: this
    // EFFECTS: set employer organization to the provided organization
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    // MODIFIES: this
    // EFFECTS: set applied position to the provided position
    public void setPosition(String position) {
        this.position = position;
    }

    // EFFECTS: return the organization name of application
    public String getOrganization() {
        return organization;
    }

    // EFFECTS: returns the name of current position applied
    public String getPosition() {
        return position;
    }

    // MODIFIES: this
    // EFFECTS: recalculate the next follow-up date.
    //          If the application is still active (super.getStatus() == Application.WAITING_FOR_RESPONSE_CODE
    //          || super.getStatus() == Application.WAITLISTED_CODE), then the next
    //          follow-up date will be FOLLOW_UP_INTERVAL days after last follow up (or submitted date).
    @Override
    public void calculateNextFollowUpDate() {
        if (super.getStatus() == Application.WAITING_FOR_RESPONSE_CODE
                || super.getStatus() == Application.WAITLISTED_CODE) {
            // the application is active
            if (this.getFollowUpDate() == null) {
                // first time to follow up, set the base date to the submit time
                this.setFollowUpDate(DateUtils.addDaysToDate(super.getSubmitDate(),
                        PositionApplication.FOLLOW_UP_INTERVAL));
            } else {
                // not first time to follow up, set the base date to last follow up
                this.setFollowUpDate(DateUtils.addDaysToDate(this.followUpDate,
                        PositionApplication.FOLLOW_UP_INTERVAL));
            }
        }
    }

    // EFFECTS: returns the string displaying on the console
    @Override
    public String getDisplayName() {
        if (isResearchApplication) {
            return String.format("[%s](Lab): %s @ %s, link: %s, submit date: %s, follow-up: %s, status: %s",
                    getId().substring(0, 5), getPosition(), getOrganization(), getInfoLink(),
                    getSubmitDate(),getFollowUpDate(), getStatusDisplayString());
        } else {
            return String.format("[%s](Intern): %s @ %s, link: %s, submit date: %s, follow-up: %s, status: %s",
                    getId().substring(0, 5), getPosition(), getOrganization(), getInfoLink(),
                    getSubmitDate(), getFollowUpDate(), getStatusDisplayString());
        }
    }

    // EFFECTS: returns whether this application is for a research position
    public boolean getIsResearchApplication() {
        return isResearchApplication;
    }

    // MODIFIES: this
    // EFFECTS: updates the application type
    public void setIsResearchApplication(boolean isResearchApplication) {
        this.isResearchApplication = isResearchApplication;
    }

    // EFFECTS: returns display string for each status code
    public String getStatusDisplayString() {
        switch (this.getStatus()) {
            case Application.REJECTION_CODE:
                return "Rejected";
            case Application.NOT_SUBMITTED_CODE:
                return "Not Submitted";
            case Application.WAITING_FOR_RESPONSE_CODE:
                return "Not Heard Back";
            case Application.WAITLISTED_CODE:
                return "Wait-listed";
            case Application.OFFER_CODE:
                return "Offered";
            default:
                return "";
        }
    }

    // EFFECTS: convert this java class instance to a json object
    @Override
    public JSONObject toJson() {
        // CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        JSONObject object = new JSONObject();
        object.put("organization", getOrganization());
        object.put("position", getPosition());
        object.put("isResearchApplication", getIsResearchApplication());
        object.put("submitDate", getSubmitDate().getTime());
        object.put("status", getStatus());
        object.put("infoLink", getInfoLink());
        object.put("id", getId());
        object.put("followUpDate", getFollowUpDate().getTime());
        return object;
    }
}
