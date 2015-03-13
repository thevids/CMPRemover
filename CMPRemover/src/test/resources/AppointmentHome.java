package no.moller.evp.model.ejb;

/**
 * This is a Home interface for the Entity Bean
 */
public interface AppointmentHome extends javax.ejb.EJBHome {
	/**
	 * Insert the method's description here.
	 * Creation date: (08.05.2001 11:35:54)
	 * @return entities.Appointment
	 * @param argFnr java.lang.String
	 * @param argAppointmentID java.lang.String
	 * @exception java.rmi.RemoteException The exception description.
	 * @exception javax.ejb.FinderException The exception description.
	 */
	no.moller.evp.model.ejb.Appointment findAppointment(java.lang.String argFnr, java.lang.String argAppointmentID) throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * Insert the method's description here.
	 * Creation date: (04.05.2001 09:11:34)
	 * @return java.util.Enumeration
	 * @param argFnr java.lang.String
	 * @param argResource int
	 * @param argDate java.lang.String
	 * @exception java.rmi.RemoteException The exception description.
	 */
	java.util.Enumeration findAppointmentFromPartsList(java.lang.String argFnr, short argGjgAar, int argGjgNr, java.lang.String argDate) throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * Insert the method's description here.
	 * Creation date: (10.04.2001 13:25:45)
	 * @return java.util.Enumeration
	 * @param argFnr java.lang.String
	 * @param argRessursID int
	 * @param argFrom java.lang.String
	 * @param argTo java.lang.String
	 * @exception java.rmi.RemoteException The exception description.
	 * @exception javax.ejb.FinderException The exception description.
	 */
	java.util.Enumeration findAppointmentsBetween(java.lang.String argFnr, int argMainResourceID, java.lang.String argFrom, java.lang.String argTo)
		throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * Insert the method's description here.
	 * Creation date: (04.05.2001 09:11:34)
	 * @return java.util.Enumeration
	 * @param argFnr java.lang.String
	 * @param argResource int
	 * @param argDate java.lang.String
	 * @exception java.rmi.RemoteException The exception description.
	 */
	java.util.Enumeration findAppointmentsOnDay(java.lang.String argFnr, java.lang.String argDate) throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * Insert the method's description here.
	 * Creation date: (04.05.2001 09:11:34)
	 * @return java.util.Enumeration
	 * @param argFnr java.lang.String
	 * @param argResource int
	 * @param argDate java.lang.String
	 * @exception java.rmi.RemoteException The exception description.
	 */
	java.util.Enumeration findAppointmentsOnDayByResource(java.lang.String argFnr, int argResourceID, java.lang.String argDate) throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * Insert the method's description here.
	 * Creation date: (04.05.2001 09:11:34)
	 * @return java.util.Enumeration
	 * @param argFnr java.lang.String
	 * @param argResource int
	 * @param argDate java.lang.String
	 * @exception java.rmi.RemoteException The exception description.
	 */
	java.util.Enumeration findByCarRegNumber(java.lang.String argFnr, java.lang.String argCarRegNumber) throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
							 * Insert the method's description here.
							 * Creation date: (04.05.2001 09:11:34)
							 * @return java.util.Enumeration
							 * @param argFnr java.lang.String
							 * @param argResource int
							 * @param argDate java.lang.String
							 * @exception java.rmi.RemoteException The exception description.
							 */
	java.util.Enumeration findByCustomerName(java.lang.String argFnr, java.lang.String argCustomerName) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * findByPrimaryKey method comment
 * @return no.moller.evp.model.ejb.Appointment
 * @param key no.moller.evp.model.ejb.AppointmentKey
 * @exception java.rmi.RemoteException The exception description.
 * @exception javax.ejb.FinderException The exception description.
 */
no.moller.evp.model.ejb.Appointment findByPrimaryKey(no.moller.evp.model.ejb.AppointmentKey key) throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * Insert the method's description here.
	 * Creation date: (04.05.2001 09:11:34)
	 * @return java.util.Enumeration
	 * @param argFnr java.lang.String
	 * @param argResource int
	 * @param argDate java.lang.String
	 * @exception java.rmi.RemoteException The exception description.
	 */
	java.util.Enumeration findBySerie(java.lang.String argFnr, int argSerieID) throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * Insert the method's description here.
	 * Creation date: (12.04.01 21:15:06)
	 * @return entities.Appointment
	 */
	no.moller.evp.model.ejb.Appointment findNewestAppointment() throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * Insert the method's description here.
	 * Creation date: (04.05.2001 09:11:34)
	 * @return java.util.Enumeration
	 * @param argFnr java.lang.String
	 * @param argResource int
	 * @param argDate java.lang.String
	 * @exception java.rmi.RemoteException The exception description.
	 */
	java.util.Enumeration findNewestSerie() throws java.rmi.RemoteException, javax.ejb.FinderException;
	public java.util.Enumeration findByWorkshopSession(java.lang.String fnr, short GJGAAR, int GJGNR, short OppdragNr) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Enumeration findByWorkshopSessionFromDate(java.lang.String fnr, short GJGAAR, int GJGNR, short OppdragNr, java.lang.String date)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * ejbCreate method for a CMP entity bean
	 * @param argAppointmentID int
	 * @param argFnr java.lang.String
	 * @exception javax.ejb.CreateException The exception description.
	 * @exception java.rmi.RemoteException The exception description.
	 */
	public no.moller.evp.model.ejb.Appointment create(
		String argFnr,
		int argMainResource,
		String argStartTime,
		String argEndTime,
		short argGjgAar,
		int argGjgNr,
		boolean argOrderConnected,
		float argPrice,
		int argJobStat,
		int argMechStat,
		int argPartsStat,
		boolean argQControl,
		boolean argMControl,
		String argAllocRes,
		String argCreatedBy,
		String argDateCreated,
		String argComment)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	 * ejbCreate method for a CMP entity bean
	 * @param argAppointmentID int
	 * @param argFnr java.lang.String
	 * @exception javax.ejb.CreateException The exception description.
	 * @exception java.rmi.RemoteException The exception description.
	 */
	public no.moller.evp.model.ejb.Appointment create(
		String argFnr,
		int argMainResource,
		String argStartTime,
		String argEndTime,
		short argGjgAar,
		int argGjgNr,
		boolean argOrderConnected,
		float argPrice,
		int argJobStat,
		int argMechStat,
		int argPartsStat,
		boolean argQControl,
		boolean argMControl,
		String argAllocRes,
		String argCreatedBy,
		String argDateCreated,
		String argComment,
		int argSerieID,
		int argType)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	 * ejbCreate method for a CMP entity bean
	 * @param argAppointmentID int
	 * @param argFnr java.lang.String
	 * @exception javax.ejb.CreateException The exception description.
	 * @exception java.rmi.RemoteException The exception description.
	 */
	public no.moller.evp.model.ejb.Appointment create(
		String argFnr,
		int argMainResource,
		String argStartTime,
		String argEndTime,
		short argGjgAar,
		int argGjgNr,
		boolean argOrderConnected,
		float argPrice,
		int argJobStat,
		int argMechStat,
		int argPartsStat,
		boolean argQControl,
		boolean argMControl,
		String argAllocRes,
		String argCustomerName,
		String argCarRegNumber,
		String argCreatedBy,
		String argDateCreated,
		String argComment)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	 * ejbCreate method for a CMP entity bean
	 * @param argAppointmentID int
	 * @param argFnr java.lang.String
	 * @exception javax.ejb.CreateException The exception description.
	 * @exception java.rmi.RemoteException The exception description.
	 */
	public no.moller.evp.model.ejb.Appointment create(
		String argFnr,
		int argMainResource,
		String argStartTime,
		String argEndTime,
		short argGjgAar,
		int argGjgNr,
		boolean argOrderConnected,
		float argPrice,
		int argJobStat,
		int argMechStat,
		int argPartsStat,
		boolean argQControl,
		boolean argMControl,
		String argAllocRes,
		String argCustomerName,
		String argCarRegNumber,
		String argCreatedBy,
		String argDateCreated,
		String argComment,
		int argSerieID,
		int argType)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	 * ejbCreate method for a CMP entity bean
	 * @param argAppointmentID int
	 * @param argFnr java.lang.String
	 * @exception javax.ejb.CreateException The exception description.
	 * @exception java.rmi.RemoteException The exception description.
	 */
	public no.moller.evp.model.ejb.Appointment create(
		String argFnr,
		int argMainResource,
		String argStartTime,
		String argEndTime,
		short argGjgAar,
		int argGjgNr,
		boolean argOrderConnected,
		float argPrice,
		int argJobStat,
		int argMechStat,
		int argPartsStat,
		boolean argQControl,
		boolean argMControl,
		String argAllocRes,
		String argCustomerName,
		String argCarRegNumber,
		String argCreatedBy,
		String argDateCreated,
		String argComment,
		int argSerieID,
		int argType,
		int argEventType)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	 * ejbCreate method for a CMP entity bean
	 * @param argAppointmentID int
	 * @param argFnr java.lang.String
	 * @exception javax.ejb.CreateException The exception description.
	 * @exception java.rmi.RemoteException The exception description.
	 */
	public no.moller.evp.model.ejb.Appointment create(
		String argFnr,
		int argMainResource,
		String argStartTime,
		String argEndTime,
		short argGjgAar,
		int argGjgNr,
		boolean argOrderConnected,
		float argPrice,
		int argJobStat,
		int argMechStat,
		int argPartsStat,
		boolean argQControl,
		boolean argMControl,
		String argAllocRes,
		String argCustomerName,
		String argCarRegNumber,
		String argCreatedBy,
		String argDateCreated,
		String argComment,
		int argSerieID,
		int argType,
		int argEventType,
		boolean argCustomerWaiting,
		String argInterruptedDescription)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	public java.util.Enumeration findAllAppointmentsOnDayByResource(java.lang.String fnr, int resourceID, java.lang.String date) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Enumeration findByOrderNumberFromDate(java.lang.String fnr, java.lang.String orderNumber, java.lang.String startTime) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Enumeration findByOrderNumber(java.lang.String fnr, java.lang.String orderNumber) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Enumeration findAppointmentFromPartsListLegacyMVK(java.lang.String fnr, java.lang.String orderNumber, java.lang.String startTime) throws javax.ejb.FinderException, java.rmi.RemoteException;
	// ================================== LEGACY MVK CREATE METHODS ==========================
	public no.moller.evp.model.ejb.Appointment create(
		String argFnr,
		int argMainResource,
		String argStartTime,
		String argEndTime,
		String argMverkOrderNumber,
		boolean argOrderConnected,
		float argPrice,
		int argJobStat,
		int argMechStat,
		int argPartsStat,
		boolean argQControl,
		boolean argMControl,
		String argAllocRes,
		String argCreatedBy,
		String argDateCreated,
		String argComment)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	* ejbCreate method for a CMP entity bean
	* @param argAppointmentID int
	* @param argFnr java.lang.String
	* @exception javax.ejb.CreateException The exception description.
	* @exception java.rmi.RemoteException The exception description.
	*/
	public no.moller.evp.model.ejb.Appointment create(
		String argFnr,
		int argMainResource,
		String argStartTime,
		String argEndTime,
		String argMverkOrderNumber,
		boolean argOrderConnected,
		float argPrice,
		int argJobStat,
		int argMechStat,
		int argPartsStat,
		boolean argQControl,
		boolean argMControl,
		String argAllocRes,
		String argCreatedBy,
		String argDateCreated,
		String argComment,
		int argSerieID,
		int argType)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	* ejbCreate method for a CMP entity bean
	* @param argAppointmentID int
	* @param argFnr java.lang.String
	* @exception javax.ejb.CreateException The exception description.
	* @exception java.rmi.RemoteException The exception description.
	*/
	public no.moller.evp.model.ejb.Appointment create(
		String argFnr,
		int argMainResource,
		String argStartTime,
		String argEndTime,
		String argMverkOrderNumber,
		boolean argOrderConnected,
		float argPrice,
		int argJobStat,
		int argMechStat,
		int argPartsStat,
		boolean argQControl,
		boolean argMControl,
		String argAllocRes,
		String argCustomerName,
		String argCarRegNumber,
		String argCreatedBy,
		String argDateCreated,
		String argComment)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	* ejbCreate method for a CMP entity bean
	* @param argAppointmentID int
	* @param argFnr java.lang.String
	* @exception javax.ejb.CreateException The exception description.
	* @exception java.rmi.RemoteException The exception description.
	*/
	public no.moller.evp.model.ejb.Appointment create(
		String argFnr,
		int argMainResource,
		String argStartTime,
		String argEndTime,
		String argMverkOrderNumber,
		boolean argOrderConnected,
		float argPrice,
		int argJobStat,
		int argMechStat,
		int argPartsStat,
		boolean argQControl,
		boolean argMControl,
		String argAllocRes,
		String argCustomerName,
		String argCarRegNumber,
		String argCreatedBy,
		String argDateCreated,
		String argComment,
		int argSerieID,
		int argType)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	* ejbCreate method for a CMP entity bean
	* @param argAppointmentID int
	* @param argFnr java.lang.String
	* @exception javax.ejb.CreateException The exception description.
	* @exception java.rmi.RemoteException The exception description.
	*/
	public no.moller.evp.model.ejb.Appointment create(
		String argFnr,
		int argMainResource,
		String argStartTime,
		String argEndTime,
		String argMverkOrderNumber,
		boolean argOrderConnected,
		float argPrice,
		int argJobStat,
		int argMechStat,
		int argPartsStat,
		boolean argQControl,
		boolean argMControl,
		String argAllocRes,
		String argCustomerName,
		String argCarRegNumber,
		String argCreatedBy,
		String argDateCreated,
		String argComment,
		int argSerieID,
		int argType,
		int argEventType)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	* ejbCreate method for a CMP entity bean
	* @param argAppointmentID int
	* @param argFnr java.lang.String
	* @exception javax.ejb.CreateException The exception description.
	* @exception java.rmi.RemoteException The exception description.
	*/
	public no.moller.evp.model.ejb.Appointment create(
		String argFnr,
		int argMainResource,
		String argStartTime,
		String argEndTime,
		String argMverkOrderNumber,
		boolean argOrderConnected,
		float argPrice,
		int argJobStat,
		int argMechStat,
		int argPartsStat,
		boolean argQControl,
		boolean argMControl,
		String argAllocRes,
		String argCustomerName,
		String argCarRegNumber,
		String argCreatedBy,
		String argDateCreated,
		String argComment,
		int argSerieID,
		int argType,
		int argEventType,
		boolean argCustomerWaiting,
		String argInterruptedDescription)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	public java.util.Enumeration findByJobStatus(java.lang.String argFnr, int argJobStatus) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Enumeration findByJobStatusPeriode(java.lang.String argFnr, int argJobStatus, java.lang.String argStartTime, java.lang.String argEndTime) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Enumeration findEventsInPeriode(java.lang.String argFnr, java.lang.String argFromDate, java.lang.String argToDate)
		throws javax.ejb.FinderException,
		java.rmi.RemoteException;
	public java.util.Enumeration findEventsForResource(
		java.lang.String argFnr,
		int argResourceId,
		java.lang.String argFromDate,
		java.lang.String argToDate) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
