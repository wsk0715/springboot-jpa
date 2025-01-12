package com.example.springboot_jpa.common.log.logger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueryExecutionLogger {

	private final SessionFactory sessionFactory;


	public void log() {
		Statistics stats = sessionFactory.getStatistics();

		if (stats.getQueryExecutionCount() == 0) {
			return;
		}

		log.info("[Query Statistics] Query Count: {}, Max Time: {} ms, Entity Count: {}",
				 stats.getQueryExecutionCount(),
				 stats.getQueryExecutionMaxTime(),
				 stats.getEntityFetchCount());
		stats.clear();
	}

}
