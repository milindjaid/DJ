package com.example.demo;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableCaching
public class SpringHibernateIntehrationApplication extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(new Class[] {SpringHibernateIntehrationApplication.class});
	}

	public static void main(String[] args) throws MalformedURLException {
		SpringApplication.run(SpringHibernateIntehrationApplication.class, args);
		String str = "{\"name\": \"John\",\"id\":\"007\"}";
		Gson gson = new Gson();
		Map<String, Object> jsonMap = JsonParserFactory.getJsonParser().parseMap(str);
		System.out.println("===>" + jsonMap);
		try {
			String host = InetAddress.getLocalHost().getHostName();
			System.out.println("Sys Address ===> " + host);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		double j = 0.0 / 0;
		System.out.println("value ===>" + j);

		List<String> list = new ArrayList<String>();
		list.add(0, "hello");
		list.add(1, "hi");
		list.set(1, "bye");

		ListIterator<String> iterator = (ListIterator<String>) list.listIterator();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(iterator.next());

		}

		URL url = new URL("http://login.yourbulksms.com/api/sendhttp.php");
		System.out.println(url);

	}

	private Map<String, Object> parseMapInternal(String json) {
		Map<String, Object> map = new LinkedHashMap<>();
		json = trimLeadingCharacter(trimTrailingCharacter(json, '}'), '{').trim();
		for (String pair : tokenize(json)) {
			String[] values = StringUtils.trimArrayElements(StringUtils.split(pair, ":"));
			String key = trimLeadingCharacter(trimTrailingCharacter(values[0], '"'), '"');
			Object value = parseInternal(values[1]);
			map.put(key, value);
		}
		return map;
	}

	private static String trimTrailingCharacter(String string, char c) {
		if (!string.isEmpty() && string.charAt(string.length() - 1) == c) {
			return string.substring(0, string.length() - 1);
		}
		return string;
	}

	private static String trimLeadingCharacter(String string, char c) {
		if (!string.isEmpty() && string.charAt(0) == c) {
			return string.substring(1);
		}
		return string;
	}

	private List<String> tokenize(String json) {
		List<String> list = new ArrayList<>();
		int index = 0;
		int inObject = 0;
		int inList = 0;
		boolean inValue = false;
		boolean inEscape = false;
		StringBuilder build = new StringBuilder();
		while (index < json.length()) {
			char current = json.charAt(index);
			if (inEscape) {
				build.append(current);
				index++;
				inEscape = false;
				continue;
			}
			if (current == '{') {
				inObject++;
			}
			if (current == '}') {
				inObject--;
			}
			if (current == '[') {
				inList++;
			}
			if (current == ']') {
				inList--;
			}
			if (current == '"') {
				inValue = !inValue;
			}
			if (current == ',' && inObject == 0 && inList == 0 && !inValue) {
				list.add(build.toString());
				build.setLength(0);
			} else if (current == '\\') {
				inEscape = true;
			} else {
				build.append(current);
			}
			index++;
		}
		if (build.length() > 0) {
			list.add(build.toString().trim());
		}
		return list;
	}

	private Object parseInternal(String json) {
		if (json.startsWith("[")) {
			return parseListInternal(json);
		}
		if (json.startsWith("{")) {
			return parseMapInternal(json);
		}
		if (json.startsWith("\"")) {
			return trimTrailingCharacter(trimLeadingCharacter(json, '"'), '"');
		}
		try {
			return Long.valueOf(json);
		} catch (NumberFormatException ex) {
			// ignore
		}
		try {
			return Double.valueOf(json);
		} catch (NumberFormatException ex) {
			// ignore
		}
		return json;
	}

	private List<Object> parseListInternal(String json) {
		List<Object> list = new ArrayList<>();
		json = trimLeadingCharacter(trimTrailingCharacter(json, ']'), '[').trim();
		for (String value : tokenize(json)) {
			list.add(parseInternal(value));
		}
		return list;
	}
}
