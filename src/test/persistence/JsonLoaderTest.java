package persistence;

import model.ApplicationPortfolio;
import model.PositionApplication;
import model.QuestionTemplatePortfolio;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonLoaderTest extends JsonTest {
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testLoadEmptyApplication() {
        JsonLoader loader = new JsonLoader("./data/testEmptyApplicationList.json");
        try {
            JSONObject jsonObject = loader.loadJsonObject();
            ApplicationPortfolio portfolio = loader.parseApplicationPortfolio(jsonObject);
            assertEquals(0, portfolio.getNum());
            assertEquals(applicationPortfolio.getName(), portfolio.getName());
        } catch (IOException e) {
            fail("Unexpected exception caught", e);
        }
    }

    @Test
    public void testLoadEmptyQuestion() {
        JsonLoader loader = new JsonLoader("./data/testEmptyQuestionList.json");
        try {
            JSONObject jsonObject = loader.loadJsonObject();
            QuestionTemplatePortfolio portfolio = loader.parseQuestionTemplatePortfolio(jsonObject);
            assertEquals(0, portfolio.getNum());
            assertEquals(questionTemplatePortfolio.getName(), portfolio.getName());
        } catch (IOException e) {
            fail("Unexpected exception caught", e);
        }
    }

    @Test
    public void testLoadWithIllegalPath() {
        JsonLoader loader = new JsonLoader("./data/doesNotExist.json");
        try {
            loader.loadJsonObject();
            fail("Expected an exception.");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testSuccessLoadApplication() {
        JsonLoader loader = new JsonLoader("./data/testApplicationList.json");
        try {
            JSONObject jsonObject = loader.loadJsonObject();
            ApplicationPortfolio portfolio = loader.parseApplicationPortfolio(jsonObject);
            assertEquals(2, portfolio.getNum());
            assertEquals(applicationPortfolio.getName(), portfolio.getName());

            checkApplication((PositionApplication) applicationPortfolio.search(id1),
                    (PositionApplication) portfolio.search(id1));
            checkApplication((PositionApplication) applicationPortfolio.search(id2),
                    (PositionApplication) portfolio.search(id2));
        } catch (IOException e) {
            fail("Unexpected exception caught", e);
        }
    }

    @Test
    public void testSuccessLoadQuestion() {
        JsonLoader loader = new JsonLoader("./data/testQuestionList.json");
        try {
            JSONObject jsonObject = loader.loadJsonObject();
            QuestionTemplatePortfolio portfolio = loader.parseQuestionTemplatePortfolio(jsonObject);
            assertEquals(2, portfolio.getNum());
            assertEquals(questionTemplatePortfolio.getName(), portfolio.getName());

            checkQuestionAndAnswerItem(questionTemplatePortfolio.search(id1), portfolio.search(id1));
            checkQuestionAndAnswerItem(questionTemplatePortfolio.search(id2), portfolio.search(id2));
        } catch (IOException e) {
            fail("Unexpected exception caught", e);
        }
    }
}
