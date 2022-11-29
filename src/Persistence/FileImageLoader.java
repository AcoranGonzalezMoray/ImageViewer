package Persistence;


import Model.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public final class FileImageLoader implements ImageLoader{
    private final File[] files;
    
    public FileImageLoader(File folder){
        this.files=folder.listFiles(imageType());
        
    }
    
    public FileFilter imageType(){
        return (File pathname) -> pathname.getName().endsWith(".JPG") 
    || pathname.getName().endsWith(".jpg") 
    || pathname.getName().endsWith(".PNG") 
    || pathname.getName().endsWith(".png") ;
    }
    
    @Override
    public Image load(){
        return imageAt(0);
    }
    
    private Image imageAt(int i){
        return new Image(){
  
            @Override
            public String name(){
                return files[i].getName();
            }

            @Override
            public InputStream stream(){
                try{
                    return new BufferedInputStream(new FileInputStream(files[i]));
                }catch (FileNotFoundException e){
                    return null;
                }catch(Exception ex){
                    System.out.println("No hay imagenes en el direcotrio seleccionado");
                    return null;
                }
            }
    
            @Override
            public Image next(){
               return i ==files.length-1 ? imageAt(0): imageAt(i+1);
            }
        
            @Override
            public Image prev(){
               return i==0 ? imageAt(files.length-1) : imageAt(i-1);
            }
            @Override
            public int Pos(){
                return i+1;
            }
           
        };
    }
    
    public File[] getFiles(){
        return files;
    }
}
