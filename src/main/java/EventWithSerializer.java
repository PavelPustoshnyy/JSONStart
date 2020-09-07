import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class EventWithSerializer {
    public String name;

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date eventDate;

    public EventWithSerializer(String party, Date date) {
        name=party;
        eventDate=date;
    }
}