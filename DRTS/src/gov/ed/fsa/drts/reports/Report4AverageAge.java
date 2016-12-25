package gov.ed.fsa.drts.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import gov.ed.fsa.drts.bean.PageUtil;
import gov.ed.fsa.drts.bean.UserSession;
import gov.ed.fsa.drts.dataaccess.DataLayer;
import gov.ed.fsa.drts.object.Report4AverageAgeBean;

@ManagedBean(name = "report4")
@ViewScoped
public class Report4AverageAge extends PageUtil implements Serializable {

	private static final long serialVersionUID = 8869095910450441159L;

	@ManagedProperty("#{userSession}")
	UserSession userSession;

	private List<Report4AverageAgeBean> report_rows = new ArrayList<Report4AverageAgeBean>();

	private BarChartModel bar_model;

	@PostConstruct
	public void init() {
		Report4AverageAgeBean row = null;

		try {
			for (int i = 0; i < 30; i++) {
				row = userSession.isAllowedToRunReportsWithAllData() ? DataLayer.getInstance().getAverageAgeReport(i)
						: DataLayer.getInstance().getAverageAgeReport(i, userSession.getUser().getId());

				if (row != null) {
					this.report_rows.add(row);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		createBarModel();
	}

	private void createBarModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries avg_series = new ChartSeries();
		Axis x_axis = null;
		Axis y_axis = null;
		int max = 10;
		int interval;
		List<Report4AverageAgeBean> reversed_report_rows = new ArrayList<Report4AverageAgeBean>(this.report_rows);

		avg_series.setLabel("Average Age of Requests");

		Collections.reverse(reversed_report_rows);

		for (Report4AverageAgeBean row : reversed_report_rows) {
			avg_series.set(row.getChartReportDate(), row.getAverageAge());
		}

		model.addSeries(avg_series);

		this.bar_model = model;

		this.bar_model.setTitle("Average Age Of Requests By Day");
		this.bar_model.setLegendPosition("ne");

		x_axis = this.bar_model.getAxis(AxisType.X);
		x_axis.setLabel("Day of Month");

		y_axis = this.bar_model.getAxis(AxisType.Y);
		y_axis.setLabel("Average Age");
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

	public BarChartModel getBarModel() {
		return this.bar_model;
	}

	public List<Report4AverageAgeBean> getRows() {
		return this.report_rows;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}
}
