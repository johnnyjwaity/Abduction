package com.johnnywaity.abduction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.SkyBox;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import GameEngine.ChunkLoader;
import GameEngine.GameObject;
import GameEngine.Input;
import GameEngine.ObjectManager;
import GameEngine.TimeManager;
import GamePlay.CameraController;
import GamePlay.CityLayout;
import GamePlay.HelicopterController;
import GamePlay.UFOController;

public class GameActivity extends Activity implements GLSurfaceView.Renderer {

    private GLSurfaceView glSurfaceView;
    public static World world;
    private FrameBuffer frameBuffer;
    private Light light;
    private RGBColor sky = new RGBColor(135, 206, 250);
    public static int scoreNum = 0;
    private TextView score;

    public static ArrayList<GameObject> objects = new ArrayList<>();

    private float originX;




    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(this);

        RelativeLayout layout = new RelativeLayout(getBaseContext());
        layout.addView(glSurfaceView);

        TextView t = new TextView(getBaseContext());
        t.setText("0");
        t.setTextColor(Color.WHITE);
        t.setTextSize(32);
        layout.addView(t);
        t.setX(10);
        t.setY(0);
        t.setWidth(200);
        t.setHeight(200);

        setContentView(layout);
        score = t;

        glSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Input.IS_MOVING = true;
                    Input.ORIGIN_X = event.getRawX();
                    Input.ORIGIN_Y = event.getRawY();
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    Input.IS_MOVING = false;
                    return true;
                }
                Input.X = event.getRawX();
                Input.Y = event.getRawY();
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
        TimeManager.initialize();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        world = new World();
        world.setAmbientLight(200, 200, 200);

        Object3D[] objects = Loader.loadSerializedObjectArray(getResources().openRawResource(R.raw.objects));
        for(Object3D o : objects){
            float scale = 0;
            switch(o.getName()){
                case "bb":
                    scale = 0.4f;
                    break;
                case "b2":
                    scale = 0.4f;
                    break;
                case "ground":
                    scale = 1;
                    break;
                case "pinetree":
                    scale = 1;
                    break;
                case "roadsquare":
                    scale = 1;
                    break;
                case "taxi":
                    scale = 0.2f;
                    break;
                case "tractorbeam":
                    scale = 1f;
                    break;
                case "tree":
                    scale = 0.3f;
                    break;
                case "ufo":
                    scale = 0.5f;
                    break;
                case "helicopter":
                    scale = 0.2f;
                    break;
                case "helicopterblade":
                    scale = 1;
                    break;
            }
            System.out.println(o.getName());
            o.setScale(scale);
            ObjectManager.getSharedInstance().loadObject(new Object3D[]{o}, o.getName());
        }


        Texture grassBlock = new Texture(getResources().openRawResource(R.raw.grassblocktexture));
        Texture brownBuilding = new Texture(getResources().openRawResource(R.raw.btex));
        Texture blueTexture = new Texture(getResources().openRawResource(R.raw.bltex));
        Texture orangeTexture = new Texture(getResources().openRawResource(R.raw.otex));
        Texture greenTexture = new Texture(getResources().openRawResource(R.raw.gtex));
        Texture redTexture = new Texture(getResources().openRawResource(R.raw.rtex));
        Texture purpleTexture = new Texture(getResources().openRawResource(R.raw.ptex));
        Texture roadSquareTex = new Texture(getResources().openRawResource(R.raw.road));
        Texture ufoTexture = new Texture(getResources().openRawResource(R.raw.ufotex));
        Texture tractorBeamTex = new Texture(getResources().openRawResource(R.raw.tractortexture));
        Texture taxiTex = new Texture(getResources().openRawResource(R.raw.taxitex));
        Texture blueCar = new Texture(getResources().openRawResource(R.raw.carb));
        Texture purpleCar = new Texture(getResources().openRawResource(R.raw.carp));
        Texture pineTree = new Texture(64, 64, new RGBColor(0, 128, 0));
        Texture tree = new Texture(getResources().openRawResource(R.raw.lowpolytree));
        Texture blue = new Texture(32, 32, new RGBColor(135, 206, 250));
        Texture helicoptertex = new Texture(getResources().openRawResource(R.raw.helicoptertex));

        TextureManager.getInstance().addTexture("grassBlock", grassBlock);
        TextureManager.getInstance().addTexture("brownBuilding", brownBuilding);
        TextureManager.getInstance().addTexture("blueBuilding", blueTexture);
        TextureManager.getInstance().addTexture("orangeBuilding", orangeTexture);
        TextureManager.getInstance().addTexture("greenBuilding", greenTexture);
        TextureManager.getInstance().addTexture("redBuilding", redTexture);
        TextureManager.getInstance().addTexture("purpleBuilding", purpleTexture);
        TextureManager.getInstance().addTexture("roadSquare", roadSquareTex);
        TextureManager.getInstance().addTexture("ufo", ufoTexture);
        TextureManager.getInstance().addTexture("tractorBeamTexture", tractorBeamTex);
        TextureManager.getInstance().addTexture("taxi", taxiTex);
        TextureManager.getInstance().addTexture("carp", purpleCar);
        TextureManager.getInstance().addTexture("carb", blueCar);
        TextureManager.getInstance().addTexture("pineTree", pineTree);
        TextureManager.getInstance().addTexture("tree", tree);
        TextureManager.getInstance().addTexture("blue", blue);
        TextureManager.getInstance().addTexture("helicoptertex", helicoptertex);



        Object3D ufo = ObjectManager.getSharedInstance().getObject("ufo")[0];
        ufo.translate(2, -4, 0);
        ufo.setTexture("ufo");
        world.addObject(ufo);
        GameObject gUfo = new GameObject(ufo);
        gUfo.addScript(new UFOController());
        this.objects.add(gUfo);

        Object3D tractorBeam = ObjectManager.getSharedInstance().getObject("tractorbeam")[0];
        tractorBeam.translate(0, 6f, 0);
        tractorBeam.setTexture("tractorBeamTexture");
        tractorBeam.setTransparency(Object3D.TRANSPARENCY_MODE_ADD);
        world.addObject(tractorBeam);
        tractorBeam.addParent(ufo);

        Object3D helicopter = ObjectManager.getSharedInstance().getObject("helicopter")[0];
        helicopter.translate(2, -4, 2);
        helicopter.setTexture("helicoptertex");
        world.addObject(helicopter);


        Object3D helicopterBlade = ObjectManager.getSharedInstance().getObject("helicopterblade")[0];
        helicopterBlade.setTexture("helicoptertex");
        world.addObject(helicopterBlade);
        helicopterBlade.addParent(helicopter);

        GameObject hg = new GameObject(helicopter);
        hg.addScript(new HelicopterController(helicopterBlade));
        this.objects.add(hg);


        Class[] chunks = {CityLayout.class};
        ChunkLoader chunkLoader = new ChunkLoader(chunks, getResources());
        chunkLoader.start();


