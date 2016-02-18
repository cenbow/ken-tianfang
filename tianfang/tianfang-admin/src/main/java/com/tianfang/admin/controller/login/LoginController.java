package com.tianfang.admin.controller.login;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.admin.controller.Const;
import com.tianfang.admin.controller.PageData;
import com.tianfang.admin.dto.SportAdminDto;
import com.tianfang.admin.service.ISportAdminService;
//import com.tianfang.admin.manager.service.IMenuService;
import com.tianfang.common.digest.MD5Coder;
import com.tianfang.common.model.MessageResp;

@Controller
public class LoginController extends BaseController {

	@Autowired
	private ISportAdminService iSportAdminService;
	
//	@Autowired
//	private IMenuService iMenuService;

	@RequestMapping(value = "/login_toLogin")
	public String toLogin() {
		this.getModelAndView();
		return "/admin/login";
	}

	@RequestMapping(value = "/login_login")
	@ResponseBody
	public Map<String,Object> login(@RequestParam(value = "userName") String userName,
			@RequestParam(value = "passWord") String passWord,
			HttpServletRequest request, HttpSession session) throws Exception {
		String md5passwd = MD5Coder.encodeMD5Hex(passWord);
		Object trainAdmin = iSportAdminService.adminLogin(userName, md5passwd);
        if (trainAdmin.toString().equals("0")) {
            return MessageResp.getMessage(false, "用户名不存在!");
        }
        if (trainAdmin.toString().equals( "1")) {
            return MessageResp.getMessage(false, "密码错误!");
        }
        else  {
            session.setAttribute(Const.SESSION_USER, trainAdmin);
            return MessageResp.getMessage(true,"登录成功!");
        }
		//return "/admin/login";
	}
	
