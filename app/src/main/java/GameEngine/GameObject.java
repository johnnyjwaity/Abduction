package GameEngine;

import com.johnnywaity.abduction.GameActivity;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;

import java.util.ArrayList;

public class GameObject {

    private Object3D object;

    private boolean isRemoved = false;

    private ArrayList<AttachableScript> scripts = new ArrayList<>();

    private AttachableScript nonDisabled;
    private boolean disable = false;

    public GameObject (Object3D object){
        this.object = object;
    }

    public void addScript(AttachableScript s){
        scripts.add(s);
        s.setGameObject(this);
        s.start();
    }
    public void startScriptUpdate(){
        for(AttachableScript s : scripts){
            if(isRemoved){
                return;
            }
            if(disable && s != nonDisabled){
                continue;
            }
            s.update();
        }
    }

    public Object3D getObject() {
        return object;
    }

    public void remove(){
        GameActivity.world.removeObject(object);
        isRemoved = true;
    }

    public void disableOtherScripts(AttachableScript s){
        disable = true;
        nonDisabled = s;
    }

    public static SimpleVector subtractVectors(SimpleVector v1, SimpleVector v2){

        float x = v2.x - v1.x;
        float y = v2.y - v1.y;
        float z = v2.z - v1.z;
        return new SimpleVector(x, y, z);
    }
}
