package com.example.ShadowSocksShare.service;

import com.example.ShadowSocksShare.BaseTest;
import com.example.ShadowSocksShare.dao.ShadowSocksRepository;
import com.example.ShadowSocksShare.entity.ShadowSocksDetailsEntity;
import com.example.ShadowSocksShare.entity.ShadowSocksEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.HashSet;

@Slf4j
public class ShadowSocksCrawlerServiceTest extends BaseTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ShadowSocksCrawlerService iShadowCrawlerServiceImpl;
	@Autowired
	private ShadowSocksRepository shadowSocksRepository;

	@Test
	public void getShadowSocks() {
		log.debug("{}", iShadowCrawlerServiceImpl.getShadowSocks().getLink(false));
	}

	public void aa() {
		ShadowSocksEntity socksEntity = new ShadowSocksEntity("targetURL", "title", true, new Date());
		socksEntity.setShadowSocksSet(new HashSet<>());

		ShadowSocksDetailsEntity entity = new ShadowSocksDetailsEntity("216.189.158.147", 5307, "mm", "chacha20", "auth_aes128_sha1", "tls1.2_ticket_auth");
		entity.setValid(true);
		entity.setRemarks("本账号来自:doub.io/sszhfx/镜像域名:doub.bid/sszhfx/");
		entity.setGroup("");

		socksEntity.getShadowSocksSet().add(entity);

		shadowSocksRepository.save(socksEntity);
	}
}