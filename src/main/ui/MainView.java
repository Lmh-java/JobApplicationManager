package ui;

import model.ApplicationPortfolio;
import model.Event;
import model.EventLog;
import model.QuestionTemplatePortfolio;
import persistence.JsonLoader;
import persistence.JsonSaver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The main GUI window of the program
 */
public class MainView extends JFrame implements WindowListener {

    // two panels for each module of the program
    private final ApplicationPortfolioPanel appPanel;
    private final QuestionPortfolioPanel questionPanel;

    // a tab panel that switch freely between two panels
    private final JTabbedPane tabbedPane;

    // menu bar
    private final JMenuBar menuBar;
    private final JMenu fileMenu;
    private final JMenuItem saveMenu;
    private final JMenuItem loadMenu;

    // portfolio
    private ApplicationPortfolio applicationPortfolio;
    private QuestionTemplatePortfolio questionTemplatePortfolio;

    // initial width and height
    private static final int INITIAL_WIDTH = 1000;
    private static final int INITIAL_HEIGHT = 500;

    // EFFECTS: initialize and show the main view
    public MainView() {
        super("Job Application Tracker");
        applicationPortfolio = new ApplicationPortfolio("Application Portfolio");
        questionTemplatePortfolio = new QuestionTemplatePortfolio("Question Template Portfolio");
        appPanel = new ApplicationPortfolioPanel(applicationPortfolio);
        questionPanel = new QuestionPortfolioPanel(questionTemplatePortfolio);
        tabbedPane = new JTabbedPane();
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        saveMenu = new JMenuItem("Save");
        loadMenu = new JMenuItem("Load");

        // set up the layout
        setUp();

        // show the main window
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: set up the layout and UI components
    private void setUp() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, INITIAL_WIDTH, INITIAL_HEIGHT);

        // set the tabbed pane
        tabbedPane.addTab("Applications", appPanel);
        tabbedPane.addTab("Question Templates", questionPanel);
        add(tabbedPane, BorderLayout.CENTER);

        // set logo
        setIconImage(new ImageIcon("data/Logo.png").getImage());

        // initialize menu bar
        setJMenuBar(menuBar);
        menuBar.add(new JLabel("  "));
        menuBar.add(new JLabel(getScaledLogo()));
        menuBar.add(fileMenu);
        fileMenu.add(saveMenu);
        fileMenu.add(loadMenu);
        saveMenu.addActionListener(e -> saveMenuClicked());
        loadMenu.addActionListener(e -> loadMenuClicked());
        addWindowListener(this);
    }

    // MODIFIES: this
    // EFFECTS: when the save menu is clicked, save all the data to json
    public void saveMenuClicked() {
        JsonSaver appSaver = new JsonSaver("./data/application.json");
        JsonSaver questionSaver = new JsonSaver("./data/question.json");
        try {
            appSaver.saveToFile(applicationPortfolio.toJson());
            questionSaver.saveToFile(questionTemplatePortfolio.toJson());
            JOptionPane.showMessageDialog(this, "Successfully saved", "Yep",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Cannot save data: file not found",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: when the load menu is clicked, load data from json
    public void loadMenuClicked() {
        JsonLoader appLoader = new JsonLoader("./data/application.json");
        JsonLoader questionLoader = new JsonLoader("./data/question.json");
        boolean exceptionThrown = false;
        // load application data
        try {
            applicationPortfolio = appLoader.parseApplicationPortfolio(appLoader.loadJsonObject());
            appPanel.updatePortfolio(applicationPortfolio);
        } catch (IOException e) {
            exceptionThrown = true;
        }
        // load question templates
        try {
            questionTemplatePortfolio = questionLoader.parseQuestionTemplatePortfolio(questionLoader.loadJsonObject());
            questionPanel.updatePortfolio(questionTemplatePortfolio);
        } catch (IOException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            JOptionPane.showMessageDialog(this, "Successfully loaded", "Yep",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Partially loaded", "Don't worry",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // EFFECTS: returns a scaled logo image
    public static ImageIcon getScaledLogo() {
        ImageIcon icon = new ImageIcon("data/Logo.png");
        return new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    }


    // EFFECTS: nothing
    @Override
    public void windowOpened(WindowEvent e) {
        // ignored
    }

    // EFFECTS: print all the logs when the window is closing
    @Override
    public void windowClosing(WindowEvent e) {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
        System.exit(0);
    }

    // EFFECTS: nothing
    @Override
    public void windowClosed(WindowEvent e) {
        // ignored
    }

    // EFFECTS: nothing
    @Override
    public void windowIconified(WindowEvent e) {
        // ignored
    }

    // EFFECTS: nothing
    @Override
    public void windowDeiconified(WindowEvent e) {
        // ignored
    }

    // EFFECTS: nothing
    @Override
    public void windowActivated(WindowEvent e) {
        // ignored
    }

    // EFFECTS: nothing
    @Override
    public void windowDeactivated(WindowEvent e) {
        // ignored
    }
}
