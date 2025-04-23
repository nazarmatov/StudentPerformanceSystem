package models;

public class Lesson {
    private long id;
    private String title;

    @Override
    public String toString() {
        return title;
    }

    public Lesson (){}
    public Lesson(String title){
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
