package com.zw.admin.server.service;

import java.util.List;

import com.zw.admin.server.model.Mail;

public interface MailService {

	void save(Mail mail, List<String> toUser);
}
