package com.example.ShadowSocksShare.controller;


import com.example.ShadowSocksShare.entity.ShadowSocksDetailsEntity;
import com.example.ShadowSocksShare.entity.ShadowSocksEntity;
import com.example.ShadowSocksShare.service.ShadowSocksSerivce;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class MainController {
	@Autowired
	private ShadowSocksSerivce shadowSocksSerivceImpl;

	/**
	 * 首页
	 */
	@RequestMapping("/")
	public String index(Model model) {
		List<ShadowSocksEntity> ssrList = shadowSocksSerivceImpl.findAll(1, 50);
		List<ShadowSocksDetailsEntity> ssrdList = new ArrayList<>();
		for(ShadowSocksEntity ssr : ssrList) {
			ssrdList.addAll(ssr.getShadowSocksSet());
		}
		// ssr 信息
		model.addAttribute("ssrList", ssrList);
		// ssr 明细信息
		model.addAttribute("ssrdList", ssrdList);
		return "index";
	}

	/**
	 * SSR 订阅地址
	 *
	 * @return
	 */
	@RequestMapping("/subscribe")
	@ResponseBody
	public String subscribe() {
		List<ShadowSocksEntity> ssrList = shadowSocksSerivceImpl.findAll(1, 50);
		String ssrLink = shadowSocksSerivceImpl.toSSLink(ssrList);
		return StringUtils.isNotBlank(ssrLink) ? ssrLink : "无有效 SSR 连接，请稍后重试！";
	}

	/**
	 * 二维码
	 */
	@RequestMapping(value = "/createQRCode", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	@ResponseBody
	public ResponseEntity<byte[]> createQRCode(String text, int width, int height) throws IOException, WriterException {
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE)).body(shadowSocksSerivceImpl.createQRCodeImage(text, width, height));
	}
}
