package serverConsole;


import gameCom.Server;


import javax.swing.*;
import java.awt.*;

public class MainServerConsole extends JPanel {

    private static JTextArea textArea;

    private MainServerConsole() {
        initializeUI();
        Server.setConsole(this);
        Server.runServer();
    }

    private void initializeUI() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(400, 200));

        textArea = new JTextArea(5, 40);
        textArea.setText("Server Console!\n--------------\n");
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    private static void showFrame() {
        JPanel panel = new MainServerConsole();
        panel.setOpaque(true);

        JFrame frame = new JFrame("TwoWeeks Server Console");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public void promptConsole(String text){
        textArea.append(text + "\n");
    }

    public static void main(String[] args) {
        showFrame();
    }
}
