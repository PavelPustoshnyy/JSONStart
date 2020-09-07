public class ItemUser {
    public int id;
    public String itemName;
    public User owner;

    public ItemUser(int id, String itemName, User owner) {
        this.id=id;
        this.itemName=itemName;
        this.owner=owner;
    }
}