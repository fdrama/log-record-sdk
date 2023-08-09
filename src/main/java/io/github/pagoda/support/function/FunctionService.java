package io.github.pagoda.support.function;

/**
 * @author fdrama
 * date 2023年07月28日 17:15
 */
public interface FunctionService {

    /**
     * apply function
     *
     * @param functionName
     * @param value
     * @return
     */
    String apply(String functionName, Object... value);

}
