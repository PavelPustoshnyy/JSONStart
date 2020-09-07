import com.fasterxml.jackson.annotation.JsonSetter;

public class MyBeanWithSetter {
    public int id;
    private String name;

    @JsonSetter("name")
    public void setTheName(String name) {
        this.name = name;
    }

    public String getTheName() {
        return name;
    }

    public int getTheId() {
        return id;
    }
}
