package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PositionApplicationTest {

    private PositionApplication app;
    private Date testDate1;
    private Date testDate2;

    @BeforeEach
    public void setUp() {
        testDate1 = new Date();
        testDate2 = new Date(2023, 10, 1);
        app = new PositionApplication("Test Company", "SWE", testDate1, "google.com", false);
    }

    @Test
    public void testInternshipApplication() {
        assertEquals("Test Company", app.getOrganization());
        assertEquals("SWE", app.getPosition());
        assertEquals(testDate1, app.getSubmitDate());
        assertEquals("google.com", app.getInfoLink());
        assertFalse(app.getIsResearchApplication());
    }

    @Test
    public void testSetCompany() {
        assertEquals("Test Company", app.getOrganization());
        app.setOrganization("New Test Company");
        assertEquals("New Test Company", app.getOrganization());
    }

    @Test
    public void testSetPosition() {
        assertEquals("SWE", app.getPosition());
        app.setPosition("New Test Pos");
        assertEquals("New Test Pos", app.getPosition());
    }

    @Test
    public void testSetInfoLink() {
        assertEquals("google.com", app.getInfoLink());
        app.setInfoLink("swe.com");
        assertEquals("swe.com", app.getInfoLink());
    }

    @Test
    public void testGetOrganization() {
        assertEquals("Test Company", app.getOrganization());
    }

    @Test
    public void testGetPosition() {
        assertEquals("SWE", app.getPosition());
    }

    @Test
    public void testGetInfoLink() {
        assertEquals("google.com", app.getInfoLink());
    }

    @Test
    public void testCalculateNextFollowUpDate() {
        Calendar followUpDate = Calendar.getInstance();
        followUpDate.setTime(testDate1);
        followUpDate.add(Calendar.DATE, PositionApplication.FOLLOW_UP_INTERVAL);
        app.calculateNextFollowUpDate();
        assertEquals(followUpDate.getTime(), app.getFollowUpDate());

        followUpDate.add(Calendar.DATE, PositionApplication.FOLLOW_UP_INTERVAL);
        app.calculateNextFollowUpDate();
        assertEquals(followUpDate.getTime(), app.getFollowUpDate());

        // test inactive application, in this case, follow update should not change
        app.setStatus(Application.OFFER_CODE);
        app.calculateNextFollowUpDate();
        assertEquals(followUpDate.getTime(), app.getFollowUpDate());


        app.setStatus(Application.REJECTION_CODE);
        app.calculateNextFollowUpDate();
        assertEquals(followUpDate.getTime(), app.getFollowUpDate());

        app.setStatus(Application.NOT_SUBMITTED_CODE);
        app.calculateNextFollowUpDate();
        assertEquals(followUpDate.getTime(), app.getFollowUpDate());

        // test a new date
        app.setSubmitDate(testDate2);
        app.setStatus(Application.WAITLISTED_CODE);
        app.followUpDate = null;
        followUpDate = Calendar.getInstance();
        followUpDate.setTime(testDate2);
        followUpDate.add(Calendar.DATE, PositionApplication.FOLLOW_UP_INTERVAL);
        app.calculateNextFollowUpDate();
        assertEquals(followUpDate.getTime(), app.getFollowUpDate());
    }

    @Test
    public void testNeedFollowUp() {
        PositionApplication newApp = new PositionApplication("Some Company"
                , "SWE", testDate2, "", false);
        assertFalse(newApp.followUpNeeded());
        newApp.calculateNextFollowUpDate();
        assertFalse(newApp.followUpNeeded());

        app.setFollowUpDate(new Date());
        assertTrue(app.followUpNeeded());
    }

    @Test
    public void testSetId() {
        app.setId("aebd9067-1ee1-0923-ebaccccccccc");
        assertEquals("aebd9067-1ee1-0923-ebaccccccccc", app.getId());
    }

    @Test
    public void testSetSubmitDate() {
        app.setSubmitDate(testDate2);
        assertEquals(testDate2, app.getSubmitDate());
    }

    @Test
    public void testGetDisplayName() {
        assertEquals(String.format("[%s](Intern): SWE @ Test Company, link: google.com, submit date: %s, "
                        + "follow-up: %s, status: %s", app.getId().substring(0, 5), app.getSubmitDate(),
                app.getFollowUpDate(), app.getStatusDisplayString()), app.getDisplayName());
        app.setOrganization("New Company");
        assertEquals(String.format("[%s](Intern): SWE @ New Company, link: google.com, submit date: %s, follow-up: %s, "
                        + "status: %s", app.getId().substring(0, 5), app.getSubmitDate(), app.getFollowUpDate(),
                app.getStatusDisplayString()), app.getDisplayName());

        app.setIsResearchApplication(true);
        assertEquals(String.format("[%s](Lab): SWE @ New Company, link: google.com, submit date: %s, follow-up: %s,"
                        + " status: %s", app.getId().substring(0, 5), app.getSubmitDate(), app.getFollowUpDate(),
                app.getStatusDisplayString()), app.getDisplayName());
    }

    @Test
    public void testGetStatusDisplayString() {
        app.setStatus(Application.REJECTION_CODE);
        assertEquals("Rejected", app.getStatusDisplayString());
        app.setStatus(Application.NOT_SUBMITTED_CODE);
        assertEquals("Not Submitted", app.getStatusDisplayString());
        app.setStatus(Application.WAITING_FOR_RESPONSE_CODE);
        assertEquals("Not Heard Back", app.getStatusDisplayString());
        app.setStatus(Application.WAITLISTED_CODE);
        assertEquals("Wait-listed", app.getStatusDisplayString());
        app.setStatus(Application.OFFER_CODE);
        assertEquals("Offered", app.getStatusDisplayString());
        app.setStatus(7);
        assertEquals("", app.getStatusDisplayString());
    }
}