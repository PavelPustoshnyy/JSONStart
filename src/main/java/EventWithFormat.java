import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class EventWithFormat {
    public String name;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss")
    public Date eventDate;

    public EventWithFormat(String name, Date eventDate) {
        this.name=name;
        this.eventDate=eventDate;
    }
}