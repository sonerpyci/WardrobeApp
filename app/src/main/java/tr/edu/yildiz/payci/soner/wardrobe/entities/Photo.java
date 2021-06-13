package tr.edu.yildiz.payci.soner.wardrobe.entities;

public class Photo {
    private String base64EncodedContent;

    public Photo() {}

    public Photo(String base64EncodedContent) {
        this.base64EncodedContent = base64EncodedContent;
    }

    public String getBase64EncodedContent() {
        return base64EncodedContent;
    }

    public void setBase64EncodedContent(String base64EncodedContent) {
        this.base64EncodedContent = base64EncodedContent;
    }
}
