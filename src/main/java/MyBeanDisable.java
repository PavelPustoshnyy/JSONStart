import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "id" })
public class MyBeanDisable {
    public int id;
    public String name;

    public MyBeanDisable(int id, String name) {
        this.id=id;
        this.name=name;
    }
}