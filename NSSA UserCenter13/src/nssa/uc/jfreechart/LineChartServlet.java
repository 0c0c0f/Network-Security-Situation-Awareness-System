package nssa.uc.jfreechart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public LineChartServlet() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String date = request.getParameter("date");

		// ʵ����DefaultCategoryDataset����
		
		LineChartData lineChartData = null;
		try {
			lineChartData = new LineChartData(date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String title = lineChartData.getTitle();
		// ͼ������
		String[] line =lineChartData.getLineNames();
		// ���
		List<Integer> dateList = lineChartData.getdateList();
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		// ʹ��ѭ�������ݼ������������
		for (int i = 0; i < line.length; i++) {
			for (int j = 0; j < dateList.size(); j++) {
				dataSet.addValue(lineChartData.getValues()[j][i], line[i],
						dateList.get(j));
			}
		}
		Font PLOT_FONT = new Font("����", Font.BOLD, 15);
		JFreeChart chart = null;
		chart = ChartFactory.createLineChart(title, // ͼ�����
				"ʱ��", // X�����
				"����", // Y�����
				dataSet, // ��ͼ���ݼ�
				PlotOrientation.VERTICAL, // ���Ʒ���
				true, // ��ʾͼ��
				true, // ���ñ�׼������
				false // �Ƿ����ɳ�����
				);

		// ���ñ�������
		chart.getTitle().setFont(new Font("����", Font.BOLD, 23));
		// ����ͼ���������
		chart.getLegend().setItemFont(new Font("����", Font.BOLD, 15));
		chart.setBackgroundPaint(Color.WHITE);
		// ���ñ���ɫ
		// ��ȡ��ͼ������
		CategoryPlot plot = chart.getCategoryPlot();
		plot.getDomainAxis().setLabelFont(PLOT_FONT);
		// ���ú�������
		plot.getDomainAxis().setTickLabelFont(PLOT_FONT);// ������������ֵ����
		plot.getRangeAxis().setLabelFont(PLOT_FONT); // ������������
		plot.getRangeAxis().setUpperBound(10.0);
		plot.setBackgroundPaint(Color.WHITE); // ���û�ͼ������ɫ
		plot.setRangeGridlinePaint(Color.RED); // ����ˮƽ���򱳾�����ɫ
		plot.setRangeGridlinesVisible(true); // �����Ƿ���ʾˮƽ���򱳾���,Ĭ��ֵΪtrue
		plot.setDomainGridlinePaint(Color.RED); // ���ô�ֱ���򱳾�����ɫ
		plot.setDomainGridlinesVisible(true); // �����Ƿ���ʾ��ֱ���򱳾���,Ĭ��ֵΪfalse
		// ��ȡ���߶���
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
				.getRenderer();

		//renderer.setShapesVisible(true);
		renderer.setDrawOutlines(true);
		renderer.setUseFillPaint(true);
		//renderer.setFillPaint(java.awt.Color.white);
		// renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator(
		// "{2} ",new
		// DecimalFormat()));//ǰ������Ǳ�ǩ��������{0},{1}����{2},{2}������ֵ�������Ǹ������������ݸ�ʽ

		BasicStroke realLine = new BasicStroke(1.6f); // ����ʵ��
		float dashes[] = { 8.0f }; // ������������
		BasicStroke brokenLine = new BasicStroke(1.6f, // ������ϸ
				BasicStroke.CAP_SQUARE, // �˵���
				BasicStroke.JOIN_MITER, // �۵���
				8.f, // �۵㴦��취
				dashes, // ��������
				0.0f); // ����ƫ����
		renderer.setSeriesStroke(1, brokenLine); // �������߻���
		renderer.setSeriesStroke(2, brokenLine); // �������߻���
		renderer.setSeriesStroke(3, realLine); // ����ʵ�߻���
		response.setContentType("image/png");
		//��ͼ�����������ķ�ʽ���ظ��ͻ���
		ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, 500, 320);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
