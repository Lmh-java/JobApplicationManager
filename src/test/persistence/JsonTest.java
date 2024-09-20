package persistence;

import model.*;
import util.DateUtils;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

// CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    protected ApplicationPortfolio applicationPortfolio;
    protected QuestionTemplatePortfolio questionTemplatePortfolio;

    protected PositionApplication app1;
    protected PositionApplication app2;

    protected QuestionAndAnswerItem questionItem1;
    protected QuestionAndAnswerItem questionItem2;

    protected String id1;
    protected String id2;

    protected Date submitDate1;
    protected Date submitDate2;

    // EFFECTS: check whether the provided fields of a question item equals to the actual item
    protected void checkQuestionAndAnswerItem(QuestionAndAnswerItem expected, QuestionAndAnswerItem actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getQuestion(), actual.getQuestion());
        assertEquals(expected.getAnswer(), actual.getAnswer());
    }

    // EFFECTS: check whether the provided fields of an application item equals to the actual item
    protected void checkApplication(PositionApplication expected, PositionApplication actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertEquals(expected.getSubmitDate().getTime(),actual.getSubmitDate().getTime());
        assertEquals(expected.getInfoLink(), actual.getInfoLink());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getIsResearchApplication(), actual.getIsResearchApplication());
        assertEquals(expected.getFollowUpDate().getTime(), actual.getFollowUpDate().getTime());
    }

    protected void setUp() {
        submitDate1 = new Date(2023, Calendar.DECEMBER, 21);
        submitDate2 = new Date(2020, Calendar.JANUARY, 1);
        id1 = "f84a42fb-f603-44b7-ae02-7b46896e9885";
        id2 = "560cf001-af06-438c-9478-db0355f4b40d";
        applicationPortfolio = new ApplicationPortfolio("ApplicationPortfolio");
        app1 = new PositionApplication("Google", "SWE", submitDate1,
                "https://google.com", false);
        app1.setId(id1);
        app1.setFollowUpDate(DateUtils.addDaysToDate(submitDate1, 7));
        app2 = new PositionApplication("Mars Lab", "RA", submitDate2,
                "https://youtube.com", true);
        app2.setStatus(Application.OFFER_CODE);
        app2.setId(id2);
        app2.setFollowUpDate(DateUtils.addDaysToDate(submitDate2, 7));
        applicationPortfolio.add(app1);
        applicationPortfolio.add(app2);
        questionItem1 = new QuestionAndAnswerItem("What's your name?", "I am Richard");
        questionItem2 = new QuestionAndAnswerItem("What's your favorite programming language?", "Java");
        questionItem1.setId(id1);
        questionItem2.setId(id2);
        questionTemplatePortfolio = new QuestionTemplatePortfolio("questionPortfolio");
        questionTemplatePortfolio.add(questionItem1);
        questionTemplatePortfolio.add(questionItem2);
    }
}
