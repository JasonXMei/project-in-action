package com.spring.boot.task;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogTimeTask {

    /*
		@Scheduled(fixedRate = 5000) ：上一次开始执行时间点之后5秒再执行
		@Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行
		@Scheduled(initialDelay=1000, fixedRate=5000) ：第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
		注意，这里的时间，单位是毫秒，1秒=1000毫秒

		cron ：通过cron表达式定义规则
    */
    @Scheduled(initialDelay = 1000, fixedRate = 1000 * 60 * 60)
    public void logTime() {
        log.info("开始执行定时任务，时间：[{}]", DateUtil.now());
    }

}
