/**
 * Tomoya Tokunaga (mailto:ttokunag@ucsd.edu)
 * This file is about ImageEditor class
 */
public class ImageEditor {

    int[][] mat;
    Stack<Integer[]> history;  // a stack to keep track of operation history
    Stack<Integer[]> undoHist; // a stack to keep track of undone operation history
    final int SIZE = 4; // the initial size of history stacks

    /*
    * ImageEditor Constructor
    * @param int[][] mat: a container of image pixels
    */
    public ImageEditor(int[][] mat) {
        this.mat = mat;
        this.history = new Stack<Integer[]>(SIZE, 0.5f, 0.5f);
        this.undoHist = new Stack<Integer[]>(SIZE, 0.5f, 0.5f);
    }

    /*
    * description: set the value at index (i,j) 0
    * @param:   i, j: index of the matrix
    * return:      void
    */
    void delete(int i, int j) throws IndexOutOfBoundsException{
        // throws an exception if given indexes are out of bounds
        if (i > mat.length - 1 || j > mat[0].length - 1 || i < 0 || j < 0) {
            throw new IndexOutOfBoundsException();
        }
        Integer previousVal = mat[i][j];
        mat[i][j] = 0;
        // write a record in the history
        history.push(new Integer[]{i, j, previousVal, 0});
    }

    /*
    * description: blurs the value at index (i,j) by multiplying by the factor
    * @param: i, j: index of the matrix
    * @param: blurFactor: percentage to blur
    * return: void
    */
    void blur(int i, int j, float blurFactor) throws IndexOutOfBoundsException, IllegalArgumentException{
        // throws an exception if given indexes are out of bounds
        if (i > mat.length - 1 || j > mat[0].length - 1 || i < 0 || j < 0) {
            throw new IndexOutOfBoundsException();
        }
        // throws an exception if an invalid value is given
        if (blurFactor <= 0 || blurFactor >= 1) {
            throw new IllegalArgumentException();
        }
        Integer previousVal = mat[i][j];
        Integer val = Math.round(blurFactor * (float)mat[i][j]);
        mat[i][j] = val;
        // write a record in the history
        history.push(new Integer[]{i, j, previousVal, val});
    }

    /*
    * description: sharpens the value at index (i,j) by multiplying by the factor
    * @param: i, j: index of the matrix
    * @param: sharpenFactor: percentage to sharpen
    * return: void
    */
    void sharpen(int i, int j, float sharpenFactor) throws IndexOutOfBoundsException, IllegalArgumentException{
        // throws an exception if given indexes are out of bounds
        if (i > mat.length - 1 || j > mat[0].length - 1 || i < 0 || j < 0) {
            throw new IndexOutOfBoundsException();
        }
        // throws an exception if an invalid value is given
        if (sharpenFactor < 1) {
            throw new IllegalArgumentException();
        }
        Integer previousVal = mat[i][j];
        int val = Math.round(sharpenFactor * (float)mat[i][j]);
        if (val > 255) {
            val = 255;
            mat[i][j] = val;
        }
        else {
            mat[i][j] = val;
        }
        // write a record in the history
        history.push(new Integer[]{i, j, previousVal, val});
    }

    /*
    * description: undo the last operation
    * return: boolean
    */
    boolean undo(){
        if (history.isEmpty()) {
            return false;            
        }
        else {
            Integer lastDone[] = history.pop();
            // put the element back to the previous state
            mat[lastDone[0]][lastDone[1]] = lastDone[2];
            // push the undone operation 
            undoHist.push(lastDone);
            
            return true;
        }
    }

    /*
    * description: redo the last undo operation
    * return: boolean
    */
    boolean redo() {
        // if there's no undone operations, return false
        if (undoHist.isEmpty()) {
            return false;
        }
        else {
            Integer[] lastUndone = undoHist.pop();
            mat[lastUndone[0]][lastUndone[1]] = lastUndone[3];
            return true;
        }
    }

    /*
    * description: returns the matrix
    * return: int[][]
    */
    int[][] getImage(){
        return this.mat;
    }


}
