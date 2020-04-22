package com.zw.admin.server.service;

import com.zw.admin.server.dto.RoleDto;

public interface RoleService {

	void saveRole(RoleDto roleDto);

	void deleteRole(Long id);
}
