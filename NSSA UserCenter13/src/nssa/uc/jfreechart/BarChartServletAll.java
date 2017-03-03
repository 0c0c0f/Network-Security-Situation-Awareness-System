package nssa.uc.jfreechart;

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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChartServletAll extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public BarChartServletAll() {
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
		
		String date = request.getParameter("date");
		
		//�����ݿ��в�ѯ����
		BarChartDataAll barChartData = null;
		try {
			barChartData = new BarChartDataAll(date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//�����ݷ������ݼ���
		DefaultCategoryDataset defaultDataset = new DefaultCategoryDataset();
		for (int i = 0; i < barChartData.getDateList().size(); i++) {
			String dateCatagory = barChartData.getDateList().get(i).toString();
			for (int j = 0; j < barChartData.getMsgs().length; j++) {
				String nameCatagory = barChartData.getMsgs()[j];
				int value = barChartData.getValues()[i][j];
				defaultDataset.addValue(value, nameCatagory, dateCatagory);
			}
		}
		//��ȡ���ݼ�����
		CategoryDataset dataset = defaultDataset;
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
		JFreeChart jfreechart = ChartFactory.createBarChart3D(barChartData.getTitle(), "ʱ��", "����", dataset, PlotOrientation.VERTICAL, true, true, false);
		//���ͼ���������
		CategoryPlot categoryPlot = (CategoryPlot)jfreechart.getPlot();
		//���������߿ɼ�
		categoryPlot.setDomainGridlinesVisible(true);
		//���x�����
		CategoryAxis categoryAxis = categoryPlot.getDomainAxis();
		//����x����ʾ�ķ������Ƶ���ʾλ��
		categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.39269908169872414D));
		categoryAxis.setCategoryMargin(0.1D);
		//���y�����
		ValueAxis rangeAxis = categoryPlot.getRangeAxis();
		//����������ݱ�ǩ�Ƿ��Զ�ȷ����Ĭ��Ϊtrue�� 
		rangeAxis.setAutoTickUnitSelection(false);
		//����������ݱ�ǩ
		rangeAxis.setStandardTickUnits(rangeAxis.getStandardTickUnits());
		//�������ϵ���ʾ��Сֵ
		rangeAxis.setLowerBound(0.0);
		//1Ϊһ�������λ 
		rangeAxis.setAutoRangeMinimumSize(1.0);
		//�����ʾͼ�ζ���
		BarRenderer3D barRenderer3d = (BarRenderer3D)categoryPlot.getRenderer();
		//���ò���ʾ�߿���
		barRenderer3d.setDrawBarOutline(false);
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
