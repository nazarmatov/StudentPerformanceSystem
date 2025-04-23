package GUI.journal;

import DAO.GradeDao;
import DAO.LessonDao;
import models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentJournal extends JDialog{
    private LessonDao lessonDao = new LessonDao();
    private GradeDao gradeDao = new GradeDao();
    
    public StudentJournal(Student student){
        setTitle("Grades of "+student.getName());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setModal(true);

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Lesson", "Grade"}, 0);
        table.setModel(model);

        List<Grade> grades = gradeDao.getGradesByStudentId(student.getId());
        for(Grade grade:grades){
            Lesson lesson = lessonDao.getLessonById((int) grade.getLessonId());
            model.addRow(new Object[]{lesson.getTitle(), grade.getValue()});
        }

        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
