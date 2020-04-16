package com.springboot.config;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import com.springboot.util.AppHelper;

public class MyLocaleResolver implements LocaleResolver {

	public Locale resolveLocale(HttpServletRequest request) {
		Locale locale = new Locale("en", "US");
		String i18n = (String) AppHelper.findMap("i18n");
		if (!StringUtils.isEmpty(i18n)) {
			String[] list = i18n.split("_");
			locale = new Locale(list[0], list[1]);
		}
		return locale;
	}

	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		// 设置Locale
	}

}
