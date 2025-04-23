package models;

import java.util.ArrayList;
import java.util.List;

public class Student{
    private String name;
    private String group;
    private long id;
    private List<Grade> grades = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }

    public Student(){}

    public Student(String name, String group) {
        this.name = name;
        this.group = group;
    }
 
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }

    public double avarageGrade(){
        if (grades.isEmpty()) {
            return 0;
        }
        int average = 0;
        for(Grade g:grades){
            average += g.getValue();
        }
        return average/(double)grades.size();
    }

    public void putGrade(Grade grade){
        grades.add(grade);
    }
}
