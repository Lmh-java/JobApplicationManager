package ui;

import model.PositionApplication;

import java.util.Date;

/**
 * CreateAndEditApplicationScreen represents console page for create and edit application
 */
public class CreateAndEditApplicationScreen extends Screen {

    // current application
    private PositionApplication application;

    // is creating a new one
    private final boolean isCreating;

    // EFFECTS: creates an instance of this class with the given application.
    //          If given application is null, then the screen will create one.
    //          If given application is not null, then this screen will modify the application.
    public CreateAndEditApplicationScreen(PositionApplication application) {
        if (application == null) {
            // a new application is being created
            this.isCreating = true;
            this.application = new PositionApplication("", "", new Date(), "", false);
        } else {
            // modifying the pre-existing application
            this.isCreating = false;
            this.application = application;
        }
    }

    // EFFECTS: start to run the current screen
    @Override
    public void run() {
        handleInput("(Application Create/Modify) Please input the following information");
    }

    // MODIFIES: this
    // EFFECTS: ask for different info with the prompt message and handle user input
    @Override
    public void handleInput(String message) {
        if (this.isCreating) {
            // create a new application
            System.out.print("(Application Create) Type 0 for internship application, 1 for lab application >>>");
            switch (readInt()) {
                case 0:
                    application.setIsResearchApplication(false);
                    break;
                case 1:
                    application.setIsResearchApplication(true);
                    break;
                default:
                    System.out.println("(Application Create) Invalid input, going back.");
                    // set the application to null, meaning creation failed
                    this.application = null;
                    return;
            }
            handleCreate();
        } else {
            // modify an application
            handleModify();
        }
    }

    // MODIFIES: this
    // EFFECTS: handle create the application
    private void handleCreate() {
        if (this.application.getIsResearchApplication()) {
            System.out.print("(Application Create) Input your lab name >>>");
            application.setOrganization(readString());
        } else {
            System.out.print("(Application Create) Input your company name >>>");
            application.setOrganization(readString());
        }
        System.out.print("(Application Create) Input your position name >>>");
        application.setPosition(readString());
        System.out.print("(Application Create) Input the info link >>>");
        application.setInfoLink(readString());
        System.out.println("(Application Create) Application Created Successfully");

        // calculate the next follow-up date
        application.calculateNextFollowUpDate();
    }

    // MODIFIES: this
    // EFFECTS: handle modify the application
    private void handleModify() {
        if (this.application.getIsResearchApplication()) {
            System.out.printf("(Application Modify) Input your lab name (Current: %s)>>>",
                    application.getOrganization());
        } else {
            System.out.printf("(Application Modify) Input your company name (Current: %s)>>>",
                    application.getOrganization());
        }
        application.setOrganization(readString(application.getOrganization()));
        System.out.printf("(Application Modify) Input your position name (Current: %s)>>>", application.getPosition());
        application.setPosition(readString(application.getPosition()));
        System.out.printf("(Application Modify) Input the info link (Current: %s)>>>",
                application.getInfoLink());
        application.setInfoLink(readString(application.getInfoLink()));
        System.out.printf("(Application Modify) Input the status code [-2] rejection, [-1] not submitted yet, [0] not"
                + " heard back, [1] wait-listed, [2] offered (Current: %s)>>>", application.getStatusDisplayString());
        int userInput = readInt();
        if (userInput < -2 || userInput > 2) {
            System.out.println("(Application Modify) Invalid Input, going back...");
            return;
        }
        application.setStatus(userInput);
        System.out.println("(Application Modify) Application Modified");
    }

    // EFFECTS: returns the current application
    public PositionApplication getApplication() {
        return application;
    }
}
