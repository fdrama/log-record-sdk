package io.github.pagoda.support.function;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fdrama
 * date 2023年07月28日 9:28
 */
public class ParseFunctionFactory {

    private Map<String, ParseFunction> parseFunctionMap;

    public ParseFunctionFactory(List<ParseFunction> parseFunctions) {
        if (CollectionUtils.isEmpty(parseFunctions)) {
            return;
        }
        parseFunctionMap = new HashMap<>();
        for (ParseFunction parseFunction : parseFunctions) {
            if (!StringUtils.hasText(parseFunction.functionName())) {
                continue;
            }
            parseFunctionMap.put(parseFunction.functionName(), parseFunction);
        }
    }

    public ParseFunction getFunction(String functionName) {
        return parseFunctionMap.get(functionName);
    }

}
