/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectserializer;
import com.threed.jpct.DeSerializer;
import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author johnn
 */
public class Serialize {
    public Serialize() throws FileNotFoundException{
        DeSerializer d = new DeSerializer();
        String modelDir = System.getProperty("user.dir");
        modelDir += "\\..\\models\\";
        File dir = new File(modelDir);
        Object3D[] objects = new Object3D[dir.listFiles().length];
        int count = 0;
        for(File file : dir.listFiles()){
            Object3D obj = Loader.load3DS(new FileInputStream(file), 1)[0];
            System.out.println(file.getName().split("\\.")[0]);
            obj.setName(file.getName().split("\\.")[0]);
            objects[count] = obj;
            count++;
        }
        FileOutputStream out = new FileOutputStream(System.getProperty("user.dir") + "\\..\\app\\src\\main\\res\\raw\\objects.ser");
        d.serializeArray(objects, out, false);
        
    }
    
    
}
