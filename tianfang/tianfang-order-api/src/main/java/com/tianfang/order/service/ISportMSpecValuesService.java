package com.tianfang.order.service;

import java.util.List;

import com.tianfang.order.dto.SportMSpecValuesDto;

public interface ISportMSpecValuesService {

	long save(SportMSpecValuesDto sportMSpecValue);

	List<SportMSpecValuesDto> selectByCreate(SportMSpecValuesDto specVal);

	long delete(String id);

	long update(SportMSpecValuesDto sportMSpecValue);

}
