package no.moller.evp.model.ejb;

public class AppointmentKey implements java.io.Serializable {
	public java.lang.String appointmentID;
	public java.lang.String fnr;
	private final static long serialVersionUID = 3206093459760846163L;

/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public AppointmentKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argAppointmentID java.lang.String
 * @param argFnr java.lang.String
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public AppointmentKey(java.lang.String argAppointmentID, java.lang.String argFnr) {
	appointmentID = argAppointmentID;
	fnr = argFnr;
}
/**
 * equals method
 * @return boolean
 * @param o java.lang.Object
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public boolean equals(java.lang.Object o) {
	if (o instanceof AppointmentKey) {
		AppointmentKey otherKey = (AppointmentKey) o;
		return ((this.appointmentID.equals(otherKey.appointmentID)
		 && this.fnr.equals(otherKey.fnr)));
	}
	else
		return false;
}
/**
 * hashCode method
 * @return int
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public int hashCode() {
	return (appointmentID.hashCode()
		 + fnr.hashCode());
}
}
