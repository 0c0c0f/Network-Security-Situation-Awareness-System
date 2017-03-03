package nssa.uc.jfreechart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nssa.uc.dao.SecurityEventDao;
import nssa.uc.util.DateUtil;
import nssa.uc.vo.SecurityEvent;


public class BarChartDataOne {
	
	private String title;
	private List<Integer> dateList;
	private int[] values;

	public BarChartDataOne(String msg, String date) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("���");
		if(date.equals("today")) {
			sb.append("һ��");
		}
		if(date.equals("lastweek")) {
			sb.append("һ��");
		}
		sb.append(msg);
		sb.append("���ͳ��");
		title = sb.toString();
		dateList = new ArrayList<Integer>();
		createData(msg, date);
	}

	private void createData(String msg, String date) throws SQLException {
		//����Dao
		SecurityEventDao securityEventDao = new SecurityEventDao();
		//����securityEventList
		List<SecurityEvent> securityEventList = null;
		//���һ��
		if(date.equals("today")){
			//��ȡsecurityEventList
			securityEventList = securityEventDao.listTodayByMsg(msg);
			//��ʩ��������
			for(int i = 0; i < securityEventList.size(); i++) {
				if(!dateList.contains(DateUtil.getHour(securityEventList.get(i).getTime()))) {
					dateList.add(DateUtil.getHour(securityEventList.get(i).getTime()));
				}
			}
			//��ʼ��ͳ����Ŀ
			values = new int[dateList.size()];
			//ͳ��
			for(int n = 0; n < securityEventList.size(); n++) {
				for(int i = 0; i < dateList.size(); i++) {
					if(DateUtil.getHour(securityEventList.get(n).getTime()) == dateList.get(i).intValue()) {
						values[i]++;
					}
				}
			}
		}
		//���һ��
		if(date.equals("lastweek")) {
			//��ȡsecurityEventList
			securityEventList = securityEventDao.listLastWeekByMsg(msg);
			//��ʩ��������
			for(int i = 0; i < securityEventList.size(); i++) {
				if(!dateList.contains(DateUtil.getDay(securityEventList.get(i).getTime()))) {
					dateList.add(DateUtil.getDay(securityEventList.get(i).getTime()));
				}
			}
			//��ʼ��ͳ����Ŀ
			values = new int[dateList.size()];
			//ͳ��
			for(int n = 0; n < securityEventList.size(); n++) {
				for(int i = 0; i < dateList.size(); i++) {
					if(DateUtil.getDay(securityEventList.get(n).getTime()) == dateList.get(i).intValue()) {
						values[i]++;
					}
				}
			}
		}
	}

	public String getTitle() {
		return title;
	}

	public List<Integer> getDateList() {
		return dateList;
	}

	public int[] getValues() {
		return values;
	}
	
}
