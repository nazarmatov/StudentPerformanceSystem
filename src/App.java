import javax.swing.SwingUtilities;

import GUI.MainApp;

public class App {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            new MainApp().setVisible(true);
        });
    }
}
