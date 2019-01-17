package com.yuanjun.weixindemo;

import java.io.BufferedInputStream;
import java.util.Scanner;

import com.yuanjun.weixindemo.util.TulingApiUtil;

public class TestTuLingRobot {
	 
	public static void main(String[] args) {
	Scanner scanner=new Scanner(new BufferedInputStream(System.in));
	while(scanner.hasNext()){
	String content=scanner.next();
	 System.out.println(TulingApiUtil.getTulingResult(content));
	}
	scanner.close();
	}

}
