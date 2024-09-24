import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FontMenu extends JDialog {

    private NotepadGui source;

    private  JTextField currentFontField, currentFontStyleField, currentSizeField;

    private JPanel currentColorBox;

    public FontMenu(NotepadGui source){
        this.source = source;
        setTitle("Font Setting");
        setSize(425,350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(source);
        setModal(true);
        setLayout(null);

        addMenuComponents();
    }

    private void addMenuComponents(){
        addFontChooser();
        addFontStyleChooser();
        addFontSizeChooser();
        addFontColorChooser();

        //action button

        //apply button
        JButton applyButton = new JButton("Apply");
        applyButton.setBounds(230, 265, 75, 25);
        applyButton.addActionListener(e -> {
            String fontName = currentFontField.getText();

            int fontStyle = switch (currentFontStyleField.getText()){
                case "Plain" -> Font.PLAIN;
                case "Bold" -> Font.BOLD;
                case "Italic" -> Font.ITALIC;
                default -> Font.BOLD | Font.ITALIC;
            };

            int fontSize = Integer.parseInt(currentSizeField.getText());

            //set the font
            source.getTextArea().setFont(new Font(fontName, fontStyle, fontSize));

            //set color ase the user setup
            source.getTextArea().setForeground(currentColorBox.getBackground());

            dispose();
        });
        add(applyButton);

        //cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(315, 265, 75, 25);
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void addFontChooser(){
        JLabel fontLabel = new JLabel("Font: ");
        fontLabel.setBounds(10, 5, 125, 10 );
        add(fontLabel);

        //font panel to choose font from
        JPanel fontPanel = new JPanel();
        fontPanel.setBounds(10, 15, 125, 160);

        //display current font
        currentFontField = new JTextField(source.getTextArea().getFont().getFontName());
        currentFontField.setPreferredSize(new Dimension(125,25));
        currentFontField.setEditable(false);
        fontPanel.add(currentFontField);

        //display available font
        JPanel listOfFontsPanel = new JPanel();

        //change our layout to only have one column
        listOfFontsPanel.setLayout(new BoxLayout(listOfFontsPanel, BoxLayout.Y_AXIS));

        //set background color to white
        listOfFontsPanel.setBackground(Color.white);

        JScrollPane scrollPane = new JScrollPane(listOfFontsPanel);
        scrollPane.setPreferredSize(new Dimension(125,125));

        //retrieve all the possible fonts
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontsName = ge.getAvailableFontFamilyNames();

        //display all fonts family to listOfFontsPanel
        for (String fontName : fontsName){
            JLabel fontNameLabel = new JLabel(fontName);
//            fontNameLabel.setPreferredSize(new Dimension(125,25));

            fontNameLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //when click set current font in field
                    currentFontField.setText(fontName);
                }

                @Override
                public void mouseEntered(MouseEvent e){
                    //add highlighter over font name when mouse hovers over them
                    fontNameLabel.setOpaque(true);
                    fontNameLabel.setBackground(new Color(75,104,184));
                    fontNameLabel.setForeground(Color.white);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    //remove highlighter over font once the mouse not hover over them
                    fontNameLabel.setBackground(null);
                    fontNameLabel.setForeground(null);
                }
            });

            // add to panel
            listOfFontsPanel.add(fontNameLabel);
        }
        fontPanel.add(scrollPane);

        add(fontPanel);
    }

    private void addFontStyleChooser(){
        JLabel fontStyleLabel = new JLabel("Font Style: ");
        fontStyleLabel.setBounds(145, 5, 125, 14);
        add(fontStyleLabel);

        //display current font style and all the style available
        JPanel fontStylePanel = new JPanel();
        fontStylePanel.setBounds(145, 15, 125, 160);

        //get current font style
        int currentFontStyle = source.getTextArea().getFont().getStyle();
        String currentFontStyleText = switch (currentFontStyle){
            case Font.PLAIN -> "Plain";
            case Font.BOLD -> "Bold";
            case Font.ITALIC -> "Italic";
            default -> "Bold Italic";
        };

        currentFontStyleField = new JTextField(currentFontStyleText);
        currentFontStyleField.setPreferredSize(new Dimension(125, 25));
        currentFontStyleField.setEditable(false);
        fontStylePanel.add(currentFontStyleField);

        //display all  the font style
        JPanel listOfFontStylePanel = new JPanel();

        //make layout have only one column
        listOfFontStylePanel.setLayout(new BoxLayout(listOfFontStylePanel, BoxLayout.Y_AXIS));
        listOfFontStylePanel.setBackground(Color.white);

        //list of font style
        JLabel plainStyle = new JLabel("Plain");
        plainStyle.setFont(new Font("Dialog", Font.PLAIN, 12));
        plainStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //update current font style label
                currentFontStyleField.setText(plainStyle.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //add highlighter
                plainStyle.setOpaque(true);
                plainStyle.setBackground(new Color(75,104,184));
                plainStyle.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                plainStyle.setBackground(null);
                plainStyle.setForeground(null);

            }
        });
        listOfFontStylePanel.add(plainStyle);

        JLabel boldStyle = new JLabel("Bold");
        boldStyle.setFont(new Font("Dialog", Font.BOLD, 12));
        boldStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //update current font style label
                currentFontStyleField.setText(boldStyle.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //add highlighter
                boldStyle.setOpaque(true);
                boldStyle.setBackground(new Color(75,104,184));
                boldStyle.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boldStyle.setBackground(null);
                boldStyle.setForeground(null);

            }
        });
        listOfFontStylePanel.add(boldStyle);

        JLabel italicStyle = new JLabel("Italic");
        italicStyle.setFont(new Font("Dialog", Font.ITALIC, 12));
        italicStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //update current font style label
                currentFontStyleField.setText(italicStyle.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //add highlighter
                italicStyle.setOpaque(true);
                italicStyle.setBackground(new Color(75,104,184));
                italicStyle.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                italicStyle.setBackground(null);
                italicStyle.setForeground(null);

            }
        });
        listOfFontStylePanel.add(italicStyle);

        JLabel boldItalicStyle = new JLabel("Bold Italic");
        boldItalicStyle.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
        boldItalicStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //update current font style label
                currentFontStyleField.setText(boldItalicStyle.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //add highlighter
                boldItalicStyle.setOpaque(true);
                boldItalicStyle.setBackground(new Color(75,104,184));
                boldItalicStyle.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boldItalicStyle.setBackground(null);
                boldItalicStyle.setForeground(null);

            }
        });
        listOfFontStylePanel.add(boldItalicStyle);

        JScrollPane scrollPane = new JScrollPane(listOfFontStylePanel);
        scrollPane.setPreferredSize(new Dimension(125,125));

        fontStylePanel.add(scrollPane);

        add(fontStylePanel);
    }

    private void addFontSizeChooser(){
        JLabel fontSizeLabel = new JLabel("Font Size: ");
        fontSizeLabel.setBounds(275, 5, 125, 10);
        add(fontSizeLabel);

        //display font size and list of font size
        JPanel fontSizePanel = new JPanel();
        fontSizePanel.setBounds(275, 15, 125, 160);

        //display current size
        currentSizeField = new JTextField(Integer.toString(source.getTextArea().getFont().getSize()));
        currentSizeField.setPreferredSize(new Dimension(125, 25));
        currentSizeField.setEditable(false);
        fontSizePanel.add(currentSizeField);

        //list of font size to choose from
        JPanel listOfFontSizePanel = new JPanel();
        listOfFontSizePanel.setLayout(new BoxLayout(listOfFontSizePanel, BoxLayout.Y_AXIS));
        listOfFontSizePanel.setBackground(Color.WHITE);

        //list of font is from 8-72 in increment of 2
        for (int i = 8; i <= 72 ; i += 2) {
            JLabel fontSizeValueLabel = new JLabel(Integer.toString(i));
            fontSizeValueLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //set current font size
                    currentSizeField.setText(fontSizeValueLabel.getText());
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    //set highlighter when the hovers over them
                    fontSizeValueLabel.setOpaque(true);
                    fontSizeValueLabel.setBackground(new Color(75,104,184));
                    fontSizeValueLabel.setForeground(Color.white);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    //remove the highlighter
                    fontSizeValueLabel.setBackground(null);
                    fontSizeValueLabel.setForeground(null);
                }
            });
            listOfFontSizePanel.add(fontSizeValueLabel);
        }

        JScrollPane scrollPane = new JScrollPane(listOfFontSizePanel);
        scrollPane.setPreferredSize(new Dimension(125,125));

        fontSizePanel.add(scrollPane);

        add(fontSizePanel);
    }

    private void addFontColorChooser(){
        //display the current color of the text
        currentColorBox =  new JPanel();
        currentColorBox.setBounds(175, 200, 23, 23);
        currentColorBox.setBackground(source.getTextArea().getForeground());
        currentColorBox.setBorder(BorderFactory.createLineBorder(Color.black));
        add(currentColorBox);

        JButton currentColorButton = new JButton("Choose Color") ;
        currentColorButton.setBounds(10, 200, 150, 25);
        currentColorButton.setFocusPainted(false);
        currentColorButton.addActionListener(e -> {
            Color c = JColorChooser.showDialog(null, "Select a color", Color.BLACK);

            // update the color box
            currentColorBox.setBackground(c);
        });
        add(currentColorButton);
    }

}





