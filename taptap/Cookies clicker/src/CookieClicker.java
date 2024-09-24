import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class CookieClicker extends JFrame {
    private JTextField counterField;

    public CookieClicker(){
        super("Cookie Clicker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 800));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        addGuiComponent();
    }

    private void addGuiComponent(){
        SpringLayout springLayout = new SpringLayout();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(springLayout);

        //1. banner
        JLabel bannerImg = loadImage("resource/cookiesBanner.png", true, 450, 100);

        jPanel.add(bannerImg);

        springLayout.putConstraint(SpringLayout.NORTH, bannerImg, 25, SpringLayout.NORTH, jPanel);
        springLayout.putConstraint(SpringLayout.WEST, bannerImg, 65, SpringLayout.WEST, jPanel);

        //2. button
        JButton cookieButton = createImageButton("resource/cookies.png");
        cookieButton.setFocusPainted(false);
       cookieButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               int count = Integer.parseInt(counterField.getText());
               counterField.setText(Integer.toString(++count));
           }
       });

        jPanel.add(cookieButton);
        springLayout.putConstraint(SpringLayout.NORTH, cookieButton, 175, SpringLayout.NORTH, jPanel);
        springLayout.putConstraint(SpringLayout.WEST, cookieButton, 112, SpringLayout.WEST, jPanel);

        //3. counter label
        JLabel countLabel = new JLabel("Clicks: ");
        countLabel.setFont(new Font("Dialog", Font.BOLD, 26));

        jPanel.add(countLabel);
        springLayout.putConstraint(SpringLayout.NORTH, countLabel, 550, SpringLayout.NORTH, jPanel);
        springLayout.putConstraint(SpringLayout.WEST, countLabel, 115, SpringLayout.WEST, jPanel);

        //4. count text field
        counterField = new JTextField(10);
        counterField.setFont(new Font("Dialog", Font.BOLD, 26));
        counterField.setHorizontalAlignment(SwingConstants.RIGHT);
        counterField.setText("0");
        counterField.setEditable(false);

        jPanel.add(counterField);
        springLayout.putConstraint(SpringLayout.NORTH, counterField, 550, SpringLayout.NORTH, jPanel);
        springLayout.putConstraint(SpringLayout.WEST, counterField, 230, SpringLayout.WEST, jPanel);

        //5. reset button
        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                counterField.setText("0");
            }
        });

        jPanel.add(resetButton);
        springLayout.putConstraint(SpringLayout.NORTH, resetButton, 650, SpringLayout.NORTH, jPanel);
        springLayout.putConstraint(SpringLayout.WEST, resetButton, 250, SpringLayout.WEST, jPanel);


        this.getContentPane().add(jPanel);
    }

    private JButton createImageButton(String fileName){
        JButton button;
        try{
            InputStream inputStream = this.getClass().getResourceAsStream(fileName);
            Image image = ImageIO.read(inputStream);
            button = new JButton(new ImageIcon(image));
            return button;
        } catch (IOException e){
            System.out.println("Error: " + e);
            return null;
        }
    }

    private JLabel loadImage(String fileName, boolean isResizeable, int targetWidth, int targetHeight){
        BufferedImage image;
        JLabel imageContainer;
        try{
            InputStream inputStream = this.getClass().getResourceAsStream(fileName);
            image = ImageIO.read(inputStream);
            if (isResizeable){
                image = resizeImage(image, targetWidth, targetHeight);
            }
            imageContainer = new JLabel(new ImageIcon(image));
            return imageContainer;

        } catch (IOException e) {
            System.out.println("Error : " + e);
            return null;
        }
    }

    private BufferedImage resizeImage(BufferedImage image, int targetWidth, int targetHeight){
        BufferedImage newImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return newImage;
    }
}









