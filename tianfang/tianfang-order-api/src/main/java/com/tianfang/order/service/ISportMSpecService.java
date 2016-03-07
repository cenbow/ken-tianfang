package com.tianfang.order.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.order.dto.SportMSpecDto;

public interface ISportMSpecService {

	long save(SportMSpecDto sportMSpec);

	long edit(SportMSpecDto sportMSpec);

	Object delete(String id);

	PageResult<SportMSpecDto> selectPageAll(SportMSpecDto sportMSpec,PageQuery changeToPageQuery);

	SportMSpecDto selectById(String id);

	List<SportMSpecDto> selectAll();
}
