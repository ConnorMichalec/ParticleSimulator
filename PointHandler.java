import java.util.*;

public class PointHandler {
    private int winWidth;
    private int winHeight;
    
    public PointHandler(int windowWidth, int windowHeight) {
        winWidth = windowWidth;
        winHeight = windowHeight;
    }
    
    //Point format: x,y,velocityx,velocity
    public ArrayList<double[]> generateNewPointArray(int amount,int colSpacing) {
        ArrayList<double[]> points = new ArrayList<double[]>();
        int x = 500; //make it in center(kinda  a hacky way to do it)
        int y = 0;
        double velocityX;
        double velocityY;
        int colAmount = (int)(1+(amount/winHeight));//figure out the amount of collumns we need cast to int to cut off decimal
        int amountUsed = 1; //amount of particles already added
        int amountThisColUsed;
        
        //generation isnt gonna worry about block size
        for(int col=0; col<colAmount; col++) {
            amountThisColUsed = 1;
            while(true) {
                amountThisColUsed++;
                y = y + 1; //cannot divide itself by a number greater than itself so we must disperse the points(1000/10001)
                
                
                
                //initial velocities:
                
                velocityX = 0; //default velocities
                velocityY = 0;
                
                //random mode:(uncomment)
                //velocityX = new Random().nextInt(30)/10;
                //velocityY = new Random().nextInt(30)/10;
                
                
                
                points.add(new double[]{x,y,velocityX,velocityY});
                amountUsed++;
                if(amountThisColUsed>=winHeight || amountUsed>=amount) {
                    break;
                }
            }
            x=x+colSpacing;
            y=0;
        }

        return(points);
    }
    
    public ArrayList<double[]> updatePoints(ArrayList<double[]> pointList, int windowWidth, int windowHeight, int pointSize, double mouseInfluence, int mouseX, int mouseY, double mouseInfluenceFalloff, double mouseVelocityX, double mouseVelocityY, double velocityDamping,double pointSelfAttractionFactor, double pointSelfAttractionFalloff) {
        VectorCalculations computeVector = new VectorCalculations();
        double[] newVector;
        int index = 0;
        
        //calculate point collisions with other points(doesnt work rn)
        //pointList = computeVector.calculatePointOverlapOperations(pointList, pointSize);
        
        //calculate mouse influence on points
        pointList = computeVector.calculateMouseInfluence(mouseInfluence, pointList, mouseX, mouseY, mouseInfluenceFalloff, mouseVelocityX, mouseVelocityY);
        
        for(double[] point : pointList) {
            newVector = computeVector.calculateVectorOperations(point[0],point[1],point[2],point[3],windowWidth,windowHeight,velocityDamping,pointSelfAttractionFactor, pointSelfAttractionFalloff, pointSize);
            
            pointList.set(index, newVector);
            
            index++;
        }

        return(pointList);
    }
}