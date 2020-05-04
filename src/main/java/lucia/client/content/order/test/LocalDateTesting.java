package main.java.lucia.client.content.order.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

//just some testing to see how localdates/times are saved in gson
public class LocalDateTesting {

    public static void main(String[] args){
        LocalDateTime now = LocalDateTime.now();
        LocalDate now2 = now.toLocalDate();
        LocalTime time = now.toLocalTime();
        DayOfWeek day = now.getDayOfWeek();
        Set<DayOfWeek> days = new HashSet<>();
        days.add(day);
        days.add(DayOfWeek.TUESDAY);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("dateTime:");
        System.out.println(gson.toJson(now));
        System.out.println("date:");
        System.out.println(gson.toJson(now2));
        System.out.println("time:");
        System.out.println(gson.toJson(time));
        System.out.println("day of week");
        System.out.println(gson.toJson(days));
    }
}
