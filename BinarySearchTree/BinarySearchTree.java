package com.cse.ds;

import java.util.*;
import java.io.*;

/**
 * @author Tomoya Tokunaga(mailto: ttokunag@ucsd.edu)
 * About this file:
 * This file implements Binary Search Tree structure
 */
public class BinarySearchTree {

	private Node root;
	
    //============== BALANCED TREE CREATION ====================//
    /**
	 * Build a binary search tree from array inputs
	 * @param String[] cities: city name of each node
     * @param int[] population: the number of population of each city
	 */
	public BinarySearchTree(String[] cities, int[] population) {
        // avoids the case inputs are null
		if (!(cities == null || population == null) && (cities.length != 0)) {
			int midIdx = (cities.length - 1)/ 2;
			root = new Node(cities[midIdx].trim(), population[midIdx]);
			for (int i = 0; i < cities.length; i++) {
				if (i == midIdx) {
					continue;
				}
				else {
					Node node = new Node(cities[i].trim(), population[i]);
                    // use a helper method below
                    this.insert(root, node);
				}
			}
		}
	}

    /**
	 * a helper method to build a BST
	 * @param Node root: root of the existing BST
     * @param Node node: a node to be inserted
	 */
	private void insert(Node root, Node node) {
        // follows the definition of BST
		if (root.population < node.population) {
			if (root.right == null) {
				root.right = node;
			} else {
				this.insert(root.right, node);
			}
		}
		else {
			if (root.left == null) {
				root.left = node;
			}
			else {
				this.insert(root.left, node);
			}
		}
	}
    