	/**
	 * 访问系统首页
	 */
	@RequestMapping(value="/main")
	public ModelAndView login_index(HttpSession session){
//		try{
////			//shiro管理的session
////			Subject currentUser = SecurityUtils.getSubject();  
////			Session session = currentUser.getSession();
////			
//			Object user = null;//(User)session.getAttribute(Const.SESSION_USER);
//			if (user != null) {
////				
////				User userr = (User)session.getAttribute(Const.SESSION_USERROL);
////				if(null == userr){
////					user = userService.getUserAndRoleById(user.getUSER_ID());
////					session.setAttribute(Const.SESSION_USERROL, user);
////				}else{
////					user = userr;
////				}
////				Role role = user.getRole();
////				String roleRights = role!=null ? role.getRIGHTS() : "";
////				//避免每次拦截用户操作时查询数据库，以下将用户所属角色权限、用户权限限都存入session
////				session.setAttribute(Const.SESSION_ROLE_RIGHTS, roleRights); 		//将角色权限存入session
////				session.setAttribute(Const.SESSION_USERNAME, user.getUSERNAME());	//放入用户名
////				
////				List<Menu> allmenuList = new ArrayList<Menu>();
////				
////				if(null == session.getAttribute(Const.SESSION_allmenuList)){
////					allmenuList = menuService.listAllMenu();
////					if(Tools.notEmpty(roleRights)){
////						for(Menu menu : allmenuList){
////							menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
////							if(menu.isHasMenu()){
////								List<Menu> subMenuList = menu.getSubMenu();
////								for(Menu sub : subMenuList){
////									sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
////								}
////							}
////						}
////					}
////					session.setAttribute(Const.SESSION_allmenuList, allmenuList);			//菜单权限放入session中
////				}else{
////					allmenuList = (List<Menu>)session.getAttribute(Const.SESSION_allmenuList);
////				}
////				
////				//切换菜单=====
////				List<Menu> menuList = new ArrayList<Menu>();
////				//if(null == session.getAttribute(Const.SESSION_menuList) || ("yes".equals(pd.getString("changeMenu")))){
////				if(null == session.getAttribute(Const.SESSION_menuList) || ("yes".equals(changeMenu))){
////					List<Menu> menuList1 = new ArrayList<Menu>();
////					List<Menu> menuList2 = new ArrayList<Menu>();
////					
////					//拆分菜单
////					for(int i=0;i<allmenuList.size();i++){
////						Menu menu = allmenuList.get(i);
////						if("1".equals(menu.getMENU_TYPE())){
////							menuList1.add(menu);
////						}else{
////							menuList2.add(menu);
////						}
////					}
////					
////					session.removeAttribute(Const.SESSION_menuList);
////					if("2".equals(session.getAttribute("changeMenu"))){
////						session.setAttribute(Const.SESSION_menuList, menuList1);
////						session.removeAttribute("changeMenu");
////						session.setAttribute("changeMenu", "1");
////						menuList = menuList1;
////					}else{
////						session.setAttribute(Const.SESSION_menuList, menuList2);
////						session.removeAttribute("changeMenu");
////						session.setAttribute("changeMenu", "2");
////						menuList = menuList2;
////					}
////				}else{
////					menuList = (List<Menu>)session.getAttribute(Const.SESSION_menuList);
////				}
//				//切换菜单=====
//				
////				if(null == session.getAttribute(Const.SESSION_QX)){
////					session.setAttribute(Const.SESSION_QX, this.getUQX(session));	//按钮权限放到session中
////				}
//				
//				//FusionCharts 报表
//			 	String strXML = "";//"<graph caption='前12个月订单销量柱状图' xAxisName='月份' yAxisName='值' decimalPrecision='0' formatNumberScale='0'><set name='2013-05' value='4' color='AFD8F8'/><set name='2013-04' value='0' color='AFD8F8'/><set name='2013-03' value='0' color='AFD8F8'/><set name='2013-02' value='0' color='AFD8F8'/><set name='2013-01' value='0' color='AFD8F8'/><set name='2012-01' value='0' color='AFD8F8'/><set name='2012-11' value='0' color='AFD8F8'/><set name='2012-10' value='0' color='AFD8F8'/><set name='2012-09' value='0' color='AFD8F8'/><set name='2012-08' value='0' color='AFD8F8'/><set name='2012-07' value='0' color='AFD8F8'/><set name='2012-06' value='0' color='AFD8F8'/></graph>" ;
//			 	mv.addObject("strXML", strXML);
//			 	//FusionCharts 报表
//			 	
//				mv.setViewName("/admin/index");
////				mv.addObject("user", user);
//				mv.addObject("menuList", menuList);
//			}else {
//				mv.setViewName("/admin/login");//session失效后跳转登录页面
//			}
//		} catch(Exception e){
//			mv.setViewName("/admin/login");
//			logger.error(e.getMessage(), e);
//		}
//		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		
		SportAdminDto user = (SportAdminDto) session.getAttribute(Const.SESSION_USER); // 当前登录用户

		ModelAndView mv = null;
		if (user != null) {
//			List<MenuRespDto> menuList = this.getLeftMenuList(user.getId());
//			session.setAttribute(Const.SESSION_menuList, menuList);
			mv = this.getModelAndView(user.getId());
			session.setAttribute(Const.SESSION_USERNAME,user.getAccount());
			mv.setViewName("/admin/index");
//			mv.addObject("menuList", menuList);			
		}else {
			mv = this.getModelAndView();
			mv.setViewName("/admin/login");//session失效后跳转登录页面
		}
		
		PageData pd = new PageData();
		pd = this.getPageData();
		
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping(value="/tab")
	public String tab(){
		return "/admin/tab";
	}
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="/login_default")
	public String defaultPage(){
		return "/admin/default";
	}
	
	
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpSession session){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		
		//shiro管理的session
//		Subject currentUser = SecurityUtils.getSubject();  
//		Session session = currentUser.getSession();
		
		session.removeAttribute(Const.SESSION_USER);
//		session.removeAttribute(Const.SESSION_ROLE_RIGHTS);
//		session.removeAttribute(Const.SESSION_allmenuList);
		session.removeAttribute(Const.SESSION_menuList);
//		session.removeAttribute(Const.SESSION_QX);
//		session.removeAttribute(Const.SESSION_userpds);
//		session.removeAttribute(Const.SESSION_USERNAME);
//		session.removeAttribute(Const.SESSION_USERROL);
//		session.removeAttribute("changeMenu");
		
//		//shiro销毁登录
//		Subject subject = SecurityUtils.getSubject(); 
//		subject.logout();
		
//		pd = this.getPageData();
//		String  msg = pd.getString("msg");
//		pd.put("msg", msg);
//		
//		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		mv.setViewName("/admin/login");
		mv.addObject("pd",pd);
		return mv;
	}
}
