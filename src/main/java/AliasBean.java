import com.fasterxml.jackson.annotation.JsonAlias;

public class AliasBean {
    @JsonAlias({ "fName", "f_name" })
    private String firstName;
    @JsonAlias({ "lastName", "l_name" })
    private String lastName;
    public AliasBean() {

    }
    public AliasBean(String firstName,String lastName) {
        this.firstName=firstName;
        this.lastName=lastName;
    }

    public String getFirstName() {

        return firstName;
    }
    public String getLastName() {

        return lastName;
    }
}