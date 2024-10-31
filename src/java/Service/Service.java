/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Predator
 */
public class Service {


    public List<String> validateInputObject(String email, String userName, String fullName) {
        List<String> errors = new ArrayList<>();

        // Kiểm tra độ dài của fullname, userName, và email
        if (fullName.length() > 50) {
            errors.add("Full name phải có độ dài tối đa 50 ký tự.");
        }
        if (userName.length() > 50) {
            errors.add("User name phải có độ dài tối đa 50 ký tự.");
        }
        if (email.length() > 50) {
            errors.add("Email phải có độ dài tối đa 50 ký tự.");
        }

        // Kiểm tra định dạng email phải là @gmail.com
        String emailRegex = "^[\\w-\\.]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()) {
            errors.add("Email phải có định dạng @gmail.com.");
        }

        return errors;
    }
    
    
}
