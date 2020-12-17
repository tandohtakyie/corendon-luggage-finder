package com.corendon.luggage_finder;

import com.corendon.luggage_finder.controllers.RegisterController;
import com.corendon.luggage_finder.database.tables.LuggagesTable;
import com.corendon.luggage_finder.database.tables.UsersTable;
import com.corendon.luggage_finder.model.*;
import org.junit.*;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.DoesNothing;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LuggageFinderASVTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {

    }

    @Test
    public void loginManager(){
        String username = "sjors", password = "wachtwoord";

        // the expected result of the user with the manager's function.
        User expectedResult = new User(
                "sjors",
                "Sjors",
                "de",
                "Snor", new Date(),
                "sjors@business.com",
                new Function("Manager"),
                new Date(), new Country("NL","Nederland"),
                "wachtwoord", null, false
        );

        // Configuring Mockito to mock the UsersTable class
        // where the queries are written
        UsersTable usersTable = Mockito.mock(UsersTable.class);

        // passing in the test values and return the expected result
        when(usersTable.login(username, password)).thenReturn(expectedResult);
        usersTable.login(username,password);
        System.out.println("Manager logs in successfully \nNavigate to statistics");

        // verifying
        verify(usersTable, Mockito.times(1)).login(username,password);
    }


}
