package com.algorithms;



import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

import com.component.Edge;
import com.component.Graph;
import com.component.LoserTree;
import com.component.Queue;
import com.file.FileInput;

public class BreadthFirstPath {
	private static final int INFINITY = Integer.MAX_VALUE;
	private boolean[] marked;//marked是干嘛的？
	private int[] distTo;//distTo是干嘛的？
	private ArrayList<Integer> k_hop = null;//k_hop是干嘛的？
	private List<ArrayList<Integer>> k_hop_list = null;//？
	private ArrayList<Integer> intersect_list = null;//？
	private Graph graph = null;
	private int K;
	public BreadthFirstPath(Graph G, int s, int k){
		this.K = k;
		this.graph = G;
		marked = new boolean[G.V()];
		distTo = new int[G.V()];
		k_hop = new ArrayList<Integer>();
		bfs(G,s,k);	
		}
	
	public BreadthFirstPath(Graph G, List<Integer> source, int k){//构建一个对象，初始化一系列向量，调用bfs进行遍历。
		this.K = k;
		this.graph = G;
		marked = new boolean[G.V()];
		distTo = new int[G.V()];
		k_hop = new ArrayList<Integer>();//初始化的工作。
		k_hop_list = new ArrayList<ArrayList<Integer>>();//一个顶点的k_hop集合。
		intersect_list = new ArrayList<Integer>();
		bfs(G,source,k);	
	}
	
	//求出一个顶点的k_hop集合
	private void bfs(Graph G, int s, int k){
		 Queue<Integer> q = new Queue<Integer>();
	        for (int v = 0; v < G.V(); v++) distTo[v] = INFINITY;
	        distTo[s] = 0;
	        marked[s] = true;
	        q.enqueue(s);
	        int v = s;
	        while (!q.isEmpty()) {
	            v = q.dequeue();
	            if(distTo[v] >= k)
	            	break;
	            for (Edge e : G.adj(v)) {
	                if (!marked[e.other(v)]) {
	                	k_hop.add(e.other(v));//添加到khop
	                    distTo[e.other(v)] = distTo[v] + 1;
	                    marked[e.other(v)] = true;
	                    q.enqueue(e.other(v));
	                }
	            }
	        }

	}
	private void bfs(Graph G, int s, int t, int k){//初始化使用的是这一个函数！
//		List<Integer> kHop = new ArrayList<Integer>();
		Queue<Integer> qs = new Queue<Integer>();//新建了两个队列
		Queue<Integer> qt = new Queue<Integer>();
		for (int v = 0; v < G.V(); v++) distTo[v] = INFINITY;//循环，枚举每一个顶点。
		qs.enqueue(s);
		qt.enqueue(t);
		distTo[s] = 0;//distTo[x]就是一个x到s的距离嘛
		marked[s] = true;//marked[x]:x被访问！
//		int v = s;
		int d = 1;
		long start;
		long finish;
		long sorttime =0;
		long comparetime = 0;
		start = new Date().getTime();
		while(d <= K){//约定K 的（处理的点组的距离）
			System.out.println(d);//处理距离为d的组   
			List<ArrayList<Integer>> sMergeList = new ArrayList<ArrayList<Integer>>();//求出s点的一个list
			List<ArrayList<Integer>> tMergeList = new ArrayList<ArrayList<Integer>>();//求出v点的一个list
			
			while(!qs.isEmpty()){
				ArrayList<Integer> sList = new ArrayList<Integer>();
				int ss = qs.dequeue();
				marked[ss] = true;
//				System.out.println("ss:" + ss);
				for(Edge e : graph.adj(ss)){///添加一个邻接表而已
					if(!marked[e.other(ss)])
						sList.add(e.other(ss));//添加到slist中，把1-HOP,2-HOP,3HOP的点都添加到slist中,采集数据
				}
				if(sList.size() > 0)
				sMergeList.add(sList);
			}
			while(!qt.isEmpty()){//求出两个点的集合病排好序。
				ArrayList<Integer> tList = new ArrayList<Integer>();
				int tt = qt.dequeue();
				marked[tt] = true;
//				System.out.println("tt:" + tt);
				for(Edge e : graph.adj(tt)){
					if(!marked[e.other(tt)])
						tList.add(e.other(tt));
				}
				if(tList.size() > 0)
				tMergeList.add(tList);
			}	
			
			LoserTree sLoserTree = new LoserTree(sMergeList);
			System.out.println("merge before:"+sMergeList);
			sLoserTree.merge();
			System.out.println("merge after:"+sLoserTree.getMerge_list());
			LoserTree tLoserTree = new LoserTree(tMergeList);
			tLoserTree.merge();	
			System.out.println("slist compare before :"+sLoserTree.getMerge_list());
			System.out.println("tlist compare before:"+tLoserTree.getMerge_list());
			compareTwoList(sLoserTree.getMerge_list(), tLoserTree.getMerge_list(), qs, qt, d);
			System.out.println("compare after:"+tLoserTree.getMerge_list());
		
			d++;
		}
		finish = new Date().getTime();
		System.out.println("sort:" + (finish - start) + "ms");
//		System.out.println("compare:" + comparetime + "ms");
	}
	
