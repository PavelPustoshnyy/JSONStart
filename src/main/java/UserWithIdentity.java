import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class UserWithIdentity {
    public int id;
    public String name;
    public List<ItemWithIdentity> userItems;

    public UserWithIdentity() {
        super();
    }

    public UserWithIdentity(int id, String name) {
        this.id=id;
        this.name=name;
        this.userItems=new ArrayList<ItemWithIdentity>();
    }

    public void addItem(final ItemWithIdentity item) {
        userItems.add(item);
    }
}