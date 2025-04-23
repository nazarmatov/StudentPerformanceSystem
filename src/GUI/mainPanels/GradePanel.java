package GUI.mainPanels;

import DAO.*;
import models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GradePanel extends JPanel {
    private GradeDao gradeDao;
    private StudentDao studentDao;
    private LessonDao lessonDao;
    private JTable gradeTable;
    private DefaultTableModel tableModel;

    public GradePanel() {
        this.gradeDao = new GradeDao();
        this.studentDao = new StudentDao();
        this.lessonDao = new LessonDao();

        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Student", "Lesson", "Grade"}, 0);
        gradeTable = new JTable(tableModel);
        add(new JScrollPane(gradeTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton addButton = new JButton("put grade");
        JButton deleteButton = new JButton("delete grade");

        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadGrades();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Student> students = studentDao.getAllStudents();
                List<Lesson> lessons = lessonDao.getAllLessons();

                JComboBox<Student> studentBox = new JComboBox<>(students.toArray(new Student[0]));
                JComboBox<Lesson> lessonBox = new JComboBox<>(lessons.toArray(new Lesson[0]));
                JTextField scoreField = new JTextField();

                Object[] inputs = {
                    "Student:", studentBox,
                    "Lesson:", lessonBox,
                    "Grade", scoreField
                };

                int option = JOptionPane.showConfirmDialog(null, inputs, "Add grade", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    Grade grade = new Grade();
                    grade.setStudentId(((Student) studentBox.getSelectedItem()).getId());
                    grade.setLessonId(((Lesson) lessonBox.getSelectedItem()).getId());
                    grade.setValue(Integer.parseInt(scoreField.getText()));
                    gradeDao.addGrade(grade);
                    loadGrades();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = gradeTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Choose grade");
                    return;
                }
                long id = (long) gradeTable.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure about this?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    gradeDao.deleteGrade((int) id);
                    loadGrades();
                }
            }
        });
    }

    private void loadGrades() {
        List<Grade> grades = gradeDao.getAllGrades();
        tableModel.setRowCount(0);
        for (Grade grade : grades) {
            String studentName = studentDao.getStudentByID((int) grade.getStudentId()).getName();
            String lessonTitle = lessonDao.getLessonById((int) grade.getLessonId()).getTitle();
            tableModel.addRow(new Object[]{
                grade.getId(),
                studentName,
                lessonTitle,
                grade.getValue()
            });
        }
    }
}
