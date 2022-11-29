package Controller;

import Model.Image;
import Persistence.FileImageLoader;
import UI.ImageDisplay;
import UI.SwingImageDisplay;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {
    private SwingImageDisplay imageDisplay;
    private JFileChooser fileChooser;
    private final JLabel path = new JLabel();
    private final JLabel len= new JLabel("/0");
    private final JLabel lenPos = new JLabel("1");
    
    public MainFrame() {
        this.setTitle("Image Viewer");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1200,900);
        this.setLocationRelativeTo(null);
        this.getContentPane().add(Navigator(), BorderLayout.NORTH);
        this.getContentPane().add(imageDisplay(),BorderLayout.CENTER);
        this.getContentPane().add(toolBar(),BorderLayout.SOUTH);
    }
    private JPanel toolBar() {
        JPanel panel = new JPanel();
        panel.add(prevButton());
        panel.add(nextButton());
        return panel;
    }
    private JPanel Navigator() {
        JPanel panelNavigator = new JPanel();
        panelNavigator.add(this.lenPos);
        panelNavigator.add(this.len);
        panelNavigator.add(navigatorRuteImage());
        panelNavigator.add(this.path);
        return panelNavigator;
    }

    private JButton prevButton() {
        JButton button = new JButton("<");
        button.addActionListener(prevImage());
        return button;
    }
    private ActionListener prevImage() {
        return (ActionEvent e) -> {
            imageDisplay.show(imageDisplay.current().prev());
            this.lenPos.setText(imageDisplay.current().Pos()+"");
        };
    }
    private JButton nextButton() {
        JButton button = new JButton(">");
        button.addActionListener(nextImage());
    return button;
    }
    private ActionListener nextImage() {
        return (ActionEvent e) -> {
            imageDisplay.show(imageDisplay.current().next());
            this.lenPos.setText(imageDisplay.current().Pos()+"");
        };
    } 
    private JPanel imageDisplay() {
        SwingImageDisplay sid = new SwingImageDisplay();
        this.imageDisplay = sid;
        return sid;
    }
    public ImageDisplay getImageDisplay() {
        return imageDisplay;
    }
    
    private JButton navigatorRuteImage(){
        this.fileChooser=new JFileChooser();
        this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        JButton fileNavigator = new JButton("Select Path");
        fileNavigator.addActionListener(navigatorListener());
        return fileNavigator;
    }
    
    public ActionListener navigatorListener() {
        return (ActionEvent e) -> {
            fileChooser.showOpenDialog(fileChooser);
            this.path.setText(fileChooser.getSelectedFile().toString());
            File file = new File(fileChooser.getSelectedFile().toString());
            FileImageLoader imageLoader = new FileImageLoader(file);
            this.len.setText("/"+(imageLoader.getFiles().length));
            Image image = imageLoader.load();
            getImageDisplay().show(image);  
        };
    } 
    
    public void init(){
        this.setVisible(true);
    }
}