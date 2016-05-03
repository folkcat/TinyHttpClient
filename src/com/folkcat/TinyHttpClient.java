package com.folkcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by Tamas on 2015/12/23. 无任何外部依赖
 *
 * Http工具类，适合细粒度的Http操作。
 */
public class TinyHttpClient {
	private static String ENCODE = "utf-8";
	
	private String urlEncode(String text) {
		try {
			return java.net.URLEncoder.encode(text, ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public TinyHttpClient(String encode) {
		ENCODE = encode;
	}

	/*
	 * 发送Get请求，返回数据
	 */
	public String sendGet(String url, Map<String, String> params,
			boolean isAutoDirect) {
		StringBuilder result =new StringBuilder();
		BufferedReader in = null;
		try {
			String urlNameString;
			if (params == null) {
				urlNameString = url;
			} else {
				urlNameString = url + "?";
				for (String key : params.keySet()) {
					urlNameString = urlNameString
							+ key
							+ "="
							+ urlEncode(params
									.get(key))
							+ "&";
				}
			}
			URL realUrl = new URL(urlNameString);
			HttpURLConnection connection = (HttpURLConnection) realUrl
					.openConnection();
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setInstanceFollowRedirects(isAutoDirect);// 是否自动跳转
			connection.connect();
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), ENCODE));
			String line;
			while ((line = in.readLine()) != null) {
				result = result.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result.toString();
	}

	private String sendGet(String url, Map<String, String> params,
			boolean isAutoDirect, Map<String, String> headers) {

		StringBuilder result = new StringBuilder();
		BufferedReader in = null;
		try {
			String urlNameString;
			if (params == null) {
				urlNameString = url;
			} else {
				urlNameString = url + "?";
				for (String key : params.keySet()) {
					urlNameString = urlNameString
							+ key
							+ "="
							+ urlEncode(params
									.get(key))
							+ "&";
				}
			}
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl
					.openConnection();
			if (headers == null) {
				connection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
			} else {
				for (String key : headers.keySet()) {
					connection.setRequestProperty(key,
							headers.get(key));
				}
			}
			connection.setInstanceFollowRedirects(isAutoDirect);// 是否自动跳转
			connection.connect();
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), ENCODE));
			String line;
			while ((line = in.readLine()) != null) {
				result = result.append(line);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

	/*
	 * 发送Post请求，返回数据 params可为空
	 */
	public String sendPost(String url, Map<String, String> params,
			boolean isAutoDirect) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) realUrl
					.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setInstanceFollowRedirects(isAutoDirect);
			if (params != null) {
				out = new PrintWriter(conn.getOutputStream());
				Object[] keyArr = params.keySet().toArray();
				String andChar;
				for (int i = 0; i < keyArr.length; i++) {
					if (i == keyArr.length - 1) {
						andChar = "";
					} else {
						andChar = "&";
					}
					out.print(keyArr[i]
							+ "="
							+ urlEncode(params
									.get(keyArr[i]))
							+ andChar);
				}
				out.flush();
			}
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result = result.append(line) ;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	}

