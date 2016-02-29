package com.tianfang.home.controller;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Objects;
import com.tianfang.business.dto.SportAddressesDto;
import com.tianfang.business.service.ISportAddressesService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.SessionConstants;
import com.tianfang.common.digest.MD5Coder;
import com.tianfang.common.exception.SystemException;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.Response;
import com.tianfang.common.tools.RandomPicTools;
import com.tianfang.common.util.StringUtils;
import com.tianfang.home.util.SessionUtil;
import com.tianfang.user.dto.LoginUserDto;
import com.tianfang.user.dto.Notifications;
import com.tianfang.user.dto.SportNotificationsDto;
import com.tianfang.user.dto.SportShippingAddressDto;
import com.tianfang.user.dto.SportUserReqDto;
import com.tianfang.user.service.IEmailSendService;
import com.tianfang.user.service.ISmsSendService;
import com.tianfang.user.service.ISportNotificationsService;
import com.tianfang.user.service.ISportShippingAddressService;
import com.tianfang.user.service.IUserService;

@Controller
@RequestMapping(value = "/userMan")
public class UserController extends BaseController{
	protected static final Log logger = LogFactory.getLog(UserController.class);
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private ISportAddressesService addressService;
	
	@Autowired
	private IUserService iUserService;	
	
	@Autowired
	private ISportShippingAddressService issaService;
	
	@Autowired
	private ISportNotificationsService inotification;
	
	@Autowired
	private ISmsSendService iSmsSendService;
	
	@Autowired
	private IEmailSendService iEmailSendService;
	