	private void compareTwoList(List<Integer> sList, List<Integer> tList, Queue<Integer> qs, Queue<Integer> qt , int k){
//		List<Integer> sLoopList = new ArrayList<Integer>(sList);
//		List<Integer> tLoopList = new ArrayList<Integer>(tList);
//		List<Integer> intersectList = new ArrayList<Integer>();
		///重点是这个，算法前的marked情况如何？仅仅只有marked初始点而已
		Queue<Integer> q = new Queue<Integer>();//新建一个队列
		System.out.println("debug: marked");
		for (Integer tv : graph.getvertices()) {
			if(marked[tv]==true)
				System.out.print(tv+",");
			
		}
		System.out.println();
		int i = 0 ;
		int j = 0;//从i,j开始比较
		for(; i < sList.size() && j < tList.size(); ){
			if(sList.get(i) > tList.get(j) && !marked[tList.get(j)]){//试图取小,标记已访问，入各自队伍：称为待处理队，问什么时候出队？
				if(k < this.K)
				qt.enqueue(tList.get(j));//入队
				marked[tList.get(j)] = true;
				j++;
				System.out.println("debug:i,j="+sList.get(i)+","+tList.get(j-1));
			}else if(sList.get(i) < tList.get(j) && !marked[sList.get(i)]){
				if(k < this.K)
				qs.enqueue(sList.get(i));//插入qs
				marked[sList.get(i)] = true;//插入小的那个元素
				i ++;
			}else{
				if(k < this.K){
					q.enqueue(sList.get(i));
					distTo[sList.get(i)] = k;
				}
//				marked[tList.get(j)] = true;
//				marked[sList.get(i)] = true;
				k_hop.add(sList.get(i));
				i ++;
				j ++;
			}
		}
		if(k < this.K){
			if(i > j){//吧没有处理完的数据一起写入
				while(i < sList.size() && !marked[sList.get(i)]){
					qs.enqueue(sList.get(i));
					
					marked[sList.get(i)] = true;
					i++;
				}
			}else{
				while(j < tList.size() && !marked[tList.get(j)]){
					qs.enqueue(tList.get(j));
					marked[tList.get(j)] = true;
					j++;
				}
			}
		}
//		System.out.println("qs: "+qs.size()+" qt:"+qt.size());
		if(k >= K)
			return ;
//		System.out.println(q.size());
		while(!q.isEmpty()){
			int v = q.dequeue();
			marked[v] = true;
			if(distTo[v] >= K)
				break;
			for(Edge e : graph.adj(v)){
				int w = e.other(v);
				if(!marked[w]){
					k_hop.add(w);
					distTo[w] = distTo[v] + 1;
					marked[w] = true;
					q.enqueue(w);
				}
			}
		}
	}
	
