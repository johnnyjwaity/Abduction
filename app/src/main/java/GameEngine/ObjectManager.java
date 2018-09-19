package GameEngine;

import com.threed.jpct.Object3D;

import java.util.HashMap;
import java.util.Map;

public class ObjectManager {

    private static ObjectManager sharedInstance = null;

    private final Map<String, Object3D[]> objects;

    public ObjectManager(){
        objects = new HashMap<>();
    }

    public static ObjectManager getSharedInstance(){
        if(sharedInstance != null){
            return sharedInstance;
        }else{
            sharedInstance = new ObjectManager();
            return sharedInstance;
        }
    }

    public void loadObject(Object3D[] obj, String key){
        objects.put(key, obj);
    }

    public Object3D[] getObject(String key){
        Object3D[] objs = objects.get(key);
        Object3D[] newObjs = new Object3D[objs.length];
        for(int i = 0; i < objs.length; i++){
            newObjs[i] = objs[i].cloneObject();
        }
        return newObjs;
    }

}
