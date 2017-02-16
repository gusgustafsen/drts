/**
 * CreateAccountRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.ed.fsa.drts.AimsDrtsx;

public class CreateAccountRequestType implements java.io.Serializable {
	private java.lang.String userId;

	private java.lang.String firstName;

	private java.lang.String lastName;

	private java.lang.String emailAddress;

	private java.lang.String group;

	public CreateAccountRequestType() {
	}

	public CreateAccountRequestType(java.lang.String userId, java.lang.String firstName, java.lang.String lastName,
			java.lang.String emailAddress, java.lang.String group) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.group = group;
	}

	/**
	 * Gets the userId value for this CreateAccountRequestType.
	 * 
	 * @return userId
	 */
	public java.lang.String getUserId() {
		return userId;
	}

	/**
	 * Sets the userId value for this CreateAccountRequestType.
	 * 
	 * @param userId
	 */
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the firstName value for this CreateAccountRequestType.
	 * 
	 * @return firstName
	 */
	public java.lang.String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the firstName value for this CreateAccountRequestType.
	 * 
	 * @param firstName
	 */
	public void setFirstName(java.lang.String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the lastName value for this CreateAccountRequestType.
	 * 
	 * @return lastName
	 */
	public java.lang.String getLastName() {
		return lastName;
	}

	/**
	 * Sets the lastName value for this CreateAccountRequestType.
	 * 
	 * @param lastName
	 */
	public void setLastName(java.lang.String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the emailAddress value for this CreateAccountRequestType.
	 * 
	 * @return emailAddress
	 */
	public java.lang.String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the emailAddress value for this CreateAccountRequestType.
	 * 
	 * @param emailAddress
	 */
	public void setEmailAddress(java.lang.String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Gets the group value for this CreateAccountRequestType.
	 * 
	 * @return group
	 */
	public java.lang.String getGroup() {
		return group;
	}

	/**
	 * Sets the group value for this CreateAccountRequestType.
	 * 
	 * @param group
	 */
	public void setGroup(java.lang.String group) {
		this.group = group;
	}

	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof CreateAccountRequestType))
			return false;
		CreateAccountRequestType other = (CreateAccountRequestType) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return(__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.userId == null && other.getUserId() == null)
						|| (this.userId != null && this.userId.equals(other.getUserId())))
				&& ((this.firstName == null && other.getFirstName() == null)
						|| (this.firstName != null && this.firstName.equals(other.getFirstName())))
				&& ((this.lastName == null && other.getLastName() == null)
						|| (this.lastName != null && this.lastName.equals(other.getLastName())))
				&& ((this.emailAddress == null && other.getEmailAddress() == null)
						|| (this.emailAddress != null && this.emailAddress.equals(other.getEmailAddress())))
				&& ((this.group == null && other.getGroup() == null)
						|| (this.group != null && this.group.equals(other.getGroup())));
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	@Override
	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		if (getUserId() != null) {
			_hashCode += getUserId().hashCode();
		}
		if (getFirstName() != null) {
			_hashCode += getFirstName().hashCode();
		}
		if (getLastName() != null) {
			_hashCode += getLastName().hashCode();
		}
		if (getEmailAddress() != null) {
			_hashCode += getEmailAddress().hashCode();
		}
		if (getGroup() != null) {
			_hashCode += getGroup().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc =
			new org.apache.axis.description.TypeDesc(CreateAccountRequestType.class, true);

	static {
		typeDesc.setXmlType(
				new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "CreateAccountRequestType"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("userId");
		elemField.setXmlName(new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "UserId"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("firstName");
		elemField.setXmlName(new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "FirstName"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("lastName");
		elemField.setXmlName(new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "LastName"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("emailAddress");
		elemField.setXmlName(new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "EmailAddress"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("group");
		elemField.setXmlName(new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "Group"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Return type metadata object
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}

	/**
	 * Get Custom Serializer
	 */
	public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType,
			java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType,
			java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

}