	/*
	 * 发送Post请求，返回数据 params可为空
	 */
	public String sendPost(String url, Map<String, String> params,
			boolean isAutoDirect, Map<String, String> headers) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) realUrl
					.openConnection();
			if (headers == null) {
				conn.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
			} else {
				for (String key : headers.keySet()) {
					conn.setRequestProperty(key,
							headers.get(key));
				}
			}
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setInstanceFollowRedirects(isAutoDirect);
			if (params != null) {
				out = new PrintWriter(conn.getOutputStream());
				Object[] keyArr = params.keySet().toArray();
				String andChar;
				for (int i = 0; i < keyArr.length; i++) {
					if (i == keyArr.length - 1) {
						andChar = "";
					} else {
						andChar = "&";
					}
					out.print(keyArr[i]
							+ "="
							+ urlEncode(params
									.get(keyArr[i]))
							+ andChar);
				}
				out.flush();
			}
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), ENCODE));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (IOException e) {
			System.out.println("未知错误 pos0");
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/*
	 * 发送Get数据，只返回头部
	 */
	public Map<String, List<String>> getGetHeaders(String url,
			Map<String, String> params, boolean isAutoDirect,
			Map<String, String> headers) {
		Map<String, List<String>> retHeaders = null;
		try {
			String urlNameString;
			if (params == null) {
				urlNameString = url;
			} else {
				urlNameString = url + "?";
				for (String key : params.keySet()) {
					urlNameString = urlNameString
							+ key
							+ "="
							+ urlEncode(params
									.get(key))
							+ "&";
				}
			}
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl
					.openConnection();
			if (headers == null) {
				connection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
			} else {
				for (String key : headers.keySet()) {
					connection.setRequestProperty(key,
							headers.get(key));
				}
			}
			connection.setInstanceFollowRedirects(isAutoDirect);// 不自动跳转
			connection.connect();
			retHeaders = connection.getHeaderFields();
		} catch (IOException e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {

		}
		return retHeaders;
	}

	/*
	 * 发送Post数据，只返回头部
	 */
	public Map<String, List<String>> getPostHeaders(String url,
			Map<String, String> params, boolean isAutoDirect,
			Map<String, String> _headers) {
		PrintWriter out = null;
		Map<String, List<String>> retHeaders = null;
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) realUrl
					.openConnection();
			if (_headers == null) {
				conn.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
			} else {
				for (String key : _headers.keySet()) {
					conn.setRequestProperty(key,
							_headers.get(key));
				}
			}
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setInstanceFollowRedirects(isAutoDirect);// 是否自动跳转
			conn.connect();
			if (params != null) {
				out = new PrintWriter(conn.getOutputStream());
				Object[] keyArr = params.keySet().toArray();
				String andChar;
				for (int i = 0; i < keyArr.length; i++) {
					if (i == keyArr.length - 1) {
						andChar = "";
					} else {
						andChar = "&";
					}
					out.print(keyArr[i]
							+ "="
							+ urlEncode(params
									.get(keyArr[i]))
							+ andChar);
				}
				out.flush();
			}
			retHeaders = conn.getHeaderFields();
		} catch (IOException e) {
			System.out.println("未知错误 pos1");
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}
		return retHeaders;
	}

	/*
	 * 发送Post数据，返回头部和内容
	 * 注意参数_retHeaders不能为空，而且最好size为0；
	 */
	public String getPostHeadersAndContent(String url,
			Map<String, String> params, boolean isAutoDirect,
			Map<String, String> _headers,Map<String, List<String>> _retHeaders) {
		Map<String, List<String>> retHeaders = null;
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuilder strBuilder = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) realUrl
					.openConnection();
			if (_headers == null) {
				conn.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
			} else {
				for (String key : _headers.keySet()) {
					conn.setRequestProperty(key,
							_headers.get(key));
				}
			}
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setInstanceFollowRedirects(isAutoDirect);// 是否自动跳转
			conn.connect();
			if (params != null) {
				out = new PrintWriter(conn.getOutputStream());
				Object[] keyArr = params.keySet().toArray();
				String andChar;
				for (int i = 0; i < keyArr.length; i++) {
					if (i == keyArr.length - 1) {
						andChar = "";
					} else {
						andChar = "&";
					}
					out.print(keyArr[i]
							+ "="
							+ urlEncode(params
									.get(keyArr[i]))
							+ andChar);
				}
				out.flush();
			}
			retHeaders = conn.getHeaderFields();
			
			for (String key : retHeaders.keySet()) {
				_retHeaders.put(key,
						retHeaders.get(key));
			}
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), ENCODE));
			String line;
			while ((line = in.readLine()) != null) {
				strBuilder.append(line);
			}
		} catch (IOException e) {
			System.out.println("未知错误 pos1");
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}

		return strBuilder.toString();
	}
	
	/*
	 * 发送Get数据，返回头部和内容
	 * 注意参数_retHeaders不能为空，而且最好size为0；
	 */
	public String getGetHeadersAndContent(String _url,
			Map<String, String> _params, boolean _isAutoDirect,
			Map<String, String> _headers,Map<String, List<String>> _retHeaders) {
		Map<String, List<String>> retHeaders = null;
		BufferedReader in = null;
		StringBuilder strBuilder = new StringBuilder();
		try {
			String urlNameString;
			if (_params == null) {
				urlNameString = _url;
			} else {
				urlNameString = _url + "?";
				for (String key : _params.keySet()) {
					urlNameString = urlNameString
							+ key
							+ "="
							+ urlEncode(_params
									.get(key))
							+ "&";
				}
			}
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl
					.openConnection();
			if (_headers == null) {
				connection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
			} else {
				for (String key : _headers.keySet()) {
					connection.setRequestProperty(key,
							_headers.get(key));
				}
			}
			connection.setInstanceFollowRedirects(_isAutoDirect);// 不自动跳转
			connection.connect();
			retHeaders = connection.getHeaderFields();
		
			
			for (String key : retHeaders.keySet()) {
				_retHeaders.put(key,
						retHeaders.get(key));
			}
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), ENCODE));
			String line;
			while ((line = in.readLine()) != null) {
				strBuilder.append(line);
			}
		} catch (IOException e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {

		}
		return strBuilder.toString();
	}
}
