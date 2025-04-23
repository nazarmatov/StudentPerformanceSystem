package GUI.journal;

import DAO.GradeDao;
import DAO.StudentDao;
import models.Grade;
import models.Lesson;
import models.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LessonJournal extends JDialog {
    private GradeDao gradeDao = new GradeDao();
    private StudentDao studentDao = new StudentDao();

    public LessonJournal(Lesson lesson) {
        setTitle("Журнал: " + lesson.getTitle());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setModal(true);

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Студент", "Оценка"}, 0);
        table.setModel(model);

        List<Grade> grades = gradeDao.getGradesByLessonId(lesson.getId());
        for (Grade grade : grades) {
            Student student = studentDao.getStudentByID((int) grade.getStudentId());
            model.addRow(new Object[]{student.getName(), grade.getValue()});
        }

        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
