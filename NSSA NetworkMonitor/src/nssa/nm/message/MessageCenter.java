package nssa.nm.message;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nssa.nm.util.Base64Util;
import nssa.nm.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.sun.http.HttpRequest;

public class MessageCenter {
	public static String HostAddress="http://127.0.0.1:8080/";
	public static void sendMessage(NMMessage message) throws JSONException {
		String jsonstr = JsonUtil.getJsonStringFromObject(message);
		String base64str = Base64Util.encodeStr(jsonstr);
		System.out.println("message:    "+message);
		System.out.println("jsonstr:    "+jsonstr);
		System.out.println("base64str:    "+base64str);
		
		Map result = toMap(jsonstr);
//		System.out.println("clientIP=  \n "+result.get("clientIP"));
		
		msg=(String) result.get("msg");
		
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time=format.format(date);
		
		sip=(String) result.get("sip");
		dip=(String) result.get("dip");
		sport=(String) result.get("sport");
		dport=(String) result.get("dport");
		packet=(String) result.get("packet");
		
		//***************
		InsertDataToDB();
		
		 JSONObject obj= new JSONObject();
			
			obj.put("msg", (String) result.get("msg"));
			obj.put("time", time);
			obj.put("sip", (String) result.get("sip"));
			obj.put("packet", (String) result.get("packet"));
			obj.put("dip", (String) result.get("dip"));
			obj.put("sport", (String) result.get("sport"));
			obj.put("dport",(String) result.get("dport"));
			obj.put("base64str",base64str);
			String objStr=obj.toString();
			
			//**************************
//			HttpRequest.sendPost(HostAddress+"TestXinanServer/Test", "json="+objStr);
			
			System.out.println("*******************json="+objStr);
			
//			if(!first)
//			{
//				TextToFile("D:/a.txt", objStr);
//			}
		
		
		//*******************
//		MessageSocket socket = new MessageSocket();
//		socket.connect();
//		socket.send(base64str);
	}
	
	
	static boolean first;
	public static void TextToFile(final String strFilename, final String strBuffer)  
	  {  
	    try  
	    {      
	      // �����ļ�����  
	      File fileText = new File(strFilename);  
	      // ���ļ�д�����д����Ϣ  
	      FileWriter fileWriter = new FileWriter(fileText);  
	  
	      // д�ļ�        
	      fileWriter.write(strBuffer);  
	      // �ر�  
	      fileWriter.close();  
	    }  
	    catch (IOException e)  
	    {  
	      //  
	      e.printStackTrace();  
	    }
	
	  }
	
	
	
	public static Map toMap(String jsonString) throws JSONException {
	
        JSONObject jsonObject = new JSONObject(jsonString);
        Map result = new HashMap();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String value = null;
        
        Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time=format.format(date);
       
        
        
        

        while (iterator.hasNext()) {

            key = (String) iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);

        }
        return result;
	}
	
	
	//static String id;
	static String msg;
	static String time;
	static String sip;
	static String dip;
	static String sport;
	static String dport;
	static String packet;
		
	public static void InsertDataToDB()
	{
		try
		{
			// �������� //����ע��
			Class.forName("com.mysql.jdbc.Driver");
			// ��������
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nssa?useUnicode=true&characterEncoding=UTF-8", "root", "root");
			// ���������ݿ⽻��ָ�
			Statement stmt = (Statement) conn.createStatement();
			String sql = "insert into securityevent (msg,time,sip,packet,dip,sport,dport) values ('"+msg+"','"+time+"','"+sip+"','"+packet+"','"+dip+"','"+sport+"','"+dport+"')";
			// String sql =
			// "update test_table1 set score = 78.1 where name = 'Think in C++' ";
			// String sql = "delete from test_table1 where id = 1";
			stmt.execute(sql);

			// �ر� �� �ͷ���Դ
			stmt.close();
			conn.close();

		} catch (Exception e)
		{

			//e.printStackTrace();
			System.out.println("sql***e**********");
		}
	}
	
}
