package gov.ed.fsa.drts.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.PieChartModel;

import gov.ed.fsa.drts.bean.PageUtil;
import gov.ed.fsa.drts.dataaccess.DataLayer;
import gov.ed.fsa.drts.object.Report3AssignedSMEBean;

@ManagedBean(name = "report3")
@ViewScoped
public class Report3AssignedSME extends PageUtil implements Serializable {

	private static final long serialVersionUID = 1662244336903193328L;

	private List<Report3AssignedSMEBean> report_rows = new ArrayList<Report3AssignedSMEBean>();

	private PieChartModel pie_model = null;

	@PostConstruct
	public void init() {
		try {
			this.report_rows = userSession.isAllowedToRunReportsWithAllData()
					? DataLayer.getInstance().getAssignedSmeReport()
					: DataLayer.getInstance().getAssignedSmeReport(userSession.getUser().getId());

			if (this.report_rows != null) {
				createPieModel();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createPieModel() {
		this.pie_model = new PieChartModel();

		this.pie_model.setTitle("Total Requests Assigned to SMEs");
		this.pie_model.setLegendPosition("w");
		this.pie_model.setShowDataLabels(true);
		this.pie_model.setDiameter(350);
		this.pie_model.setDataFormat("percent");

		for (Report3AssignedSMEBean row : this.report_rows) {
			this.pie_model.set(row.getName(), row.getTotalCount());
		}
	}

	public PieChartModel getPieModel() {
		return this.pie_model;
	}

	public List<Report3AssignedSMEBean> getRows() {
		return this.report_rows;
	}
}