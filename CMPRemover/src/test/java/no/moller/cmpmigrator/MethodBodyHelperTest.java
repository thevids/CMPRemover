package no.moller.cmpmigrator;

import junit.framework.Assert;
import no.moller.cmpmigrator.MethodBodyHelper;

import org.junit.Test;


public class MethodBodyHelperTest {

    @Test
    public void testGettify() {
        String gettify = MethodBodyHelper.gettify("hoursWorked", "Appointment");
        Assert.assertEquals("appointment.getHoursWorked()", gettify);
    }
}
