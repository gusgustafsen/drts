/**
 * AimsDrtsService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.ed.fsa.drts.AimsDrtsServicex;

public interface AimsDrtsService_PortType extends java.rmi.Remote {
	public gov.ed.fsa.drts.AimsDrtsx.CreateAccountResponseType createAccount(
			gov.ed.fsa.drts.AimsDrtsx.CreateAccountRequestType createAccountRequest) throws java.rmi.RemoteException;

	public gov.ed.fsa.drts.AimsDrtsx.DeleteAccountResponseType deleteAccount(
			gov.ed.fsa.drts.AimsDrtsx.DeleteAccountRequestType deleteAccountRequest) throws java.rmi.RemoteException;
}
