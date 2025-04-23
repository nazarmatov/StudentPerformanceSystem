package GUI.mainPanels;

import DAO.StudentDao;
import models.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class StudentPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private StudentDao studentDao;

    public StudentPanel() {
        studentDao = new StudentDao();
        setLayout(new BorderLayout());

        // Верхняя панель: поиск + кнопка меню
        JPanel topPanel = new JPanel(new BorderLayout());

        searchField = new JTextField();
        topPanel.add(searchField, BorderLayout.CENTER);

        JButton menuButton = new JButton("⋮");
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("Добавить");
        JMenuItem updateItem = new JMenuItem("Обновить");
        JMenuItem deleteItem = new JMenuItem("Удалить");

        popupMenu.add(addItem);
        popupMenu.add(updateItem);
        popupMenu.add(deleteItem);

        menuButton.addActionListener(e -> popupMenu.show(menuButton, 0, menuButton.getHeight()));
        topPanel.add(menuButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Таблица
        tableModel = new DefaultTableModel(new String[]{"ID", "Имя", "Группа"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadStudents();

        // Поиск по тексту
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                filterStudents(searchField.getText());
            }
        });

        // Действия меню
        addItem.addActionListener(e -> showAddDialog());
        updateItem.addActionListener(e -> showUpdateDialog());
        deleteItem.addActionListener(e -> deleteSelectedStudent());
    }

    private void loadStudents() {
        List<Student> students = studentDao.getAllStudents();
        updateTable(students);
    }

    private void filterStudents(String keyword) {
        List<Student> filtered = studentDao.getAllStudents().stream()
            .filter(s -> s.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                         s.getGroup().toLowerCase().contains(keyword.toLowerCase()))
            .collect(Collectors.toList());
        updateTable(filtered);
    }

    private void updateTable(List<Student> students) {
        tableModel.setRowCount(0);
        for (Student s : students) {
            tableModel.addRow(new Object[]{s.getId(), s.getName(), s.getGroup()});
        }
    }

    private void showAddDialog() {
        JTextField nameField = new JTextField();
        JTextField groupField = new JTextField();
        Object[] inputs = {
            "Имя:", nameField,
            "Группа:", groupField
        };
        int option = JOptionPane.showConfirmDialog(this, inputs, "Добавить студента", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Student s = new Student(nameField.getText(), groupField.getText());
            studentDao.addStudent(s);
            loadStudents();
        }
    }

    private void showUpdateDialog() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Выберите студента!");
            return;
        }

        long id = (long) table.getValueAt(row, 0);
        String currentName = (String) table.getValueAt(row, 1);
        String currentGroup = (String) table.getValueAt(row, 2);

        JTextField nameField = new JTextField(currentName);
        JTextField groupField = new JTextField(currentGroup);

        Object[] inputs = {
            "Имя:", nameField,
            "Группа:", groupField
        };

        int option = JOptionPane.showConfirmDialog(this, inputs, "Обновить студента", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Student s = new Student(nameField.getText(), groupField.getText());
            s.setId(id);
            studentDao.updateStudent(s);
            loadStudents();
        }
    }

    private void deleteSelectedStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Выберите студента!");
            return;
        }

        long id = (long) table.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Удалить этого студента?", "Подтверждение", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            studentDao.deleteStudent((int) id);
            loadStudents();
        }
    }
}

