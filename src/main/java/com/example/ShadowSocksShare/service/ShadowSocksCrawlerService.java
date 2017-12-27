package com.example.ShadowSocksShare.service;

import com.example.ShadowSocksShare.entity.ShadowSocksDetailsEntity;
import com.example.ShadowSocksShare.entity.ShadowSocksEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.Date;
import java.util.Set;

/**
 * SSR 爬虫 抽象类
 * 本类只负责 爬取 SS 信息，入库操作由 ShadowSocksSerivce 处理
 * 1. 请求 目标地址 获取 Document
 * 2. 构造 ShadowSocksEntity 对象
 * 3. 解析 Document 构造 ShadowSocksSet
 */
@Slf4j
public abstract class ShadowSocksCrawlerService {
	// 目标网站请求超时时间（60 秒）
	private static final int TIME_OUT = 60 * 1000;
	// 测试网络超时时间（3 秒）
	private static final int SOCKET_TIME_OUT = 3 * 1000;

	/**
	 * 请求目标 URL 获取 Document
	 */
	private Document getDocument() throws IOException {
		Document document;
		try {
			document = Jsoup.connect(getTargetURL())
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36")
					.referrer("https://www.google.com/")
					.ignoreContentType(true)
					.followRedirects(true)
					.timeout(TIME_OUT)
					// .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 1080)))
					.get();
		} catch (IOException e) {
			throw new IOException("请求[" + getTargetURL() + "]异常：" + e.getMessage(), e);
		}
		return document;
	}

	/**
	 * 爬取 ss 账号
	 */
	public ShadowSocksEntity getShadowSocks() {
		try {
			Document document = getDocument();
			ShadowSocksEntity entity = new ShadowSocksEntity(getTargetURL(), document.title(), true, new Date());
			entity.setShadowSocksSet(parse(document));
			return entity;
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return new ShadowSocksEntity(getTargetURL(), "", false, new Date());
	}

	/**
	 * 网络连通性测试
	 */
	protected boolean isReachable(ShadowSocksDetailsEntity ss) {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(ss.getServer(), ss.getServer_port()), SOCKET_TIME_OUT);
			return true;
		} catch (IOException e) {
			// log.error(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * 连接解析
	 */
	protected ShadowSocksDetailsEntity parseLink(String link) {

		if (StringUtils.isNotBlank(link) && StringUtils.startsWithIgnoreCase(link, "ssr")) {
			String ssrInfoStr = new String(Base64.decodeBase64(StringUtils.remove(link, "ssr://")));
			try {
				System.out.println(ssrInfoStr);

				// 按照 /? 拆分，前半段为 主要配置信息，后半段为 URL 参数
				String[] strs = StringUtils.split(ssrInfoStr, "/?");

				// ssr://server:port:protocol:method:obfs:password_base64/?suffix_base64
				// obfsparam=obfsparam_base64&protoparam=protoparam_base64&remarks=remarks_base64&group=group_base64

				String[] ssInfo = StringUtils.split(strs[0], ":", 6);

				ShadowSocksDetailsEntity entity = new ShadowSocksDetailsEntity(ssInfo[0], Integer.parseInt(ssInfo[1]), new String(Base64.decodeBase64(ssInfo[5])), ssInfo[3], ssInfo[2], ssInfo[4]);

				/*String suffix_base64 = strs[1];
				byte[] _remarks = Base64.decodeBase64(StringUtils.substringBetween(suffix_base64, "remarks=", "&"));
				if (_remarks != null && _remarks.length > 0)
					this.remarks = new String(_remarks);

				byte[] _group = Base64.decodeBase64(StringUtils.substringBetween(suffix_base64, "group="));
				if (_group != null && _group.length > 0)
					this.group = new String(_group);*/
				return entity;
			} catch (Exception e) {
				throw new RuntimeException("SSR 连接[" + ssrInfoStr + "]解析异常：" + e.getMessage(), e);
			}
		} else {
			throw new IllegalArgumentException("SSR 连接[" + link + "]解析异常：协议类型错误");
		}
	}

	/**
	 * 网页内容解析 ss 信息
	 */
	protected abstract Set<ShadowSocksDetailsEntity> parse(Document document);

	/**
	 * 目标网站 URL
	 */
	protected abstract String getTargetURL();
}
