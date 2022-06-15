package com.esign.service.configuration.utils;

import com.google.common.annotations.VisibleForTesting;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class PageUtils {

  private PageUtils() {
    throw new IllegalStateException("PageUtils class");
  }

  public static Pageable getPageable(Integer offSet, Integer limit) {
    int page = (offSet == null || offSet.equals(0)) ? 0 : offSet - 1;
    int size = (limit == null || limit.equals(0)) ? 10 : limit;
    return PageRequest.of(page, size);
  }

  public static Pageable getPageable(Integer offSet, Integer limit, String sort, String direction) {
    int page = (offSet == null || offSet.equals(0)) ? 0 : offSet - 1;
    int size = (limit == null || limit.equals(0)) ? 10 : limit;
    if (StringUtils.isEmpty(sort)) return PageRequest.of(page, size);
    if (direction.toUpperCase().equals("DESC")) {
      return PageRequest.of(page, size, Sort.by(sort).descending());
    } else {
      return PageRequest.of(page, size, Sort.by(sort).ascending());
    }
  }
}
