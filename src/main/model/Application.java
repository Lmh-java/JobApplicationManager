package model;

import util.DateUtils;

import java.util.Date;
import java.util.UUID;

/**
 * Application class represents an abstract concept of all kinds of application
 * Different kinds of application (ResearchPositionApplication, JobApplication, etc.) must extend this class.
 */
public abstract class Application {

    // status code for rejection
    public static final int REJECTION_CODE = -2;
    // status code for not submitted yet
    public static final int NOT_SUBMITTED_CODE = -1;
    // status code for waiting for a response
    public static final int WAITING_FOR_RESPONSE_CODE = 0;
    // status code for being wait-listed
    public static final int WAITLISTED_CODE = 1;
    // status code for received an offer
    public static final int OFFER_CODE = 2;

    // the unique id of application for future reference
    private String id;

    // date when the application is submitted
    private Date submitDate;

    // status of the application
    // -2 for rejection, 1 for not submitted yet, 0 for not hear back yet, 1 for wait-listed, 2 for offered.
    private int status;

    // the date when the user should follow up
    protected Date followUpDate;

    // job info link
    private String infoLink;

    // EFFECTS: create an application with given submit date, info link, and a unique id.
    public Application(Date submitDate, String infoLink) {
        this.submitDate = submitDate;
        // generate a unique id
        this.id = UUID.randomUUID().toString();
        this.infoLink = infoLink;
    }

    // MODIFIES: this
    // EFFECTS: calculate next follow-up date
    public abstract void calculateNextFollowUpDate();

    // EFFECTS: returns whether the application needs follow up today
    //          if the followUpDate is null, then returns false
    public boolean followUpNeeded() {
        if (this.getFollowUpDate() == null) {
            return false;
        }

        return DateUtils.areDatesEqual(new Date(), this.getFollowUpDate());
    }

    // MODIFIES: this
    // EFFECTS: set the submit date of application to the provided submitDate
    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    // EFFECTS: returns the submit date of application
    public Date getSubmitDate() {
        return submitDate;
    }

    // EFFECTS: returns the current status of application
    // -2 for rejection, 1 for not submitted yet, 0 for not hear back yet, 1 for wait-listed, 2 for offered.
    public int getStatus() {
        return status;
    }

    // MODIFIES: this
    // EFFECTS: update application status to the given status
    public void setStatus(int status) {
        this.status = status;
    }

    // EFFECTS: returns the next follow-up date
    public Date getFollowUpDate() {
        return followUpDate;
    }

    // MODIFIES: this
    // EFFECTS: set the follow-up date to the provided date
    public void setFollowUpDate(Date nextFollowUpDate) {
        this.followUpDate = nextFollowUpDate;
    }

    // EFFECTS: returns the application id
    public String getId() {
        return id;
    }

    // MODIFIES: this
    // EFFECTS: update the application id to the provided new id
    public void setId(String id) {
        this.id = id;
    }

    // EFFECTS: returns the application info link
    public String getInfoLink() {
        return infoLink;
    }

    // MODIFIES: this
    // EFFECTS: update the application info link to the provided new link
    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    // EFFECTS: returns the name that is displayed on the console
    public abstract String getDisplayName();
}
