import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "id" })
public class BeanWithIgnore {
    @JsonIgnore
    public int id;
    public String name;

    public BeanWithIgnore(int id, String name) {
        this.id=id;
        this.name=name;
    }
}