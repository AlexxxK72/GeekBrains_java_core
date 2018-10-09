package pattern2;

import java.util.Date;

public abstract class Document implements IViewed {
    private Date dateCreate;
    private Date dateLastEdit;
    private String name;
    private String author;

    public Document(String name, String author) {
        this.name = name;
        this.author = author;
        this.dateCreate = new Date();
        this.dateLastEdit = new Date();
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public Date getDateLastEdit() {
        return dateLastEdit;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void save();

}
