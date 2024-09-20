package persistence;

import model.*;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonSaverTest extends JsonTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testSaveSuccessApplicationPortfolio() {
        JsonLoader appLoader = new JsonLoader("./data/application.json");
        JsonSaver appSaver = new JsonSaver("./data/application.json");
        try {
            appSaver.saveToFile(applicationPortfolio.toJson());
            JSONObject object = appLoader.loadJsonObject();
            ApplicationPortfolio portfolio = appLoader.parseApplicationPortfolio(object);

            assertEquals(applicationPortfolio.getName(), portfolio.getName());
            assertEquals(applicationPortfolio.getNum(), portfolio.getNum());

            checkApplication((PositionApplication) applicationPortfolio.getList().get(0),
                    (PositionApplication) portfolio.getList().get(0));
            checkApplication((PositionApplication) applicationPortfolio.getList().get(1),
                    (PositionApplication) portfolio.getList().get(1));
        } catch (IOException e) {
            fail("Unexpected exception is thrown", e);
        }
    }

    @Test
    public void testSaveEmptyApplicationPortfolio() {
        JsonSaver appSaver = new JsonSaver("./data/application.json");
        JsonLoader appLoader = new JsonLoader("./data/application.json");
        applicationPortfolio.delete(id1);
        applicationPortfolio.delete(id2);
        try {
            appSaver.saveToFile(applicationPortfolio.toJson());
            JSONObject object = appLoader.loadJsonObject();
            ApplicationPortfolio portfolio = appLoader.parseApplicationPortfolio(object);

            assertEquals(applicationPortfolio.getName(), portfolio.getName());
            assertEquals(0, portfolio.getNum());
        } catch (IOException e) {
            fail("Unexpected exception is thrown", e);
        }
    }

    @Test
    public void testSaveApplicationPortfolioToIllegalPath() {
        JsonSaver appSaver = new JsonSaver("./dne/application.json");
        try {
            appSaver.saveToFile(applicationPortfolio.toJson());
            fail("Expected exception is not thrown");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testSaveSuccessQuestionPortfolio() {
        JsonLoader appLoader = new JsonLoader("./data/question.json");
        JsonSaver appSaver = new JsonSaver("./data/question.json");
        try {
            appSaver.saveToFile(questionTemplatePortfolio.toJson());
            JSONObject object = appLoader.loadJsonObject();
            QuestionTemplatePortfolio portfolio = appLoader.parseQuestionTemplatePortfolio(object);

            assertEquals(questionTemplatePortfolio.getName(), portfolio.getName());
            assertEquals(questionTemplatePortfolio.getNum(), portfolio.getNum());

            checkQuestionAndAnswerItem(questionTemplatePortfolio.search(id1), portfolio.search(id1));
            checkQuestionAndAnswerItem(questionTemplatePortfolio.search(id2), portfolio.search(id2));
        } catch (IOException e) {
            fail("Unexpected exception is thrown", e);
        }
    }

    @Test
    public void testSaveEmptyQuestionPortfolio() {
        JsonLoader appLoader = new JsonLoader("./data/question.json");
        JsonSaver appSaver = new JsonSaver("./data/question.json");
        questionTemplatePortfolio.delete(id1);
        questionTemplatePortfolio.delete(id2);
        try {
            appSaver.saveToFile(questionTemplatePortfolio.toJson());
            JSONObject object = appLoader.loadJsonObject();
            QuestionTemplatePortfolio portfolio = appLoader.parseQuestionTemplatePortfolio(object);

            assertEquals(questionTemplatePortfolio.getName(), portfolio.getName());
            assertEquals(0, portfolio.getNum());
        } catch (IOException e) {
            fail("Unexpected exception is thrown", e);
        }
    }

    @Test
    public void testSaveQuestionPortfolioToIllegalPath() {
        JsonSaver appSaver = new JsonSaver("./dne/question.json");
        try {
            appSaver.saveToFile(questionTemplatePortfolio.toJson());
            fail("Expected exception is not thrown");
        } catch (IOException e) {
            // pass
        }
    }
}