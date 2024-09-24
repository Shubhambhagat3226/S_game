package gui;

import constant.CommonConstant;
import service.CalculatorService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame implements ActionListener {
    private SpringLayout springLayout = new SpringLayout();

    //display field
    private JTextField displayField;

    //button
    private JButton[] button;

    //calculator service obj
    private CalculatorService calculatorService;

    //flag
    private boolean pressedOperator = false;
    private boolean pressedEquals = false;

    public CalculatorGUI(){
        super(CommonConstant.APP_NAME);
        setSize(CommonConstant.APP_SIZE[0], CommonConstant.APP_SIZE[1]);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(springLayout);

        calculatorService = new CalculatorService();

        addGuiComponents();

    }

    void addGuiComponents(){
        //add fields components
        addFieldComponents();

        //add button components
        addButtonComponents();
    }

    void addFieldComponents(){
        JPanel displayFieldPanel = new JPanel();
        displayField = new JTextField(CommonConstant.TEXTFIELD_LENGTH);
        displayField.setFont(new Font("Dialog", Font.PLAIN, CommonConstant.TEXTFIELD_FONTSIZE));
        displayField.setEditable(false);
        displayField.setText("0");
        displayField.setHorizontalAlignment(SwingConstants.RIGHT);

        displayFieldPanel.add(displayField);

        this.getContentPane().add(displayFieldPanel);

        springLayout.putConstraint(SpringLayout.NORTH, displayFieldPanel, CommonConstant.TEXTFIELD_SPRINGLAYOUT_NORTHPAD, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, displayFieldPanel, CommonConstant.TEXTFIELD_SPRINGLAYOUT_WESTPAD, SpringLayout.WEST, this);
    }

    void addButtonComponents(){
        GridLayout gridLayout = new GridLayout(CommonConstant.BUTTON_ROWCOUNT, CommonConstant.BUTTON_COLCOUNT);
        gridLayout.setHgap(CommonConstant.BUTTON_HGAP);
        gridLayout.setVgap(CommonConstant.BUTTON_VGAP);
        JPanel buttonPanel = new JPanel(gridLayout);

        button = new JButton[CommonConstant.BUTTON_COUNT];
        for (int i = 0; i < CommonConstant.BUTTON_COUNT; i++) {
            button[i] = new JButton(getButtonLabel(i));
            button[i].setFont(new Font("Dialog", Font.PLAIN, CommonConstant.BUTTON_FONTSIZE));
            button[i].setFocusPainted(false);
            button[i].addActionListener(this);

            buttonPanel.add(button[i]);
        }

        springLayout.putConstraint(SpringLayout.NORTH, buttonPanel, CommonConstant.BUTTON_SPRINGLAYOUT_NORTHPAD, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, buttonPanel, CommonConstant.BUTTON_SPRINGLAYOUT_WESTPAD, SpringLayout.WEST, this);

        this.getContentPane().add(buttonPanel);
    }

    String getButtonLabel(int index){
        return switch (index){
            case 0 -> "7";
            case 1 -> "8";
            case 2 -> "9" ;
            case 3 -> "/";
            case 4 -> "4";
            case 5 -> "5";
            case 6 -> "6";
            case 7 -> "*";
            case 8 -> "1";
            case 9 -> "2";
            case 10 -> "3";
            case 11 -> "-";
            case 12 -> ".";
            case 13 -> "0";
            case 14 -> "=";
            case 15 -> "+";
            default -> throw new IllegalStateException("Unexpected value: " + index);
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonCommand = e.getActionCommand();
        if (buttonCommand.matches("[0-9]")){
            if (pressedOperator || pressedEquals || displayField.getText().equals("0")){
                displayField.setText(buttonCommand);
            } else {
                displayField.setText(displayField.getText() + buttonCommand);
            }

            //update flag
            pressedOperator = false;
            pressedEquals = false;
        } else if (buttonCommand.equals("=")){
            //calculate
            calculatorService.setNum2(Double.parseDouble(displayField.getText()));

            double result = switch (calculatorService.getMathSymbol()){
              case '+' -> calculatorService.add();
              case '-' -> calculatorService.subtract();
              case '*' -> calculatorService.multiple();
              case '/' -> calculatorService.divide();
                default -> throw new IllegalStateException("Unexpected value: " + calculatorService.getMathSymbol());
            };
            //update display field
            displayField.setText(Double.toString(result));

            //update flags
            pressedEquals = true;
            pressedOperator = false;
        } else if (buttonCommand.equals(".")) {
            if (!displayField.getText().contains(".")){
                displayField.setText(displayField.getText() + buttonCommand);
            }
        } else {
            //operator
            if(!pressedOperator)
                calculatorService.setNum1(Double.parseDouble(displayField.getText()));

            calculatorService.setMathSymbol(buttonCommand.charAt(0));

            pressedOperator = true;
            pressedEquals = false;
        }

    }
}









