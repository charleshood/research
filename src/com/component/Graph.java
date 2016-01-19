package com.component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.file.FileInput;

public class Graph {
	 	private final int V; //顶点个数
	    private int E;		 //边个数
	    private HashSet<Integer> vertices;
//	    private Bag<Edge>[] adj;//邻接数组
	    private LinkedList<Edge>[] adj;
	    static String str = null;//样本文件读出一行单位
	   private LinkedList<Edge> [] adjA;
	    @SuppressWarnings("unchecked")
		public Graph(int V) {
	        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
	        this.V = V;
	        this.E = 0;
	        adj = (LinkedList<Edge>[]) new LinkedList[V];
	        for (int v = 0; v < V; v++) {
	            adj[v] = new LinkedList<Edge>();
	        }
	    }
	    
	    public Graph(int V, int E) {
	        this(V);
	        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
	        for (int i = 0; i < E; i++) {
	            int v = (int) (Math.random() * V);
	            int w = (int) (Math.random() * V);
	            int weight = (int)Math.round(100 * Math.random()) / 100;
	            Edge e = new Edge(v, w, weight);
	            addEdge(e);

	        }
	    }
	   
	    public Graph(FileInput in) {
	    	this(Integer.parseInt((str = in.readData()).substring(0, str.lastIndexOf(" "))));
//	    	this(2000000);
	    	vertices=new HashSet<Integer>();
	        int E = Integer.parseInt(str.substring(str.lastIndexOf(" ")+1,str.length()));
	        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
//	        System.out.println("E:" + E);
	        for (int i = 0; i < E; i++) {
	        	str = in.readData();
	        	String[] strArray = str.split(" ");
	            int v = Integer.parseInt(strArray[0]);
	            int w = Integer.parseInt(strArray[1]);
	            //此处暂时这样处理，等样本数据考虑时间戳时，再将时间戳赋值与weight
	            if(!vertices.contains(v))
	            	vertices.add(v);
	            int weight = 1;
	            Edge edge = new Edge(v,w,weight);
	            addEdge(edge);
	        }
	        in.close();
	    }

	    
	    public Graph(Graph G) {
	    	 this(G.V());
	         this.E = G.E();
	         for (int v = 0; v < G.V(); v++) {
	             Stack<Edge> reverse = new Stack<Edge>();
	             for (Edge e : G.adj[v]) {//载入过程而已
	                 reverse.push(e);
	             }
	             for (Edge e : reverse) {
	                 adj[v].add(e);
	             }
	         }
	    }

	    
	    public int V() {
	        return V;
	    }

	   
	    public int E() {
	        return E;
	    }

	   //添加边
	    public void addEdge(Edge e) {
	        int v = e.either();
	        int w = e.other(v);
	        if (v < 0 || v >= V) throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
	        if (w < 0 || w >= V) throw new IndexOutOfBoundsException("vertex " + w + " is not between 0 and " + (V-1));
	        int vIndex = findInsertIndex(adj[v], w, v, 0, adj[v].size() - 1);
	        int wIndex = findInsertIndex(adj[w], v, w, 0, adj[w].size() - 1);
	        adj[v].add(vIndex, e);
	        adj[w].add(wIndex, e);
	        E++;
	    }


	    public Iterable<Edge> adj(int v) {
	        if (v < 0 || v >= V) throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
	        return adj[v];
	    }

	    public LinkedList<Edge> getAdj(int v){
	    	if (v < 0 || v >= V) throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
	        return adj[v];
	    }
	    
	    public Set<Integer> getvertices(){
	    	return vertices;
	    	
	    }
	    public String toString() {
	        String NEWLINE = System.getProperty("line.separator");
	        StringBuilder s = new StringBuilder();
	        s.append(V + " vertices" + E + " edges" + NEWLINE);
	        for (int v = 0; v < V; v++) {
	            s.append(v + ": ");
	            for (Edge e : adj[v]) {
	                s.append(e.other(v) + "  ");
	            }
	            s.append(NEWLINE);
	        }
	        return s.toString();
	    }
	    
	    private int findInsertIndex(LinkedList<Edge> adjList, int element,int vertex, int beginIndex, int endIndex){
	    	if(adjList.size() == 0)
	    		return 0;
	    	if(element <= adjList.get(beginIndex).other(vertex))
	    		return beginIndex;
	    	if(element >= adjList.get(endIndex).other(vertex))
	    		return (endIndex + 1);
	    	if(beginIndex + 1 == endIndex)
	    		return endIndex;
	    	int index = (beginIndex + endIndex) / 2;
	    	if(element > adjList.get(index).other(vertex))
	    		beginIndex = index;
	    	else
	    		endIndex = index;
	    	return findInsertIndex(adjList, element,vertex, beginIndex, endIndex); 	
	    }
}
