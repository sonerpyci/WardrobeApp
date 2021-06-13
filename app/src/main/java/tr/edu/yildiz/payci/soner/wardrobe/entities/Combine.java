package tr.edu.yildiz.payci.soner.wardrobe.entities;

public class Combine {

    private String guid;
    private String name;
    private Clothing firstClothing;
    private Clothing secondClothing;
    private Clothing thirdClothing;
    private Clothing fourthClothing;
    private Clothing fifthClothing;

    public Combine() {

    }

    public Combine(String name) {
        this.name = name;
    }

    public Combine(String name, Clothing firstClothing, Clothing secondClothing, Clothing thirdClothing, Clothing fourthClothing, Clothing fifthClothing) {
        this.name = name;
        this.firstClothing = firstClothing;
        this.secondClothing = secondClothing;
        this.thirdClothing = thirdClothing;
        this.fourthClothing = fourthClothing;
        this.fifthClothing = fifthClothing;
    }

    public Combine(String guid, String name, Clothing firstClothing, Clothing secondClothing, Clothing thirdClothing, Clothing fourthClothing, Clothing fifthClothing) {
        this.guid = guid;
        this.name = name;
        this.firstClothing = firstClothing;
        this.secondClothing = secondClothing;
        this.thirdClothing = thirdClothing;
        this.fourthClothing = fourthClothing;
        this.fifthClothing = fifthClothing;
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

    public Clothing getFirstClothing() {
        return firstClothing;
    }

    public void setFirstClothing(Clothing firstClothing) {
        this.firstClothing = firstClothing;
    }

    public Clothing getSecondClothing() {
        return secondClothing;
    }

    public void setSecondClothing(Clothing secondClothing) {
        this.secondClothing = secondClothing;
    }

    public Clothing getThirdClothing() {
        return thirdClothing;
    }

    public void setThirdClothing(Clothing thirdClothing) {
        this.thirdClothing = thirdClothing;
    }

    public Clothing getFourthClothing() {
        return fourthClothing;
    }

    public void setFourthClothing(Clothing fourthClothing) {
        this.fourthClothing = fourthClothing;
    }

    public Clothing getFifthClothing() {
        return fifthClothing;
    }

    public void setFifthClothing(Clothing fifthClothing) {
        this.fifthClothing = fifthClothing;
    }
}
