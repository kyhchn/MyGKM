package com.example.mygkm.utils;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class Utils {
    public static boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public static boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    public static String getCurrentFormattedDate(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }

    public static String getCurrentFormattedTime(){
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter
                .ofPattern("HH:mm", new Locale("id", "ID"));
        String formattedTime = currentTime.format(timeFormatter);
        return formattedTime;
    }

    public static String formatRupiah(int amount) {
        // Create a NumberFormat for the specified locale (Indonesia in this case)
        NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String formatted = rupiahFormat.format(amount);
        // Format the amount as currency
        return formatted.substring(0, formatted.length()-3);
    }
}
