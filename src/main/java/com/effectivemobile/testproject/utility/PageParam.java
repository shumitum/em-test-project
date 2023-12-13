package com.effectivemobile.testproject.utility;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;

@UtilityClass
public class PageParam {
    public static PageRequest of(int from, int size) {
        return PageRequest.of(from > 0 ? from / size : 0, size);
    }
}