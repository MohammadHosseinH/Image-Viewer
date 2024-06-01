/* Links that i used for this project:
javatpoint.com for JFileChooser Class, JActionListener Interface,
docs.oracle.com for JFileChooser,
for grayscale section : https://stackoverflow.com/questions/532586/change-a-specific-color-in-an-imageicon ,
                        https://www.tutorialspoint.com/how-to-get-pixels-rgb-values-of-an-image-using-java-opencv-library,
                        https://www.geeksforgeeks.org/image-processing-in-java-colored-image-to-grayscale-image-conversion/
                        https://stackoverflow.com/questions/3336558/getting-height-and-width-of-image-in-java-without-an-imageobserver

for Brightness section: https://stackoverflow.com/questions/3433275/adjust-brightness-and-contrast-of-bufferedimage-in-java
                        https://stackoverflow.com/questions/3433275/adjust-brightness-and-contrast-of-bufferedimage-in-java

 */





import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageViewerGUI extends JFrame implements ActionListener{
    JButton selectFileButton = new JButton("Choose Image");
    JButton showImageButton = new JButton("Show Image");
    JButton resizeButton = new JButton("Resize");
    JButton grayscaleButton = new JButton("Gray Scale");
    JButton brightnessButton = new JButton("Brightness");
    JButton closeButton = new JButton("Exit");
    JButton showResizeButton = new JButton("Result");
    JButton showBrightnessButton = new JButton("Result");
    JButton backButton = new JButton("Back");
    JTextField widthTextField = new JTextField();
    JTextField heightTextField = new JTextField();
    JTextField brightnessTextField = new JTextField();
    String filePath = "C:\\Users\\Asus\\Desktop\\Image";
    File file;
    JFileChooser fileChooser = new JFileChooser(filePath);
    float brightenFactor = 1;
    JLabel pictureLabel = new JLabel();
    JPanel mainPanel = new JPanel();



    ImageViewerGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Image Viewer");
        this.setSize(700, 300);
        this.setVisible(true);
        this.setResizable(false);

        mainPanel();
    }

    public void mainPanel(){
        // Create main panel for adding to Frame
        mainPanel.setLayout(null);

        // Create Grid panel for adding buttons to it, then add it all to main panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 2));

        // Adding all buttons to Grid panel
        buttonsPanel.add(this.selectFileButton);
        buttonsPanel.add(this.showImageButton);
        buttonsPanel.add(this.brightnessButton);
        buttonsPanel.add(this.grayscaleButton);
        buttonsPanel.add(this.resizeButton);
        buttonsPanel.add(this.closeButton);

        // add Grid panel that contains 6 buttons to main panel
        JLabel mainTitleLabel = new JLabel("Image Viewer");
        mainTitleLabel.setFont(new Font("Adobe Arabic",Font.PLAIN,20));
        mainTitleLabel.setBounds(300,20,200,50);
        mainPanel.add(mainTitleLabel);
        buttonsPanel.setBounds(100,100,500,150);
        mainPanel.add(buttonsPanel);

        // adding action listener to buttons
        selectFileButton.addActionListener(this);
        showImageButton.addActionListener(this);
        grayscaleButton.addActionListener(this);
        brightnessButton.addActionListener(this);
        backButton.addActionListener(this);
        showBrightnessButton.addActionListener(this);
        resizeButton.addActionListener(this);
        showResizeButton.addActionListener(this);
        closeButton.addActionListener(this);


        // add main panel to our frame
        this.add(mainPanel);
    }

    public void resizePanel(){
        JPanel resizePanel = new JPanel();
        resizePanel.setLayout(null);

        // Create and put the components in resize panel
        JLabel widthLabel = new JLabel("Width:");
        JLabel heightLabel = new JLabel("Height:");
        widthLabel.setBounds(200,80,100,50);
        heightLabel.setBounds(200,120,100,50);
        resizePanel.add(widthLabel);
        resizePanel.add(heightLabel);
        widthTextField.setBounds(250,85,200,40);
        heightTextField.setBounds(250,130,200,40);
        resizePanel.add(widthTextField);
        resizePanel.add(heightTextField);
        backButton.setBounds(200,200,90,40);
        resizePanel.add(backButton);
        showResizeButton.setBounds(450,200,90,40);
        resizePanel.add(showResizeButton);


        // remove main panel and add resize panel
        this.getContentPane().removeAll();
        this.add(resizePanel);
        this.repaint();
        this.revalidate();
    }
    public void brightnessPanel(){
        JPanel brightnessPanel = new JPanel();
        brightnessPanel.setLayout(null);

        // Create and put the components in brightness panel
        JLabel brightnessPageLabel1 = new JLabel("Enter f");
        brightnessPageLabel1.setBounds(100,80,100,30);
        brightnessPanel.add(brightnessPageLabel1);
        JLabel brightnessPageLabel2 = new JLabel("(must be between 0 to 1)");
        brightnessPageLabel2.setBounds(60,95,200,30);
        brightnessPanel.add(brightnessPageLabel2);
        brightnessTextField.setBounds(250,80,200,40);
        brightnessPanel.add(brightnessTextField);
        backButton.setBounds(70,180,90,40);
        brightnessPanel.add(backButton);
        showBrightnessButton.setBounds(450,180,90,40);
        brightnessPanel.add(showBrightnessButton);

        // remove main panel and add resize panel
        this.getContentPane().removeAll();
        this.add(brightnessPanel);
        this.repaint();
        this.revalidate();
    }

    public void chooseFileImage(){

        // we want to see only image files.
        fileChooser.setAcceptAllFileFilterUsed(false);

        // to check if users click on the open button in file chooser window
        int option = fileChooser.showOpenDialog(ImageViewerGUI.this);
        if (option == 0){
            file = fileChooser.getSelectedFile();
            // put the image as an icon on a label, with its own size.
            // IOException happened because of read method of ImageIO class, so we use try / catch.
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                int w = bufferedImage.getWidth();
                int h = bufferedImage.getHeight();
                if (w > 1800)
                    w = 1800;
                if (h > 1000)
                    h = 1000;
                pictureLabel.setIcon(new ImageIcon(new ImageIcon(bufferedImage).getImage().getScaledInstance(w,h,Image.SCALE_DEFAULT)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

    }
    public void showOriginalImage(){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();

        // showing picture on our temporary panel
        tempPanel.setLayout(new BorderLayout());
        tempPanel.add(pictureLabel,BorderLayout.CENTER);

        // set the temporary frame for showing the picture
        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    public void grayScaleImage(){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        JLabel grayscalePictureLabel = new JLabel();

        // IOException happened because of read method of ImageIO class, so we use try / catch.
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            /* for each pixel in the image,
            getting the rgb and set the average of red,green and blue as a way of converting colorful image to grayscale image.
             */
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                for (int y = 0; y < bufferedImage.getHeight(); y++) {
                    Color originalColor = new Color(bufferedImage.getRGB(x, y));
                    int average = (originalColor.getRed() + originalColor.getBlue() + originalColor.getGreen()) / 3;
                    Color grayscaleColor = new Color(average, average, average);
                    bufferedImage.setRGB(x, y, grayscaleColor.getRGB());
                }
            }
            // put the grayscale image as an icon on a label, with its own size.
            int w = bufferedImage.getWidth();
            int h = bufferedImage.getHeight();
            if (w > 1800)
                w = 1800;
            if (h > 1000)
                h = 1000;
            grayscalePictureLabel.setIcon(new ImageIcon(new ImageIcon(bufferedImage).getImage().getScaledInstance(w,h,Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // put grayscale label on panel and set the temporary frame.
        tempPanel.add(grayscalePictureLabel);
        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }
    public void showResizeImage(int w, int h){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel(new GridLayout(1,1));
        JLabel resizePicture = new JLabel();

        //resize the image with given width and height. then put resized image on the temporary panel.
        resizePicture.setIcon(new ImageIcon(new ImageIcon(String.valueOf(file)).getImage().getScaledInstance(w,h,Image.SCALE_DEFAULT)));
        tempPanel.add(resizePicture);

        //set the temporary frame.
        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }
    public void showBrightnessImage(float f){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel(new GridLayout(1,1));
        JLabel brightnessPictureLabel = new JLabel();

        // IOException happened because of read method of ImageIO class, so we use try / catch.
        try {
            BufferedImage bufferedImage = ImageIO.read(file);

            // using RescaleOp class as a filter to set brightness with given brighten factor.
            RescaleOp rescaleOp = new RescaleOp(f,0,null);
            rescaleOp.filter(bufferedImage,bufferedImage);

            // put the grayscale image as an icon on a label, with its own size.
            int w = bufferedImage.getWidth();
            int h = bufferedImage.getHeight();
            if (w > 1800)
                w = 1800;
            if (h > 1000)
                h = 1000;
            brightnessPictureLabel.setIcon(new ImageIcon(new ImageIcon(bufferedImage).getImage().getScaledInstance(w,h,Image.SCALE_DEFAULT)));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // put the image with new brightness on the temporary panel and set the frame.
        tempPanel.add(brightnessPictureLabel);
        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    // a method to call when an error happened, it shows an error window with proper message.
    private void error(String s){
        JFrame errorFrame = new JFrame();
        JPanel errorPanel = new JPanel(null);
        JLabel errorMessage = new JLabel(s);

        errorMessage.setBounds(50,10,250,50);
        errorPanel.add(errorMessage);

        errorPanel.setSize(300, 100);
        errorFrame.setTitle("Error");
        errorFrame.setSize(300, 100);
        errorFrame.setVisible(true);
        errorFrame.setResizable(false);
        errorFrame.add(errorPanel);
    }

    // Action performed method (we have to write this because of implementing action listener interface to this class.)
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==resizeButton){
            resizePanel();

        }else if(e.getSource()== showImageButton){
            // if users click the button without choosing a picture.
            if (file == null)
                error("There is no image!");
            else
                showOriginalImage();

        }else if(e.getSource()==grayscaleButton){
            // if users click the button without choosing a picture.
            if (file == null)
                error("There is no image!");
            else
                grayScaleImage();

        }else if(e.getSource()== showResizeButton){
            // try to handle the improper inputs error
            try {
                int w = Integer.parseInt(widthTextField.getText());
                int h = Integer.parseInt(heightTextField.getText());
                // if users click the button without choosing a picture.
                if (file == null)
                    error("There is no image!");
                else
                    showResizeImage(w, h);
            }catch (Exception exception){
                error("Width and height should be an integer");
            }

        }else if(e.getSource()==brightnessButton){
            brightnessPanel();

        }else if(e.getSource()== showBrightnessButton){
            String brightnessInput = brightnessTextField.getText();
            // try to handle the improper inputs error
            try {
                brightenFactor = Float.parseFloat(brightnessInput);
                // brighten factor has a limit.
                if (brightenFactor > 1 || brightenFactor < 0)
                    error("f must be between 0 to 1");
                // if users click the button without choosing a picture.
                else if (file == null)
                    error("There is no image!");
                else
                    showBrightnessImage(brightenFactor);
            }catch (Exception exception){
                error("f must be a float number");
            }

        }else if(e.getSource()== selectFileButton){
            chooseFileImage();

        }else if(e.getSource()==closeButton){
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        else if(e.getSource()== backButton){
            // we want to renew the text fields, every time we went back from a panel to main menu.
            brightnessTextField.setText(null);
            widthTextField.setText(null);
            heightTextField.setText(null);
            // remove current panel and add main panel to the frame.
            this.getContentPane().removeAll();
            this.add(mainPanel);
            this.revalidate();
            this.repaint();
        }
    }
}