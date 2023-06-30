package fallingsand;

import java.awt.*;

public class SandLab
{
    //add constants for particle types here
    public static final int EMPTY = 0;
    public static final int METAL = 1;
    public static final int BUTTON = 2;
    public static final int BUTTON2 = 3;
    public static final int BUTTON3 = 4;
    public static final int BOX = 5;

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
    
    public int[] matrixMultiply(int[][] fbfMatrix, int[] fboMatrix) {
        int[] outputMatrix = {1, 1, 1, 1};
        outputMatrix[0] = fbfMatrix[0][0] * fboMatrix[0] + fbfMatrix[1][0] * fboMatrix[1] + fbfMatrix[2][0] * fboMatrix[2] + fbfMatrix[3][0] * fboMatrix[3];
        outputMatrix[1] = fbfMatrix[0][1] * fboMatrix[0] + fbfMatrix[1][1] * fboMatrix[1] + fbfMatrix[2][1] * fboMatrix[2] + fbfMatrix[3][1] * fboMatrix[3];
        outputMatrix[2] = fbfMatrix[0][2] * fboMatrix[0] + fbfMatrix[1][2] * fboMatrix[1] + fbfMatrix[2][2] * fboMatrix[2] + fbfMatrix[3][2] * fboMatrix[3];
        outputMatrix[3] = fbfMatrix[0][3] * fboMatrix[0] + fbfMatrix[1][3] * fboMatrix[1] + fbfMatrix[2][3] * fboMatrix[2] + fbfMatrix[3][3] * fboMatrix[3];
        
        return outputMatrix;
    }
    
    
    
    int[][] button1 = {{25, 110}, {24, 110}, {24, 109}, {24, 108}, {23, 110}};
    int[][] button2 = {{35, 110}, {36, 110}, {37, 110}, {35, 109}, {35, 111}};    
    int[][] button3 = {{13, 110}, {12, 110}, {11, 110}, {13, 109}, {13, 111}};
    
    int[][] box = {{50, 50}, {50, 51}, {51, 50}, {51, 51}};
    
    //called when the user clicks on a location using the given tool
    private void locationClicked(int row, int col, int tool)
            
    {
        if (grid[row][col] == BUTTON && (int)(Math.random() * 100) == 45) {
            System.out.println("Clicked Button");
            for (int i = 0; i < box.length; i++) {
                grid[box[i][1]][box[i][0]] = EMPTY;
                box[i][1] -= 1;
            }
        }
        if (grid[row][col] == BUTTON2 && (int)(Math.random() * 100) == 45) {
            System.out.println("Clicked Button");
            for (int i = 0; i < box.length; i++) {
                grid[box[i][1]][box[i][0]] = EMPTY;
                box[i][0] += 1;
            }
        }
        if (grid[row][col] == BUTTON3 && (int)(Math.random() * 100) == 45) {
            System.out.println("Clicked Button");
            for (int i = 0; i < box.length; i++) {
                grid[box[i][1]][box[i][0]] = EMPTY;
                box[i][0] -= 1;
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
                if (grid[row][column] == BOX)
                {
                    display.setColor(row, column, metal);
                }
            }
        }
        buttonMaker(button1, BUTTON);
        buttonMaker(button2, BUTTON2);
        buttonMaker(button3, BUTTON3);
        buttonMaker(box, BOX);
        
        int[][] matrixOne = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        int[] matrixTwo = {1, 2, 3, 4};
        int[] multipliedMatrix = matrixMultiply(matrixOne, matrixTwo);
        
        for (int i = 0; i < multipliedMatrix.length; i++) {
            System.out.print(multipliedMatrix[i]);
            System.out.print(" ");
        }
        System.out.println(" ");
    }

    //called repeatedly.
    //causes one random particle to maybe do something.
    public void step()
    {
        
    }

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
                locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
            }
            
        }
    }

        
}