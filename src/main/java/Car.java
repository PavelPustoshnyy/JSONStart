public class Car {
    Car (){}
    private String color;
    private String type;

    public Car(String color_in, String type_in) {

        color=color_in;
        type=type_in;

    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String colorIn) {
        color = colorIn;
    }

    public void setType(String typeIn) {
        type = typeIn;
    }
    // standard getters setters
}