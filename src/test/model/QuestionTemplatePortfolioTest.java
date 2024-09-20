package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTemplatePortfolioTest {

    private QuestionTemplatePortfolio portfolio;
    private QuestionAndAnswerItem item1;
    private QuestionAndAnswerItem item2;

    @BeforeEach
    public void setUp() {
        portfolio = new QuestionTemplatePortfolio("Test Question Portfolio");
        item1 = new QuestionAndAnswerItem("How old are you!", "200");
        item2 = new QuestionAndAnswerItem("What programming language do you use?", "Java");
    }

    @Test
    public void testQuestionTemplatePortfolio() {
        assertEquals("Test Question Portfolio", portfolio.getName());
        assertEquals(new ArrayList<>(), portfolio.getList());
        assertEquals(0, portfolio.getNum());
    }

    @Test
    public void testAdd() {
        portfolio.add(item1);
        assertEquals(1, portfolio.getNum());
        portfolio.add(item2);
        assertEquals(2, portfolio.getNum());

        assertEquals(item1, portfolio.search(item1.getId()));
        assertEquals(item2, portfolio.search(item2.getId()));
    }

    @Test
    public void testDelete() {
        portfolio.add(item1);
        portfolio.add(item2);

        assertEquals(2, portfolio.getNum());
        assertFalse(portfolio.delete("a"));
        assertEquals(2, portfolio.getNum());
        assertTrue(portfolio.delete(item1.getId()));
        assertEquals(1, portfolio.getNum());
        assertFalse(portfolio.delete(item1.getId()));
        assertEquals(1, portfolio.getNum());
        assertTrue(portfolio.delete(item2.getId()));
        assertEquals(0, portfolio.getNum());
    }

    @Test
    public void testSearch() {
        portfolio.add(item1);
        portfolio.add(item2);
        assertNull(portfolio.search("a"));
        assertEquals(item1, portfolio.search(item1.getId()));
        assertEquals(item2, portfolio.search(item2.getId()));
    }

    @Test
    public void testGetList() {
        portfolio.add(item1);
        portfolio.add(item2);
        assertEquals(Arrays.asList(item1, item2), portfolio.getList());
    }

    @Test
    public void testGetNum() {
        portfolio.add(item1);
        assertEquals(1, portfolio.getNum());
        portfolio.add(item2);
        assertEquals(2, portfolio.getNum());
    }

    @Test
    public void testGetName() {
        assertEquals("Test Question Portfolio", portfolio.getName());
        portfolio.setName("new Name");
        assertEquals("new Name", portfolio.getName());
    }

    @Test
    public void testSetName() {
        assertEquals("Test Question Portfolio", portfolio.getName());
        portfolio.setName("new Name");
        assertEquals("new Name", portfolio.getName());
    }

    @Test
    public void testSearchByIdPrefix() {
        portfolio.add(item1);
        portfolio.add(item2);

        assertEquals(item1, portfolio.searchByIdPrefix(item1.getId()));
        assertEquals(item1, portfolio.searchByIdPrefix(item1.getId().substring(0, 1)));
        assertEquals(item1, portfolio.searchByIdPrefix(item1.getId().substring(0, 5)));
        assertNull(portfolio.searchByIdPrefix("nnnn"));
    }

    @Test
    public void testGetDisplayString() {
        List<String> expected = new ArrayList<>();
        assertEquals(expected, portfolio.getDisplayString());

        portfolio.add(item1);
        portfolio.add(item2);
        expected.add(String.format("[%s]\t Q: %s \n\t\t A: %s", item1.getId().substring(0, 5), item1.getQuestion(),
                item1.getAnswer()));
        expected.add(String.format("[%s]\t Q: %s \n\t\t A: %s", item2.getId().substring(0, 5), item2.getQuestion(),
                item2.getAnswer()));
        assertEquals(expected, portfolio.getDisplayString());
    }
}