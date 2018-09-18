package GameEngine;

import android.content.res.Resources;

import com.johnnywaity.abduction.GameActivity;
import com.johnnywaity.abduction.R;
import com.threed.jpct.Loader;
import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import GamePlay.CityLayout;

public class ChunkLoader {
    private Class[] chunks;

    private final float width = 16;
    private final float height = 16;
    private final Resources resources;

    public ChunkLoader(Class[] chunks, Resources res){
        this.chunks = chunks;
        this.resources = res;
    }

    public void start(){
        for(int r = -2; r < 3; r++){
            for(int c = -2; c < 3; c++){
                Object3D[] objects = Loader.loadOBJ(resources.openRawResource(R.raw.ground), resources.openRawResource(R.raw.groundmat), 1);
                for(Object3D o : objects){
                    if(o != objects[0]){
                        o.addParent(objects[0]);
                    }
                    o.translate(0, 0, 0);
                    o.setTexture("grassBlock");
                    //o.rotateX((float)Math.PI);
                    GameActivity.world.addObject(o);
                }
                try{
                    Class layoutClass = chunks[(int) Math.floor(Math.random() * chunks.length)];
                    Constructor ctor = layoutClass.getConstructor(Object3D.class);
                    Layout layout = (Layout) ctor.newInstance(new Object[] {objects[0]});
                    layout.populate(resources);
                    layout.finalize(r * width, c * height);
                }catch (Exception e){
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }
    }
}
