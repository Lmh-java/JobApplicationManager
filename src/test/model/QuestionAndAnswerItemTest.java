package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionAndAnswerItemTest {

    private QuestionAndAnswerItem item;

    @BeforeEach
    public void setUp() {
        item = new QuestionAndAnswerItem("How old are you?", "200");
    }

    @Test
    public void testQuestionAndAnswerItemTest() {
        assertEquals("How old are you?", item.getQuestion());
        assertEquals("200", item.getAnswer());
    }

    @Test
    public void getAnswer() {
        assertEquals("200", item.getAnswer());
    }

    @Test
    public void getQuestion() {
        assertEquals("How old are you?", item.getQuestion());
    }

    @Test
    public void setAnswer() {
        assertEquals("200", item.getAnswer());
        item.setAnswer("300");
        assertEquals("300", item.getAnswer());
    }

    @Test
    public void setQuestion() {
        assertEquals("How old are you?", item.getQuestion());
        item.setQuestion("300");
        assertEquals("300", item.getQuestion());
    }

    @Test
    public void getId() {
        item.setId("a");
        assertEquals("a", item.getId());
    }

    @Test
    public void setId() {
        item.setId("a");
        assertEquals("a", item.getId());
    }
}