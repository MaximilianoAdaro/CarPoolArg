package austral.ing.lab1.util;

import java.util.List;

public class LangUtils {

  private LangUtils() {}

  public static String notEmpty(String value, String defaultValue) {
    return value == null || value.isEmpty() ? defaultValue : value;
  }

  public static <E> List<E> checkedList(List list) {
    //noinspection unchecked
    return (List<E>) list;
  }


}
