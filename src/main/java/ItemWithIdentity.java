import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class ItemWithIdentity {
    public int id;
    public String itemName;
    public UserWithIdentity owner;

    public ItemWithIdentity(int id, String itemName, UserWithIdentity owner) {
        this.owner=owner;
        this.id=id;
        this.itemName=itemName;
    }
}