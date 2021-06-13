package tr.edu.yildiz.payci.soner.wardrobe.entities;

public class Clothing {

    private String guid;
    private String type;
    private String color;
    private String name;
    private double price;
    private Photo photo;
    private boolean isSelected = false;

    public Clothing(String guid, String type, String color, String name, double price, Photo photo) {
        this.guid = guid;
        this.type = type;
        this.color = color;
        this.name = name;
        this.price = price;
        this.photo = photo;
    }

    public Clothing(String name, String type, String color, double price, Photo photo) {
        this.type = type;
        this.color = color;
        this.name = name;
        this.price = price;
        this.photo = photo;
    }
    public Clothing(String name, String type, String color, double price) {
        this.type = type;
        this.color = color;
        this.name = name;
        this.price = price;
    }

    public Clothing() { }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
