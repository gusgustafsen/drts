package gov.ed.fsa.drts.security;

public enum SecurityPermissions {

	REQUESTS_CREATE("requests:create"),

	REQUESTS_EDIT_ALL("requests:edit:all"),

	REQUESTS_DELETE("requests:delete"),

	REQUESTS_VIEW_ALL("requests:view:all"),

	REQUESTS_RESOLVE("requests:resolve"),

	REQUESTS_ASSIGN("requests:assign:all"),

	REQUESTS_CLOSE("requests:close"),

	REQUESTS_HOLD("requests:hold"),

	SEARCH_REQUESTS_ALL("search:requests:all"),

	SEARCH_ATTACHMENTS_ALL("search:attachments:all"),

	REPORTS_VIEW_ALL("reports:view:all"),

	REPORTS_EXPORT("reports:export"),

	ACCOUNTS_CREATE("accounts:create"),

	ACCOUNTS_EDIT("accounts:edit");

	private String value;

	private SecurityPermissions(String value) {
		this.value = value;
	}

	public String getStringValue() {
		return value;
	}
}
