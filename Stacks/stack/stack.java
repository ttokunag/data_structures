import java.util.EmptyStackException;

/**
 * Tomoya Tokunaga (mailto:ttokunag@ucsd.edu)
 * This file is about Stack class, a Last In First Out data structure
 */
class Stack<E>
{
    float loadFactor;
    float shrinkFactor;
    E arr[]; // Container of elements
    int capacity;
    int top; // Index of the top element

    /*
    * Stack Constructor
    * @param capacity: the size of a container
    */
    Stack(int capacity)
    {
        // we can't initialize a generic type array
        Object[] temp = new Object[capacity];
        this.arr = (E[]) temp;
        this.capacity = capacity;
        this.top = -1;
    }

    /*
    * Growable Stack Constructor
    * @param capacity: the size of a container
    * @param loadFactor: a float percentage to grow the size of the container
    */
    Stack(int capacity, float loadFactor)
    {
        Object[] temp = new Object[capacity];
        this.arr = (E[]) temp;
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.top = -1;
    }

    /*
    * Growable, shrinkable Stack Constructor
    * @param capacity: the size of a container
    * @param loadFactor: a float percentage to grow the size of the container
    * @param shrinkFactor: a float percentage to shrink the size of the container
    */
    Stack(int capacity, float loadFactor, float shrinkFactor)
    {
        Object[] temp = new Object[capacity];
        this.arr = (E[]) temp;
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.shrinkFactor = shrinkFactor;
        this.top = -1;
    }

    /*
    * description: tells if a stack is empty
    * parameter:   no parameter
    * return:      boolean
    */
    boolean isEmpty()
    {
        if (top == -1) return true;
        else return false;
    }

    /*
    * description: returns the capacity of the stack
    * parameter:   no parameter
    * return:      int
    */
    int getCapacity(){
        return this.capacity;
    }

    /*
    * description: push an element to a stack
    * parameter:   E x
    * return:      void
    */
    void push(E x) throws StackOverflowError, NullPointerException
    {
        // if a pushed element is null, throw an exception
        if (x == null) {
            throw new NullPointerException();
        }
        // we grow the size if the array is x% filled
        if (loadFactor != 0 && ((float)this.currentSize()/(float)capacity) >= loadFactor) {
            // double the size
            Object[] obj = new Object[2 * capacity]; 
            E[] temp = (E[])obj;
            // copy elements in the previous array to the new one
            for (int i = 0; i <= top; i++) {
                temp[i] = arr[i];
            }
            this.arr = temp;
            this.capacity = 2 * this.capacity; 
        }
        // if a stack is full, throw an exception
        if (this.isFull()) {
            throw new StackOverflowError();
        }
        // push the element at the top
        arr[top + 1] = x;
        top++;
    }

    /*
    * description: pop the last element of a stack, then returns the popped element
    * parameter:   None
    * return:      E
    */
    E pop() throws EmptyStackException
    {
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        // we shrink the size if the array is x% filled
        if (((float)this.currentSize()/(float)capacity) <= shrinkFactor) {
            // half the size
            Object[] obj = new Object[capacity/2]; 
            E[] temp = (E[])obj;
            // copy elements in the previous array to the new one
            for (int i = 0; i <= top; i++) {
                temp[i] = arr[i];
            }
            this.arr = temp;
            this.capacity = capacity / 2; 
        }
        // this is for the return statement
        E poppedE = arr[top];
        // remove the top item
        arr[top] = null;
        top--;

        return poppedE;
    }

    /*
    * description: returns the element at the top of the stack
    * parameter:   None
    * return:      E
    */
    E peek() throws EmptyStackException{
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        return arr[top];
    }

    /*
    * description: reset the stack
    * parameter:   None
    * return:      void
    */
    void clear(){
        // reset the array by initializing an array
        Object[] obj = new Object[capacity];
        arr = (E[])obj;
        top = -1;
    }

    /*
    * description: tells if a stack is full
    * parameter:   None
    * return:      boolean
    */
    boolean isFull(){
        return capacity == top + 1;
    }

    /*
    * description: returns the number of elements in the stack
    * parameter:   None
    * return:      int
    */
    int currentSize(){
        return this.top + 1;
    }

    /*
    * description: pop the top k elements from the stack, then returns items in the form of array
    * parameter:   int k
    * return:      E[]
    */
    E[] multiPop(int k) throws EmptyStackException{
        // Object array for a generic type array
        Object[] temp = new Object[k];
        E[] poppedItems = (E[])temp;
        for (int i = 0; i < k; i++) {
            E poppedItem = this.pop();
            poppedItems[i] = poppedItem;
            System.out.println(poppedItem);
        }

        return poppedItems;
    }

    /*
    * description: push multiple elements to the stack
    * parameter:   E[] arr
    * return:      void
    */
    void multiPush(E[] arr) throws StackOverflowError, NullPointerException{
        for (E e : arr) {
            // if a pushed element is null, throw an exception
            if (e == null) {
                throw new NullPointerException();
            }
            this.push(e);
        }
    }

    /*
    * description: reverse the order of elements in the stack
    * parameter:   None
    * return:      void
    */
    void reverse() throws EmptyStackException{
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        // swap the elements in the following lines
        int i = 0;
        int j = top;
        while (i <= j) {
            E temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
    }

    /*
    * description: push an element to a stack only if the top element differs
    from the given value. returns true if the element is successfully pushed
    * parameter:   E x
    * return:      boolean
    */
    boolean pushUnique(E x) throws StackOverflowError, NullPointerException{
        // if x is the same as the stack top, we don't push x
        if (x.equals(arr[top])) {
            return false;
        }
        else {
            this.push(x);
            return true;
        }
    }

    /*
    * description: returns the distance from the top by a 1-based integer
    returns -1 if the given element is not found in the stack
    * parameter:   E x
    * return:      int
    */
    int search(E x) throws EmptyStackException{
        //if a stack is empty
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }

        for (int i = 0; i < this.currentSize(); i++) {
            if (arr[i].equals(x)) {
                return this.currentSize() - i;
            }
        }
        //if x is not found
        return -1;
     }

}