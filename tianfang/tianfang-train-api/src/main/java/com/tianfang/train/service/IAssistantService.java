package com.tianfang.train.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.AddAssistantReqDto;
import com.tianfang.train.dto.AssistantDto;
import com.tianfang.train.dto.AssistantSpaceTimeRespDto;
import com.tianfang.train.dto.LocaleIndexDto;


public interface IAssistantService {

	/**
	 *  登录验证
	 * 
	 * @param loginName 用户名
	 * @param loginPassword 密码
	 * @return 现场负责人信息
	 */
    public AssistantDto checkAccount(String loginName, String loginPassword);
	
	/**
	 * 根据负责人id查询所管理的场所信息
	 * 
	 * @param assistantId
	 * @return
	 */
	public List<LocaleIndexDto> queryAddressByAssistantId(String assistantId);
	
	public PageResult<AssistantDto> findPage(AssistantDto assistantDto,PageQuery page);
	
	public Object save(AddAssistantReqDto assistantDto,String traTimeAddresss, String addressId);
	
	public Object edit(AssistantDto assistantDto,String traTimeAddresss,String addressId);
	
	public Object delAsIds(String  delAsIds) ;
	
	public AssistantDto getAssistant(String id) ;
	
	public Object save(AddAssistantReqDto assistantDto, String jsonClasss);
	
    public List<AssistantSpaceTimeRespDto> findAssistantAddressTimeById(String id);
    
    public Object edit(AddAssistantReqDto assistantDto, String jsonClasss);
}