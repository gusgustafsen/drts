package gov.ed.fsa.drts.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.time.DateUtils;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import gov.ed.fsa.drts.bean.PageUtil;
import gov.ed.fsa.drts.bean.UserSession;
import gov.ed.fsa.drts.dataaccess.DataLayer;
import gov.ed.fsa.drts.object.Report2OpenClosedBean;

@ManagedBean(name = "report2")
@ViewScoped
public class Report2OpenClosed extends PageUtil implements Serializable {

	private static final long serialVersionUID = -2473015043512535653L;

	private List<Report2OpenClosedBean> report_rows = new ArrayList<Report2OpenClosedBean>();

	private LineChartModel line_model = null;

	@ManagedProperty("#{userSession}")
	UserSession userSession;

	@PostConstruct
	public void init() {
		List<Report2OpenClosedBean> report_rows_from_db = null;
		Report2OpenClosedBean empty_row = null;
		boolean day_found = false;
		Date today = new Date();
		Calendar calendar = new GregorianCalendar();

		try {
			report_rows_from_db = userSession.isAllowedToRunReportsWithAllData()
					? DataLayer.getInstance().getOpenClosedReqReport()
					: DataLayer.getInstance().getOpenClosedReqReport(userSession.getUser().getId());

			if (report_rows_from_db == null) {
				report_rows_from_db = new ArrayList<Report2OpenClosedBean>();
			}

			for (int i = 0; i < 30; i++) {
				today = calendar.getTime();
				day_found = false;

				for (int j = 0; j < report_rows_from_db.size(); j++) {
					System.out.println("comparing: " + today.toString() + " to "
							+ report_rows_from_db.get(j).getReportDate().toString());
					if (DateUtils.isSameDay(today, report_rows_from_db.get(j).getReportDate()) == true) {
						this.report_rows.add(report_rows_from_db.get(j));
						day_found = true;
						break;
					}
				}

				if (day_found == false) {
					empty_row = new Report2OpenClosedBean();
					empty_row.setReportDate(calendar.getTime());
					empty_row.setOpenedRequests(0);
					empty_row.setClosedRequests(0);

					this.report_rows.add(empty_row);
				}

				calendar.add(Calendar.DAY_OF_MONTH, -1);
			}

			createLineModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createLineModel() {
		int max = 10;
		int interval;
		LineChartModel model = new LineChartModel();
		ChartSeries open_requests_series = new ChartSeries();
		ChartSeries closed_requests_series = new ChartSeries();
		Axis y_axis = null;
		List<Report2OpenClosedBean> reversed_report_rows = new ArrayList<Report2OpenClosedBean>(this.report_rows);

		open_requests_series.setLabel("Opened Requests");
		closed_requests_series.setLabel("Closed Requests");

		Collections.reverse(reversed_report_rows);

		for (Report2OpenClosedBean row : reversed_report_rows) {
			open_requests_series.set(row.getChartReportDate(), row.getOpenedRequests());
			closed_requests_series.set(row.getChartReportDate(), row.getClosedRequests());
		}

		model.addSeries(open_requests_series);
		model.addSeries(closed_requests_series);

		this.line_model = model;
		this.line_model.setTitle("Open-Closed Requests By Day");
		this.line_model.setLegendPosition("ne");
		this.line_model.setShowPointLabels(true);
		this.line_model.getAxes().put(AxisType.X, new CategoryAxis("Days of Month"));

		y_axis = this.line_model.getAxis(AxisType.Y);
		y_axis.setLabel("Number of Requests");
		y_axis.setMin(0);
		y_axis.setTickFormat("%#d");

		if (max < 5) {
			y_axis.setTickInterval("1");
		} else {
			for (int i = 10; i <= 50; i += 10) {
				if (max < i) {
					y_axis.setMax(i);

					interval = i / 10;

					y_axis.setTickInterval(Integer.toString(interval));

					break;
				}
			}
		}
	}

	public List<Report2OpenClosedBean> getRows() {
		return this.report_rows;
	}

	public LineChartModel getLineModel() {
		return this.line_model;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}
}
