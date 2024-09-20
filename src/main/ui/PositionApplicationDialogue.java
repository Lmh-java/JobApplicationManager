package ui;

import model.PositionApplication;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;
import java.util.function.Function;

/**
 * Dialogue for creating and modifying position application
 */
public class PositionApplicationDialogue extends JDialog {

    public static final String[] STATUS = {
            "REJECTED", "NOT SUBMITTED", "WAITING FOR RESPONSE", "WAIT-LISTED", "OFFERED"
    };

    // result application
    private final PositionApplication originalApplication;

    private final Function<PositionApplication, Void> callback;

    // the main layout of the dialogue
    private final GridLayout mainLayout;

    // main panel
    private final JPanel panel;

    // components
    private final JButton submitButton;
    private final JButton cancelButton;
    private final JTextField organizationText;
    private final JTextField positionText;
    private final JComboBox<String> statusComboBox;
    private final JTextField linkText;
    private final JCheckBox typeCheckbox;

    // EFFECTS: initialize the dialog
    // NOTE: if app == null, it means the dialogue is called for creating a new Application,
    //       otherwise, it is modifying an existing Application.
    public PositionApplicationDialogue(PositionApplication app, Function<PositionApplication, Void> callback) {
        this.originalApplication = app;
        this.callback = callback;
        mainLayout = new GridLayout(6, 2);

        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        organizationText = new JTextField();
        positionText = new JTextField();
        statusComboBox = new JComboBox<>();
        linkText = new JTextField();
        typeCheckbox = new JCheckBox();
        panel = new JPanel();

        // set up the UI
        setUp();
        autoFillInfo();
    }

    // MODIFIES: this
    // EFFECTS: set up the layout
    private void setUp() {
        this.setResizable(false);
        setBounds(100, 100, 400, 300);
        this.getContentPane().add(panel, SwingConstants.CENTER);
        this.panel.setBorder(new EmptyBorder(10, 10, 10,10));
        panel.setLayout(mainLayout);
        panel.add(new JLabel("Organization Name: "));
        panel.add(organizationText);
        panel.add(new JLabel("Position Name: "));
        panel.add(positionText);
        panel.add(new JLabel("Status: "));
        panel.add(statusComboBox);
        panel.add(new JLabel("Info Link: "));
        panel.add(linkText);
        panel.add(new JLabel("Is a Research Position? "));
        panel.add(typeCheckbox);
        panel.add(submitButton);
        panel.add(cancelButton);
        submitButton.addActionListener(e -> submitClicked());
        cancelButton.addActionListener(e -> cancelClicked());
        setUpComboBox();
    }

    // MODIFIES: this
    // EFFECTS: set up combo boxes
    private void setUpComboBox() {
        statusComboBox.addItem(STATUS[0]);
        statusComboBox.addItem(STATUS[1]);
        statusComboBox.addItem(STATUS[2]);
        statusComboBox.addItem(STATUS[3]);
        statusComboBox.addItem(STATUS[4]);
        statusComboBox.setSelectedItem(STATUS[2]);
    }

    // MODIFIES: this
    // EFFECTS: autofill the info into the fields when we are modifying
    private void autoFillInfo() {
        if (this.originalApplication != null) {
            organizationText.setText(originalApplication.getOrganization());
            positionText.setText(originalApplication.getPosition());
            linkText.setText(originalApplication.getInfoLink());
            typeCheckbox.setSelected(originalApplication.getIsResearchApplication());
            try {
                statusComboBox.setSelectedItem(STATUS[originalApplication.getStatus() + 2]);
            } catch (IndexOutOfBoundsException e) {
                // ignore it
                // Since user can modify the data saved on the disk directly, there can be problems with
                // loading the status.
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: the cancel button is clicked
    private void cancelClicked() {
        this.setVisible(false);
        this.dispose();
    }

    // MODIFIES: this
    // EFFECTS: the submit button is clicked, submit the changes
    private void submitClicked() {
        PositionApplication app = new PositionApplication(
                organizationText.getText(),
                positionText.getText(),
                new Date(),
                linkText.getText(),
                typeCheckbox.isSelected()
        );
        int index = mapStringToStatusFlag((String) statusComboBox.getSelectedItem());
        app.setStatus(index);
        app.calculateNextFollowUpDate();
        if (this.originalApplication != null) {
            app.setId(this.originalApplication.getId());
            app.setSubmitDate(this.originalApplication.getSubmitDate());
        }
        this.callback.apply(app);
        this.setVisible(false);
        this.dispose();
    }

    // EFFECTS: return the int flag of the status by given status string
    private int mapStringToStatusFlag(String statusStr) {
        int count = 0;
        for (String ss : STATUS) {
            if (ss.equals(statusStr)) {
                return count - 2;
            }
            count++;
        }
        return 0;
    }
}
