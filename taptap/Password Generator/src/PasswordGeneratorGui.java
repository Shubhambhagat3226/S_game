import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

//frontend
public class PasswordGeneratorGui extends JFrame {
    private PasswordGenerator passwordGenerator;

    public PasswordGeneratorGui(){
        //frame
        super("Password Generator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(540, 570);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        //backend obj
        passwordGenerator = new PasswordGenerator();

        //add gui components
        addGuiComponents();
    }

    private void addGuiComponents(){
        //password label
        JLabel passwordLabel = new JLabel("Password Generator");
        passwordLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setBounds(0, 10, 540, 39);

        add(passwordLabel);

        //password result area
        JTextArea passwordOutput = new JTextArea();
        passwordOutput.setFont(new Font("Dialog", Font.PLAIN, 32));
        passwordOutput.setEditable(false);

        //add scroll
        JScrollPane passwordOutputPane = new JScrollPane(passwordOutput);
        passwordOutputPane.setBounds(25, 97, 479, 70);

        //border add
        passwordOutputPane.setBorder(BorderFactory.createLineBorder(Color.black));
        add(passwordOutputPane);


        //password length label
        JLabel passwordLengthLabel = new JLabel("Password Length: ");
        passwordLengthLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
        passwordLengthLabel.setBounds(25, 215, 272, 39);
        add(passwordLengthLabel);

        //password length input area
        JTextField passwordLengthInputArea = new JTextField();
        passwordLengthInputArea.setBounds(310, 215, 192, 39);
        passwordLengthInputArea.setFont(new Font("Dialog", Font.PLAIN, 32));
        passwordLengthInputArea.setBorder(BorderFactory.createLineBorder(Color.black));
        passwordLengthInputArea.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLengthInputArea.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyChar() == '\b')
                    passwordLengthInputArea.setEditable(true);
                else
                    passwordLengthInputArea.setEditable(false);
            }
        });
        add(passwordLengthInputArea);

        //Toggle Button
        //Uppercase button
        JToggleButton uppercaseToggle = new JToggleButton("Uppercase");
        uppercaseToggle.setBounds(25, 302, 225, 56);
        uppercaseToggle.setFont(new Font("Dialog", Font.PLAIN, 26));
        uppercaseToggle.setFocusPainted(false);
        add(uppercaseToggle);

        //lowercase button
        JToggleButton lowercaseToggle = new JToggleButton("Lowercase");
        lowercaseToggle.setBounds(282, 302, 225, 56);
        lowercaseToggle.setFont(new Font("Dialog", Font.PLAIN, 26));
        lowercaseToggle.setFocusPainted(false);
        add(lowercaseToggle);

        //number button
        JToggleButton numberToggle = new JToggleButton("Number");
        numberToggle.setBounds(25, 376, 225, 56);
        numberToggle.setFont(new Font("Dialog", Font.PLAIN, 26));
        numberToggle.setFocusPainted(false);
        add(numberToggle);

        //symbols button
        JToggleButton symbolToggle = new JToggleButton("Symbols");
        symbolToggle.setBounds(282, 376, 225, 56);
        symbolToggle.setFont(new Font("Dialog", Font.PLAIN, 26));
        symbolToggle.setFocusPainted(false);
        add(symbolToggle);

        //generate button
        JButton generateButton = new JButton("Generate");
        generateButton.setBounds(155, 465, 222, 41);
        generateButton.setFont(new Font("Dialog", Font.PLAIN, 32));
        generateButton.setFocusPainted(false);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check if length is not 0 and any one toggle is selected
                if (passwordLengthInputArea.getText().isBlank()) return;
                boolean anyToggledSelected = uppercaseToggle.isSelected()
                        || lowercaseToggle.isSelected()
                        || numberToggle.isSelected()
                        || symbolToggle.isSelected();

                //generate password
                int passwordLength = Integer.parseInt(passwordLengthInputArea.getText());
                if (anyToggledSelected){
                    String generatePassword = passwordGenerator.generatePassWord(passwordLength,
                            uppercaseToggle.isSelected(), lowercaseToggle.isSelected(),
                            numberToggle.isSelected(), symbolToggle.isSelected());

                    //display password
                    passwordOutput.setText(generatePassword);
                }
            }
        });
        add(generateButton);
    }
}