	@RequestMapping("/reg")
	public ModelAndView toReg(){
		ModelAndView mv = getModelAndView();
		mv.setViewName("/reg");
		return mv;
	}
	@RequestMapping("index")
	public ModelAndView toIndex(){
		ModelAndView mv = getModelAndView();
		mv.setViewName("/index");
		return mv;
	}
	
	
	@RequestMapping(value = "/myteam")
	public ModelAndView myteam(){
		String redict = "";
		if(getTeamIdByUserId()!=null){
			redict = "redirect:/tc/index/"+getTeamIdByUserId()+".htm";	
		}else{
			redict = "redirect:/index.htm";
		}
		return new ModelAndView(redict);
	}
    /**
     * V3.0注册用户
     * @author YIn 20151223
     * @time:2015年11月19日 下午2:14:37
     */
	@RequestMapping(value = "/register")
	@ResponseBody
	public Response<SportUserReqDto> register(
			HttpSession session,
			SportUserReqDto sportUserReqDto,
			@RequestParam(value = "randomPic", required = false) String randomPic) {
		logger.debug("SportUserReqDto：" + sportUserReqDto);
		Response<SportUserReqDto> result = new Response<SportUserReqDto>();
		//sportUserReqDto.setUtype(UserTypeEnum.NORMALUSER.getIndex());
		String md5oldPwd;// 获取页面上输入的密码并加密校验
		try {
			md5oldPwd = MD5Coder.encodeMD5Hex(sportUserReqDto.getPassword());
			sportUserReqDto.setPassword(md5oldPwd);
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
		String key = "";
		if(StringUtils.isBlank(sportUserReqDto.getMobile())){
			 key = "reg" + sportUserReqDto.getEmail();
		}else{
			 key = "reg" + sportUserReqDto.getMobile();
		}
		if(!StringUtils.isBlank(randomPic)){
			int num;
			try {
				num = Integer.parseInt(randomPic);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("输入的短信验证码不是4位数字！");
				return result;
			}
			if (redisTemplate.opsForValue().get(key) == null) {
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("没有点击获取验证码！");
				return result;
			}if(!redisTemplate.opsForValue().get(key).equals(num)){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("手机验证码输入错误！");
				return result;
			}
		}
		if (iUserService.checkRepeat(sportUserReqDto) > 0) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户名已存在！");
			return result;
		}
		SportUserReqDto dto = iUserService.register(sportUserReqDto);
		if (dto == null) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("注册失败！");
			return result;
		} else {
			/*LoginUserDto loginUserDto = new LoginUserDto();
			loginUserDto.setId(loginUserDto.getId());
			loginUserDto.setType(loginUserDto.getType());
			session.setAttribute(SessionConstants.LOGIN_USER_INFO, loginUserDto);*/
			if(!StringUtils.isEmpty(dto.getEmail())){
				dto.setUserAccount(dto.getEmail());
			}
			if(!StringUtils.isEmpty(dto.getMobile())){
				dto.setUserAccount(dto.getMobile());
			}
			SportUserReqDto userInfo = iUserService.checkUser(dto);
			LoginUserDto loginUserDto = new LoginUserDto();
			loginUserDto.setId(userInfo.getId());
			loginUserDto.setType(userInfo.getUtype());
			session.setAttribute(SessionConstants.LOGIN_USER_INFO, loginUserDto);
			if(userInfo != null){
				//result.setData(sportUserReqDto.getUserAccount());
				loginUserDto.setUserAccount(sportUserReqDto.getUserAccount());
				redisTemplate.opsForValue().set(userInfo.getId(), loginUserDto);
			}
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("恭喜您注册成功！");
			return result;
		}
	}
	
	/**
	 * 验证码绘图
	 * @author YIn
	 * @time:2015年11月19日 下午6:02:22
	 * @param reponse
	 * @param request
	 * @param session
	 */
	@RequestMapping(value = "/drawRandom")
	@ResponseBody
	public void drawRandom(HttpServletResponse reponse,
			HttpServletRequest request, HttpSession session) {
		RandomPicTools randomPicTools = new RandomPicTools();
		int width = 80;// 图片宽
		int height = 26;// 图片高
		int lineSize = 40;// 干扰线数量
		int stringNum = 4;// 随机产生字符数量
		session = request.getSession();
		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
		g.setColor(randomPicTools.getRandColor(110, 133));
		// 绘制干扰线
		for (int i = 0; i <= lineSize; i++) {
			randomPicTools.drowLine(g, width, height);
		}
		// 绘制随机字符
		String randomString = "";
		for (int i = 1; i <= stringNum; i++) {
			randomString = randomPicTools.drowString(g, randomString, i);
		}
		session.removeAttribute("RandomCode");
		session.setAttribute("RandomCode", randomString);
		g.dispose();
		try {
			// 将内存中的图片通过流动形式输出到客户端
			ImageIO.write(image, "JPEG", reponse.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 聚运动V3 检验图片验证码
	 * @author YIn
	 * @time:2015年11月19日 上午12:39:23
	 * @param reponse
	 * @param request
	 * @param session
	 */
	@RequestMapping(value = "/checkRandom")
	@ResponseBody
	public Response<String> checkRandom(HttpServletResponse reponse,
			HttpServletRequest request, HttpSession session ,@RequestParam(value = "picCaptcha", required = false) String picCaptcha) {
		Response<String> result = new Response<String>();
		if (session.getAttribute("RandomCode") != null) {
			String randomPicSession = session.getAttribute("RandomCode")
					.toString().toLowerCase();
			if (!picCaptcha.toLowerCase().equals(randomPicSession)) {
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("验证码输入错误！");
				return result;
			}else{
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setMessage("验证码输入正确！");
				return result;
			}
		 }else {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码不存在！");
			return result;
		}
	}
	
	
	/**
	 * 聚运动用户登录(记住密码的情况下) BY ：Yin 20170702
	 * @param accountName
	 * @param pwd
	 * @param randomPic
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/loginRemPwd")
	@ResponseBody
	public Response<String> loginRemPwd(HttpServletRequest request,
			HttpServletResponse response, SportUserReqDto sportUserReqDto,
			String repwdflag, String randomPic, HttpSession session) {
		logger.debug("user login :SportUserReqDto=" + sportUserReqDto 
				+ "randomPic=" + randomPic);
		Response<String> result = new Response<String>();
		if ((sportUserReqDto.getUserAccount() == "" || sportUserReqDto.getUserAccount().equals(""))){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("输入的用户帐户(手机/邮箱)为空！");
			return result;
		}
		if (sportUserReqDto.getPassword() == "" || sportUserReqDto.getPassword().equals("")){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("输入的用户密码为空！");
			return result;
		}
		SportUserReqDto dto = iUserService.checkUser(sportUserReqDto);
		if (dto == null) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户名或密码不正确,请重新输入！");
			return result;
		}
		if (repwdflag.equals("1")) {
			// 设置cookie时间为14天(2星期)
			int maxAge = 60 * 60 * 24 * 14;
			StringBuffer sb = new StringBuffer();
			if(StringUtils.isEmpty(sportUserReqDto.getUserAccount())){
				//if(sportUserReqDto.getUserAccount() != null && sportUserReqDto.getUserAccount().equals("")){
					result.setData(sportUserReqDto.getUserAccount());
				}
				sb.append(sportUserReqDto.getUserAccount());
				sb.append(",");
			sb.append(sportUserReqDto.getPassword());
			Cookie cookie = new Cookie("userInfo", sb.toString());
			cookie.setMaxAge(maxAge);
			cookie.setPath("/");
			cookie.setDomain(request.getServerName());
			response.setHeader("P3P", "CP=CAO PSA OUR");
			response.addCookie(cookie);
		}
		LoginUserDto loginUserDto = new LoginUserDto();
		loginUserDto.setId(dto.getId());
		loginUserDto.setType(dto.getUtype());
		//loginUserDto.setType(sportUserReqDto.getUtype());
		session.setAttribute(SessionConstants.LOGIN_USER_INFO, loginUserDto);
		if(dto != null){
			result.setData(sportUserReqDto.getUserAccount());
			loginUserDto.setUserAccount(sportUserReqDto.getUserAccount());
			redisTemplate.opsForValue().set(dto.getId(), loginUserDto);
		}
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("用户登录成功！");
		return result;
	}

	/**
	 * 第二版用户登录 BY ：Yin 20170702
	 * @param accountName
	 * @param pwd
	 * @param randomPic
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public Response<String> login(HttpServletRequest request,
			HttpServletResponse response, SportUserReqDto sportUserReqDto,
			String repwdflag, String randomPic, HttpSession session) {
		logger.debug("user login :SportUser=" + sportUserReqDto 
				+ "randomPic=" + randomPic);
		Response<String> result = new Response<String>();
		if ((sportUserReqDto.getUserAccount() == "" || sportUserReqDto.getUserAccount().equals(""))){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("输入的用户帐户(手机/邮箱)为空！");
			return result;
		}
		if (sportUserReqDto.getPassword() == "" || sportUserReqDto.getPassword().equals("")){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("输入的用户密码为空！");
			return result;
		}
		String md5oldPwd;// 获取页面上输入的密码并加密校验
		try {
			md5oldPwd = MD5Coder.encodeMD5Hex(sportUserReqDto.getPassword());
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
		sportUserReqDto.setPassword(md5oldPwd);
		SportUserReqDto dto= iUserService.checkUser(sportUserReqDto);
		//更新用户最后登录时间
		if (dto == null)  {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户名或密码不正确,请重新输入！");
			return result;
		}
		if (repwdflag.equals("1")) {
			// 设置cookie时间为14天 
			int maxAge = 60 * 60 * 24 * 14;
			StringBuffer sb = new StringBuffer();
			if(StringUtils.isEmpty(sportUserReqDto.getUserAccount())){
				result.setData(sportUserReqDto.getUserAccount());
			}
			
			sb.append(sportUserReqDto.getUserAccount());
			sb.append(",");
			sb.append(md5oldPwd);
			Cookie cookie = new Cookie("userInfo", sb.toString());
			cookie.setMaxAge(maxAge);
			cookie.setPath("/");
			cookie.setDomain(request.getServerName());

			response.setHeader("P3P", "CP=CAO PSA OUR");
			response.addCookie(cookie);
		}

		LoginUserDto loginUserDto = new LoginUserDto();
		loginUserDto.setId(dto.getId());
		loginUserDto.setType(dto.getUtype());
		//loginUserDto.setType(sportUserReqDto.getUtype());
		session.setAttribute(SessionConstants.LOGIN_USER_INFO, loginUserDto);
		if(dto != null){
			result.setData(sportUserReqDto.getUserAccount());
			loginUserDto.setUserAccount(sportUserReqDto.getUserAccount());
			redisTemplate.opsForValue().set(dto.getId(), loginUserDto);
		}
		
		LoginUserDto logDto= (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
    	SportUserReqDto userInfo = getLoginUser(request,response,session,logDto);
    	if(userInfo==null){
    		return null;
    	}
//    	session.removeAttribute("lastloginTime");
//    	session.setAttribute("lastloginTime", DateUtils.format(userInfo.getLastLoginTime(), "yyyy-MM-dd HH:mm"));
    	int sta = upLastLoginTime(userInfo);
		if(sta>0){
		}else{
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("系统异常,更新登录时间失败!");
			return result;
		}
    	//用户邮箱为
    	String str="";
		if(userInfo.getEmail()!=null && !userInfo.getEmail().equals("")){
			str=userInfo.getEmail();
		}else if(userInfo.getMobile()!=null && !userInfo.getMobile().equals("")){
			str=userInfo.getMobile();
		}else{
			str=UUID.randomUUID().toString();
		}
    	
		//用户登录提醒功能
    	SportNotificationsDto spDto = new SportNotificationsDto();
		spDto.setOwnerId(dto.getId());
		spDto= inotification.getByCriteria(spDto);
    	if(spDto != null){
    		if(spDto.getLoginStat() == Notifications.email.getIndex()){
    			if(spDto.getEmail()==null || spDto.getEmail().equals("")){
    				/*result.setStatus(DataStatus.HTTP_SUCCESS);
    				result.setMessage("用户邮箱未绑定~~");
    				return result;*/
    			}else{
    				send(spDto.getEmail(),session,request,str,1);
    			}
    		}
    		if(spDto.getLoginStat() == Notifications.phone.getIndex()){
    			if(userInfo.getMobile()==null || userInfo.getMobile().equals("")){
    				
    			}else{
    				upPasswordMobileVal(userInfo.getMobile(),request,str,1);
    			}
    		}
    	}
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("用户登录成功！");
		return result;
	}

	/**
	 * 用户登录成功后跳转控制
	 * @author YIn
	 * @time:2015年11月19日 下午6:49:08
	 * @param session
	 * @param request
	 * @param response
	 */
	
	@RequestMapping(value = "/umanages")
	public void umanages(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		// 获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		String redirectUrl = "/index.html";
		// 只允许登录用户进行操作
		if (loginUserDto == null) {
			redirectUrl = "/index.html";
		} else {
			if (Objects.equal(loginUserDto.getType(), DataStatus.USERTYPE)) {// 普通用户
				redirectUrl = "/personal_center.html";
			}
			/*else if (Objects.equal(loginUserDto.getType(),
					DataStatus.SITETYPE)) {// 场馆用户
				redirectUrl = "/app/site/siteIndex.html";
			}*/
		}
		try {
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
		 * 此方法描述的是：异步获取用户是否是登陆状态
		 * @author: cwftalus@163.com
		 * @version: 2015年12月1日 上午9:51:13
	 */
	@RequestMapping(value = "/checkUser")
	@ResponseBody	
	public Response<LoginUserDto> checkUser(){
		Response<LoginUserDto> data = new Response<LoginUserDto>();
		if(StringUtils.isEmpty(getSessionUserId())){
			data.setStatus(DataStatus.HTTP_FAILE);
			return data;
		}
		
		SportUserReqDto userReqDto = iUserService.selectUserAccountByUserId(getSessionUserId());// getUserAccountByUserId();
		String teamId = getTeamIdByUserId();
		LoginUserDto userDto = new LoginUserDto();
		userDto.setId(userReqDto.getId());
		userDto.setTeamId(teamId);
		if(!StringUtils.isEmpty(userReqDto.getCname())){//用户名
			userDto.setUserAccount(userReqDto.getCname());	
		}else if(!StringUtils.isEmpty(userReqDto.getNickName())){//昵称
			userDto.setUserAccount(userReqDto.getCname());
		}else if(!StringUtils.isEmpty(userReqDto.getTelephone())){//电话
			userDto.setUserAccount(userReqDto.getTelephone());
		}else if(!StringUtils.isEmpty(userReqDto.getEmail())){//邮件
			userDto.setUserAccount(userReqDto.getEmail());
		}else{
			userDto.setUserAccount("");	
		}
		data.setData(userDto);
		return data;
	}
	
	/**
	 * 用户退出操作
	 */
	@RequestMapping(value = "/loginout.htm")
	public void loginout(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		session.removeAttribute(SessionConstants.LOGIN_USER_INFO);
		//删除记录的上次登录时间
		try {
			String path = request.getContextPath() + "/index.htm";
			response.sendRedirect(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**//**
	 * 根据session 获取用户信息
	 *//*
	@RequestMapping(value = "/loadInfo.htm")
	@ResponseBody
	public Response<Object> loadInfo(HttpSession session) {
		Response<Object> result = new Response<Object>();
		// 获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		if (loginUserDto == null) {
			// return JsonUtil.getJsonStr(new RequestResult(false,
			// "非登陆用户不允许查看基本信息!"));
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("非登陆用户不允许查看基本信息!");
			return result;
		}

		UsersDto usersDto = null;
		VenusInfoDto venusInfoDto = null;
		if (Objects.equal(loginUserDto.getType(), DataStatus.USERTYPE)) {// 普通用户
			usersDto = iUserService.selectUsersByUserAccountId(loginUserDto
					.getId());
			usersDto.setUserAccount(usersDto.getNickName());
			result.setData(usersDto);
		} else if (Objects.equal(loginUserDto.getType(), DataStatus.SITETYPE)) {// 场馆用户
			venusInfoDto = iUserService
					.selectStadiumByUserAccountId(loginUserDto.getId());
			venusInfoDto.setUserAccount(venusInfoDto.getNickName());
			result.setData(venusInfoDto);
		}
		return result;
	}

	*//**
	 * 根据session 获取用户信息
	 *//*
	@RequestMapping(value = "/loadEmail.htm")
	@ResponseBody
	public Response<Object> loadEmail(HttpSession session) {
		Response<Object> result = new Response<Object>();
		// 获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		if (loginUserDto == null) {
			// return JsonUtil.getJsonStr(new RequestResult(false,
			// "非登陆用户不允许查看基本信息!"));
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("非登陆用户不允许查看基本信息!");
			return result;
		}

		UsersDto usersDto = null;
		if (Objects.equal(loginUserDto.getType(), DataStatus.USERTYPE)) {// 普通用户
			usersDto = iUserService.selectUsersByUserAccountId(loginUserDto
					.getId());
			if (usersDto != null) {
				String email = usersDto.getEmail();
				if (!StringUtils.isEmpty(email)) {
					String[] emails = email.split("@");
					result.setData("******@" + emails[1]);
				} else {
					result.setData("******@163.com");
				}
			}
		}
		return result;
	}*/
	/**
	 * 去用户详情页面
	 * @return
	 */
	@RequestMapping("/userInfo")
	public ModelAndView toUserInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session,SportAddressesDto addresses,SportShippingAddressDto shipping){
		
		ModelAndView mv = getModelAndView();
		
		LoginUserDto logDto= (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
		SportUserReqDto userInfo = getLoginUser(request,response,session,logDto);
		if(userInfo==null){
			return null;
		}
//		userInfo.setLastLoginTimeStr(session.getAttribute("lastloginTime")+"");
		shipping.setUserId(userInfo.getId());
		//获取用户对应的收货地址
		SportShippingAddressDto userShipping = issaService.getByCriteria(shipping);
		SportAddressesDto proName =new SportAddressesDto();   //省
		SportAddressesDto areName =new SportAddressesDto();	  //市
		SportAddressesDto locName = new SportAddressesDto();  //区
		if(StringUtils.isNotBlank(userInfo.getProvince())){
			proName = addressService.getAddressesById(Integer.valueOf(userInfo.getProvince()));
		}
		if(StringUtils.isNotBlank(userInfo.getArea())){
			areName = addressService.getAddressesById(Integer.valueOf(userInfo.getArea()));
		}
		if(StringUtils.isNotBlank(userInfo.getLocation())){
			locName = addressService.getAddressesById(Integer.valueOf(userInfo.getLocation()));
		}
		//获取全部区县数据
		List<SportAddressesDto> lis = new ArrayList<SportAddressesDto>();
		addresses.setParentId("1");
		lis = addressService.getDistrict(addresses);
//		mv.addObject(attributeName, attributeValue)
		mv.addObject("result", lis);
		mv.addObject("proName",proName);
		mv.addObject("areName",areName);
		mv.addObject("locName",locName);
		mv.addObject("userInfo", userInfo);
		mv.addObject("userShipping", userShipping);
		mv.setViewName("/usercenter");
		return mv;
	}
	/**
     *     
     * edit：修改用户
     * @param sportUserReqDto
     * @return 
     * @exception 
     * @author mr
     * @date
     */
    @ResponseBody
    @RequestMapping("/editUser")
    public Map<String, Object> edit(SportUserReqDto sportUserReqDto){
     if(sportUserReqDto == null ){
    	 return MessageResp.getMessage(false,"保存信息不完善~~~");
     }
   	 int stat = iUserService.updateUserAccount(sportUserReqDto);
   	 if(stat<=0){
   		return MessageResp.getMessage(false,"保存失败~~~");
   	 }
 	 return MessageResp.getMessage(true,"保存成功~~~");
    }
    /**
     * 添加或修改收货地址
     * @param shipping  根据id是否为null判断
     * @return
     */
    @ResponseBody
    @RequestMapping("/addOrUpShipping")
    public Map<String, Object> addShipping(SportShippingAddressDto shipping,HttpServletRequest request,HttpSession session,HttpServletResponse response){
    	logger.debug("SportShippingAddressDto"+shipping);
    	//获取用户id保存
    	LoginUserDto logDto= (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
    	SportUserReqDto userInfo = getLoginUser(request,response,session,logDto);
    	if(userInfo==null){
    		return null;
    	}
    	shipping.setUserId(userInfo.getId());
    	SportShippingAddressDto isNull =issaService.getByCriteria(shipping);
    	if(isNull.getId()==null){
    		long stat = issaService.add(shipping);
    		if(stat<=0){
    			return MessageResp.getMessage(false,"添加失败~~~");
    		}
    		return MessageResp.getMessage(true,"添加成功~~~");
    	}else{
    		long stat = issaService.up(shipping);
    		if(stat<=0){
    			return MessageResp.getMessage(false,"修改失败~~~");
    		}
    		return MessageResp.getMessage(true,"修改成功~~~");
    	}
    }
    @RequestMapping("/toUserSenter")
    public ModelAndView toUsercenterSecure(HttpServletRequest request,HttpSession session,HttpServletResponse response,
    		SportAddressesDto addresses){
    	logger.debug("用户中心,安全管理~~");
    	ModelAndView mv = getModelAndView();
    	//获取用户信息
    	LoginUserDto logDto= (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
    	SportUserReqDto userInfo = getLoginUser(request,response,session,logDto);
    	if(userInfo==null){
    		return null;
    	}
		//提醒消息
		SportNotificationsDto notification = new SportNotificationsDto();
		SportNotificationsDto oldObj = new SportNotificationsDto();
    	notification.setOwnerId(userInfo.getId());
    	oldObj.setOwnerId(userInfo.getId());
    	oldObj= inotification.getByCriteria(oldObj);
    	
		//获取地址信息
		List<SportAddressesDto> lis = new ArrayList<SportAddressesDto>();
		addresses.setParentId("1");
		lis = addressService.getDistrict(addresses);
		mv.addObject("notification", oldObj);
		mv.addObject("userInfo", userInfo);
		mv.addObject("result", lis);
		mv.setViewName("/usercenter_secure");
    	return mv;
    }
    
    /**
     * 修改密码
     * @return
     */
    @ResponseBody
    @RequestMapping("/changePw")
    public Map<String, Object> changePassword(LoginUserDto loginUserDto,HttpServletRequest request, HttpServletResponse response,
    		HttpSession session){
    	logger.debug("修改密码loginUserDto:"+loginUserDto);
    	LoginUserDto logDto= (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
    	SportUserReqDto userInfo = getLoginUser(request,response,session,logDto);
    	if(userInfo==null){
    		return null;
    	}
    	//用户邮箱为
    	String str="";
		if(userInfo.getEmail()!=null && !userInfo.getEmail().equals("")){
			str=userInfo.getEmail();
		}else if(userInfo.getMobile()!=null && !userInfo.getMobile().equals("")){
			str=userInfo.getMobile();
		}else{
			str=UUID.randomUUID().toString();
		}
    	//修改密码提醒功能
    	SportNotificationsDto spDto = new SportNotificationsDto();
    	spDto.setOwnerId(userInfo.getId());
    	spDto= inotification.getByCriteria(spDto);
    	//手机提醒
    	if(spDto!=null){
    		if(spDto.getUpPasswordStat() == Notifications.email.getIndex()){
    			if(spDto.getEmail()==null || spDto.getEmail().equals("")){
    				//return MessageResp.getMessage(false,"您还没有绑定邮箱,请先绑定~~~");
    			}else{
    				send(spDto.getEmail(),session,request,str,2);
    			}
    		}
    		if(spDto.getUpPasswordStat() == Notifications.phone.getIndex()){
    			if(userInfo.getMobile()==null || userInfo.getMobile().equals("")){
    				
    			}else{
    				upPasswordMobileVal(userInfo.getMobile(),request,str,2);
    			}
    		}
    	}
    	
    	String md5oldPwd;// 获取页面上输入的密码并加密校验
    	String md5NewPwd;
		try {
			md5oldPwd = MD5Coder.encodeMD5Hex(loginUserDto.getOldPassword());
			md5NewPwd = MD5Coder.encodeMD5Hex(loginUserDto.getNewPassword());
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
		if(!md5oldPwd.equals(userInfo.getPassword())){
			return MessageResp.getMessage(false,"原密码输入错误~~~");
		}
		userInfo.setPassword(md5NewPwd);
		int stat = iUserService.updateUserAccount(userInfo);
		if(stat<=0){
			return MessageResp.getMessage(false,"修改失败~~~");
		}
		return MessageResp.getMessage(true,"修改成功~~~");
    }
    /**
     * 证件信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/papersInfo")
    public Map<String, Object>  papersInfo(SportUserReqDto userReqDto,HttpServletRequest request, HttpServletResponse response,
    		HttpSession session){
    	LoginUserDto logDto= (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
    	SportUserReqDto userInfo = getLoginUser(request,response,session,logDto);
    	if(userInfo==null){
    		return null;
    	}
    	userInfo.setPostcode(userReqDto.getPostcode());
    	userInfo.setCname(userReqDto.getCname());
    	userInfo.setLocation(userReqDto.getLocation());
    	userInfo.setProvince(userReqDto.getProvince());
    	userInfo.setTelephone(userReqDto.getTelephone());
    	userInfo.setMobile(userReqDto.getMobile());
    	int stat = iUserService.updateUserAccount(userInfo);
		if(stat<=0){
			return MessageResp.getMessage(false,"修改失败~~~");
		}
		return MessageResp.getMessage(true,"修改成功~~~");
    }
    
    /**
     * 提醒消息新增或修改
     * @param notification
     * @return
     */
    @ResponseBody
    @RequestMapping("/doNotification")
    public Map<String, Object> doNotification(SportNotificationsDto notification,HttpServletRequest request, HttpServletResponse response,
    		HttpSession session,LoginUserDto logDto){
    	logger.debug("用户信息-消息提醒操作notification"+notification);
    	//新邮箱不为空,更换邮箱,新有限为空,注册邮箱
    	if(notification.getEmailNew()!=null &&! notification.getEmailNew().equals("")){
    		notification.setEmail(notification.getEmailNew());
    	}
    	if(notification.getRandomCar()!=null && !notification.getRandomCar().equals("")){
    		String randomPicSession = session.getAttribute("RandomCode")
					.toString().toLowerCase();
    		if(!notification.getRandomCar().toLowerCase().equals(randomPicSession)){
    			return MessageResp.getMessage(false,"验证码输入错误~~~");
    		}
    	}
    	SportUserReqDto userInfo = getLoginUser(request,response,session,logDto);
    	if(userInfo==null){
    		return null;
    	}
    	SportNotificationsDto oldObj = new SportNotificationsDto();
    	notification.setOwnerId(userInfo.getId());
    	oldObj.setOwnerId(userInfo.getId());
    	oldObj= inotification.getByCriteria(oldObj);
    	long stat=0;
    	if(oldObj == null){
    		stat= inotification.save(notification);
    	}else{
    		stat= inotification.update(notification);
    	}
    	if(stat>0){
    		return MessageResp.getMessage(true,"修改成功~~~");
    	}
    	return MessageResp.getMessage(false,"修改失败~~~");
    }
    
   /**
    * 获取当前用户
    * @param request
    * @param response
    * @param session
    * @param logDto  登录dto
    * @return
    */
    public SportUserReqDto getLoginUser(HttpServletRequest request, HttpServletResponse response,
    		HttpSession session,LoginUserDto logDto){
    	logDto= (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
    	if(logDto==null){
    		try {
				response.sendRedirect(request.getContextPath() + "/index.htm");
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	return iUserService.selectUserAccountByUserId(logDto.getId());
    }
    

	/**
	 * 保存密码发送验证码
	 * Mr 
	 * */
	@RequestMapping(value = "/upPasswordMobileVal")
	@ResponseBody
	public Response<String> upPasswordMobileVal(String mobilePhone,HttpServletRequest request,String cname,int type) {
		Response<String> result = new Response<String>();
		int randomNumber = (int) (Math.random() * 9000 + 1000);
		String content = null;
		if(type == 1){
			content="温馨提示,您的账号"+cname+",已在聚运动官网登录,祝您浏览愉快~~~";// 登录提示
		}
		if(type == 2){
			content = "温馨提示,您的账号"+cname+",在聚运动官网完成修改密码功能,祝您浏览愉快~~~";// 修改提示
		}
		String returnString = iSmsSendService.sendSms(randomNumber, mobilePhone, content);
		/*String keyCode = "upPw"+mobilePhone;
		redisTemplate.opsForValue().set(keyCode, randomNumber, 90, TimeUnit.SECONDS);*/
		result.setStatus(DataStatus.HTTP_SUCCESS);
		return result;
	}
	/**
	 * 发送邮件
	 * @param email
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/send")
	@ResponseBody
	public Response<String> send(String email, HttpSession session,
			HttpServletRequest request,String cname,int type) {
		Response<String> result = new Response<String>();
		int randomNumber = (int) (Math.random() * 9000 + 1000); // 验证码
		String content=null;
		if(type==1){
			content= "温馨提示,您的账号"+cname+",已在聚运动官网登录,祝您浏览愉快~~~";// 登录提示
		}
		if(type==2){
			content = "温馨提示,您的账号"+cname+",在聚运动官网完成修改密码功能,祝您浏览愉快~~~";// 修改提示
		}
		String from_ = "jujusports@ssic.cn"; // 发送的邮箱
		String subject = "聚运动邮箱验证"; // 主题
		String keyCode = SessionConstants.EMAIL_NUMBER;
		redisTemplate.opsForValue().set(keyCode, randomNumber, 90,
				TimeUnit.SECONDS);
		try {
			iEmailSendService.sendEmail(randomNumber, email, content, from_,
					subject);
		} catch (Exception e) {
			// return JsonUtil.getJsonStr(new RequestResult(true,"发送邮件失败！"));
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("发送邮件失败！");
			return result;
		}
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("发送邮件成功！");
		return result;
	}
	
	public int upLastLoginTime(SportUserReqDto dto){
		dto.setLastLoginTime(new Date());
		return iUserService.updateUserAccount(dto);
	}
}

