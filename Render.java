import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Render {
    public Render(ArrayList<double[]> points, Graphics graphicPane, int size, int mouseX, int mouseY) {
        for(double[] point : points) {
            graphicPane.setColor(new Color(255,0,0));
            
            //uncomment for velocity mode:
            //int vel = (int)(Math.pow(Math.abs((float)point[2]+(float)point[3]),0.5)*200); //abs value
            //if(vel>225) { vel=225; }
            //graphicPane.setColor(new Color(vel,0,0));
            
            //cast all to int
            graphicPane.fillRect((int)point[0],(int)point[1],(int)size,(int)size);
        }
        
        //render mouse stuff
        graphicPane.setColor(new Color(0,0,255));
        graphicPane.fillOval(mouseX-5,mouseY-5,10,10);
    }
}