    /**
	 * Build a binary search tree from an input file
	 * @param String filename: a input file to be read
     * @param int num_lines: the number of lines in the file
	 */
	public BinarySearchTree(String filename, int num_lines) {
        List<String[]> inputs = new ArrayList<String[]>();
        // reads the input file 
        try {
            File file = new File(filename); 
            BufferedReader br = new BufferedReader(new FileReader(file)); 
            String st; 
            while ((st = br.readLine()) != null) {
                String[] temp = st.split("=>");
                for (int i = 0; i < 2; i++) {
                    temp[i] = temp[i].trim();
                }
                inputs.add(0, temp);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        // creats cities and population like ones in the other constructor
        String[] cities = new String[inputs.size()];
        int[] population = new int[inputs.size()];
        for (int i = 0; i < inputs.size(); i++) {
            cities[i] = (inputs.get(i))[0];
            population[i] = Integer.parseInt((inputs.get(i))[1]);
        }
        // the same steps as the above constructor
        int midIdx = (inputs.size() - 1) / 2;
        this.root = new Node(cities[midIdx], population[midIdx]);
        for (int i = 0; i < inputs.size(); i++) {
            if (i == midIdx) {
                continue;
            }
            else {
                Node node = new Node(cities[i], population[i]);
                this.insert(this.root, node);
            }
        }
	}
	
    //============== COMMON TREE OPERATIONS ====================//
    /**
	 * adds a new city to the BST
	 * @param String city: the name of the city
     * @param int population: the number of population of the city
	 */
	public void addCity(String city, int population) {
        Node node = new Node(city.trim(), population);
        // the case the BST is empty
		if (this.root == null) {
			this.root = node;
		}
		else {
			this.insert(root, node);
		}
    }
    
    /**
	 * returns the largest depth of the BST
	 */
	public int getMaxDepth() {
        // the case the BST is emptu
		if (this.root == null) {
			return 0;
        }
        // the followings do Breadth-First search basically
        Queue<ArrayList<Node>> currLevelQueue = new Queue<ArrayList<Node>>();
        ArrayList<Node> initial = new ArrayList<Node>();
        initial.add(this.root);
        currLevelQueue.enqueue(initial);
        int depth = 0;
        while (currLevelQueue.size() != 0) {
            ArrayList<Node> currLevel = currLevelQueue.dequeue();
            ArrayList<Node> nextLevel = new ArrayList<Node>();
            for (int i = 0; i < currLevel.size(); i++) {
                Node currNode = currLevel.get(i);
                if (!(currNode.right == null)) {
                    nextLevel.add(currNode.right);
                }
                if (!(currNode.left == null)) {
                    nextLevel.add(currNode.left);
                }
            }
            depth++;
            if (nextLevel.size() == 0) continue;
            else currLevelQueue.enqueue(nextLevel);
            
        }
        return depth;
    }
    
    /**
	 * returns the largest width of the BST
	 */
	public int getMaxWidth() {
		//TODO: Get the maximum width of BST
		return 0;
	}
    
    /**
	 * returns the name of the smallest population city
	 */
	public String getSmallestCity() {
        Node currNode = this.root;
        // going the left until reaching null
        while (currNode.left != null) {
            currNode = currNode.left;
        }
        return currNode.city;
    }
    
    /**
	 * returns the name of the largset population city
	 */
	public String getLargestCity() {
        Node currNode = this.root;
        // going the right until reaching null
        while (currNode.right != null) {
            currNode = currNode.right;
        }
        return currNode.city;
    }
	
    //============== TREE TRAVERSALS ====================//
    /**
	 * returns the preorder traversal result
	 */
	public List<String> getPreOrderTraversal() {
        List<String> list = new ArrayList<String>();
        // use the helper method below
        this.preOrderHelper(this.root, list);
		return list;
    }
    /**
	 * a helper method to implement preorder traversal
     * @param Node node: the node we are looking at 
     * @param List<String> list: a list to store node info
	 */
    private void preOrderHelper(Node node, List<String> list) {
        if (node == null) {
            return;
        }
        list.add(node.city);
        this.preOrderHelper(node.left, list);
        this.preOrderHelper(node.right, list);
    }

	/**
	 * returns the postorder traversal result
	 */
	public List<String> getPostOrderTraversal() {
        List<String> list = new ArrayList<String>();
        // use the helper method below
        this.postOrderHelper(this.root, list);
		return list;
    }
    
    /**
	 * a helper method to implement postorder traversal
     * @param Node node: the node we are looking at 
     * @param List<String> list: a list to store node info
	 */
    private void postOrderHelper(Node node, List<String> list) {
        if (node == null) {
            return;
        }
        this.postOrderHelper(node.left, list);
        this.postOrderHelper(node.right, list);
        list.add(node.city);
    }
    
    /**
	 * returns the list of city name sorted based on population
	 */
	public List<String> getSortedCities() {
        // the followings store the names of all cities in a list
        List<String> res = new ArrayList<String>();
        List<Node> stack = new ArrayList<Node>();
        stack.add(this.root);
        while (stack.size() != 0) {
            Node currNode = stack.remove(stack.size() - 1);
            res.add(currNode.city);
            if (currNode.right != null) {
                stack.add(currNode.right);
            }
            if (currNode.left != null) {
                stack.add(currNode.left);
            }
        }
        // sorts the list in ascending order
        Collections.sort(res);
        return res;
    }
    
    /**
	 * returns the list of lists which contains city names of each node
	 */
    public List<List<String>> getLevelWiseCities() {
        // the following do Breadth-First search
        List<List<String>> res = new ArrayList<List<String>>();
        // a queue to store nodes in a current level
        Queue<List<Node>> currLevelQueue = new Queue<List<Node>>();
        // initialize the currLevelQueue
        ArrayList<Node> initial = new ArrayList<Node>();
        initial.add(this.root);
        currLevelQueue.enqueue(initial);
        // starts BFS
        while (currLevelQueue.size() != 0) {
            List<Node> currLevel = currLevelQueue.dequeue();
            int pointer = 0;
            List<Node> nextLevel = new ArrayList<Node>();
            List<String> currString = new ArrayList<String>();
            while (pointer < currLevel.size()) {
                Node currNode = currLevel.get(pointer);
                currString.add(currNode.city);
                if (currNode.left != null) {
                    nextLevel.add(currNode.left);
                }
                if (currNode.right != null) {
                    nextLevel.add(currNode.right);
                }
                pointer++;
            }
            res.add(currString);
            if (nextLevel.size() != 0) {
                currLevelQueue.enqueue(nextLevel);
            }
        }
        return res;
	}
	
	public List<List<String>> getTwistyTraversal() {
		return null;
	}
	
    //============== TREE VIEWS ====================//
    /**
	 * returns the list of city names which reside in the leftmost of the BST
	 */
	public List<String> getRightView() {
        // we first get a list of lists containing nodes in each level
        // then, only looks for the first elements of those lists
		List<List<String>> eachLevel = this.getLevelWiseCities();
		List<String> mostRight = new ArrayList<String>();
		for (int i = 0; i < eachLevel.size(); i++) {
			List<String> theLevel = eachLevel.get(i);
			mostRight.add(theLevel.get(theLevel.size() - 1));
		}
		return mostRight;
    }
	/**
	 * returns the list of city names which reside in the rightmost of the BST
	 */
	public List<String> getLeftView() {		
        // we first get a list of lists containing nodes in each level
        // then, only looks for the last elements of those lists
		List<List<String>> eachLevel = this.getLevelWiseCities();
		List<String> mostLeft = new ArrayList<String>();
		for (int i = 0; i < eachLevel.size(); i++) {
			List<String> theLevel = eachLevel.get(i);
			mostLeft.add(theLevel.get(0));
		}
		return mostLeft;
    }
	/**
	 * returns the list of city names which reside in the top of the BST
	 */
	public List<String> getTopView() {
        List<String> res = new ArrayList<String>();
        // a class which knows the horizontal distance of nodes from the root
		class QueueObj {
			Node node;
			int hd;
			QueueObj(Node node, int hd) {
				this.node = node;
				this.hd = hd;
			}
		}
        LinkedList<QueueObj> q = new LinkedList<QueueObj>();
        Map<Integer, Node> topViewMap = new TreeMap<Integer, Node>();

        if (this.root == null) {
            return null;
        }
        else {
            q.add(new QueueObj(this.root, 0));
        }
        while (!q.isEmpty()) {
            QueueObj tempNode = q.poll();
            if (!topViewMap.containsKey(tempNode.hd)) {
                topViewMap.put(tempNode.hd, tempNode.node);
            }
            if (tempNode.node.left != null) {
                q.add(new QueueObj(tempNode.node.left, tempNode.hd - 1));
            }
            if (tempNode.node.right != null) {
                q.add(new QueueObj(tempNode.node.right, tempNode.hd + 1));
            }
        }
        for (Map.Entry<Integer, Node> entry: topViewMap.entrySet()) {
            res.add(entry.getValue().city);
        }
        return res;
    }

    /**
	 * returns the list of city names which reside in the bottom of the BST
	 */
    public List<String> getBottomView() { 
        if (root == null) return null; 
        List<String> res = new ArrayList<String>();
  
        int hd = 0; 
        Map<Integer, String> map = new TreeMap<>(); 
        LinkedList<Node> queue = new LinkedList<Node>(); 
  
        this.root.hd = hd; 
        queue.add(this.root); 
  
        while (!queue.isEmpty()) 
        { 
            Node temp = queue.remove(); 
            hd = temp.hd; 

            map.put(hd, temp.city); 
            if (temp.left != null) { 
                temp.left.hd = hd-1; 
                queue.add(temp.left); 
            } 
            if (temp.right != null) { 
                temp.right.hd = hd+1; 
                queue.add(temp.right); 
            } 
        } 
  
        Set<Map.Entry<Integer, String>> set = map.entrySet(); 
        // Make an iterator 
        Iterator<Map.Entry<Integer, String>> iterator = set.iterator(); 
  
        // Traverse the map elements using the iterator. 
        while (iterator.hasNext()) { 
            Map.Entry<Integer,String> me = iterator.next(); 
            res.add(me.getValue()); 
        } 
        return res;
    } 
	
    //============== SPECIAL TREE OPERATIONS ====================//
    /**
	 * calculates the tilt of the BST
	 */
	public int getBSTilt() {
		//TODO: Get the tilt of BST
		return 0;
	}
	/**
	 * returns the list of paths from the root to each leaf
	 */
	public List<String> getAllPaths() {
        List<String> res = new ArrayList<String>();
        // use the helper method below
        this.pathHelper(this.root, "", res);
		
		return res;
    }
    /**
	 * a helper method to find the path 
     * @param Node node: the node we look at 
     * @param String currPath: the path to the node
     * @param List<String> res: a list of all paths
	 */
    private void pathHelper(Node node, String currPath, List<String> res) {
        if (node == null) {
            return;
        }
        // at leaves we add the path to the res list
		if (node.left == null && node.right == null) {
			res.add(currPath + "=>" + node.city);
        }
        else {
            this.pathHelper(node.left, currPath + "=>" + node, res);
            this.pathHelper(node.right, currPath + "=>" + node, res);
        }
	}
    
    /**
	 * flattens the BST like a likedlist
	 */
	public void flattenToLL() {
        // initializes a new root
		Node newRoot = new Node(this.root.city, this.root.population);
        Node temp = newRoot;
        // a stack to conduct the BFS
		List<Node> stack = new ArrayList<Node>();
		stack.add(this.root.right);
        stack.add(this.root.left);
        // starts the BFS
		while (stack.size() != 0) {
			Node currNode = stack.remove(stack.size() - 1);
			temp.right = new Node(currNode.city, currNode.population);
			temp = temp.right;
			if (currNode.right != null) {
				stack.add(currNode.right);
			}
			if (currNode.left != null) {
				stack.add(currNode.left);
			}
		}
		this.root = newRoot;
	}
	
    //============== TREE VISUALIZATION ====================//
    /**
	 * returns the String representation of the BST
	 */
	@Override
	public String toString() {
        List<String> temp = new ArrayList<String>();
        // use the helper method below
        this.toStringHelper(this.root, 'n', 0, temp);
        // concatenate each String
        String res = "";
        for (int i = 0; i < temp.size(); i++) {
            res += temp.get(i);
        }
        
        return res;
    }
    /**
	 * a helper method for implement toString method
     * @param Node node: a node we look at then
     * @param char dir: the next direction to proceed
     * @param int depth: the depth of the node
     * @param List<String> res: a list to contain String info
	 */
    private void toStringHelper(Node node, char dir, int depth, List<String> res) {
        String temp = "|";
        for (int i = 0; i < 5*(depth - 1); i++) {
            temp += "-";
        }
        // operates differently depending on the relationship on the parent
        if (dir == 'R') temp += "R:\n|";
        else if (dir == 'L') temp += "L:\n|";
        // adds hyphens
        for (int i = 0; i < 5*depth; i++) {
            temp += "-";
        }
        // if node is not null add info, otherwise add NULL
        if (node == null) temp += "NULL\n";
        else temp = temp + node.city + ":" + Integer.toString(node.population) + "\n";
        
        res.add(temp);
        // takes recursive actions
        if (node != null) {
            this.toStringHelper(node.left, 'L', depth+1, res);
            this.toStringHelper(node.right, 'R', depth+1, res);
        }
    }
}