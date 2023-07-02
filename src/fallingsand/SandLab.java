package fallingsand;

import java.awt.*;
import java.util.Arrays;

public class SandLab
{
    //add constants for particle types here
    public static final int EMPTY = 0;
    public static final int METAL = 1;
    public static final int BUTTON = 2;
    public static final int BUTTON2 = 3;
    public static final int BUTTON3 = 4;
    public static final int BOX = 5;
    public static final int BUTTON4 = 6;
    
    private double cameraX;     // X position of the camera
    private double cameraY;     // Y position of the camera
    private double cameraZ;     // Z position of the camera
    private double cameraYaw;   // Yaw angle of the camera (rotation around the Y axis)
    private double cameraPitch; // Pitch angle of the camera (rotation around the X axis)


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

    public void buttonMaker(int[][] arr, int button) {
        for (int i = 0; i < arr.length; i++) {
            grid[arr[i][1]][arr[i][0]] = button;
        }
    }
    
    public float[][] perspectiveMatrix(float FOV, float width, float height, float zFar, float zNear) {
        float aspectRatio = height / width;
        float[][] returnMatrix = new float[4][4];
        returnMatrix[0][0] = (float) (aspectRatio * (1/Math.tan(FOV / 2)));
        returnMatrix[1][1] = (float) (1/Math.tan(FOV / 2));
        returnMatrix[2][2] = (float) zFar / (zFar - zNear);
        returnMatrix[2][3] = (float) (-zFar * zNear) / (zFar - zNear);
        returnMatrix[3][2] = (float) 1.0;
        
        return returnMatrix;
    }
    
    public float[] matrixMultiply(float[][] fbfMatrix, float[] fboMatrix) {
        float[] outputVector = new float[4];
        outputVector[0] = fboMatrix[0] * fbfMatrix[0][0] + fboMatrix[1] * fbfMatrix[0][1] + fboMatrix[2] * fbfMatrix[0][2] + fboMatrix[3] * fbfMatrix[0][3];
        outputVector[1] = fboMatrix[0] * fbfMatrix[1][0] + fboMatrix[1] * fbfMatrix[1][1] + fboMatrix[2] * fbfMatrix[1][2] + fboMatrix[3] * fbfMatrix[1][3];
        outputVector[2] = fboMatrix[0] * fbfMatrix[2][0] + fboMatrix[1] * fbfMatrix[2][1] + fboMatrix[2] * fbfMatrix[2][2] + fboMatrix[3] * fbfMatrix[2][3];
        outputVector[3] = fboMatrix[0] * fbfMatrix[3][0] + fboMatrix[1] * fbfMatrix[3][1] + fboMatrix[2] * fbfMatrix[3][2] + fboMatrix[3] * fbfMatrix[3][3];
        
        return outputVector;
    }
    
    
    
    
    int[][] button1 = {{25, 105}, {24, 105}, {24, 104}, {24, 103}, {23, 105}};
    int[][] button2 = {{35, 110}, {36, 110}, {37, 110}, {35, 109}, {35, 111}};    
    int[][] button3 = {{13, 110}, {12, 110}, {11, 110}, {13, 109}, {13, 111}};
    int[][] button4 = {{25, 115}, {24, 115}, {24, 116}, {24, 117}, {23, 115}};
    
    int[][] box = {{50, 50}, {50, 51}, {51, 50}, {51, 51}};
    
    //called when the user clicks on a location using the given tool
    private void locationClicked(int row, int col, int tool, int j)
            
    {
        if (grid[row][col] == BUTTON && j % 10 == 0 && box[0][1] > 0) {
            System.out.println("Clicked Button");
            for (int i = 0; i < box.length; i++) {
                grid[box[i][1]][box[i][0]] = EMPTY;
                box[i][1] -= 1;
            }
        }
        if (grid[row][col] == BUTTON2 && j % 10 == 0 && box[0][0] < COLUMNS - 2) {
            System.out.println("Clicked Button");
            for (int i = 0; i < box.length; i++) {
                grid[box[i][1]][box[i][0]] = EMPTY;
                box[i][0] += 1;
                System.out.println(box[i][0]);
            }
        }
        if (grid[row][col] == BUTTON3 && j % 10 == 0 && box[2][0] > 1) {
            System.out.println("Clicked Button");
            for (int i = 0; i < box.length; i++) {
                grid[box[i][1]][box[i][0]] = EMPTY;
                box[i][0] -= 1;
                System.out.println(box[i][0]);
            }
        }
        if (grid[row][col] == BUTTON4 && j % 10 == 0 && box[3][1] < ROWS - 1) {
            System.out.println("Clicked Button");
            for (int i = 0; i < box.length; i++) {
                grid[box[i][1]][box[i][0]] = EMPTY;
                box[i][1] += 1;
            }
        }
    };
    
    
    

    //copies each element of grid into the display
    public void updateDisplay()
    {
        Color empty = new Color(0, 0, 0);
        Color metal = new Color(100, 100, 100);

        for (int row = 0; row < ROWS; row++)
        {
            for (int column = 0; column < COLUMNS; column++)
            {
                if (grid[row][column] == EMPTY)
                {
                    display.setColor(row, column, empty);
                }
                if (grid[row][column] == METAL)
                {
                    display.setColor(row, column, metal);
                }
                if (grid[row][column] == BUTTON)
                {
                    display.setColor(row, column, metal);
                }
                if (grid[row][column] == BUTTON2)
                {
                    display.setColor(row, column, metal);
                }
                if (grid[row][column] == BUTTON3)
                {
                    display.setColor(row, column, metal);
                }
                if (grid[row][column] == BUTTON4)
                {
                    display.setColor(row, column, metal);
                }
                if (grid[row][column] == BOX)
                {
                    display.setColor(row, column, metal);
                }
            }
        }
        buttonMaker(button1, BUTTON);
        buttonMaker(button2, BUTTON2);
        buttonMaker(button3, BUTTON3);
        buttonMaker(button4, BUTTON4);
        buttonMaker(box, BOX);
        
        int[][] matrixOne = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        int[] matrixTwo = {1, 2, 3, 4};

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
            
            if (j == 1000) {
                j = 0;
            }
            j++;
            float[] position = {1, 4, 3, 1};
            float[][] pos2 = {{1, 5, 2, 3}, {1, 5, 3, 4}, {3, 2, 1, 6}, {1, 7, 8, 4}};
            float[][] matrix = perspectiveMatrix(50, 80, 120, 10, 2);
            float[] fMatrix = matrixMultiply(matrix, position);
            float[] mat2 = matrixMultiply(pos2, position);
            
            System.out.print(Arrays.toString(mat2) + " ");
            
            System.out.println();
        }
    }

        
}x