	//求出S个顶点的k_hop集合的交集，source个顶点的问题构造索引而不是全图构建索引。
	private void bfs(Graph G, List<Integer> source, int k){
		for(int i = 0; i < source.size(); i++){
			long start=new Date().getTime();
			List<Integer> k_hop_set = new ArrayList<Integer>();//存储经过排序的3跳顶点
			List<ArrayList<Integer>> k_hop_adj_list = new ArrayList<ArrayList<Integer>>();//把每个顶点的邻接数组都存储下来，包括k=1 k =2
			Queue<Integer> q = new Queue<Integer>();
	        for (int v = 0; v < G.V(); v++) distTo[v] = INFINITY;
	        System.out.println(i+":开始：点数"+source.get(i)+"\n");
		        distTo[source.get(i)] = 0;
		        marked[source.get(i)] = true;
		        q.enqueue(source.get(i));
		        int v = source.get(i);//取出一个点
		        while (!q.isEmpty()) {
		        	ArrayList<Integer> k_hop_adj = new ArrayList<Integer>();//生成一个khopadj
		            v = q.dequeue();
		            if(distTo[v] >= k)//先求出距离，如果超过了K就不再添加
		            	break;
		            for (Edge e : G.adj(v)) {
		            	
		                if (!marked[e.other(v)]) {
		                	k_hop_adj.add(e.other(v));
		                    distTo[e.other(v)] = distTo[v] + 1;
		                    marked[e.other(v)] = true;
		                    q.enqueue(e.other(v));
		                }
		            } 
		            if(k_hop_adj.size() > 0){
		            	k_hop_adj_list.add(k_hop_adj);
		                System.out.println("debug:k_hop_adj"+k_hop_adj);
		            }
		        }
	        //对选择的用户顶点的3跳集合进行败者树归并排序 排序的路数等于1+n+n^2
		        LoserTree loserTree = new LoserTree(k_hop_adj_list);
		        loserTree.merge();
		        k_hop_set = loserTree.getMerge_list();
		        marked = new boolean[G.V()];
		        System.out.println("debug:keyhopset:"+k_hop_set);
		        k_hop_list.add((ArrayList<Integer>) k_hop_set);//khopset就是一个集合装着每个人的东西
		        long end=new Date().getTime();
		        System.out.println("cindextimefor"+i+":"+(end-start));
		        System.out.println("debug end");
	       
		}
	}
	 public static void main(String[] args){
		    long bfs_start;
			long bfs_finish;
			
			long bfs_duration0;
			long bfs_duration1;
			//文件路径，暂时没有考虑时间戳，文本格式为一行两个数字，表示两个顶点，如：1 2，表示点1与点2连接，
			//文本第一行两个数字表示整个图的顶点个数和边的个数，如250 1234，表示有250个顶点，1234条边
			String filePath = "a.txt";//自己根据电脑上数据文本放置路径进行修改
			bfs_start = new Date().getTime();
			FileInput in = new FileInput(filePath);
			Graph G = new Graph(in);
			List<Integer> source = new ArrayList<Integer>();
			for(int i=0;i<1000;i++)
				source.add(i);
			bfs_start = new Date().getTime();
			BreadthFirstPath bfs = new BreadthFirstPath(G, source, 5);
			bfs_finish= new Date().getTime();
			System.out.println("timeforall:"+(bfs_finish-bfs_start));
			int indexsize=0;
			for(int i=0;i<1000;i++){
				indexsize+=bfs.k_hop_list.get(i).size();
			}
			System.out.println(indexsize);
			
		 
	 }

	private static void addSource(List<Integer> source, Set<Integer> getvertices) {
		// TODO Auto-generated method stub
		for (Integer integer : getvertices) {
			source.add(integer);					
		}
		
	}

	public boolean[] getMarked() {
		return marked;
	}

	public void setMarked(boolean[] marked) {
		this.marked = marked;
	}

	public int[] getDistTo() {
		return distTo;
	}

	public void setDistTo(int[] distTo) {
		this.distTo = distTo;
	}

	public ArrayList<Integer> getK_hop() {
		return k_hop;
	}

	public void setK_hop(ArrayList<Integer> k_hop) {
		this.k_hop = k_hop;
	}

	public List<ArrayList<Integer>> getK_hop_list() {
		return k_hop_list;
	}

	public void setK_hop_list(List<ArrayList<Integer>> k_hop_list) {
		this.k_hop_list = k_hop_list;
	}

	public ArrayList<Integer> getIntersect_list() {
		return intersect_list;
	}
	public void setIntersect_list(ArrayList<Integer> intersect_list) {
		this.intersect_list = intersect_list;
	}
	
	
	
}
