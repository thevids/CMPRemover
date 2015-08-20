package no.moller.evp.model.ejb;

import java.sql.Timestamp;
import java.util.*;
import no.moller.util.EvpUtils;

/**
 * Domain object.
 */
public class AppointmentDom
{
    private final static boolean DEBUG = false;
    private java.util.Vector v = new Vector();
    private String tempFnr;
    private String allocatedRes;
    private String appointmentID;
    private String carChassisNumber;
    private int carKm;
    private String carRegNumber;
    private String changedBy;
    private String createdBy;
    private String customerAddress;
    private String customerEmail;
    private String customerName;
    private String customerTlf;
    private String customerTlfToday;
    private String dateChanged;
    private String dateCreated;
    private String endTime;
    private String fnr;
    private int intStatus1 = 0;
    private int intStatus2 = 0;
    private float invoicePrice = 0;
    private int jobStatus;
    private int mainResource;
    private boolean mechanicianControl = false;
    private int mechanicianStatus;
    private boolean orderConnected = false;
    private String orderText;
    private int partsStatus;
    private boolean qualityControl = false;
    private final static long serialVersionUID = 3206093459760846163L;
    private String startTime;
    private String strStatus1 = null;
    private String strStatus2 = null;
    private java.sql.Timestamp timeStampCreated;
    private String comment;
    private int serieID;
    private int appointmentType;
    private int eventtype;
    private boolean customerWaiting;
    private String interruptedDescription;
    private short OppdragNr;
    private int gjgNr;
    private short gjgAar;
    private java.lang.String mverkOrderNumber;
    private java.lang.String appSubtypeLimit;
    private int appSubtype;
    private int appSubTypeStatus;
    private double soldHoursAmount;
    private java.lang.String holidayCode;
    private boolean authorized;
    private java.util.Date authorizedDate;
    private boolean hasVcoInSession;

    public AppointmentDom(Vector v) {
        this.v = v;
    }

    public AppointmentDom(Vector v, String tempFnr, String allocatedRes, String appointmentID, String carChassisNumber, int carKm, String carRegNumber, String changedBy, String createdBy, String customerAddress, String customerEmail, String customerName, String customerTlf, String customerTlfToday, String dateChanged, String dateCreated, String endTime, String fnr, int intStatus1, int intStatus2, float invoicePrice, int jobStatus, int mainResource, boolean mechanicianControl, int mechanicianStatus, boolean orderConnected, String orderText, int partsStatus, boolean qualityControl, String startTime, String strStatus1, String strStatus2, Timestamp timeStampCreated, String comment, int serieID, int appointmentType, int eventtype, boolean customerWaiting, String interruptedDescription, short oppdragNr, int gjgNr, short gjgAar, String mverkOrderNumber, String appSubtypeLimit, int appSubtype, int appSubTypeStatus, double soldHoursAmount, String holidayCode, boolean authorized, Date authorizedDate, boolean hasVcoInSession) {
        this.v = v;
        this.tempFnr = tempFnr;
        this.allocatedRes = allocatedRes;
        this.appointmentID = appointmentID;
        this.carChassisNumber = carChassisNumber;
        this.carKm = carKm;
        this.carRegNumber = carRegNumber;
        this.changedBy = changedBy;
        this.createdBy = createdBy;
        this.customerAddress = customerAddress;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.customerTlf = customerTlf;
        this.customerTlfToday = customerTlfToday;
        this.dateChanged = dateChanged;
        this.dateCreated = dateCreated;
        this.endTime = endTime;
        this.fnr = fnr;
        this.intStatus1 = intStatus1;
        this.intStatus2 = intStatus2;
        this.invoicePrice = invoicePrice;
        this.jobStatus = jobStatus;
        this.mainResource = mainResource;
        this.mechanicianControl = mechanicianControl;
        this.mechanicianStatus = mechanicianStatus;
        this.orderConnected = orderConnected;
        this.orderText = orderText;
        this.partsStatus = partsStatus;
        this.qualityControl = qualityControl;
        this.startTime = startTime;
        this.strStatus1 = strStatus1;
        this.strStatus2 = strStatus2;
        this.timeStampCreated = timeStampCreated;
        this.comment = comment;
        this.serieID = serieID;
        this.appointmentType = appointmentType;
        this.eventtype = eventtype;
        this.customerWaiting = customerWaiting;
        this.interruptedDescription = interruptedDescription;
        OppdragNr = oppdragNr;
        this.gjgNr = gjgNr;
        this.gjgAar = gjgAar;
        this.mverkOrderNumber = mverkOrderNumber;
        this.appSubtypeLimit = appSubtypeLimit;
        this.appSubtype = appSubtype;
        this.appSubTypeStatus = appSubTypeStatus;
        this.soldHoursAmount = soldHoursAmount;
        this.holidayCode = holidayCode;
        this.authorized = authorized;
        this.authorizedDate = authorizedDate;
        this.hasVcoInSession = hasVcoInSession;
    }