//        for(int i = 0; i < 2; i++){
//            Object3D[] objects = Loader.loadOBJ(getResources().openRawResource(R.raw.ground), getResources().openRawResource(R.raw.groundmat), 1);
//            for(Object3D o : objects){
//                if(o != objects[0]){
//                    o.addParent(objects[0]);
//                }
//                o.translate(0, 0, 0);
//                o.setTexture("grassBlock");
//                //o.rotateX((float)Math.PI);
//                world.addObject(o);
//            }
//
//            CityLayout cityLayout = new CityLayout(objects[0]);
//            cityLayout.populate(getResources());
//            cityLayout.finalize((i * 16), (i*16));
//        }

        world.getCamera().setPosition(0, -5, 0);
        world.getCamera().rotateCameraY(((float)(-Math.PI / 4)));
        world.getCamera().rotateCameraX(((float) (Math.PI / 4)));

        new CameraController(world.getCamera());
        light = new Light(world);

        light.setIntensity(255, 255, 255);
        light.setPosition(new SimpleVector(0, -0, 12));



    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        frameBuffer = new FrameBuffer(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        frameBuffer.clear(sky);
        TimeManager.updateTime();
        for(GameObject go : objects){
            go.startScriptUpdate();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                score.setText("" + scoreNum);
            }
        });
        CameraController.sharedInstance.update();
        world.renderScene(frameBuffer);
        world.draw(frameBuffer);
        frameBuffer.display();
    }


}
