package com.algorithms;

import com.component.Graph;
import com.file.FileInput;

public class LKreach {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath = "a.txt";
		FileInput in = new FileInput(filePath);
		Graph G = new Graph(in);
		BreadthFirstPath bfs = new BreadthFirstPath(G, source, K);

	}

}
