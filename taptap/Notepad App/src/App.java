import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
                    NotepadGui notepad = new NotepadGui();
                    notepad.setVisible(true);
                }catch (Exception e){
                    e.getStackTrace();
                }

            }
        });
    }
}
