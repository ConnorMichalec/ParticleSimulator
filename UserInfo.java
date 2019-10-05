import javax.swing.*;
import java.awt.*;

public class UserInfo {
    
    public int[] mousePos() {
        int coords[] = new int[2];
        coords[0] = (int) Math.round(MouseInfo.getPointerInfo().getLocation().getX());
        coords[1] = (int) Math.round(MouseInfo.getPointerInfo().getLocation().getY())-60;//for some reason the mouse is offset up
        return(coords);
    }
    
    public double[] mouseVelocity(int thisPosX, int thisPosY, int lastPosX, int lastPosY) {
        return(new double[]{thisPosX-lastPosX,thisPosY-lastPosY});
    }
}