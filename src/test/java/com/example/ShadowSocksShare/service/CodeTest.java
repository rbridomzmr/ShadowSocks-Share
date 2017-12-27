package com.example.ShadowSocksShare.service;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Base64;

@Slf4j
public class CodeTest {

	@Test
	public void test() {
		// 104.236.187.174:1118:auth_sha1_v4:chacha20:tls1.2_ticket_auth:ZGFzamtqZGFr/?obfsparam=&remarks=MTExOCDml6fph5HlsbEgMTDkurogMTAwRyBTU1I&group=Q2hhcmxlcyBYdQ
		String str1 = "c3NyOi8vWWk1cGMzaGhMbUpwWkRveE1qTTNPVHBoZFhSb1gzTm9ZVEZmZGpRNllXVnpMVEkxTmkxalptSTZkR3h6TVM0eVgzUnBZMnRsZEY5aGRYUm9PazVFVlRGTlZHTTBUbFJOTHo5dlltWnpjR0Z5WVcwOUpuSmxiV0Z5YTNNOVlVaFNNR05JVFRaTWVUbHVZa2M1YVZsWGQzVmhXRTV2V1ZkU2RtUXpaM1ZpYlZZd1RIY21aM0p2ZFhBOVlWWk9iMWxYVW5aa2R3DQpzc3I6Ly9ZUzVwYzNoakxtSnBaRG94T0RZNE9UcGhkWFJvWDNOb1lURmZkalE2WVdWekxUSTFOaTFqWm1JNmRHeHpNUzR5WDNScFkydGxkRjloZFhSb09rMVVhek5OYWtsM1RVUlJMejl2WW1aemNHRnlZVzA5Sm5KbGJXRnlhM005WVVoU01HTklUVFpNZVRsdVlrYzVhVmxYZDNWaFdFNXZXVmRTZG1RelozVmliVll3VEhjbVozSnZkWEE5WVZaT2IxbFhVblprZHcNCnNzcjovL1l5NXBjM2hpTG1KcFpEb3hPVFl5T1RwaGRYUm9YM05vWVRGZmRqUTZZV1Z6TFRJMU5pMWpabUk2ZEd4ek1TNHlYM1JwWTJ0bGRGOWhkWFJvT2sxVVRURlBSRVY1VDBSWkx6OXZZbVp6Y0dGeVlXMDlKbkpsYldGeWEzTTlZVWhTTUdOSVRUWk1lVGx1WWtjNWFWbFhkM1ZoV0U1dldWZFNkbVF6WjNWaWJWWXdUSGNtWjNKdmRYQTlZVlpPYjFsWFVuWmtkdw0K";


		log.debug("{}", new String(Base64.getDecoder().decode(str1)));
		// log.debug("{}", new String(Base64.getDecoder().decode(str2)));
		//log.debug("{}", new String(Base64.getDecoder().decode(str3)));
		//log.debug("{}", new String(Base64.getDecoder().decode(str4)));
	}
}
