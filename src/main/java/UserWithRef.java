import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.*;

public class UserWithRef {
    public int id;
    public String name;

    @JsonBackReference
    public List<ItemWithRef> userItems;

    public UserWithRef() {
        super();
    }

    public UserWithRef(final int id, final String name) {
        this.id = id;
        this.name = name;
        userItems = new ArrayList<ItemWithRef>();
    }

    public void addItem(ItemWithRef item) {
        userItems.add(item);
    }
}