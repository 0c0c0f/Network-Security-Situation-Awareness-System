package nssa.uc.jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYBarDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class BarChartServletOne extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public BarChartServletOne() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String msg = request.getParameter("msg");
		String date = request.getParameter("date");
		
		//�����ݿ��в�ѯ����
		BarChartDataOne barChartData = null;
		try {
			barChartData = new BarChartDataOne(msg, date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//�����ݷ������ݼ���
		XYSeries xyseries = new XYSeries(msg);
		for(int i = 0; i < barChartData.getValues().length; i++) {
			xyseries.add(barChartData.getDateList().get(i).intValue(), barChartData.getValues()[i]);
		}
		XYSeriesCollection xyseriescollection = new XYSeriesCollection();
		xyseriescollection.addSeries(xyseries);
		IntervalXYDataset dataset = new XYBarDataset(xyseriescollection, 0.90000000000000002D);
		//����������ʽ
		StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
		//���ñ�������  
		standardChartTheme.setExtraLargeFont(new Font("����",Font.BOLD,20));  
		//����ͼ��������  
		standardChartTheme.setRegularFont(new Font("����",Font.PLAIN,15));  
		//�������������  
		standardChartTheme.setLargeFont(new Font("����",Font.PLAIN,15));  
		//Ӧ��������ʽ  
		ChartFactory.setChartTheme(standardChartTheme); 
		//����ͼ�ζ���
		JFreeChart jfreechart = ChartFactory.createXYBarChart(barChartData.getTitle(), "ʱ��", false, "����", dataset, PlotOrientation.VERTICAL, true, false, false);
		//���ñ���Ϊ��ɫ
		jfreechart.setBackgroundPaint(Color.WHITE);
		//���ͼ���������
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		//����������󱳾�Ϊ��ɫ
		xyplot.setBackgroundPaint(Color.LIGHT_GRAY);
		//����������������ߵ�Ϊ��ɫ
		xyplot.setDomainGridlinePaint(Color.WHITE);
		xyplot.setRangeGridlinePaint(Color.WHITE);
		//���x�����
		ValueAxis catagoryAxis = xyplot.getDomainAxis();
		//����������ݱ�ǩ�Ƿ��Զ�ȷ����Ĭ��Ϊtrue�� 
		catagoryAxis.setAutoTickUnitSelection(false);
		//����������ݱ�ǩ
		catagoryAxis.setStandardTickUnits(catagoryAxis.getStandardTickUnits());
		//1Ϊһ�������λ 
		catagoryAxis.setAutoRangeMinimumSize(1.0);
		//���y�����
		ValueAxis rangeAxis = xyplot.getRangeAxis();
		//����������ݱ�ǩ�Ƿ��Զ�ȷ����Ĭ��Ϊtrue�� 
		rangeAxis.setAutoTickUnitSelection(false);
		//����������ݱ�ǩ
		rangeAxis.setStandardTickUnits(rangeAxis.getStandardTickUnits());
		//�������ϵ���ʾ��Сֵ
		rangeAxis.setLowerBound(0.0);
		//1Ϊһ�������λ 
		rangeAxis.setAutoRangeMinimumSize(1.0);
		//����ʾͼ�ζ���
		XYBarRenderer xybarrenderer = (XYBarRenderer) xyplot.getRenderer();
		//��������Ϊ��ɫ
		xybarrenderer.setSeriesPaint(0, Color.BLUE);
		//���ò���ʾ�߿���
		xybarrenderer.setDrawBarOutline(false);
		//���÷�������ΪͼƬ
		response.setContentType("image/png");
		//��ͼ�����������ķ�ʽ���ظ��ͻ���
		ChartUtilities.writeChartAsPNG(response.getOutputStream(), jfreechart, 500, 320);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
