package com.zw.admin.server.service;

import com.zw.admin.server.model.Permission;

public interface PermissionService {

	void save(Permission permission);

	void update(Permission permission);

	void delete(Long id);
}
