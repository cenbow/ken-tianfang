package com.tianfang.home.controller;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

public class test {
	public static void main(String[] args) {
		File ff = new File("E:\\111111");
		File[] tempList = ff.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			try {
				Thumbnails.of(tempList[i]).size(1024, 768).toFile(tempList[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("文     件："+tempList[i]);
		}
	}
}
