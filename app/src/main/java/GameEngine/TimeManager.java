package GameEngine;

public class TimeManager {
    private static long lastTime;
    public static long deltaTime;

    public static void initialize(){
        lastTime = System.currentTimeMillis();
    }

    public static void updateTime(){
        deltaTime = System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
    }
}
