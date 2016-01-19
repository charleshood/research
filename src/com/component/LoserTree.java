package com.component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LoserTree {
	private class Array{
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int num;
		int pos;
	};
	private ArrayList<Integer> merge_list = new ArrayList<Integer>() ;//多路归并之后的结果，采用链表列表方便插入
	private ArrayList<Array> array_list = new ArrayList<LoserTree.Array>();	//按照不同路对所有节点进行保存
	private int[] loser_tree;	//内部比较节点和冠军节点，ls[0]为冠军节点，ls[1]-ls-[k-1]为内部比较节点
	private int[] external;		//记录叶子节点当前的值
	private int k; 				// 归并的路数，即败者树的叶子节点个数
	private int count;
	public  LoserTree(List<ArrayList<Integer>> k_hop_adj_list){
		k = k_hop_adj_list.size();
		loser_tree = new int[k];
		external = new int[k+1];
		count = 0;
		for(int i = 0; i < k; i++){
			Array A = new Array();
			A.arr = k_hop_adj_list.get(i);
			A.num = k_hop_adj_list.get(i).size();
			count = count + A.num;
			A.pos = 0;
			array_list.add(A);
		}
//		System.out.println("array_list " + array_list.size());
	}
	
	public void merge(){
		int p = 0;
		for(int i = 0; i < k; i++){
			p = array_list.get(i).pos;
			if(array_list.get(i).arr.size() == 0)
				System.out.println("null!!!");
			external[i] = array_list.get(i).arr.get(p);
			array_list.get(i).pos ++;
		}
		createLoserTree();
		int No = 0;
		while(No < count){
			p = loser_tree[0];
			if(p < k)
				merge_list.add(external[p]);
			No ++;
			if(array_list.get(p).pos >= array_list.get(p).num)
				external[p] = Integer.MAX_VALUE;
			else{
				external[p] = array_list.get(p).arr.get(array_list.get(p).pos);
				array_list.get(p).pos ++;
			}
			adjust(p);
		}
	}
	
	private void createLoserTree(){
		external[k] = -1;//初始化的时候败者树的每个节点均为最小值，以此来构造初始败者树
		for(int i = 0; i < k; i++) loser_tree[i] = k;
		for(int i = k-1; i >= 0; i--) adjust(i);
		
	}
	
	private void adjust(int s){
		int t = (s+k) / 2;
		int temp ;
		while(t > 0){
			if(external[s] > external[loser_tree[t]]){
				temp = s;
				s = loser_tree[t];
				loser_tree[t] = temp;
			}
			t = t / 2;
		}
		loser_tree[0] = s;
	}

	public ArrayList<Integer> getMerge_list() {
		return merge_list;
	}

	public void setMerge_list(ArrayList<Integer> merge_list) {
		this.merge_list = merge_list;
	}

	public ArrayList<Array> getArray_list() {
		return array_list;
	}

	public void setArray_list(ArrayList<Array> array_list) {
		this.array_list = array_list;
	}

	public int[] getLoser_tree() {
		return loser_tree;
	}

	public void setLoser_tree(int[] loser_tree) {
		this.loser_tree = loser_tree;
	}
	
	
}
