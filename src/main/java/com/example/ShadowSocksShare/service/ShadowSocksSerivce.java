package com.example.ShadowSocksShare.service;


import com.example.ShadowSocksShare.entity.ShadowSocksEntity;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.List;

/**
 * 1. 爬取 目标网站 SS 信息
 * 2. SS 信息入库
 * 3. 前台页面展示信息
 */
public interface ShadowSocksSerivce {
	/**
	 * 1. 爬取 SS 并入库
	 * 2. SS 信息入库
	 */
	void crawlerAndSave(ShadowSocksCrawlerService service);

	/**
	 * 3. 查询 SS 信息
	 */
	List<ShadowSocksEntity> findAll(int page, int size);

	/**
	 * 3. 生成 SSR 连接
	 */
	String toSSLink(List<ShadowSocksEntity> entities, boolean valid);

	/**
	 * 生成二维码
	 */
	byte[] createQRCodeImage(String text, int width, int height) throws WriterException, IOException;
}
