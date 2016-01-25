package com.algorithms;
//问题是什么？他不是构建的整个图的索引，而是构建的几个定点的索引，怎么可以和全图索引进行比较呢
/**
 * @author mushuai
 * @time 2014-03-10
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.component.Edge;
import com.component.Graph;
import com.file.FileInput;

public class FindKeyPoints {
	public static final int N = 2;//用户给定点的个数
	public static final int V = 1000;//产生用户给定点的范围 即《=N
	public static final int S = 25;//随机选取求K_HOP集合交集点的个数
	public static final int K = 3;//跳数
	public static final int ThreadNum =5;
//	public static final int max = INFINITY;
	
	public static void main(String args[]){
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
		bfs_finish = new Date().getTime();
		System.out.println("memory: " + (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
		bfs_duration1 = bfs_finish - bfs_start;
		System.out.println("Graph Constructed!!" );

		List<Integer> source = new ArrayList<Integer>();
		source = random_vertices(N, V);
		System.out.println(source);
		bfs_start = new Date().getTime();
		BreadthFirstPath bfs = new BreadthFirstPath(G, source, K);
		bfs_finish = new Date().getTime();
		bfs_duration0 = bfs_finish - bfs_start;
		System.out.println("fifalmemory: " + (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));

//		
//		
		System.out.println("index : " + bfs_duration1 + "ms");
		System.out.println("time : " + bfs_duration0 + "ms");
		System.out.println("key_set : " + bfs.getK_hop().size());//+ key_set.toString()
		
		
	}

	public static ArrayList<Integer> find_key_set_sort(List<ArrayList<Integer>> k_hop_list){
		boolean finish = false;
		//申请一个数组记录k_hop_list中每个顶点的邻接数组的当前移动index
		ArrayList<Integer> key_set_list = new ArrayList<Integer>();//结果空间
        int k_hop_index[] = new int [k_hop_list.size()];
        for(int r = 0; r < k_hop_index.length; r++)
        	k_hop_index[r] = 0;
        
        ArrayList<Integer> adj0List = k_hop_list.get(0);//k_hop_list.get(0)
        for(int s = 0; s < adj0List.size(); s++){
        	//记录有多少个邻接数组中包含当前顶点
        	int flag = 0;
        	for(int t = 1; t < k_hop_list.size(); t++){
        		//获取当前比较顶点的邻接数组
        		ArrayList<Integer> adjList = k_hop_list.get(t);//去除1的khop信息
        		//如果其中有一个顶点的邻接数组以及被遍历比较，那么就可以终止比较，剩下没有比较的顶点均不是关键人集合
        		if(k_hop_index[t] >= adjList.size()){//?index大于K
        			finish = true;
        			break;
        		}
        		//从上次比较的索引开始，与其中一个用户给定顶点的3跳集合进行对比 知道找到比当前元素大的元素停止
        		for(int u = k_hop_index[t]; u < adjList.size(); u++){//再娶一个点，遍历adjlist
        			if(adj0List.get(s) > adjList.get(u))//比较0[s],t=1[u]
        				k_hop_index[t] ++;
       				else if(adj0List.get(s) < adjList.get(u))
       					break;
       				else{
       					flag ++;
       					k_hop_index[t] ++;
       				}      			
        		}
        	}
        	if(finish)
        		break;
        	if(flag == k_hop_list.size() - 1)
        		key_set_list.add(adj0List.get(s));
        }
        return key_set_list;
	}
	//随机产生n个小于v的点
	public static ArrayList<Integer> random_vertices(int n,int v){
		HashSet<Integer> hs = new HashSet<Integer>();
		
		while(true){
			int num = (int) (Math.random()*v);
			hs.add(num);
			if(hs.size() == n)
				break;
		}
		ArrayList<Integer> list = new ArrayList<Integer>(hs);
		return list;
	}
	
	//检查每个K跳交集中的点进行BFS算法后的集合是否包含整个用户给点点集合（user_vertices_list）
}

