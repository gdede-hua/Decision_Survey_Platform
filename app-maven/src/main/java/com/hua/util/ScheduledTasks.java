package com.hua.util;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hua.model.ProblemUserExpire;
import com.hua.repository.ProblemUserExpireRepository;
/**
 * check if a survey had expire
 */
@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private final ProblemUserExpireRepository problemUserExpireRepository;

	public ScheduledTasks(ProblemUserExpireRepository problemUserExpireRepository) {
		this.problemUserExpireRepository = problemUserExpireRepository;
	}

	@Scheduled(cron="0 40 20 * * ?")//cron="1 0 0 * * ?"
	public void reportCurrentTime() {
		log.info("----- Execute Scheduler -----");
		List<ProblemUserExpire> problemUserExpireList = problemUserExpireRepository.findAllByExpireDateLessThanAndProblemStatus(new Date(), 2);
		for (ProblemUserExpire problemUserExpire : problemUserExpireList) {
			problemUserExpire.getProblem().setStatus(3);
			problemUserExpireRepository.save(problemUserExpire);

			log.info("Complete Problem:`{}`", problemUserExpire.getProblem().getName());
		}
	}
}
