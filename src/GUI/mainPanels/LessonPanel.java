package GUI.mainPanels;

import DAO.LessonDao;
import models.Lesson;
import GUI.journal.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LessonPanel extends JPanel {
    private LessonDao lessonDao;
    private JTable lessonTable;
    private DefaultTableModel tableModel;

    public LessonPanel() {
        this.lessonDao = new LessonDao();
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Title"}, 0);
        lessonTable = new JTable(tableModel);
        add(new JScrollPane(lessonTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton addButton = new JButton("add lesson");
        JButton deleteButton = new JButton("delete lesson");
        JButton journalButton = new JButton("show jornal");

        bottomPanel.add(journalButton);
        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadLessons();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField titleField = new JTextField();
                Object[] inputs = {"title:", titleField};
                int option = JOptionPane.showConfirmDialog(null, inputs, "Add lesson", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    Lesson lesson = new Lesson();
                    lesson.setTitle(titleField.getText());
                    lessonDao.addLesson(lesson);
                    loadLessons();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = lessonTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Choose lesson");
                    return;
                }
                long id = (long) lessonTable.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    lessonDao.deleteLesson((int) id);
                    loadLessons();
                }
            }
        });

        journalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = lessonTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Choose lesson");
                    return;
                }
                long lessonId = (long) tableModel.getValueAt(selectedRow, 0);
                Lesson lesson = lessonDao.getLessonById((int) lessonId);
                new LessonJournal(lesson).setVisible(true);
            }
        });
    }

    private void loadLessons() {
        List<Lesson> lessons = lessonDao.getAllLessons();
        tableModel.setRowCount(0);
        for (Lesson lesson : lessons) {
            tableModel.addRow(new Object[]{lesson.getId(), lesson.getTitle()});
        }
    }
}
