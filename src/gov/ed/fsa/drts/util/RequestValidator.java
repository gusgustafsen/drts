package gov.ed.fsa.drts.util;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

public class RequestValidator implements PhaseListener {

	private static final long serialVersionUID = -2282664829406618457L;

	@Override
	public void afterPhase(PhaseEvent phase_event)
	{
		
	}

	@Override
	public void beforePhase(PhaseEvent phase_event) 
	{
		FacesContext faces_context = phase_event.getFacesContext();
		HttpServletResponse response = (HttpServletResponse) faces_context.getExternalContext().getResponse();

		/*
		 * Cross-Frame Scripting (XFS) vulnerability can allow an attacker to
		 * load the vulnerable application inside an HTML iframe tag. Fixed by
		 * setting the X-Frame-Options header to SAMEORIGIN- The page can be
		 * framed by another page only if it belongs to the same origin as the
		 * page being framed
		 */

		response.addHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
	}

	@Override
	public PhaseId getPhaseId()
	{
		return PhaseId.RESTORE_VIEW;
	}

}
