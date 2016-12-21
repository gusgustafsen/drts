//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package gov.ed.fsa.drts.aimsdrts;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReasonCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReasonCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FAILURE_USERID_NOT_FOUND"/>
 *     &lt;enumeration value="GENERAL_ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReasonCodeType", namespace = "http://drts.fsa.ed.gov/AimsDrts")
@XmlEnum
public enum ReasonCodeType {

    FAILURE_USERID_NOT_FOUND,
    GENERAL_ERROR;

    public String value() {
        return name();
    }

    public static ReasonCodeType fromValue(String v) {
        return valueOf(v);
    }

}
