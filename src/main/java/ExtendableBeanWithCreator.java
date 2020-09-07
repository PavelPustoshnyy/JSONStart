import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class ExtendableBeanWithCreator {
    public String name;
    private Map<String, String> properties;

    @JsonAnySetter
    public void add(String key, String value) {
        if (this.properties == null) {
            this.properties = new HashMap<String, String>();
        }
        properties.put(key, value);
    }


    public Map<String, String> getProperties() {
        return properties;
    }
}