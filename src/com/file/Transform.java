package com.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Transform {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner in=new Scanner(new File("D:/a.txt"));
		FileWriter fw=new FileWriter(new File("D:/data/b.txt"));
		BufferedWriter bw=new BufferedWriter(fw);
		int source,des;
		int flag=0;
		List edges=new ArrayList<Integer>();
		while(in.hasNext()){
			source=in.nextInt();
			des=in.nextInt();
			if(source==flag){
				edges.add(des);
			}
			if(source!=flag){
				bw.append(flag+" "+edges.size());
				Iterator it=edges.iterator();
				while(it.hasNext()){
					bw.append(" "+it.next());
				}
				bw.append("\n");
				flag=source;
				edges.clear();
				edges.add(des);
			}
		}
		in.close();
		bw.close();
		fw.close();
		System.out.println("×ª»»Íê³É");

	}

}
