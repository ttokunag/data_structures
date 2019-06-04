package com.cse.ds;

/**
 * @author Tomoya Tokunaga(mailto: ttokunag@ucsd.edu)
 * About this file:
 * This file implements Binary Search Tree nodes
 */
public class Node {
    String city;
    int population;
    // horizontal distance from the node to
    // implement getTopView
    int hd = Integer.MAX_VALUE;
    
    Node left;
    Node right;
    
    /**
     * a constructor for a node
     */
    public Node() {
		  
	  }
    
    /**
     * a constructor for a node
     * @param String city: a name of the city
     * @param int population: the number of population of the city
     */
    public Node(String city, int population) {
      this.city = city;
      this.population = population;
    }
    
}