    public java.lang.String getAllocatedRes()
    {
        return allocatedRes;
    }

    public int getAppointmentType()
    {
        return appointmentType;
    }

    public java.lang.String getCarChassisNumber()
    {
        return carChassisNumber;
    }

    public int getCarKm()
    {
        return carKm;
    }

    public java.lang.String getCarRegNumber()
    {
        return carRegNumber;
    }

    public java.lang.String getChangedBy()
    {
        return changedBy;
    }

    public java.lang.String getComment()
    {
        return comment;
    }

    public java.lang.String getCreatedBy()
    {
        return createdBy;
    }

    public java.lang.String getCustomerAddress()
    {
        return customerAddress;
    }

    public java.lang.String getCustomerEmail()
    {
        return customerEmail;
    }

    public java.lang.String getCustomerName()
    {
        return customerName;
    }

    public java.lang.String getCustomerTlf()
    {
        return customerTlf;
    }

    public java.lang.String getCustomerTlfToday()
    {
        return customerTlfToday;
    }

    public boolean getCustomerWaiting()
    {
        return customerWaiting;
    }

    public java.lang.String getDateChanged()
    {
        return dateChanged;
    }

    public java.lang.String getDateCreated()
    {
        return dateCreated;
    }

    public java.lang.String getEndTime()
    {
        return endTime;
    }

    public javax.ejb.EntityContext getEntityContext()
    {
        return entityContext;
    }

    public int getEventtype()
    {
        return eventtype;
    }

    public java.lang.String getInterruptedDescription()
    {
        return interruptedDescription;
    }

    public int getIntStatus1()
    {
        return intStatus1;
    }

    public int getIntStatus2()
    {
        return intStatus2;
    }

    public float getInvoicePrice()
    {
        return invoicePrice;
    }

    public int getJobStatus()
    {
        return jobStatus;
    }

    public int getMainResource()
    {
        return mainResource;
    }

    public boolean getMechanicianControl()
    {
        return mechanicianControl;
    }

    public int getMechanicianStatus()
    {
        return mechanicianStatus;
    }

    public boolean getOrderConnected()
    {
        return orderConnected;
    }

    public java.lang.String getOrderText()
    {
        return orderText;
    }

    public int getPartsStatus()
    {
        return partsStatus;
    }

    public boolean getQualityControl()
    {
        return qualityControl;
    }

    public int getSerieID()
    {
        return serieID;
    }

    public java.lang.String getStartTime()
    {
        return startTime;
    }

    public java.lang.String getStrStatus1()
    {
        return strStatus1;
    }

    public java.lang.String getStrStatus2()
    {
        return strStatus2;
    }

    public java.sql.Timestamp getTimeStampCreated()
    {
        return timeStampCreated;
    }

    public void setAllocatedRes(java.lang.String newValue)
    {
        this.allocatedRes = newValue;
    }

    public void setAppointmentType(int newValue)
    {
        this.appointmentType = newValue;
    }

    public void setCarChassisNumber(java.lang.String newValue)
    {
        this.carChassisNumber = newValue;
    }

    public void setCarKm(int newValue)
    {
        this.carKm = newValue;
    }

    public void setCarRegNumber(java.lang.String newValue)
    {
        this.carRegNumber = newValue;
    }

    public void setChangedBy(java.lang.String newValue)
    {
        this.changedBy = newValue;
    }

    public void setComment(java.lang.String newValue)
    {
        this.comment = newValue;
    }

    public void setCreatedBy(java.lang.String newValue)
    {
        this.createdBy = newValue;
    }

    public void setCustomerAddress(java.lang.String newValue)
    {
        this.customerAddress = newValue;
    }

    public void setCustomerEmail(java.lang.String newValue)
    {
        this.customerEmail = newValue;
    }

    public void setCustomerName(java.lang.String newValue)
    {
        this.customerName = newValue;
    }

    public void setCustomerTlf(java.lang.String newValue)
    {
        this.customerTlf = newValue;
    }

    public void setCustomerTlfToday(java.lang.String newValue)
    {
        this.customerTlfToday = newValue;
    }

    public void setCustomerWaiting(boolean newValue)
    {
        this.customerWaiting = newValue;
    }

    public void setDateChanged(java.lang.String newValue)
    {
        this.dateChanged = newValue;
    }

    public void setDateCreated(java.lang.String newValue)
    {
        this.dateCreated = newValue;
    }

    public void setEndTime(java.lang.String newValue)
    {
        this.endTime = newValue;
    }

    public void setEntityContext(javax.ejb.EntityContext ctx)
    {
        entityContext = ctx;
    }

    public void setEventtype(int newValue)
    {
        this.eventtype = newValue;
    }

