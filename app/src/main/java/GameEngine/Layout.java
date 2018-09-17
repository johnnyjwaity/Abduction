package GameEngine;

import android.content.res.Resources;

import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;

import java.util.ArrayList;

public abstract class Layout {
    private Object3D base;

    private ArrayList<Object3D> objects;


    public Layout(Object3D base){
        this.base = base;
        objects = new ArrayList<>();
    }

    public void addObject(Object3D obj){
        objects.add(obj);
    }

    public abstract void populate(Resources res, float x, float z);

    public void finalize(float x, float z){
        for(Object3D object : objects){
            object.addParent(base);
        }
        base.translate(x, 0, z);
//        for(Object3D object : objects){
//            Matrix transform = object.getWorldTransformation().cloneMatrix();
//            object.removeParent(base);
//            object.setTranslationMatrix(transform);
//        }
    }

}
