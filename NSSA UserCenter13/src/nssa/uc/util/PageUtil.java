package nssa.uc.util;

import java.util.List;

public class PageUtil {

	/** ��ǰҳ */
	private int pageNo;
	/** ÿҳ������ */
	private int pageSize;
	/** �������� */
	private int allCount;
	/** ��ҳ�� */
	private int allPage;
	/** ÿҳ��ʼ */
	private int recordStart;
	/** ÿҳ���� */
	private int recordEnd;
	/** ��ʾҳ�� */
	private int showCount;
	/** ��ʾ��ʼ */
	private int showStart;
	/** ��ʾ���� */
	private int showEnd;
	/** RUL��ַ */
	private String url;// ҳ�洫������URL��������ʲô�õ��»���ͣ������Ӧ�ö�����������˰ɣ�
	/** ��ʽ���� */
	//private String styleClass;

	/**
	 * ���췽��,ȱʡÿҳ��¼��Ϊ10����ʾҳ����Ϊ9����ʽ����Ϊ
	 * 
	 * @param pageNo
	 *            ��ǰҳ
	 * @param allCount
	 *            �ܼ�¼��
	 * @param url
	 *            ��ҳURL
	 */
	public PageUtil(int pageNo, int allCount, String url) {
		this(pageNo, 10, allCount, 9, url/*, ""*/);
	}

	/**
	 * ���췽��
	 * 
	 * @param pageNo
	 *            ��ǰҳ
	 * @param pageSize
	 *            ÿҳ��¼��
	 * @param allCount
	 *            �ܼ�¼��
	 * @param showCount
	 *            ��ʾҳ����
	 * @param url
	 *            ��ҳURL
	 * @param styleClass
	 *            ��ʽ����
	 */
	public PageUtil(int pageNo, int pageSize, int allCount, int showCount,
			String url/*, String styleClass*/) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.allCount = allCount;
		this.showCount = showCount;
		this.url = url;
		//this.styleClass = styleClass;
		init();
	}

	/**
	 * ��ʼ����ҳ���ֲ���
	 */
	private void init() {
		allPage = allCount % pageSize == 0 ? allCount / pageSize : allCount
				/ pageSize + 1;
		if (pageNo > allPage) {
			pageNo = allPage;
		}
		if (pageNo < 1) {
			pageNo = 1;
		}
		recordStart = (pageNo - 1) * pageSize;
		recordEnd = Math.min(recordStart + pageSize, allCount);
		showCount = Math.min(showCount, allPage);
		if (showCount >= allPage) {
			showStart = 1;
			showEnd = allPage;
		} else {
			if (pageNo <= (showCount + 1) / 2) {
				showStart = 1;
				showEnd = showCount;
			} else if (pageNo > allPage - (showCount + 1) / 2) {
				showStart = allPage - showCount + 1;
				showEnd = allPage;
			} else {
				showStart = pageNo - showCount / 2;
				showEnd = showStart + showCount - 1;
			}
		}
	}

	/**
	 * ��ȡ��ҳ����
	 * 
	 * @param <T>
	 *            ���Ͳ���
	 * @param list
	 *            ��Ҫ��ҳ�������ݼ���
	 * @return
	 */
	public <T> List<T> getPageDate(List<T> list) {
		return list.subList(recordStart, recordEnd);
	}

	/**
	 * ����ҳ��ķ�ҳԪ��(��ҳhtmlҳ���ǩ)
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (pageNo < 2) {
			sb.append("<a href='#'>��ҳ</a>");
			sb.append("<a href='#'>��һҳ</a>");
		} else {
			sb.append("<a href='" + url + "pageNo=1'>��ҳ</a>");
			sb.append("<a href='" + url + "pageNo=" + (pageNo - 1) + "'>��һҳ</a>");
		}
		for (int i = showStart; i < pageNo; i++) {
			sb.append("<a href='" + url + "pageNo=" + i + "'>" + i + "</a>");
		}
		sb.append("<a href='#'>" + pageNo + "</a>");
		for (int i = pageNo + 1; i <= showEnd; i++) {
			sb.append("<a href='" + url + "pageNo=" + i + "'>" + i + "</a>");
		}
		if (pageNo >= allPage) {
			sb.append("<a href='#'>��һҳ</a>");
			sb.append("<a href='#'>βҳ</a>");
		} else {
			sb.append("<a href='" + url + "pageNo=" + (pageNo + 1) + "'>��һҳ</a>");
			sb.append("<a href='" + url + "pageNo=" + allPage + "'>βҳ</a>");
		}
		return sb.toString();
	}

}