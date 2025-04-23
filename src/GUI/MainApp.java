package GUI;

import javax.swing.*;
import java.awt.*;

import GUI.mainPanels.*;

public class MainApp extends JFrame {

    public MainApp() {
        setTitle("Система Успеваемости Студентов");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Студенты", new StudentPanel());
        tabbedPane.addTab("Уроки", new LessonPanel());
        tabbedPane.addTab("Оценки", new GradePanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
}
