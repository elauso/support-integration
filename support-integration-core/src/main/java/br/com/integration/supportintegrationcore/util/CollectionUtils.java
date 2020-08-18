package br.com.integration.supportintegrationcore.util;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class CollectionUtils {

    private CollectionUtils() {
    }

    public static <K, V> Dictionary<K, V> convertFromMapToDictiorary(Map<K, V> map) {
        Dictionary<K, V> dict = new Hashtable<>();
        map.forEach(dict::put);
        return dict;
    }

    public static <T> Stream<T> convertFromEnumerationToStream(Enumeration<T> enumeration) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                new Iterator<T>() {
                    @Override
                    public boolean hasNext() {
                        return enumeration.hasMoreElements();
                    }

                    @Override
                    public T next() {
                        return enumeration.nextElement();
                    }
                }, Spliterator.ORDERED), false);
    }
}
