package GameEngine;

public class TimeManager {
    private static long lastTime;
    public static float deltaTime;

    public static void initialize(){
        lastTime = System.currentTimeMillis();
    }

    public static void updateTime(){
        deltaTime = System.currentTimeMillis() - lastTime;
//        System.out.println(deltaTime);
        deltaTime /= 1000;
        lastTime = System.currentTimeMillis();
//        System.out.println(deltaTime);
    }
}
