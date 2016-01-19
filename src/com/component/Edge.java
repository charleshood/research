package com.component;

public class Edge {
	private final int v;
	private final int w;
	private final int weight;
	
	public Edge(int v,int w,int weight){
		if (v < 0) throw new IndexOutOfBoundsException("Vertex names must be nonnegative integers");
        if (w < 0) throw new IndexOutOfBoundsException("Vertex names must be nonnegative integers");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        this.v = v;
        this.w = w;
        this.weight = weight;
	}
	
	public int either(){
		return v;
	}
	
	public int other(int vertex) {
        if(vertex == v) 
        	return w;
        else if(vertex == w) 
        	return v;
        else 
        	throw new IllegalArgumentException("Illegal endpoint");
    }
	
	public int weight(){
		return weight;
	}
	
	public String toString(){
		return String.format("%d-%d %.2f", v, w, weight);
	}
	
	 public int compareTo(Edge that) {
	        if(this.weight() < that.weight()) 
	        	return -1;
	        else if(this.weight() > that.weight()) 
	        	return 1;
	        else                                    
	        	return  0;
	 }
}
