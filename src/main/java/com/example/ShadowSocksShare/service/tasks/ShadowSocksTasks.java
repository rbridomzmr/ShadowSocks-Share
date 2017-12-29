package com.example.ShadowSocksShare.service.tasks;

import com.example.ShadowSocksShare.service.ShadowSocksCrawlerService;
import com.example.ShadowSocksShare.service.ShadowSocksSerivce;
import com.example.ShadowSocksShare.service.impl.DoubCrawlerServiceImpl;
import com.example.ShadowSocksShare.service.impl.IShadowCrawlerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 定时抓取 ss 信息
 */
@Slf4j
@Component
public class ShadowSocksTasks {
	@Autowired
	private ShadowSocksSerivce shadowSocksSerivce;
	@Autowired
	@Qualifier("iShadowCrawlerServiceImpl")
	private ShadowSocksCrawlerService iShadowCrawlerServiceImpl;    // ishadow
	@Autowired
	@Qualifier("doubCrawlerServiceImpl")
	private ShadowSocksCrawlerService doubCrawlerServiceImpl;                // https://doub.io

	/**
	 * 第一次延迟 10 秒执行，之后每 fixedRate 执行一次
	 */
	@Scheduled(initialDelay = 10 * 1000, fixedRate = IShadowCrawlerServiceImpl.REFRESH_TIME)
	public void iShadowCrawler() {
		shadowSocksSerivce.crawlerAndSave(iShadowCrawlerServiceImpl);
	}

	@Scheduled(initialDelay = 20 * 1000, fixedRate = DoubCrawlerServiceImpl.REFRESH_TIME)
	public void doubCrawler() {
		shadowSocksSerivce.crawlerAndSave(doubCrawlerServiceImpl);
	}

	/**
	 * 为防止 herokuapp 休眠，每 10 分钟访问一次
	 */
	@Scheduled(initialDelay = 10 * 60 * 1000, fixedRate = 10 * 60 * 1000)
	public void monitor() throws IOException {
		Jsoup.connect("https://shadowsocks-share.herokuapp.com/subscribe").get();
	}

	/**
	 * SS 有效性检查，每 1 小时
	 */
	// @Scheduled(cron = "0 */2 * * * ?")
	@Scheduled(cron = "0 0 */1 * * ?")
	public void checkValid() {
		log.debug("定时有效性检查开始...");
		shadowSocksSerivce.checkValid();
		log.debug("定时有效性检查结束...");
	}
}
