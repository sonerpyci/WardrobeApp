package tr.edu.yildiz.payci.soner.wardrobe.entities;

import java.util.Date;

public class Event {

    private int id;
    private String name;
    private String content;
    private Date date;
    private Location location;


    public Event() {
    }

    public Event(int id, String name, String content, Date date, Location location) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.location = location;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}