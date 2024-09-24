import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class NotepadGui extends JFrame {
    //file explore
    private JFileChooser fileChooser;

    private JTextArea textArea;
    private File currentFile;

    //Swing inbuilt library for undo and redo
    private UndoManager undoManager;

    public JTextArea getTextArea(){return textArea;}

    public NotepadGui(){
        super("Notepad");
        setSize(400,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        pack();

        //file chooser setup
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("src/Assets"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));

        undoManager = new UndoManager();

        //addComponent
        addComponent();
    }

    public void addComponent(){
        addToolBar();

        //text area
        textArea = new JTextArea();
        //add each edit we do in the text area (either adding or removing the text)
        textArea.getDocument().addUndoableEditListener(e -> undoManager.addEdit(e.getEdit()));

        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void addToolBar(){
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        //menu bar
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);

        //add menu
        menuBar.add(addFileMenu());
        menuBar.add(addEditMenu());
        menuBar.add(addFormatMenu());
        menuBar.add(addViewMenu());

        add(toolBar, BorderLayout.NORTH);
    }

    private JMenu addFileMenu(){
        JMenu fileMenu = new JMenu("File");

        //"new" functionality - reset everything
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.addActionListener(e -> {
            //reset title header
            setTitle("Notepad");

            //set current file
            currentFile = null;

            //reset the text area
            textArea.setText("");

        });
        fileMenu.add(newMenuItem);

        //"open" functionality - open a text file
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(e -> {
            // open file explorer
            int result = fileChooser.showOpenDialog(NotepadGui.this);

            //continue only when click ok
            if (result != JFileChooser.APPROVE_OPTION) return;

            //reset notepad
            newMenuItem.doClick();
            try {
                //selected file
                File selectedFile = fileChooser.getSelectedFile();

                //update current file
                currentFile = selectedFile;

                //update title
                setTitle(selectedFile.getName());

                //read the file
                FileReader fileReader = new FileReader(selectedFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                //store the text
                StringBuilder filetext = new StringBuilder();
                String readText;
                while ((readText = bufferedReader.readLine()) != null){
                    filetext.append(readText + "\n");
                }

                //update text on area
                textArea.setText(filetext.toString());

            } catch (Exception e1){
                e1.getStackTrace();
            }

        });
        fileMenu.add(openMenuItem);

        //"save as" functionality - create new twxt file and save user text file
        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        saveAsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open save dialogBox
                int result = fileChooser.showSaveDialog(NotepadGui.this);

                //continue to execute code if user click save button
                if (result != JFileChooser.APPROVE_OPTION) return;

                try{
                    File selectedFile = fileChooser.getSelectedFile();


                    //add .txt if not written
                    String fileName = selectedFile.getName();
                    if (fileName.length() < 4  || !fileName.substring(fileName.length() - 4).equalsIgnoreCase(".txt")) {
                        selectedFile = new File(selectedFile.getAbsoluteFile() + ".txt");
                    }
                    //create a new file
                    selectedFile.createNewFile();

                    //we will now write the user content in just created file
                    FileWriter fileWriter = new FileWriter(selectedFile);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(textArea.getText());
                    bufferedWriter.close();
                    fileWriter.close();

                    //update title for the save file
                   /* if (fileName.length()<4 || !fileName.substring(fileName.length()-4).equalsIgnoreCase(".txt")){
                        setTitle(fileName);
                    } else {
                        setTitle(fileName.substring(0,fileName.length()-4));
                    }
                    */
                    setTitle(selectedFile.getName());

                    //update current file
                    currentFile = selectedFile;

                    //show dialog box
                    JOptionPane.showMessageDialog(NotepadGui.this, "Saved File!");

                } catch (Exception e1){
                    e1.getStackTrace();
                }
            }
        });
        fileMenu.add(saveAsMenuItem);

        //"save " functionality -  save text in current text file
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(e -> {
            if (currentFile == null) {
                saveAsMenuItem.doClick();
                return;
            }

            try {
                //write the current file
                FileWriter fileWriter = new FileWriter(currentFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(textArea.getText());
                bufferedWriter.close();
                fileWriter.close();

            }catch (Exception e1){
                e1.getStackTrace();
            }

        });
        fileMenu.add(saveMenuItem);

        //"exit" functionality - to exit the app
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> NotepadGui.this.dispose());
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    private JMenu addEditMenu(){
        JMenu editMenu = new JMenu("Edit");

        //add undo functionality
        JMenuItem undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.addActionListener(e -> {
            //means if there are any edit that can be undo then undo it
            if (undoManager.canUndo()) undoManager.undo();
        });
        editMenu.add(undoMenuItem);

        //add redo functionality
        JMenuItem redoMenuItem = new JMenuItem("Redo");
        redoMenuItem.addActionListener(e -> {
            //means if there is any edit that can be redo then redo it
            if (undoManager.canRedo()) undoManager.redo();
        });
        editMenu.add(redoMenuItem);

        return editMenu;
    }

    private JMenu addFormatMenu(){
        JMenu formatMenu = new JMenu("Format");

        // add wrap feature
        JCheckBoxMenuItem wordWrapMenuItem = new JCheckBoxMenuItem("Word Wrap");
        wordWrapMenuItem.addActionListener(e -> {
            if (wordWrapMenuItem.getState()){
                //wrap word
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
            } else {
                //unwrap the word
                textArea.setLineWrap(false);
                textArea.setWrapStyleWord(false);
            }
        });
        formatMenu.add(wordWrapMenuItem);

        //align text
        JMenu alignTextMenu = new JMenu("Align Text");

        //left alignment
        JMenuItem alignTextLeftMenuItem = new JMenuItem("Left");
        alignTextLeftMenuItem.addActionListener(e -> textArea
                .setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT));
        alignTextMenu.add(alignTextLeftMenuItem);

        //right alignment
        JMenuItem alignTextRightMenuItem = new JMenuItem("Right");
        alignTextRightMenuItem.addActionListener(e -> textArea
                .setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT));
        alignTextMenu.add(alignTextRightMenuItem);

        formatMenu.add(alignTextMenu);

        //font menu functionality
        JMenuItem fontMenuItem = new JMenuItem("Font...");
        fontMenuItem.addActionListener(e -> {
            new FontMenu(this).setVisible(true);
        });
        formatMenu.add(fontMenuItem);

        return formatMenu;
    }

    private JMenu addViewMenu(){
        JMenu viewMenu = new JMenu("View");

        JMenu zoomMenu = new JMenu("Zoom");

        //zoom in function
        JMenuItem zoomInMenuItem = new JMenuItem("Zoom in");
        zoomInMenuItem.addActionListener(e -> {
            Font currentFont = textArea.getFont();
            textArea.setFont(new Font(currentFont.getName()
                    , currentFont.getStyle()
                    , currentFont.getSize() + 1));
        });
        zoomMenu.add(zoomInMenuItem);

        //zoom out functionality
        JMenuItem zoomOutMenuItem = new JMenuItem("Zoom out");
        zoomOutMenuItem.addActionListener(e -> {
            Font currentFont = textArea.getFont();

            if (currentFont.getSize() < 9) return;

            textArea.setFont(new Font(currentFont.getName()
                    , currentFont.getStyle()
                    , currentFont.getSize() - 1));
        });
        zoomMenu.add(zoomOutMenuItem);

        //zoom restore functionality
        JMenuItem zoomRestoreMenuItem = new JMenuItem("Restore Default Zoom");
        zoomRestoreMenuItem.addActionListener(e -> {
            Font currentFont = textArea.getFont();
            textArea.setFont(new Font(currentFont.getName()
                    , currentFont.getStyle()
                    , 12));
        });
        zoomMenu.add(zoomRestoreMenuItem);

        viewMenu.add(zoomMenu);

        return viewMenu;
    }
}
