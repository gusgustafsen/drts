package gov.ed.fsa.drts.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import gov.ed.fsa.drts.dataaccess.DataLayer;
import gov.ed.fsa.drts.object.DataRequest;
import gov.ed.fsa.drts.table.RequestTablePaginator;
import gov.ed.fsa.drts.table.TablePaginator;

@ManagedBean(name = "requestTable")
@SessionScoped
public class RequestTable extends PageUtil implements Serializable {

	private static final long serialVersionUID = -859173943018328800L;

	private TablePaginator table_paginator = null;

	public TablePaginator getTable1() {
		if (this.table_paginator == null) {
			this.table_paginator = new RequestTablePaginator(1, this.userSession);
		}

		return this.table_paginator;
	}

	public TablePaginator getTable2() {
		if (this.table_paginator == null) {
			this.table_paginator = new RequestTablePaginator(2, this.userSession);
		}

		return this.table_paginator;
	}

	public TablePaginator getTable3() {
		if (this.table_paginator == null) {
			this.table_paginator = new RequestTablePaginator(3, this.userSession);
		}

		return this.table_paginator;
	}

	public TablePaginator getTable4() {
		if (this.table_paginator == null) {
			this.table_paginator = new RequestTablePaginator(4, this.userSession);
		}

		return this.table_paginator;
	}

	public TablePaginator getTable5() {
		if (this.table_paginator == null) {
			if (userSession.isAllowedToViewAllRequests()) {
				this.table_paginator = new RequestTablePaginator(4, this.userSession);
			}

			if (userSession.isAllowedToViewMyRequests()) {
				this.table_paginator = new RequestTablePaginator(2, this.userSession);
			}

		}

		return this.table_paginator;
	}

	public String goToTask(DataRequest dataRequest) {
		if (dataRequest.getCurrentTaskFormKey() == null) {
			return goToOpenNew(dataRequest);
		}

		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsDataRequest", dataRequest);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dataRequest", null);

		return dataRequest.getCurrentTaskFormKey() + "?faces-redirect=true";
	}

	public String viewRequest(DataRequest dataRequest) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsDataRequest", dataRequest);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dataRequest", null);

		return "/dataRequest/view.htm?faces-redirect=true";
	}

	public String goToOpenNew(DataRequest dataRequest) {
		int next_iteration = 2;

		try {
			next_iteration = DataLayer.getInstance().getNextIteration(dataRequest.getId());

			/*
			 * DataRequest new_request = new DataRequest();
			 * new_request.initialize(this.userSession.getUser().getId());
			 * 
			 * new_request.setIteration(next_iteration);
			 * new_request.setDescription(dataRequest.getDescription());
			 * new_request.setParentId(dataRequest.getId());
			 */
			dataRequest.initialize(true);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsDataRequest", dataRequest);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dataRequest", null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// return "/dataRequest/view.htm?faces-redirect=true";
		return "/dataRequest/newRequest.htm?faces-redirect=true";
	}
}