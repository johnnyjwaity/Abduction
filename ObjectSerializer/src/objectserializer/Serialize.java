/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectserializer;
import com.threed.jpct.DeSerializer;
import java.io.File;
/**
 *
 * @author johnn
 */
public class Serialize {
    public Serialize(){
        DeSerializer d = new DeSerializer();
        String modelDir = System.getProperty("user.dir");
        modelDir += "\\..\\models\\";
        File dir = new File(modelDir);
        System.out.println(dir.exists());
    }
    
    
}
