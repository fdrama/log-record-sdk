package io.github.pagoda.support.function;

/**
 * @author fdrama
 * date 2023年07月28日 9:22
 */
public interface ParseFunction {

    /**
     * function name
     *
     * @return
     */
    String functionName();

    /**
     * apply function
     *
     * @param value
     * @return
     */
    String apply(Object... value);

}
