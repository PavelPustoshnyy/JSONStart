import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "user", namespace="users")
public class UserWithRootNamespace {
    public int id;
    public String name;

    public UserWithRootNamespace(int i, String john) {
        id=i;
        name=john;
    }

    // ...
}