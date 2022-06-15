package com.esign.service.configuration.utils;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

public interface MessageByLocaleService {
  String getMessage(String id);

  @Component
  class MessageByLocaleServiceImpl implements MessageByLocaleService {

    @Autowired private MessageSource messageSource;

    @Override
    public String getMessage(String id) {
      /*Locale locale = LocaleContextHolder.getLocale();*/
      Locale locale = new Locale("en", "US");
      return messageSource.getMessage(id, null, locale);
    }
  }
}
