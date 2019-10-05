import java.util.*;

public class VectorCalculations {   
    public double[] calculateVectorOperations(double x, double y, double velocityX, double velocityY, int windowWidth, int windowHeight, double velocityDamping, double pointSelfAttractionFactor,double pointSelfAttractionFalloff, int pointSize) {
        double newX = x;
        double newY = y;
        double newXVelocity;
        double newYVelocity;
        
        //calculate damping:
        newXVelocity = velocityX*(1-(velocityDamping));//velocity damping needs to be closer to 1 to be less so just it from one so the higher it is the farther away it is from one
        newYVelocity = velocityY*(1-(velocityDamping));
        
        //calculate self attraction:
        
        
        //calculate collisions with borders
        if(x+pointSize>=windowWidth || x<=0) {
            newXVelocity = -(newXVelocity); //bounce back(reverse the current velocity)
        }
        
        if(y+pointSize>=windowHeight-30 || y<=0) { //remember 0 is top. -30 because window is smaller den actually is for sum reason
            newYVelocity = -(newYVelocity);
        }
        
        
        newX = newX+newXVelocity;
        newY = newY+newYVelocity;
        
        return(new double[]{newX,newY,newXVelocity,newYVelocity});
    }
    
    public ArrayList<double[]> calculatePointOverlapOperations(ArrayList<double[]> points, int pointSize) {
        //this doesnt work
        int x;
        int y;
        int x2;
        int y2;
        
        double newX;
        double newY;
        double newXVelocity;
        double newYVelocity;
        
        
        //go through all da points
        for(int i=0; i<points.size(); i++) {
            //get the coordinate that we want to search for
            x = (int)points.get(i)[0];
            y = (int)points.get(i)[1];
            
            newX = x;
            newY = y;
            newXVelocity = points.get(i)[2];
            newYVelocity = points.get(i)[3];
            
            //now search the list for other points that overlap
            for(int b=0; b<points.size(); b++) {
                x2 = (int)points.get(b)[0];
                y2 = (int)points.get(b)[1];
                
                if((x+pointSize>=x2 && y+pointSize>=y2 && x+pointSize<=x2+pointSize && y+pointSize<=y2+pointSize) ||
                (x>=x2 && y>=y2 && x<=x2+pointSize && y<=y2+pointSize) ||
                (x+pointSize>=x2 && y>=y2 && x+pointSize<=x2+pointSize && y<=y2+pointSize) ||
                (x>=x2 && y+pointSize>=y2 && x<=x2+pointSize && y+pointSize<=y2+pointSize)
                && b!=i) {
                    newXVelocity = -(newXVelocity);
                    newYVelocity = -(newYVelocity);
                }
            }
            
            //set all the values
            
            points.get(i)[0] = newX;
            points.get(i)[1] = newY;
            points.get(i)[2] = newXVelocity;
            points.get(i)[3] = newYVelocity;
        }
        return(points);
    }
    
    public double getTotalDistanceToPoint(int x1, int y1, int x2, int y2) {
        return(Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2)));//equation: Square root of: (x2-x1)^2+(y2-y1)^2 pothagereum theorum
    }

    
    public ArrayList<double[]> calculateMouseInfluence(double mouseInfluenceFactor, ArrayList<double[]> points, int mouseX, int mouseY, double mouseInfluenceFalloff, double mouseVelocityX, double mouseVelocityY) {
        int distance;
        double newMouseInfluenceFactor;
        for(int i=0; i<points.size(); i++) {
            newMouseInfluenceFactor = mouseInfluenceFactor;
            
            distance = (int) getTotalDistanceToPoint((int)points.get(i)[0],(int)points.get(i)[1],mouseX,mouseY);
            //something to do with this:
            if(distance!=0) {
                newMouseInfluenceFactor = (mouseInfluenceFactor/Math.pow(mouseInfluenceFalloff,distance)); //i dont think this equation is right but basic explanation: The quadradic part is like contrast because adding a high exponent to a base results in a type of contrast so the higher the distance the bigger the exponent is so it seperates the values well
            }
            
            //apply velocities
            points.get(i)[2] = points.get(i)[2] + (mouseVelocityX*newMouseInfluenceFactor);
            points.get(i)[3] = points.get(i)[3] + (mouseVelocityY*newMouseInfluenceFactor);
        }
        
        return(points);
    }
}