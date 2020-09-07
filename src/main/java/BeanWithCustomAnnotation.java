import java.util.Date;

@CustomAnnotation
public class BeanWithCustomAnnotation {
    public int id;
    public String name;
    public Date dateCreated;

    public BeanWithCustomAnnotation(int id, String name, Date dateCreated) {
        this.dateCreated=dateCreated;
        this.id=id;
        this.name=name;
    }
}