/**
 * DeleteAccountResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.ed.fsa.drts.AimsDrtsx;

public class DeleteAccountResponseType extends gov.ed.fsa.drts.AimsDrtsx.CommonResponseType
		implements java.io.Serializable {
	public DeleteAccountResponseType() {
	}

	public DeleteAccountResponseType(gov.ed.fsa.drts.AimsDrtsx.StatusCodeType statusCode,
			gov.ed.fsa.drts.AimsDrtsx.ReasonCodeType reasonCode, java.lang.String reasonCodeDescription) {
		super(statusCode, reasonCode, reasonCodeDescription);
	}

	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof DeleteAccountResponseType))
			return false;
		DeleteAccountResponseType other = (DeleteAccountResponseType) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return(__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = super.equals(obj);
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
		int _hashCode = super.hashCode();
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc =
			new org.apache.axis.description.TypeDesc(DeleteAccountResponseType.class, true);

	static {
		typeDesc.setXmlType(
				new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "DeleteAccountResponseType"));
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
