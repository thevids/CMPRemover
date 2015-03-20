package no.moller.cmpmigrator;

import junit.framework.Assert;

import org.junit.Test;


public class MethodBodyHelperTest {

    @Test
    public void testGettify() {
        String gettify = DaoMethodBody.gettify("hoursWorked", "Appointment");
        Assert.assertEquals("appointment.getHoursWorked()", gettify);
    }
}
