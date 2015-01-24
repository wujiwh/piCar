package com.apiof.picar;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.util.Log;

public class WebUtils {

	
	private static final int HTTP_200 = 200;
	private static final String TAG_GET = "Web GET";
	private static final String TAG_POST = "Web POST";


	// Get方式请求
	public static void requestByGet(String path) throws Exception {
		URL url = new URL(path);
		// 打开一个HttpURLConnection连接
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		// 设置连接超时时间
		urlConn.setConnectTimeout(5 * 1000);
		// 开始连接
		urlConn.connect();
		// 判断请求是否成功
		if (urlConn.getResponseCode() == HTTP_200) {
			// 获取返回的数据
			byte[] data = readStream(urlConn.getInputStream());
			Log.i(TAG_GET, "Get方式请求成功，返回数据如下：");
			Log.i(TAG_GET, new String(data, "UTF-8"));
		} else {
			Log.i(TAG_GET, "Get方式请求失败");
		}
		// 关闭连接
		urlConn.disconnect();
	}
	
	
	// Post方式请求
	public static void requestByPost(String path) throws Throwable {
		// 请求的参数转换为byte数组
		String params = "id=" + URLEncoder.encode("t_stop", "UTF-8");
		byte[] postData = params.getBytes();
		// 新建一个URL对象
		URL url = new URL(path);
		// 打开一个HttpURLConnection连接
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		// 设置连接超时时间
		urlConn.setConnectTimeout(5 * 10000);
		// Post请求必须设置允许输出
		urlConn.setDoOutput(true);
		// Post请求不能使用缓存
		urlConn.setUseCaches(false);
		// 设置为Post请求
		urlConn.setRequestMethod("POST");
		urlConn.setInstanceFollowRedirects(true);
		// 配置请求Content-Type
		urlConn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencode");
		// 开始连接
		urlConn.connect();
		// 发送请求参数
		DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
		dos.write(postData);
		dos.flush();
		dos.close();
		// 判断请求是否成功
		if (urlConn.getResponseCode() == HTTP_200) {
			// 获取返回的数据
			byte[] data = readStream(urlConn.getInputStream());
			Log.i(TAG_POST, "Post请求方式成功，返回数据如下：");
			Log.i(TAG_POST, new String(data, "UTF-8"));
		} else {
			Log.i(TAG_POST, "Post方式请求失败");
		}
	}
	
	
	/**
	 * @功能 读取流
	 * @param inStream
	 * @return 字节数组
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception {
		int count = 0;
		while (count == 0) {
			count = inStream.available();
		}
		byte[] b = new byte[count];
		inStream.read(b);
		return b;
	}
}
