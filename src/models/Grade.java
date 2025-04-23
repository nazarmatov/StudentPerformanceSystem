package models;

public class Grade {
    private long id;
    private long student_id;
    private long lesson_id;
    private int value;

    public Grade(){}
    public Grade( long student_id, long lesson_id, int value) {
        this.student_id = student_id;
        this.lesson_id = lesson_id;
        this.value = value;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }

    public long getLessonId() {
        return lesson_id;
    }
    public void setLessonId(long lesson_id) {
        this.lesson_id = lesson_id;
    }

    public long getStudentId() {
        return student_id;
    }
    public void setStudentId(long student_id) {
        this.student_id = student_id;
    }
}
