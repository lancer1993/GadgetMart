package com.gadget_mart.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {

    public String gender(String nicNumber) {
        String gen = "";
        String genNumber = "";
        if (nicNumber.length() == 10) {
            genNumber = nicNumber.substring(2, 5);
        } else if (nicNumber.length() == 12) {
            genNumber = nicNumber.substring(4, 7);
        }
        int num = Integer.parseInt(genNumber);
        if (num > 500) {
            gen = "Female";
        } else {
            gen = "Male";
        }
        return gen;
    }

    public int calculateAge(String nicNumber) {
        int age = 0;
        int year = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        if (nicNumber.length() == 10) {
            year = Integer.parseInt("19" + nicNumber.substring(0, 2));
        } else if (nicNumber.length() == 12) {
            year = Integer.parseInt(nicNumber.substring(0, 4));
        }
        age = Integer.parseInt(simpleDateFormat.format(date)) - year;
        return age;
    }

    public String getDateOfBirthString(String nicNumber) {
        String dob = "";
        String year = "";
        String days = "";
        String mon = "";
        if (nicNumber.length() == 10) {
            year = 19 + nicNumber.substring(0, 2);
            days = nicNumber.substring(2, 5);
        } else {
            year = nicNumber.substring(0, 4).toString();
            days = nicNumber.substring(4, 7);
        }
        int[] months = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int day = Integer.parseInt(days);
        if (day > 500) {
            day = day - 500;
        }
        int i = 0;
        while (day > months[i]) {
            day = day - months[i];
            i++;
        }
        String regex = "(.*?\\d){2,}";
        String input = i + 1 + "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        boolean isMatched = matcher.matches();
        if (!isMatched) {
            mon = 0 + input;
        } else {
            mon = input;
        }
        dob = year+"-"+mon+"-"+day;
        return dob;
    }

    public boolean validateEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
