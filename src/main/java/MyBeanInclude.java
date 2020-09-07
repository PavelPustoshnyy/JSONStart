import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyBeanInclude {
    public int id;
    public String name;

    public MyBeanInclude(int id, String name) {
        this.id=id;
        this.name=name;

    }
}