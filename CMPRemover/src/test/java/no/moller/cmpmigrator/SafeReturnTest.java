package no.moller.cmpmigrator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class SafeReturnTest {

    private ArrayList<String> list;

    @Before
    public void setup() {
        list = new ArrayList<String>();
        list.add("String1");
        list.add("String2");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testWhatWeUse() {
        Enumeration<String> go = SafeReturn.ret(list, Enumeration.class);
        Assert.assertTrue(go instanceof Enumeration<?>); // Must be, but illustrates the test
        Assert.assertEquals("String1", go.nextElement());
        Assert.assertEquals("String2", go.nextElement());

        @SuppressWarnings("rawtypes")
        Enumeration go2 = SafeReturn.ret(list, Enumeration.class);
        Assert.assertTrue(go2 instanceof Enumeration<?>);
        Assert.assertEquals("String1", go2.nextElement());
        Assert.assertEquals("String2", go2.nextElement());

        Iterator<String> it = SafeReturn.ret(list, Iterator.class);
        Assert.assertTrue(it instanceof Iterator<?>);
        Assert.assertEquals("String1", it.next());
        Assert.assertEquals("String2", it.next());

        String single = SafeReturn.ret(list, String.class);
        Assert.assertTrue(single instanceof String);
        Assert.assertEquals("String1", single);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOthers() {
        Iterator<String> it = SafeReturn.ret(list, Iterator.class);
        Assert.assertTrue(it instanceof Iterator<?>);
        Assert.assertEquals("String1", it.next());
        Assert.assertEquals("String2", it.next());

        List<String> lst = SafeReturn.ret(list, List.class);
        Assert.assertTrue(lst instanceof List<?>);
        Assert.assertTrue(lst.size() == 2);

        Collection<String> coll = SafeReturn.ret(list, Collection.class);
        Assert.assertTrue(coll instanceof Collection<?>);
        Assert.assertTrue(coll.size() == 2);


    }
}
