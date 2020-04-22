package com.zw.admin.server.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zw.admin.server.page.table.PageTableRequest;
import com.zw.admin.server.page.table.PageTableHandler;
import com.zw.admin.server.page.table.PageTableResponse;
import com.zw.admin.server.page.table.PageTableHandler.CountHandler;
import com.zw.admin.server.page.table.PageTableHandler.ListHandler;
import com.zw.admin.server.dao.DictDao;
import com.zw.admin.server.model.Dict;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/dicts")
public class DictController {

	@Autowired
	private DictDao dictDao;

	@RequiresPermissions("dict:add")
	@PostMapping
	@ApiOperation(value = "保存")
	public Dict save(@RequestBody Dict dict) {
		Dict d = dictDao.getByTypeAndK(dict.getType(), dict.getK());
		if (d != null) {
			throw new IllegalArgumentException("类型和key已存在");
		}
		dictDao.save(dict);

		return dict;
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取")
	public Dict get(@PathVariable Long id) {
		return dictDao.getById(id);
	}

	@RequiresPermissions("dict:add")
	@PutMapping
	@ApiOperation(value = "修改")
	public Dict update(@RequestBody Dict dict) {
		dictDao.update(dict);

		return dict;
	}

	@RequiresPermissions("dict:query")
	@GetMapping(params = { "start", "length" })
	@ApiOperation(value = "列表")
	public PageTableResponse list(PageTableRequest request) {
		return new PageTableHandler(new CountHandler() {

			@Override
			public int count(PageTableRequest request) {
				return dictDao.count(request.getParams());
			}
		}, new ListHandler() {

			@Override
			public List<Dict> list(PageTableRequest request) {
				return dictDao.list(request.getParams(), request.getOffset(), request.getLimit());
			}
		}).handle(request);
	}

	@RequiresPermissions("dict:del")
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除")
	public void delete(@PathVariable Long id) {
		dictDao.delete(id);
	}

	@GetMapping(params = "type")
	public List<Dict> listByType(String type) {
		return dictDao.listByType(type);
	}
}
