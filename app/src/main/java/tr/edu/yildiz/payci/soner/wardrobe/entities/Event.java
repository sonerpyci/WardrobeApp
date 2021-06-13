package tr.edu.yildiz.payci.soner.wardrobe.entities;

import java.util.ArrayList;
import java.util.Date;

public class Event {

    private String guid;
    private String name;
    private String type;
    private Date date;
    private Location location;
    private ArrayList<Combine> combines;

    public Event() {}

    public Event(String guid, String name, String type, Date date, Location location, ArrayList<Combine> combines) {
        this.guid = guid;
        this.name = name;
        this.type = type;
        this.location = location;
        this.date = date;
        this.combines = combines;
    }
    public Event(String guid, String name, String type, Date date, Location location) {
        this.guid = guid;
        this.name = name;
        this.type = type;
        this.location = location;
        this.date = date;
    }

    public Event(String name, String type, Date date, Location location, ArrayList<Combine> combines) {
        this.guid = guid;
        this.name = name;
        this.type = type;
        this.location = location;
        this.date = date;
        this.combines = combines;
    }

    public Event(String name, String type, Date date, Location location) {
        this.guid = guid;
        this.name = name;
        this.type = type;
        this.location = location;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<Combine> getCombines() {
        return combines;
    }

    public void setCombines(ArrayList<Combine> combines) {
        this.combines = combines;
    }
}
