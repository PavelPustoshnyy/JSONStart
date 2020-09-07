import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeEnumWithValue {
    TYPE1(1, "Type A"), TYPE2(2, "Type 2");

    private Integer id;
    private String name;

    TypeEnumWithValue(int i, String s) {
        id=i;
        name=s;
    }

    // standard constructors

    @JsonValue
    public String getName() {
        return name;
    }
}