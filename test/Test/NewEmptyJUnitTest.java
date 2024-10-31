/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Test;

import Service.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class NewEmptyJUnitTest {

    private Service service;

    @Before
    public void setUp() {
        service = new Service();
    }

    public List<String> validateInputObject(String email, String userName, String fullName) {
        List<String> errors = new ArrayList<>();

        // Kiểm tra độ dài của fullname, userName và email
        if (fullName == null || fullName.length() > 50) {
            errors.add("Full name must be a maximum of 50 characters long.");
        }
        if (userName == null || userName.length() > 50) {
            errors.add("User name must be a maximum of 50 characters long.");
        }
        if (email == null || email.length() > 50) {
            errors.add("Email must be a maximum of 50 characters long.");
        }

        // Kiểm tra định dạng email phải là @gmail.com
        String emailRegex = "^[\\w-\\.]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email != null && !pattern.matcher(email).matches()) {
            errors.add("Email must be in the format @gmail.com.");
        }

        return errors; // Trả về danh sách lỗi
    }

    @Test
    public void validateInputObjectTest() {
        // Dữ liệu kiểm thử
        String[][] testData = {
            {"abcdxyz@gmail.com", "validUserName", "Valid Full Name"}, // Case hợp lệ
            {"abcdxyz@gmail.com", "validUserName", "A very very long full name that exceeds the maximum length allowed for a full name."}, // FullName dài
            {"thisIsAVeryLongUserNameThatExceedsFiftyCharactersInLength@gmail.com", "validUserName", "Valid Full Name"}, // UserName dài
            {"invalidEmailFormat", "validUserName", "Valid Full Name"}, // Định dạng email không hợp lệ
            {"abcdxyz@gmail.comabcdxyz@gmail.comabcdxyz@gmail.comabcdxyz@gmail.com", "validUserName", "Valid Full Name"}, // Email dài
            {"test@gmail.com", "testUserName", "ThisNameIsWayTooLongForTheValidationCheckAndShouldFail"}, // FullName quá dài
        };

        for (String[] data : testData) {
            String email = data[0];
            String userName = data[1];
            String fullName = data[2];
            List<String> errors = validateInputObject(email, userName, fullName);

            System.out.println("Testing with Email: " + email + ", UserName: " + userName + ", FullName: " + fullName);
            System.out.println("Errors: " + errors);
//            assertTrue(errors.size() > 0); // Kiểm tra rằng có lỗi phát sinh
        }
    }
}
