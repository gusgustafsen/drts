/**
 * CommonResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.ed.fsa.drts.AimsDrtsx;

public class CommonResponseType implements java.io.Serializable {
	private gov.ed.fsa.drts.AimsDrtsx.StatusCodeType statusCode;

	private gov.ed.fsa.drts.AimsDrtsx.ReasonCodeType reasonCode;

	private java.lang.String reasonCodeDescription;

	public CommonResponseType() {
	}

	public CommonResponseType(gov.ed.fsa.drts.AimsDrtsx.StatusCodeType statusCode,
			gov.ed.fsa.drts.AimsDrtsx.ReasonCodeType reasonCode, java.lang.String reasonCodeDescription) {
		this.statusCode = statusCode;
		this.reasonCode = reasonCode;
		this.reasonCodeDescription = reasonCodeDescription;
	}

	/**
	 * Gets the statusCode value for this CommonResponseType.
	 * 
	 * @return statusCode
	 */
	public gov.ed.fsa.drts.AimsDrtsx.StatusCodeType getStatusCode() {
		return statusCode;
	}

	/**
	 * Sets the statusCode value for this CommonResponseType.
	 * 
	 * @param statusCode
	 */
	public void setStatusCode(gov.ed.fsa.drts.AimsDrtsx.StatusCodeType statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * Gets the reasonCode value for this CommonResponseType.
	 * 
	 * @return reasonCode
	 */
	public gov.ed.fsa.drts.AimsDrtsx.ReasonCodeType getReasonCode() {
		return reasonCode;
	}

	/**
	 * Sets the reasonCode value for this CommonResponseType.
	 * 
	 * @param reasonCode
	 */
	public void setReasonCode(gov.ed.fsa.drts.AimsDrtsx.ReasonCodeType reasonCode) {
		this.reasonCode = reasonCode;
	}

	/**
	 * Gets the reasonCodeDescription value for this CommonResponseType.
	 * 
	 * @return reasonCodeDescription
	 */
	public java.lang.String getReasonCodeDescription() {
		return reasonCodeDescription;
	}

	/**
	 * Sets the reasonCodeDescription value for this CommonResponseType.
	 * 
	 * @param reasonCodeDescription
	 */
	public void setReasonCodeDescription(java.lang.String reasonCodeDescription) {
		this.reasonCodeDescription = reasonCodeDescription;
	}

	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof CommonResponseType))
			return false;
		CommonResponseType other = (CommonResponseType) obj;
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
				&& ((this.statusCode == null && other.getStatusCode() == null)
						|| (this.statusCode != null && this.statusCode.equals(other.getStatusCode())))
				&& ((this.reasonCode == null && other.getReasonCode() == null)
						|| (this.reasonCode != null && this.reasonCode.equals(other.getReasonCode())))
				&& ((this.reasonCodeDescription == null && other.getReasonCodeDescription() == null)
						|| (this.reasonCodeDescription != null
								&& this.reasonCodeDescription.equals(other.getReasonCodeDescription())));
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
		if (getStatusCode() != null) {
			_hashCode += getStatusCode().hashCode();
		}
		if (getReasonCode() != null) {
			_hashCode += getReasonCode().hashCode();
		}
		if (getReasonCodeDescription() != null) {
			_hashCode += getReasonCodeDescription().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc =
			new org.apache.axis.description.TypeDesc(CommonResponseType.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "CommonResponseType"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("statusCode");
		elemField.setXmlName(new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "StatusCode"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "StatusCodeType"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("reasonCode");
		elemField.setXmlName(new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "ReasonCode"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "ReasonCodeType"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("reasonCodeDescription");
		elemField.setXmlName(new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "ReasonCodeDescription"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
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
