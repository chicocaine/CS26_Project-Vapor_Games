package Utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {

    public DateTime () {}

    public String getDateTime () {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);

    }
}
