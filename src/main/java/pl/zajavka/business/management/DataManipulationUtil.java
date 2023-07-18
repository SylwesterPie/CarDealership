package pl.zajavka.business.management;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class DataManipulationUtil {

    public static List<String> dataAsList(final String line) {
        return Arrays.stream(line.split("->")[1].trim().split(";")).toList();
    }
}
