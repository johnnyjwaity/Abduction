package com.johnnywaity.abduction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import GameEngine.GameObject;
import GameEngine.Input;
import GameEngine.TimeManager;
import GameEngine.Vector2;
import GamePlay.Atractable;
import GamePlay.CameraController;
import GamePlay.CarController;
import GamePlay.CityLayout;
import GamePlay.UFOController;

public class GameActivity extends Activity implements GLSurfaceView.Renderer {

    private GLSurfaceView glSurfaceView;
    public static World world;
    private FrameBuffer frameBuffer;
    private Light light;

    public static ArrayList<GameObject> objects = new ArrayList<>();

    private float originX;




    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(this);
        setContentView(glSurfaceView);

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
        world.setAmbientLight(255, 255, 255);
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

        Object3D ufo = Loader.load3DS(getResources().openRawResource(R.raw.ufo), 0.5f)[0];
        ufo.translate(2, -4, 0);
        ufo.setTexture("ufo");
        world.addObject(ufo);
        GameObject gUfo = new GameObject(ufo);
        gUfo.addScript(new UFOController());
        this.objects.add(gUfo);

        Object3D tractorBeam = Loader.load3DS(getResources().openRawResource(R.raw.tractorbeam), 0.5f)[0];
        tractorBeam.translate(0, 2.8f, 0);
        tractorBeam.setTexture("tractorBeamTexture");
        tractorBeam.setTransparency(Object3D.TRANSPARENCY_MODE_ADD);
        world.addObject(tractorBeam);
        tractorBeam.addParent(ufo);




        for(int i = 0; i < 2; i++){
            Object3D[] objects = Loader.loadOBJ(getResources().openRawResource(R.raw.ground), getResources().openRawResource(R.raw.groundmat), 1);
            for(Object3D o : objects){
                o.translate(0, 0, 0);
                o.setTexture("grassBlock");
                //o.rotateX((float)Math.PI);
                world.addObject(o);
            }

            CityLayout cityLayout = new CityLayout(objects[0]);
            cityLayout.populate(getResources(), (i * 15), 0);
            cityLayout.finalize((i * 15), 0);
        }

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
        frameBuffer.clear();
        TimeManager.updateTime();
        for(GameObject go : objects){
            go.startScriptUpdate();
        }
        CameraController.sharedInstance.update();
        world.renderScene(frameBuffer);
        world.draw(frameBuffer);
        frameBuffer.display();
    }


}
