/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Library {

    public enum GetType {
        ALL,
        OVERVIEW,
        DETAIL
    }

    private static final Gson gson = new Gson();
    private static final SecureRandom random = new SecureRandom();
    private static final Logger LOGGER = Logger.getLogger(Library.class.getName());

    /**
     * Mã hóa mật khẩu bằng SHA-256.
     *
     * @param password Mật khẩu cần mã hóa.
     * @return Mật khẩu đã mã hóa dưới dạng chuỗi hex.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error occurred while hashing password", e);
        }
    }

    /**
     * Chuyển đổi mảng byte sang chuỗi hex.
     *
     * @param hash Mảng byte cần chuyển đổi.
     * @return Chuỗi hex.
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Lấy thời gian hiện tại và cộng thêm số phút mong muốn.
     *
     * @param minutes Số phút cần cộng thêm.
     * @return Thời gian sau khi cộng thêm phút dưới dạng chuỗi.
     */
    public static String getCurrentTimePlusMinutes(int minutes) {
        LocalDateTime currentTime = LocalDateTime.now();

        LocalDateTime newTime = currentTime.plusMinutes(minutes);

        // Định dạng thời gian thành chuỗi với định dạng mong muốn
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return newTime.format(formatter);
    }

    /**
     * Tạo token ngẫu nhiên với độ dài tùy chọn.
     *
     * @param length Độ dài token cần tạo.
     * @return Token ngẫu nhiên dưới dạng chuỗi.
     */
    public static String generateRandomToken(int length) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder token = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            token.append(chars.charAt(random.nextInt(chars.length())));
        }
        return token.toString();
    }

    /**
     * Đọc và dịch chuỗi JSON thành Map, xử lý lỗi nếu có (sử dụng Gson).
     *
     * @param jsonString Chuỗi JSON cần đọc.
     * @return Map chứa dữ liệu từ JSON hoặc null nếu có lỗi xảy ra.
     */
    public static Map<String, Object> parseJson(String jsonString) {
        try {
            return gson.fromJson(jsonString, Map.class);
        } catch (JsonSyntaxException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi dịch JSON: ", e);
            return null;
        }
    }

    /**
     * Tạo mã số ngẫu nhiên gồm 6 chữ số.
     *
     * @return Mã số ngẫu nhiên 6 chữ số dưới dạng chuỗi.
     */
    public static String generateSixDigitCode() {
        int number = random.nextInt(900000) + 100000; // Tạo số ngẫu nhiên trong khoảng 100000 đến 999999
        return String.valueOf(number);
    }

    /**
     * Tạo UUID ngẫu nhiên.
     *
     * @return UUID dưới dạng chuỗi.
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    

    public static void main(String[] args) {
        String password = "mySecurePassword";
        String hashedPassword = hashPassword(password);
        LOGGER.log(Level.INFO, "Mat khau da ma hoa: " + hashedPassword);

        String uuid = generateUUID();
        LOGGER.log(Level.INFO, "UUID: " + uuid);

        String sixDigitCode = generateSixDigitCode();
        LOGGER.log(Level.INFO, "Ma 6 so ngau nhien: " + sixDigitCode);

        int minutesToAdd = 30;
        String newTime = getCurrentTimePlusMinutes(minutesToAdd);
        LOGGER.log(Level.INFO, "Thoi gian hien tai + " + minutesToAdd + " phut: " + newTime);

        int tokenLength = 60;
        String token = generateRandomToken(tokenLength);
        LOGGER.log(Level.INFO, "Token ngẫu nhiên dài " + tokenLength + " ký tự: " + token);

        String json = "{\"name\":\"John Doe\", \"age\":30}";
        Map<String, Object> jsonData = parseJson(json);

        if (jsonData != null) {
            LOGGER.log(Level.INFO, "Dữ liệu JSON đã dịch: " + jsonData);
        } else {
            LOGGER.log(Level.WARNING, "Lỗi xảy ra khi dịch JSON.");
        }
    }

}
