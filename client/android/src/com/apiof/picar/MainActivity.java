package com.apiof.picar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 禁止屏幕锁屏，也可以用View.setKeepScreenOn(boolean)来实现
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		InitHandler();

		//TestNetWork();
	}

	public void DisplayToast(String str)
	{
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		switch (KeyCode) {
		case KeyEvent.KEYCODE_0:
			DisplayToast("按下数字0");
			break;
		}
		return super.onKeyDown(KeyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode,KeyEvent event){
		switch(keyCode){
		case KeyEvent.KEYCODE_0:
			DisplayToast("松开数字0");
			break;
		}
		return super.onKeyUp(keyCode, event);
	}

	public void TestNetWork() {

		String strUrl = "http://192.168.1.171:2000";
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		URL url = null;
		try {
			url = new URL(strUrl);
			System.out.println(url.getPort());
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			InputStreamReader in = new InputStreamReader(
					urlConn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String result = "";
			String readerLine = null;
			while ((readerLine = br.readLine()) != null) {
				result += readerLine;
			}
			in.close();
			urlConn.disconnect();

			System.out.println("r:" + result);
			TextView textView = (TextView) this.findViewById(R.id.textView2);
			textView.setText(result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("确认退出吗？")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“确认”后的操作
						MainActivity.this.finish();
					}
				})
				.setNegativeButton("返回", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“返回”后的操作,这里不设置没有任何操作
					}
				}).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private String intToIp(int i) {

		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}

	public String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return null;
	}

	public void InitHandler() {
		// 获取wifi服务
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		// 判断wifi是否开启
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		String ip = intToIp(ipAddress);
		TextView txt1 = (TextView) findViewById(R.id.textView1);
		txt1.setText(txt1.getText() + "\r\nLocal IP: " + ip);

		((Button)findViewById(R.id.btnStop)).setOnClickListener(stopHandler);
		((Button)findViewById(R.id.btnForward)).setOnClickListener(upHandler);
		((Button)findViewById(R.id.btnBackward)).setOnClickListener(downHandler);
		((Button)findViewById(R.id.btnTurnLeft)).setOnClickListener(leftHandler);
		((Button)findViewById(R.id.btnTurnRight)).setOnClickListener(rightHandler);
	}

	private void Trun(String id) {
		TextView txt = (TextView) findViewById(R.id.textView2);
		try {
			String requestUrl = "http://192.168.1.171:2000/ctl";
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("id", id);
			String result = HttpUtils.submitPostData(requestUrl,
					requestParams, "utf-8");

			txt.setText("turn to: "+result);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private OnClickListener stopHandler=new OnClickListener() {
		public void onClick(View v) {
			Trun("t_stop");
		}
	};
	private OnClickListener leftHandler=new OnClickListener() {
		public void onClick(View v) {
			Trun("t_left");
		}
	};
	private OnClickListener rightHandler=new OnClickListener() {
		public void onClick(View v) {
			Trun("t_right");
		}
	};
	private OnClickListener upHandler=new OnClickListener() {
		public void onClick(View v) {
			Trun("t_up");
		}
	};
	private OnClickListener downHandler=new OnClickListener() {
		public void onClick(View v) {
			Trun("t_down");
		}
	};

}
