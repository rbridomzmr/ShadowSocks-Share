package com.example.ShadowSocksShare.entity;

import lombok.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.net.URL;
import java.util.Date;

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
	private boolean valid;    // 是否有效

	@Column
	private Date validTime;		// 有效性验证时间

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
