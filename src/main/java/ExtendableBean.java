import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.HashMap;
import java.util.Map;

public class ExtendableBean {

    public String name;
    private Map<String, String> properties;

    public ExtendableBean(String myBean) {
        properties = new HashMap<String, String>();
        name = myBean;
    }


    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return properties;
    }

    public void add(String key, String val) {
        properties.put(key,val);
    }
};

