package com.zw.admin.server.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.zw.admin.server.dto.Token;
import com.zw.admin.server.service.TokenManager;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * EhCache实现的Token<br>
 * 默认采用此实现
 * 
 * @author 小威老师 xiaoweijiagou@163.com
 *
 *         2017年8月4日
 */
@Primary
@Service
public class EhCacheTokenManager implements TokenManager {

	@Autowired
	private EhCacheManager cacheManager;
	/**
	 * token过期秒数
	 */
	@Value("${token.expire.seconds}")
	private Integer expireSeconds;

	@Override
	public Token saveToken(UsernamePasswordToken usernamePasswordToken) {
		Cache cache = cacheManager.getCacheManager().getCache("login_user_tokens");

		String key = UUID.randomUUID().toString();
		Element element = new Element(key, usernamePasswordToken);
		element.setTimeToLive(expireSeconds);
		cache.put(element);

		return new Token(key, DateUtils.addSeconds(new Date(), expireSeconds));
	}

	@Override
	public UsernamePasswordToken getToken(String key) {
		Cache cache = cacheManager.getCacheManager().getCache("login_user_tokens");
		Element element = cache.get(key);
		if (element != null) {
			return (UsernamePasswordToken) element.getObjectValue();
		}

		return null;
	}

	@Override
	public boolean deleteToken(String key) {
		Cache cache = cacheManager.getCacheManager().getCache("login_user_tokens");
		return cache.remove(key);
	}
}
