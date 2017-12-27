package com.example.ShadowSocksShare.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ShadowSocksDetailsEntityTest {

	@Test
	public void testgetLink() {
		// ssr://MjE2LjE4OS4xNTguMTQ3OjUzMDc6YXV0aF9hZXMxMjhfc2hhMTpjaGFjaGEyMDp0bHMxLjJfdGlja2V0X2F1dGg6Wkc5MVlpNXBieTl6YzNwb1puZ3ZLbVJ2ZFdJdVltbGtMM056ZW1obWVDOHFOVE13TncvP29iZnNwYXJhbT0mcmVtYXJrcz01cHlzNkxTbTVZLTM1cDJsNkllcU9tUnZkV0l1YVc4dmMzTjZhR1o0TC1tVm5PV0RqLVdmbi1XUWpUcGtiM1ZpTG1KcFpDOXpjM3BvWm5ndg
		ShadowSocksDetailsEntity entity = new ShadowSocksDetailsEntity("216.189.158.147", 5307, "mm", "chacha20", "auth_aes128_sha1", "tls1.2_ticket_auth");
		entity.setValid(true);
		entity.setRemarks("本账号来自:doub.io/sszhfx/镜像域名:doub.bid/sszhfx/");
		entity.setGroup("");
		log.debug("{}", entity.getLink());
	}


	@Test
	public void testsetLink() {
		String str = "ssr://MjE2LjE4OS4xNTguMTQ3OjM1MDc6YXV0aF9hZXMxMjhfc2hhMTpjaGFjaGEyMDp0bHMxLjJfdGlja2V0X2F1dGg6Wkc5MVlpNXBieTl6YzNwb1puZ3ZLbVJ2ZFdJdVltbGtMM056ZW1obWVDOHFNelV3TncvP3JlbWFya3M9NXB5czZMU201WS0zNXAybDZJZXFPbVJ2ZFdJdWFXOHZjM042YUdaNEwtbVZuT1dEai1XZm4tV1FqVHBrYjNWaUxtSnBaQzl6YzNwb1puZ3Y";
		log.debug("{}", new ShadowSocksDetailsEntity(str));
		// log.debug("{}", new ShadowSocksDetailsEntity(str).getLink());
	}

}