package nssa.uc.util;

import java.text.DecimalFormat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.nio.charset.Charset;
import nssa.uc.util.HttpRequest;
import java.io.PrintWriter;

public class Forecast {
	public static double WeekForecast() throws NumberFormatException, SQLException, ClassNotFoundException{
		//�������� //����ע��
		Class.forName("com.mysql.jdbc.Driver");
		// ��������
		Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nssa?useUnicode=true&characterEncoding=UTF-8", "root", "root");
  	Statement stmt=conn.createStatement();
  	
  	
  	ResultSet rs=stmt.executeQuery("select * from week_linchart");
  	
  		/*������������*/
  	double Assets_num[]=new double[7];//Assets_num��ʾ��вָ����
	  	double Risk_num[]=new double[7];//Risk_numl��ʾ����ָ����
	  	double Threaten_num[]=new double[7];//Threaten_num��ʾ�ȶ�ָ����
	  	double Positive_num[]=new double[7];//Positive_num��ʾ����ָ����
  	int i=0;
  	while(rs.next())
  	{
  	
  		String day=rs.getString("day");
  		
  		String Assets=rs.getString("Assets");
  		Assets_num[i]=  Double.parseDouble(Assets);
  		
  		String Risk=rs.getString("Risk");
  		Risk_num[i]= Double.parseDouble(Risk);
  		
  		String Threaten=rs.getString("Threaten");
  		Threaten_num[i]=  Double.parseDouble(Threaten);
  		
  		String Positive=rs.getString("Positive");
  		Positive_num[i]=  Double.parseDouble(Positive);
  		i++;
  	}
  	
  		/*���ְٷֱ�����*/
  	/*���ֺ���*/
	DecimalFormat df = new DecimalFormat( "00.0");
  	double all_sum[]=new double[7];
  	double Assets_sum=0;
  	double Risk_sum=0;
  	double Threaten_sum=0;
  	double Positive_sum=0;
  	double All_sum=0;
  	
  	for(i=0;i<7;i++){
  		
//  		Assets_sum=Assets_sum+Assets_num[i];
//  		Risk_sum=Risk_sum+Risk_num[i];
//  		Threaten_sum=Threaten_sum+Threaten_num[i];
//  		Positive_sum=Positive_sum+Positive_num[i];
//  		//Ȩ�أ�w=(0.40830,0.07781,0.33941,0.17448)
//  		
//  		all_sum[i]=Assets_sum*0.4083+Risk_sum*0.07781+Threaten_sum*0.33941+Positive_sum*0.17448;
  		all_sum[i]=Assets_num[i]*0.4083+Risk_num[i]*0.07781+Threaten_num[i]*0.33941+Positive_num[i]*0.17448;
  		
	  		All_sum=All_sum+all_sum[i];
  	}
  	All_sum=Double.parseDouble(df.format(All_sum/7));
  	
  	stmt.close();
  	conn.close();
	
	return All_sum;
}
}
