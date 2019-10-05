//Particle water simulation - by Connor Michalec

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class Main extends JPanel implements ActionListener {//actionlistener for button
    private final static int particleAmount = 120000;
    private final int generationColSpacing = 5;
    private static int windowWidth = 1500;
    private static int windowHeight = 1000;
    private PointHandler pointHandler = new PointHandler(windowWidth, windowHeight);
    private ArrayList<double[]> points = pointHandler.generateNewPointArray(particleAmount, generationColSpacing);//create new points and save them to point var;
    private ArrayList<double[]> pointsWaitToBeUpdated;
    private boolean pointsRequestReset = false;
    private final int pointSize = 2;
    private final double mouseInfluenceFactor = 0.04;
    private final double mouseInfluenceFalloff = 1.03;
    private final double velocityDamping = 0.003;
    
    //self attraction(gravity) not implemented
    private final double pointSelfAttractionFactor = 0.01; //how fast the points should attract to each other
    private final double pointSelfAttractionFalloff = 1; //falloff of the point self attraction
    private UserInfo info = new UserInfo();
    
    private int mouseX = 0;
    private int mouseY = 0;
    private int lastMouseX = 0;//used for calculation of mouse velocity
    private int lastMouseY = 0; 
    private double mouseVelocityX = 0;
    private double mouseVelocityY = 0;

    public void paint(Graphics g) {
        new Render(points, g, pointSize, mouseX, mouseY);
    }
    
    public void actionPerformed(ActionEvent e) { //for button press
        //if action is detected(only button is registered so dont rlly have to check which action is performed
        //need this because gotta wait till all the memory flushes running through other methods
        pointsWaitToBeUpdated = pointHandler.generateNewPointArray(particleAmount, generationColSpacing);
        pointsRequestReset = true;
    }
    
    public static void main(String[] args) {
        JFrame window = new JFrame();
        
        window.setTitle("particle simulator - by Connor Michalec");
        
        window.setSize(windowWidth, windowHeight);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Main thisMain = new Main();
        
        JButton resetButton = new JButton("reset");
        resetButton.addActionListener(thisMain);
        JLabel message = new JLabel("move yo mouse to get sumthin kool");
        JPanel panel;
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(255,255,255));
        panel.add(message,BorderLayout.NORTH);
        panel.add(resetButton, BorderLayout.SOUTH);
        panel.setSize(500,50);
        
        window.getContentPane().add(panel);
        window.getContentPane().add(thisMain);
        
        

        window.setVisible(true);
        
        
        while(true) { //main loop
            thisMain.update();
            window.repaint();
            try {
                Thread.sleep(0);//delay i wud do the FPS thing but im lazy
            }
            catch(InterruptedException e) {
            }
            thisMain.checkPointReset();
        }
    }
    
    public void update() {
        mouseX = info.mousePos()[0];
        mouseY = info.mousePos()[1];
        mouseVelocityX = info.mouseVelocity(mouseX, mouseY, lastMouseX, lastMouseY)[0];
        mouseVelocityY = info.mouseVelocity(mouseX, mouseY, lastMouseX, lastMouseY)[1];
        points = pointHandler.updatePoints(points, windowWidth, windowHeight, pointSize, mouseInfluenceFactor, mouseX, mouseY, mouseInfluenceFalloff, mouseVelocityX, mouseVelocityY, velocityDamping, pointSelfAttractionFactor, pointSelfAttractionFalloff); //update the points(next tick)
        
        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }
    
    public void checkPointReset() {
            //all the memory is out of the methods so now we can update the points
        if(pointsRequestReset) {
            points = pointsWaitToBeUpdated;
            pointsRequestReset = false;
        }
    }
}