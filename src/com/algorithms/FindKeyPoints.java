package com.algorithms;
//������ʲô�������ǹ���������ͼ�����������ǹ����ļ����������������ô���Ժ�ȫͼ�������бȽ���
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
	public static final int N = 2;//�û�������ĸ���
	public static final int V = 1000;//�����û�������ķ�Χ ����=N
	public static final int S = 25;//���ѡȡ��K_HOP���Ͻ�����ĸ���
	public static final int K = 3;//����
	public static final int ThreadNum =5;
//	public static final int max = INFINITY;
	
	public static void main(String args[]){
		long bfs_start;
		long bfs_finish;
		
		long bfs_duration0;
		long bfs_duration1;
		//�ļ�·������ʱû�п���ʱ������ı���ʽΪһ���������֣���ʾ�������㣬�磺1 2����ʾ��1���2���ӣ�
		//�ı���һ���������ֱ�ʾ����ͼ�Ķ�������ͱߵĸ�������250 1234����ʾ��250�����㣬1234����
		String filePath = "a.txt";//�Լ����ݵ����������ı�����·�������޸�
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
		//����һ�������¼k_hop_list��ÿ��������ڽ�����ĵ�ǰ�ƶ�index
		ArrayList<Integer> key_set_list = new ArrayList<Integer>();//����ռ�
        int k_hop_index[] = new int [k_hop_list.size()];
        for(int r = 0; r < k_hop_index.length; r++)
        	k_hop_index[r] = 0;
        
        ArrayList<Integer> adj0List = k_hop_list.get(0);//k_hop_list.get(0)
        for(int s = 0; s < adj0List.size(); s++){
        	//��¼�ж��ٸ��ڽ������а�����ǰ����
        	int flag = 0;
        	for(int t = 1; t < k_hop_list.size(); t++){
        		//��ȡ��ǰ�Ƚ϶�����ڽ�����
        		ArrayList<Integer> adjList = k_hop_list.get(t);//ȥ��1��khop��Ϣ
        		//���������һ��������ڽ������Լ��������Ƚϣ���ô�Ϳ�����ֹ�Ƚϣ�ʣ��û�бȽϵĶ�������ǹؼ��˼���
        		if(k_hop_index[t] >= adjList.size()){//?index����K
        			finish = true;
        			break;
        		}
        		//���ϴαȽϵ�������ʼ��������һ���û����������3�����Ͻ��жԱ� ֪���ҵ��ȵ�ǰԪ�ش��Ԫ��ֹͣ
        		for(int u = k_hop_index[t]; u < adjList.size(); u++){//��Ȣһ���㣬����adjlist
        			if(adj0List.get(s) > adjList.get(u))//�Ƚ�0[s],t=1[u]
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
	//�������n��С��v�ĵ�
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
	
	//���ÿ��K�������еĵ����BFS�㷨��ļ����Ƿ���������û�����㼯�ϣ�user_vertices_list��
}

