
package UI;

import Model.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author acora
 */
public class SwingImageDisplay  extends JPanel implements ImageDisplay{
    private Image currentImage;
    
    @Override
    public Image current(){
        return currentImage;
    }
    @Override
    public void show(Image image){
        this.currentImage=image;
        this.repaint();
    }
    @Override
    public void paint(Graphics g) {
        if(currentImage==null)return;
        try {
            super.paintComponent(g);
            BufferedImage imageof = imageOf(currentImage);
            dimension d = new dimension(imageof, this);
            d.dimensionm();
            int x = (int)(this.getWidth() - d.restow) / 2;
            int y = (int)(this.getHeight() - d.restoh) / 2;
            java.awt.Image image = imageof.
                    getScaledInstance((int)d.restow
                            ,(int)d.restoh
                            ,java.awt.Image.SCALE_DEFAULT);
            
            g.drawImage(image,x,y,null);
        } catch (IOException ex) {
            Logger.getLogger(SwingImageDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public class dimension{
        public double restow = 0;
        public double restoh = 0;
        public SwingImageDisplay panel;
        public dimension(BufferedImage image, SwingImageDisplay panel) {
            this.restoh = image.getHeight();
            this.restow = image.getWidth();
            this.panel= panel;
        }
        
        public void dimensionm(){
            if(restow >panel.getWidth()){
                double resto = restow / panel.getWidth();
                restow = restow / resto; 
                restoh = restoh / resto;
            } 

        }

    }
    
    
    public BufferedImage imageOf(Image image) throws IOException{
        return ImageIO.read(image.stream());
    };
}
