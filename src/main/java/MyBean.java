import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"name", "id"})
public class MyBean {
    public int id;
    private String name;

    public MyBean() {
    }

    public MyBean(int i, String my_bean) {
        id = i;
        name = my_bean;
    }

    @JsonGetter("name")
    @JsonProperty("name")
    public String getTheName() {

        return name;
    }

    @JsonProperty("name")
    public void setTheName(String name) {
        this.name = name;
    }

}