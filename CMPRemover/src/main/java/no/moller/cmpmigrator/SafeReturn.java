package no.moller.cmpmigrator;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class SafeReturn {

    public SafeReturn() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T ret(List<?> list, Class<T> retType) {
        if(retType.equals(Enumeration.class)) {
            return (T) Collections.enumeration(list);
        } else if(retType.equals(Iterator.class)) {
            return (T) list.iterator();
        } else if(retType.equals(Collection.class)) {
            return (T) list;
        } else if(retType.equals(List.class)) {
            return (T) list;
        } else {
            return retrieveSingle(list);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T retrieveSingle(List<?> list) {
        if(list.isEmpty()) {
            return null;
        }
        return (T) list.get(0);
    }
}
