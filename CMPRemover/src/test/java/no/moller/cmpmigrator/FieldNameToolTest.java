package no.moller.cmpmigrator;

import junit.framework.Assert;

import org.junit.Test;


public class FieldNameToolTest {

    @Test
    public void testGettify() {
        String gettify = FieldNameTool.gettify("hoursWorked", "Appointment");
        Assert.assertEquals("appointment.getHoursWorked()", gettify);
    }
}