    public void setInterruptedDescription(java.lang.String newValue)
    {
        this.interruptedDescription = newValue;
    }

    public void setIntStatus1(int newValue)
    {
        this.intStatus1 = newValue;
    }

    public void setIntStatus2(int newValue)
    {
        this.intStatus2 = newValue;
    }

    public void setInvoicePrice(float newValue)
    {
        this.invoicePrice = newValue;
    }

    public void setJobStatus(int newValue)
    {
        this.jobStatus = newValue;
    }

    public void setMainResource(int newValue)
    {
        this.mainResource = newValue;
    }

    public void setMechanicianControl(boolean newValue)
    {
        this.mechanicianControl = newValue;
    }

    public void setMechanicianStatus(int newValue)
    {
        this.mechanicianStatus = newValue;
    }

    public void setOrderConnected(boolean newValue)
    {
        this.orderConnected = newValue;
    }

    public void setOrderText(java.lang.String newValue)
    {
        this.orderText = newValue;
    }

    public void setPartsStatus(int newValue)
    {
        this.partsStatus = newValue;
    }

    public void setQualityControl(boolean newValue)
    {
        this.qualityControl = newValue;
    }

    public void setSerieID(int newValue)
    {
        this.serieID = newValue;
    }

    public void setStartTime(java.lang.String newValue)
    {
        this.startTime = newValue;
    }

    public void setStrStatus1(java.lang.String newValue)
    {
        this.strStatus1 = newValue;
    }

    public void setStrStatus2(java.lang.String newValue)
    {
        this.strStatus2 = newValue;
    }

    public void setTimeStampCreated(java.sql.Timestamp newValue)
    {
        this.timeStampCreated = newValue;
    }

    public void unsetEntityContext()
    {
        entityContext = null;
    }

    public short getOppdragNr()
    {
        return OppdragNr;
    }

    public void setOppdragNr(short newOppdragNr)
    {
        OppdragNr = newOppdragNr;
    }

    public int getGjgNr()
    {
        return gjgNr;
    }

    public void setGjgNr(int newGjgNr)
    {
        gjgNr = newGjgNr;
    }

    public short getGjgAar()
    {
        return gjgAar;
    }

    public void setGjgAar(short newGjgAar)
    {
        gjgAar = newGjgAar;
    }

    public no.moller.evp.model.ejb.AppointmentData getAppointmentData()
    {
        return new no.moller.evp.model.ejb.AppointmentData(this);
    }


    public AppointmentKey getPrimaryKey()
    {
        return new AppointmentKey(appointmentID, fnr);
    }

    public java.lang.String getMverkOrderNumber()
    {
        return mverkOrderNumber;
    }

    public void setMverkOrderNumber(java.lang.String newMverkOrderNumber)
    {
        mverkOrderNumber = newMverkOrderNumber;
    }

    public java.lang.String getAppSubtypeLimit()
    {
        return appSubtypeLimit;
    }

    public void setAppSubtypeLimit(java.lang.String newAppSubtypeLimit)
    {
        appSubtypeLimit = newAppSubtypeLimit;
    }

    public int getAppSubtype()
    {
        return appSubtype;
    }

    public void setAppSubtype(int newAppSubtype)
    {
        appSubtype = newAppSubtype;
    }

    public int getAppSubTypeStatus()
    {
        return appSubTypeStatus;
    }

    public void setAppSubTypeStatus(int newAppSubTypeStatus)
    {
        appSubTypeStatus = newAppSubTypeStatus;
    }

    public double getSoldHoursAmount()
    {
        return soldHoursAmount;
    }

    public void setSoldHoursAmount(double newSoldHoursAmount)
    {
        soldHoursAmount = newSoldHoursAmount;
    }

    public java.lang.String getHolidayCode()
    {
        return holidayCode;
    }

    public void setHolidayCode(java.lang.String newHolidayCode)
    {
        holidayCode = newHolidayCode;
    }

    public boolean isAuthorized()
    {
        return authorized;
    }

    public void setAuthorized(boolean newAuthorized)
    {
        authorized = newAuthorized;
    }

    public java.util.Date getAuthorizedDate()
    {
        return authorizedDate;
    }

    public void setAuthorizedDate(java.util.Date newAuthorizedDate)
    {
        authorizedDate = newAuthorizedDate;
    }

    public boolean isHasVcoInSession()
    {
        return hasVcoInSession;
    }

    public void setHasVcoInSession(boolean newHasVcoInSession)
    {
        hasVcoInSession = newHasVcoInSession;
    }

    public java.lang.String getAppointmentID()
    {
        return appointmentID;
    }

    public void setAppointmentID(java.lang.String appointmentID)
    {
        this.appointmentID = appointmentID;
    }

    public java.lang.String getFnr()
    {
        return fnr;
    }

    public void setFnr(java.lang.String fnr)
    {
        this.fnr = fnr;
    }
}
