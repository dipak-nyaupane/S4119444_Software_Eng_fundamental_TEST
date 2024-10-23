import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PrescriptionTest {
    Prescription prescription = new Prescription();

    // Test valid prescription input
    @Test
    public void testAddPrescription_ValidInput() {
        boolean result = prescription.addPrescription("Dipak", "Nyaupane", "unit 5/75, Bruce Street, Melbourne, 2000, Australia", -1.50, 2.00, 90, "12/10/24", "Dr. Sudip");
        assertTrue(result);

        boolean res = prescription.addPrescription("Deep", "Nyaupane", "unit 5/75, Bruce Street, Melbourne, 2000, Australia", -1.50, 2.00, 90, "12/10/24", "Dr. Sudip");
        assertTrue(res);

    }

    // Test invalid first name length and last name
    @Test
    public void testAddPrescription_InvalidFirstNameLastName() {
        boolean result = prescription.addPrescription("Dip", "Nyaupane", "unit 5/75, Bruce Street, Melbourne, 2000, Australia", -1.50, 2.00, 90, "12/10/24", "Dr. Sudip");
        assertFalse(result);// Invalid because the first name has fewer than 4 characters

        boolean res = prescription.addPrescription("Deep", "Nya", "unit 5/75, Bruce Street, Melbourne, 2000, Australia", -1.50, 2.00, 90, "12/10/24", "Dr. Sudip");
        assertFalse(res); // Invalid because the last name has fewer than 4 characters
    }

    // Test invalid address length
    @Test
    public void testAddPrescription_InvalidAddress() {
        boolean result = prescription.addPrescription("Dipak", "Nyaupane", "Short address", -1.50, 2.00, 90, "12/10/24", "Dr. Sudip");
        assertFalse(result); // Invalid because the address has fewer than 20 characters

        boolean res = prescription.addPrescription("Deep", "Nyaupane", "12, City", -1.50, 2.00, 90, "12/10/24", "Dr. Sudip");
        assertFalse(res); // Invalid because the address has fewer than 20 characters
    }

    // Test invalid sphere value
    @Test
    public void testAddPrescription_InvalidSphere() {
        boolean result1 = prescription.addPrescription("Dipak", "Nyaupane", "unit 5/75, Bruce Street, Melbourne, 2000, Australia", -25.00, 2.00, 90, "12/10/24", "Dr. Sudip");
        assertFalse(result1); // Invalid because the sphere value is less than -20.00

        boolean result2 = prescription.addPrescription("Deep", "Nyaupane", "unit 5/75, Bruce Street, Melbourne, 2000, Australia", 25.00, 2.00, 90, "12/10/24", "Dr. Sudip");
        assertFalse(result2); // Invalid because the sphere value is greater than 20.00
    }

// Test invalid date format
    @Test
    public void testAddPrescription_InvalidDateFormat() {
        boolean result1 = prescription.addPrescription("Dipak", "Nyaupane", "unit 5/75, Bruce Street, Melbourne, 2000, Australia", -1.50, 2.00, 90, "2024-10-12", "Dr. Sudip");
        assertFalse(result1); // Invalid because the date format is not DD/MM/YY

        boolean result2 = prescription.addPrescription("Deep", "Nyaupane", "unit 5/75, Bruce Street, Melbourne, 2000, Australia", -1.50, 2.00, 90, "10-12-24", "Dr. Sudip");
        assertFalse(result2); // Invalid because the date format is not DD/MM/YY
    }

    // Test valid optometrist name length
    @Test
    public void testAddPrescription_ValidOptometristNameLength() {
        boolean result1 = prescription.addPrescription("Dipak", "Nyaupane", "unit 5/75, Bruce Street, Melbourne, 2000, Australia", -1.50, 2.00, 90, "12/10/24", "Dr. Sudip Sharma");
        assertTrue(result1); // Valid because the optometrist name length is within 8-25 characters

        boolean result2 = prescription.addPrescription("Deep", "Nyaupane", "unit 5/75, Bruce Street, Melbourne, 2000, Australia", -1.50, 2.00, 90, "12/10/24", "Dr. Alex Johnson");
        assertTrue(result2); // Valid because the optometrist name length is within 8-25 characters
    }

    //---------------------------For remarks---------------------------------
    // Test valid remark text and valid category
    @Test
    public void testAddRemark_ValidRemarkTextAndCategory() {
        boolean result1 = prescription.addRemark("The client had a positive experience with the product.", "client");
        assertTrue(result1); // Valid remark with 8 words, starting with an uppercase letter, and valid category "client"

        boolean result2 = prescription.addRemark("The optometrist suggested regular checkups for better vision.", "optometrist");
        assertTrue(result2); // Valid remark with 10 words, starting with an uppercase letter, and valid category "optometrist"
    }

    // Test invalid remark text (less than 6 words)
    @Test
    public void testAddRemark_InvalidRemarkTextTooShort() {
        boolean result1 = prescription.addRemark("Good service.", "client");
        assertFalse(result1); // Invalid remark with only 2 words

        boolean result2 = prescription.addRemark("Excellent service.", "optometrist");
        assertFalse(result2); // Invalid remark with only 2 words
    }

    // Test invalid remark text (more than 20 words)
    @Test
    public void testAddRemark_InvalidRemarkTextTooLong() {
        boolean result1 = prescription.addRemark("The client reported that they are very satisfied with the service and products provided by the optometrist, and they would recommend it to others.", "client");
        assertFalse(result1); // Invalid remark with more than 20 words

        boolean result2 = prescription.addRemark("The optometrist noted that the patient's vision was improving but required further monitoring, and they would need another appointment in six months to check their progress.", "optometrist");
        assertFalse(result2); // Invalid remark with more than 20 words
    }

    // Test invalid category
    @Test
    public void testAddRemark_InvalidCategory() {
        boolean result1 = prescription.addRemark("The client was happy with the service provided.", "customer");
        assertFalse(result1); // Invalid category "customer"

        boolean result2 = prescription.addRemark("The optometrist provided great advice on eye care.", "doctor");
        assertFalse(result2); // Invalid category "doctor"
    }

    // Test valid remark but exceeding remark limit (more than 2 remarks)
    @Test
    public void testAddRemark_ExceedsRemarkLimit() {
        Prescription prescription = new Prescription();

        // Add two valid remarks
        prescription.addRemark("The client appreciated the fast service.", "client");
        prescription.addRemark("The optometrist suggested a follow-up appointment.", "optometrist");

        // Try to add a third remark, should return false
        boolean result1 = prescription.addRemark("The client mentioned the product was affordable.", "client");
        assertFalse(result1);  // Invalid because there are already 2 remarks

        boolean result2 = prescription.addRemark("The optometrist recommended annual eye tests.", "optometrist");
        assertFalse(result2);  // Invalid because there are already 2 remarks
    }



    // Test valid remark with exactly 6 and 20 words
    @Test
    public void testAddRemark_BoundaryRemarkText() {
        boolean result1 = prescription.addRemark("The service provided was excellent and quick.", "client");
        assertTrue(result1); // Valid remark with exactly 6 words

        boolean result2 = prescription.addRemark("The client expressed their satisfaction with the quality of the eyewear and service, mentioning they will return soon.", "client");
        assertTrue(result2); // Valid remark with exactly 20 words
    }

}
