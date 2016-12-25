package gov.ed.fsa.drts.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import gov.ed.fsa.drts.util.ApplicationProperties;

public class DRTSRealm extends AuthorizingRealm {

	private static final Logger logger = Logger.getLogger(DRTSRealm.class);

	public static final String DEFAULT_USERNAME = "user";
	public static final String DEFAULT_PASSWORD = "user";
	private static final String REALM_NAME = "DRTSRealm";

	private String username;
	private char[] password;

	private Map<String, Set<WildcardPermission>> group_permissions = null;

	private Set<String> roles;

	protected DRTSRealm() {
	}

	public DRTSRealm(final Set<String> roles) {
		this.roles = roles;

		this.setAuthorizationCachingEnabled(false);
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal_collection) {
		if (group_permissions == null) {
			group_permissions = new HashMap<String, Set<WildcardPermission>>();

			for (final String groupPermission : ApplicationProperties.SHIRO_PERMISSIONS_BY_GROUP.getListValue()) {
				final String[] group_and_permissions = groupPermission.split("=");

				final String group = group_and_permissions[0];
				final String permissions_string = group_and_permissions[1];

				final String[] permissions = permissions_string.split(",");

				final Set<WildcardPermission> permissions_set = new HashSet<WildcardPermission>();

				for (final String permission : permissions) {
					permissions_set.add(new WildcardPermission(permission));
				}

				group_permissions.put(group, permissions_set);
			}
		}

		final Set<Permission> permissions = new HashSet<Permission>();

		for (final String role : roles) {
			if (group_permissions.containsKey(role)) {
				permissions.addAll(group_permissions.get(role));
			}
		}

		final SimpleAuthorizationInfo auth_info = new SimpleAuthorizationInfo(roles);
		auth_info.addObjectPermissions(permissions);

		return auth_info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authentication_token)
			throws AuthenticationException {
		username = DEFAULT_USERNAME;
		password = DEFAULT_PASSWORD.toCharArray();

		if (authentication_token instanceof UsernamePasswordToken) {
			final UsernamePasswordToken username_password_token = (UsernamePasswordToken) authentication_token;

			username = username_password_token.getUsername();
			password = username_password_token.getPassword();
		}

		return new SimpleAuthenticationInfo(username, password, REALM_NAME);
	}
}
