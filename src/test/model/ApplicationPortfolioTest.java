package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationPortfolioTest {

    private ApplicationPortfolio portfolio;
    private Application testApp1;
    private Application testApp2;

    @BeforeEach
    public void setUp() {
        portfolio = new ApplicationPortfolio("Test Portfolio");
        testApp1 = new PositionApplication("Google", "SWE", new Date(), "test link", false);
        testApp2 = new PositionApplication("Mars", "RA", new Date(), "test link", true);
    }

    @Test
    public void testPortfolio() {
        assertEquals("Test Portfolio", portfolio.getName());
        assertEquals(0, portfolio.getNum());
        assertEquals(new ArrayList<>(), portfolio.getList());
    }

    @Test
    public void testAdd() {
        assertEquals(0, portfolio.getNum());
        portfolio.add(testApp1);
        assertEquals(1, portfolio.getNum());
        portfolio.add(testApp2);
        assertEquals(2, portfolio.getNum());

        assertEquals(testApp1, portfolio.search(testApp1.getId()));
        assertEquals(testApp2, portfolio.search(testApp2.getId()));
    }

    @Test
    public void testSearch() {
        assertNull(portfolio.search(testApp1.getId()));

        portfolio.add(testApp2);
        portfolio.add(testApp1);

        assertEquals(testApp1, portfolio.search(testApp1.getId()));
        assertEquals(testApp2, portfolio.search(testApp2.getId()));
    }

    @Test
    public void testDelete() {
        assertFalse(portfolio.delete(testApp1.getId()));

        portfolio.add(testApp1);
        portfolio.add(testApp2);
        assertEquals(2, portfolio.getNum());
        assertTrue(portfolio.delete(testApp2.getId()));
        assertEquals(1, portfolio.getNum());

        assertNull(portfolio.search(testApp2.getId()));
    }

    @Test
    public void testGetApplicationList() {
        assertEquals(new ArrayList<Application>(), portfolio.getList());
        portfolio.add(testApp1);
        portfolio.add(testApp2);
        assertEquals(Arrays.asList(testApp1, testApp2), portfolio.getList());
    }

    @Test
    public void testGetName() {
        assertEquals("Test Portfolio", portfolio.getName());
        portfolio.setName("New Test Name");
        assertEquals("New Test Name", portfolio.getName());
    }

    @Test
    public void testSetName() {
        assertEquals("Test Portfolio", portfolio.getName());
        portfolio.setName("New Test Name");
        assertEquals("New Test Name", portfolio.getName());
    }

    @Test
    public void testGetNum() {
        assertEquals(0, portfolio.getNum());
        portfolio.add(testApp1);
        assertEquals(1, portfolio.getNum());
        portfolio.add(testApp2);
        assertEquals(2, portfolio.getNum());
    }

    @Test
    public void testSearchByIdPrefix() {
        portfolio.add(testApp1);
        portfolio.add(testApp2);

        assertEquals(testApp1, portfolio.searchByIdPrefix(testApp1.getId()));
        assertEquals(testApp1, portfolio.searchByIdPrefix(testApp1.getId().substring(0, 1)));
        assertEquals(testApp1, portfolio.searchByIdPrefix(testApp1.getId().substring(0, 5)));
        assertNull(portfolio.searchByIdPrefix("nnnn"));
    }

    @Test
    public void testGetDisplayString() {
        ArrayList<String> expected = new ArrayList<>();
        assertEquals(expected, portfolio.getDisplayString());

        portfolio.add(testApp1);
        portfolio.add(testApp2);
        expected.add(testApp1.getDisplayName());
        expected.add(testApp2.getDisplayName());
        assertEquals(expected, portfolio.getDisplayString());
    }
}
