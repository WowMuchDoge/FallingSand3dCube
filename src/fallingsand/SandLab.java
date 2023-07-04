package fallingsand;

import java.awt.*;
import java.util.Arrays;

public class SandLab
{
    //add constants for particle types here
    public static final int EMPTY = 0;
    public static final int METAL = 1;
    public static final int POINT = 2;


    //do not add any more fields
    private int[][] grid;
    private SandDisplay display;
    public static final int ROWS = 120;
    public static final int COLUMNS = 80;

    public static void main(String[] args)
    {
        SandLab lab = new SandLab(ROWS, COLUMNS);
        lab.run();
    }

    public SandLab(int numRows, int numCols)
    {
        String[] names;
        names = new String[2];
        names[EMPTY] = "Empty";
        names[METAL] = "Metal";
        display = new SandDisplay("Falling Sand", numRows, numCols, names);
        grid = new int[numRows][numCols];
    }
    
    public void pointMaker(float[][] arr, int button) {
        if (arr[0][1] < ROWS - 1 && arr[0][1] > 0 && arr[0][0] < COLUMNS - 1 && arr[0][0] > 0) {
        for (int i = 0; i < arr.length; i++) {
            grid[(int)arr[i][1]][(int)arr[i][0]] = POINT;
        }
    };
        
    }
    
    public float[] matrixVectorMultiply(float[][] matrix, float[] vector) {
        float[] result = new float[matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                float sum = 0;
                for (int j = 0; j < vector.length; j++) {
                    sum += matrix[i][j] * vector[j];
                }
                result[i] = sum;
            }
            return result;
    };
    
    public float[][] matrixMatrixMultiply(float[][] matrixA, float[][] matrixB) {
        float[][] result = new float[matrixA.length][matrixB.length];
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA.length; j++) {
                for (int k = 0; k < matrixB.length; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return result;
    };
    
    //called when the user clicks on a location using the given tool
    private void locationClicked(int row, int col, int tool, int j) {
        
    };
    
    private void drawLine(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            float[][] point = {{x1, y1}};
            pointMaker(point, POINT);

            if (x1 == x2 && y1 == y2)
                break;

            int err2 = 2 * err;

            if (err2 > -dy) {
                err -= dy;
                x1 += sx;
            }

            if (err2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }
    

    //copies each element of grid into the display
    public void updateDisplay()
    {
        Color empty = new Color(0, 0, 0);
        Color metal = new Color(100, 100, 100);

        for (int row = 0; row < ROWS; row++)
        {
            for (int column = 0; column < COLUMNS; column++)
            {
                if (grid[row][column] == POINT) {
                    display.setColor(row, column, metal);
                }
                if (grid[row][column] == EMPTY){
                    display.setColor(row, column, empty);
                }
            }
        }
        

    }
    
    float[][] other = {
        {20, 40, 0},
       {20, 20, 0},
       
       {20, 20, 20},
       {20, 40, 20},
    };
    
    float[][] points = {
        {0, 20, 20},
        {20, 20, 20},
        {20, 20, 0},
        {20, 20, 20},
        {20, 40, 20},
        {20, 40, 0},
        {20, 40, 20},
        {0, 40, 20},
        {0, 40, 0},
        {0, 40, 20},
        {0, 20, 20},
        
        {0, 20, 0},
        {0, 40, 0},
        {20, 40, 0},
        {20, 20, 0},
        {0, 20, 0}
    };
    
    int[][] nPoints = new int[16][2];
    
    public void aRun(int i) {
        float j = (float) i / 4;

        float angle = (float) Math.toRadians(j); 
        float[] position = {20, 20, -20};
        float[][] xRotate = {
            {1, 0, 0},
            {0, (float) Math.cos(angle), (float) -Math.sin(angle)},
            {0, (float) Math.sin(angle), (float) Math.cos(angle)}
        };
        float[][] yRotate = {
            {(float) Math.cos(angle), 0, (float) Math.sin(angle)},
            {0, 1, 0},
            {(float) -Math.sin(angle), 0, (float) Math.cos(angle)}
        };
        float[][] zRotate = {
            {(float) Math.cos(angle), (float) -Math.sin(angle), 0},
            {(float) Math.sin(angle), (float) Math.cos(angle), 0},
            {0, 0, 1}
        };
        
        float[][] xyMatrix = matrixMatrixMultiply(xRotate, yRotate);
        float[][] xyzRotate = matrixMatrixMultiply(xyMatrix, zRotate);
        
        for (int row = 0; row < ROWS; row++){
            for (int col = 0; col < COLUMNS; col++){
                grid[row][col] = EMPTY;
            }
        }
        
        int l = 0;
        
        for (float[] point : points) {
            float[][] dPos = {matrixVectorMultiply(xyzRotate, point)};
            dPos[0][1] += ROWS / 2;
            dPos[0][0] += COLUMNS / 2;
            pointMaker(dPos, POINT);
            nPoints[l][0] = (int) dPos[0][0];
            nPoints[l][1] = (int) dPos[0][1];
            l++;
        }
        for (int k = 0; k < nPoints.length; k++) {
            if (k < nPoints.length - 1) {
                drawLine(nPoints[k][0], nPoints[k][1], nPoints[k + 1][0], nPoints[k + 1][1]);
            }
            else {
                drawLine(nPoints[k][0], nPoints[k][1], nPoints[0][0], nPoints[0][1]);
                System.out.println(nPoints[0][0]);
            }
        }
    }

    //called repeatedly.
    //causes one random particle to maybe do something.
    public void step()
    {
    }
    
    int j = 0;
    
    //do not modify
    public void run()
    {
        while (true)
        {
            for (int i = 0; i < display.getSpeed(); i++)
            {
                step();
            }

            updateDisplay();
            display.repaint();
            display.pause(1);  //wait for redrawing and for mouse
            int[] mouseLoc = display.getMouseLocation();

            if (mouseLoc != null)  //test if mouse clicked
            {
                locationClicked(mouseLoc[0], mouseLoc[1], display.getTool(), j);
            }
            
            if (j == 1440) {
                j = 0;
            }
            j++;
            aRun(j);
        }
    }

        
}