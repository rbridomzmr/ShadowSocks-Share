package com.example.ShadowSocksShare.entity;

import lombok.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.net.URL;

/**
 * ssr 信息
 * 参考：
 * https://www.zfl9.com/ssr.html
 * http://rt.cn2k.net/?p=328
 * https://vxblue.com/archives/115.html
 */
@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "remarks", "group"})
public class ShadowSocksDetailsEntity implements Serializable {
	// SSR 连接 分隔符
	public static final String SSR_LINK_SEPARATOR = ":";
	private static final long serialVersionUID = 952212276705742190L;

	// 必填字段
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	@NonNull
	private String server; // 地址

	@Column
	@NonNull
	private int server_port;    // 端口

	@Column
	@NonNull
	private String password;    // 密码

	@Column
	@NonNull
	private String method;    // 加密

	@Column
	@NonNull
	private String protocol;    // 协议

	@Column
	@NonNull
	private String obfs;    // 混淆

	// 非必填
	@Column
	private String remarks;    // 备注

	@Column(name = "grp")
	private String group; // 组

	@Column
	private boolean isValid;    // 是否有效


	public ShadowSocksDetailsEntity(String ssrLink) {
		if (StringUtils.isNotBlank(ssrLink) && StringUtils.startsWithIgnoreCase(ssrLink, "ssr")) {
			String ssrInfoStr = new String(Base64.decodeBase64(StringUtils.remove(ssrLink, "ssr://")));
			try {
				System.out.println(ssrInfoStr);

				// 按照 /? 拆分，前半段为 主要配置信息，后半段为 URL 参数
				String[] strs = StringUtils.split(ssrInfoStr, "/?");

				// ssr://server:port:protocol:method:obfs:password_base64/?suffix_base64
				// obfsparam=obfsparam_base64&protoparam=protoparam_base64&remarks=remarks_base64&group=group_base64

				String[] ssInfo = StringUtils.split(strs[0], ":", 6);
				this.server = ssInfo[0];
				this.server_port = Integer.parseInt(ssInfo[1]);
				this.protocol = ssInfo[2];
				this.method = ssInfo[3];
				this.obfs = ssInfo[4];
				this.password = new String(Base64.decodeBase64(ssInfo[5]));

				/*String suffix_base64 = strs[1];
				byte[] _remarks = Base64.decodeBase64(StringUtils.substringBetween(suffix_base64, "remarks=", "&"));
				if (_remarks != null && _remarks.length > 0)
					this.remarks = new String(_remarks);

				byte[] _group = Base64.decodeBase64(StringUtils.substringBetween(suffix_base64, "group="));
				if (_group != null && _group.length > 0)
					this.group = new String(_group);*/
			} catch (Exception e) {
				throw new RuntimeException("SSR 连接[" + ssrInfoStr + "]解析异常：" + e.getMessage(), e);
			}
		} else {
			throw new IllegalArgumentException("SSR 连接[" + ssrLink + "]解析异常：协议类型错误");
		}
	}

	public String getLink() {
		// 104.236.187.174:1118:auth_sha1_v4:chacha20:tls1.2_ticket_auth:ZGFzamtqZGFr/?obfsparam=&remarks=MTExOCDml6fph5HlsbEgMTDkurogMTAwRyBTU1I&group=Q2hhcmxlcyBYdQ
		StringBuilder link = new StringBuilder();
		link
				.append(server)
				.append(SSR_LINK_SEPARATOR).append(server_port)
				.append(SSR_LINK_SEPARATOR).append(protocol)
				.append(SSR_LINK_SEPARATOR).append(method)
				.append(SSR_LINK_SEPARATOR).append(obfs)
				.append(SSR_LINK_SEPARATOR).append(Base64.encodeBase64URLSafeString(password.getBytes()))
				.append("/?obfsparam=")
				.append("&remarks=").append(Base64.encodeBase64URLSafeString((remarks != null ? remarks : "").getBytes()))
				.append("&group=").append(Base64.encodeBase64URLSafeString((group != null ? group : "").getBytes()));
		return "ssr://" + Base64.encodeBase64URLSafeString(link.toString().getBytes());
	}
}
