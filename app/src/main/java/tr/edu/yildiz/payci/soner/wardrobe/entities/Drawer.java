package tr.edu.yildiz.payci.soner.wardrobe.entities;

import java.util.ArrayList;

public class Drawer {

    private String guid;
    private String name;
    private ArrayList<Clothing> Clothes;

    public Drawer() {
        this.Clothes = new ArrayList<>();
    }

    public Drawer(String name) {
        this.name = name;
        this.Clothes = new ArrayList<>();
    }

    public Drawer(String name, ArrayList<Clothing> clothes) {
        this.name = name;
        this.Clothes = clothes;
    }

    public Drawer(String guid, String name, ArrayList<Clothing> clothes) {
        this.guid = guid;
        this.name = name;
        this.Clothes = clothes;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Clothing> getClothes() {
        return Clothes;
    }

    public void setClothes(ArrayList<Clothing> clothes) {
        Clothes = clothes;
    }
}
