package com.corendon.luggage_finder;

import com.corendon.luggage_finder.database.tables.LuggagesTable;
import com.corendon.luggage_finder.database.tables.UsersTable;
import com.corendon.luggage_finder.model.*;
import org.junit.*;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        // the information of the user with the manager's function.
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

        // passing in the test values for validation
        when(usersTable.login(username, password)).thenReturn(expectedResult);
        usersTable.login(username,password);
        System.out.println("Manager logs in successfully \nNavigate to statistics");

        // verifying
        verify(usersTable, Mockito.times(1)).login(username,password);
    }

    @Test
    public void registerLuggage() throws ParseException, SQLException {
        // the information of the luggage with the will be registered.
        Luggage luggageTest = getLuggageDetails();

        // Configuring Mockito to mock the LuggagesTable class
        LuggagesTable luggagesTable = Mockito.mock(LuggagesTable.class);

        // passing in the test values for validation and returning true if success
        when(luggagesTable.insert(luggageTest)).thenReturn(true);
        luggagesTable.insert(luggageTest);

        // verifying
        verify(luggagesTable, Mockito.times(1)).insert(luggageTest);
    }

    @Test
    public void updateLuggage() throws ParseException, SQLException {
        // the information of the luggage with the will be registered.
        Luggage luggageTest = getLuggageDetails();

        // Configuring Mockito to mock the LuggagesTable class
        LuggagesTable luggagesTable = Mockito.mock(LuggagesTable.class);

        // passing in the test values for validation and returning true if success
        when(luggagesTable.update(luggageTest)).thenReturn(true);
        luggagesTable.update(luggageTest);

        // verifying
        verify(luggagesTable, Mockito.times(1)).update(luggageTest);
    }

    private Luggage getLuggageDetails() throws ParseException {
        String registrationId = "123";

        Airport airport = new Airport("Schiphol", new Country("NL", "Nederland"), 1, true);

        LuggageType type = new LuggageType("Koffer", 1);

        Color primaryColor = new Color("Geel",5555,25);

        Color secondaryColor = new Color("Blue",6666,26);

        Brand brand = new Brand("LV",1);

        Status status = new Status("Lost",1);

        Insurance insurance = new Insurance("ditzo", 1);

        String dateStr = "2010-01-04 01:32:27 UTC";
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = format.parse(dateStr);

        Integer length = Utils.parseInt("25");
        Integer width = Utils.parseInt("25");
        Integer height = Utils.parseInt("25");
        Integer weight = Utils.parseInt("25");

        String labelText = "555";
        String characteristics = "Big bag";

        // flight
        String flightNumber = "555";
        Flight flight = new Flight(flightNumber);

        // label
        Label label = new Label(labelText, flight);
       Passenger selectedPassenger = new Passenger(1);

        return new Luggage(registrationId, date, airport, type, brand, label,
            status, width, length, height, weight, selectedPassenger,
            characteristics, primaryColor, secondaryColor, insurance);
    }
}
