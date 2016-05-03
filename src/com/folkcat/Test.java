package com.folkcat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
	public static void main(String[] args) {
		String url = "http://localhost/test/postandget.php";

		Map<String, String> get = new HashMap<String, String>();
		get.put("get", "ggggggggggggggg");

		Map<String, String> post = new HashMap<String, String>();
		post.put("post", "ppppppppppppppp");

		Map<String, String> header = new HashMap<String, String>();
		header.put("TESTHEAD", "hhhhhhhhhhhhh");

		TinyHttpClient tinyHttp = new TinyHttpClient("utf-8");

		/*
		 * String getStr=tinyHttp.sendGet(url, get, false);
		 * System.out.println(getStr);
		 * 
		 * String getStr2=tinyHttp.sendGet(url, get, false, null);
		 * System.out.println(getStr2);
		 * 
		 * String postStr=tinyHttp.sendPost(url, post, false);
		 * System.out.println(postStr);
		 * 
		 * String postStr2=tinyHttp.sendPost(url, post, false, header);
		 * System.out.println(postStr2);
		 * 
		 * 
		 * printMap(tinyHttp.getGetHeaders(url, get,false,null));
		 * 
		 * printMap(tinyHttp.getPostHeaders(url, post,false,header));
		 */
		
		Map<String,List<String>> retHead=new HashMap<String,List<String>>();
		List<String> list=new ArrayList<String>();
		list.add("Bar");
		retHead.put("Foo", list);
		String content=tinyHttp.getGetHeadersAndContent(url, post, false, header, retHead);
		System.out.println(content);
		printMap(retHead);
	}

	public static void printMap(Map<String, List<String>> map) {
		System.out.println("遍历Map开始==============");
		for (String key : map.keySet()) {
			System.out.println(key + " : " + map.get(key));
		}
		System.out.println("遍历Map结束==============");
	}
	/*
	 * http://localhost/test/postandget.php 内容 <?php error_reporting(0);
	 * echo "//POST:".$_POST['post']; echo "//GET:".$_GET['get']; echo
	 * "//HEAD:".$_SERVER['HTTP_TESTHEAD']; ?>
	 */

}
