package GamePlay;

import android.content.res.Resources;

import com.johnnywaity.abduction.GameActivity;
import com.johnnywaity.abduction.R;
import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureInfo;
import com.threed.jpct.World;

import java.util.Random;

import GameEngine.GameObject;
import GameEngine.Layout;
import GameEngine.Vector2;

public class CityLayout extends Layout {
    
    
    
    public CityLayout(Object3D base){
        super(base);
    }
    
    @Override
    public void populate(Resources res) {
        World world = GameActivity.world;

        Object3D[] roads = Loader.load3DS(res.openRawResource(R.raw.roadsquare), 1);
        for (Object3D road : roads){
            road.translate(0, -1, 0);
            road.setTexture("roadSquare");
            world.addObject(road);
            super.addObject(road);
        }

        for(int i = 0; i < 7; i ++){
            Object3D[] buildings = Loader.load3DS(res.openRawResource((Math.random() > 0.5) ? R.raw.bb : R.raw.b2), 0.4f);
            for (Object3D building : buildings){
                building.translate(-4.5f + (i * 1.5f), -1, 4.7f);
                building.setTexture(randomTex());
                world.addObject(building);
                super.addObject(building);
            }
        }

        for(int i = 0; i < 3; i ++){
            Object3D[] buildings = Loader.load3DS(res.openRawResource((Math.random() > 0.5) ? R.raw.bb : R.raw.b2), 0.4f);
            for (Object3D building : buildings){
                building.translate(-4.1f + (i * 1.5f), -1, -3.5f);
                building.setTexture(randomTex());
                world.addObject(building);
                super.addObject(building);
            }
        }

        for(int i = 0; i < 3; i ++){
            Object3D[] buildings = Loader.load3DS(res.openRawResource((Math.random() > 0.5) ? R.raw.bb : R.raw.b2), 0.4f);
            for (Object3D building : buildings){
                building.translate(1.2f + (i * 1.4f), -1, -3.5f);
                building.setTexture(randomTex());
                world.addObject(building);
                super.addObject(building);
            }
        }

        for(int i = 0; i < 2; i ++){
            Object3D[] buildings = Loader.load3DS(res.openRawResource((Math.random() > 0.5) ? R.raw.bb : R.raw.b2), 0.4f);
            for (Object3D building : buildings){
                building.translate(-1.2f , -1, -1 + (i * 1.4f));
                building.rotateY((float)-Math.PI /2);
                building.setTexture(randomTex());
                world.addObject(building);
                super.addObject(building);
            }
        }

        // (-5.1, 4)      (0, 4)      (5.1, 4)
        // (-5.1, -4.3)  (0, -4.3)  (5.1, -4.3)

        Vector2[] path = {new Vector2(-5.1f, 4), new Vector2(0, 4), new Vector2(0, -4.3f),
                new Vector2(5.1f, -4.3f), new Vector2(5.1f, 4), new Vector2(0, 4),
                new Vector2(0, -4.3f), new Vector2(-5.1f, -4.3f)};



        Object3D taxi = Loader.load3DS(res.openRawResource(R.raw.taxi), 0.2f)[0];
        taxi.translate(-5.1f, -1, 4f);
        taxi.setTexture("taxi");
        world.addObject(taxi);
        GameObject gTaxi = new GameObject(taxi);
        gTaxi.addScript(new Atractable());
        gTaxi.addScript(new CarController(path, 0));
        GameActivity.objects.add(gTaxi);
        super.addObject(taxi);

        Object3D c1 = Loader.load3DS(res.openRawResource(R.raw.taxi), 0.2f)[0];
        c1.translate(5.1f, -1, -4.3f);
        c1.setTexture("carp");
        c1.rotateY((float) Math.PI / 2);
        world.addObject(c1);
        GameObject c1g = new GameObject(c1);
        c1g.addScript(new Atractable());
        c1g.addScript(new CarController(path, 3));
        GameActivity.objects.add(c1g);
        super.addObject(c1);

        Object3D c2 = Loader.load3DS(res.openRawResource(R.raw.taxi), 0.2f)[0];
        c2.translate(0f, -1, -4.3f);
        c2.setTexture("carb");
        world.addObject(c2);
        GameObject c2g = new GameObject(c2);
        c2g.addScript(new Atractable());
        c2g.addScript(new CarController(path, 2));
        GameActivity.objects.add(c2g);
        super.addObject(c2);


        // (0.6, 3.2)  (4.7, 3.2)
        // (0.6, -1.7) (4.7, -1.7)
        for(int i = 0; i < 30; i++){
            Object3D tree = Loader.load3DS(res.openRawResource(R.raw.tree), 0.3f)[0];
            tree.translate(0.6f + (float)(Math.random() * (4.7f - 0.6f)), -1, -1.7f + (float)(Math.random() * (3.2 + 1.7)));
            tree.setTexture("tree");
            world.addObject(tree);
            GameObject tg = new GameObject(tree);
            tg.addScript(new Atractable());
            GameActivity.objects.add(tg);
            super.addObject(tree);
        }



    }

    private String randomTex(){
        int rand = (int) Math.floor(Math.random() * 6);

        switch (rand){
            case 0:
                return "brownBuilding";
            case 1:
                return "orangeBuilding";
            case 2:
                return "blueBuilding";
            case 3:
                return "redBuilding";
            case 4:
                return "greenBuilding";
            case 5:
                return "purpleBuilding";
        }
        return "redBuilding";

    }
}
