package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Workbill;
import com.itheima.bos.domain.Workordermanage;

public interface WorkordermanagerService {

	void save(Workordermanage model);

	List<Workordermanage> findListNotStart();

	public void start(String id);

	public Workordermanage findById(String workordermanageId);

	public void checkWorkordermanage(String taskId, Integer check, String workordermanageId);
	
}
