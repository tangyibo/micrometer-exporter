package com.weishao.micrometer.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;

@Service
public class TestService {

	private static Map<String, Double> strongRefGauge = new ConcurrentHashMap<>();

	public void doTest() {

		Tag t = Tag.of("tag_name", "tag_val");
		Metrics.counter("my_user_counter_total", Collections.singletonList(t)).increment();

		// 这里存放需要考虑强引用问题，否在会被垃圾回收导致显示为NaN
		// https://micrometer.io/docs/concepts#_why_is_my_gauge_reporting_nan_or_disappearing
		double val = System.currentTimeMillis() / 100.0;
		System.out.println("random value =" + val);
		strongRefGauge.put("gauage", val);

		Tag t1 = Tag.of("tag_name_1", "tag_val_1");
		Tag t2 = Tag.of("tag_name_2", "tag_val_2");
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(t1);
		tags.add(t2);
		Metrics.gauge("my_user_gauge_value", tags, strongRefGauge, (x) -> x.get("gauage"));
	}
}
