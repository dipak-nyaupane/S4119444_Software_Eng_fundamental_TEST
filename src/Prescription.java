import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Prescription {
    public boolean addPrescription(String firstName, String lastName, String address,
                                   double sphere, double cylinder, int axis,
                                   String date, String optometrist) {
        // Validate first name and last name
        if (firstName.length() < 4 || firstName.length() > 15) {
            System.out.println("Invalid first name length");
            return false;
        }
        if (lastName.length() < 4 || lastName.length() > 15) {
            System.out.println("Invalid last name length");
            return false;
        }

        // Validate address
        if (address.length() < 20) {
            System.out.println("Invalid address length");
            return false;
        }

        // Validate sphere, cylinder, and axis
        if (sphere < -20.00 || sphere > 20.00) {
            System.out.println("Invalid sphere value");
            return false;
        }
        if (cylinder < -4.00 || cylinder > 4.00) {
            System.out.println("Invalid cylinder value");
            return false;
        }
        if (axis < 0 || axis > 180) {
            System.out.println("Invalid axis value");
            return false;
        }

        // Validate date (DD/MM/YY format)
        if (!date.matches("\\d{2}/\\d{2}/\\d{2}")) {
            System.out.println("Invalid date format");
            return false;
        }

        // Validate optometrist name
        if (optometrist.length() < 8 || optometrist.length() > 25) {
            System.out.println("Invalid optometrist name length");
            return false;
        }

        // If all validations pass, write to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Prescription.txt", true))) {
            writer.write(firstName + " " + lastName + ", " + address + ", " + sphere + ", " + cylinder + ", " + axis + ", " + date + ", " + optometrist);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<String> remarks = new ArrayList<>();  // Holds remarks for a prescription

    // Method to add a remark
    public boolean addRemark(String remarkText, String category) {
        // Check if the prescription already has 2 remarks
        if (remarks.size() >= 2) {
            return false;  // Limit exceeded
        }

        // Validate remark text (minimum 6 words, maximum 20 words, first word capitalized)
        String[] words = remarkText.split("\\s+");
        if (words.length < 6 || words.length > 20 || !Character.isUpperCase(words[0].charAt(0))) {
            return false;  // Invalid remark text
        }

        // Validate category (must be "client" or "optometrist")
        if (!category.equals("client") && !category.equals("optometrist")) {
            return false;  // Invalid category
        }

        // Add the remark to the list
        remarks.add(remarkText);
        return true;  // Successfully added
    }
}

