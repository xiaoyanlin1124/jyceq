package com.jy.common.device;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

public class IpAdrressUtil {
	/**
     * 获取Ip地址
     * @param request
     * @return
     */
	public static  String getIpAdrress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }
	
	/**
	 * 获取地址
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public  String getAddress(String params) throws Exception {
		//淘宝IP库地址
		String path = "http://ip.taobao.com/service/getIpInfo.php";

		String returnStr = this.getRs(path, "ip=" +params, "utf-8");

		JSONObject json = null;

		if (returnStr != null) {

			json = new JSONObject(returnStr);

			if ("0".equals(json.get("code").toString())) {

				StringBuffer buffer = new StringBuffer();

				 buffer.append(decodeUnicode(json.optJSONObject("data").getString("country")));//国家

				 buffer.append(decodeUnicode(json.optJSONObject("data").getString("area")));//地区

				buffer.append(decodeUnicode(json.optJSONObject("data")
						.getString("region")));// 省份

				buffer.append(decodeUnicode(json.optJSONObject("data")
						.getString("city")));// 市区

				buffer.append(decodeUnicode(json.optJSONObject("data")
						.getString("county")));// 地区

				buffer.append(decodeUnicode(json.optJSONObject("data")
						.getString("isp")));// ISP公司

				//System.out.println(buffer.toString());

				return buffer.toString().replaceAll("XX", "");

			} else {
				return null;//失败
			}
		}
		return null;
	}

	/**
	 * 从url获取结果
	 * 
	 * @param path
	 * @param params
	 * @param encoding
	 * @return
	 */
	public String getRs(String path, String params, String encoding) {

		URL url = null;

		HttpURLConnection connection = null;

		try {

			url = new URL(path);

			connection = (HttpURLConnection) url.openConnection();// 新建连接实例

			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫S?

			connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫S?

			connection.setDoInput(true);// 是否打开输出S? true|false

			connection.setDoOutput(true);// 是否打开输入流true|false

			connection.setRequestMethod("POST");// 提交方法POST|GET

			connection.setUseCaches(false);// 是否缓存true|false

			connection.connect();// 打开连接端口

			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());

			out.writeBytes(params);

			out.flush();

			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), encoding));

			StringBuffer buffer = new StringBuffer();

			String line = "";

			while ((line = reader.readLine()) != null) {

				buffer.append(line);

			}

			reader.close();

			return buffer.toString();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			connection.disconnect();// 关闭连接

		}

		return null;
	}

	/**
	 * 字符转码
	 * 
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {

		char aChar;

		int len = theString.length();

		StringBuffer buffer = new StringBuffer(len);

		for (int i = 0; i < len;) {

			aChar = theString.charAt(i++);

			if (aChar == '\\') {

				aChar = theString.charAt(i++);

				if (aChar == 'u') {

					int val = 0;

					for (int j = 0; j < 4; j++) {

						aChar = theString.charAt(i++);

						switch (aChar) {

						case '0':

						case '1':

						case '2':

						case '3':

						case '4':

						case '5':

						case '6':

						case '7':

						case '8':

						case '9':

							val = (val << 4) + aChar - '0';

							break;

						case 'a':

						case 'b':

						case 'c':

						case 'd':

						case 'e':

						case 'f':

							val = (val << 4) + 10 + aChar - 'a';

							break;

						case 'A':

						case 'B':

						case 'C':

						case 'D':

						case 'E':

						case 'F':

							val = (val << 4) + 10 + aChar - 'A';

							break;

						default:

							throw new IllegalArgumentException(

							"Malformed      encoding.");
						}

					}

					buffer.append((char) val);

				} else {

					if (aChar == 't') {

						aChar = '\t';
					}

					if (aChar == 'r') {

						aChar = '\r';
					}

					if (aChar == 'n') {

						aChar = '\n';
					}

					if (aChar == 'f') {

						aChar = '\f';

					}

					buffer.append(aChar);
				}

			} else {

				buffer.append(aChar);

			}

		}

		return buffer.toString();

	}
}
	
