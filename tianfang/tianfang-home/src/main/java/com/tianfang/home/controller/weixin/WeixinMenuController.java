package com.tianfang.home.controller.weixin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tianfang.common.alipay.util.httpClient.GetPostUtil;
import com.tianfang.common.jsonxml.JsonXmlUtil;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.HttpClientUtil;
import com.tianfang.common.util.JsonUtil;
import com.tianfang.home.dto.QrcodeDto;

/**
 * <p>Title: WeixinMenuController </p>
 * <p>Description: 微信菜单接入接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 张亚伟	
 * @date 2015年9月1日 上午9:07:54	
 * @version 1.0
 * <p>修改人：张亚伟</p>
 * <p>修改时间：2015年9月1日 上午9:07:54</p>
 * <p>修改备注：</p>
 */
@RequestMapping("/weixin")
@Controller
public class WeixinMenuController {

	
	/**
	 * getUnionID：获取微信用户的UnionID
	 * @param code
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年10月6日 下午2:58:28
	 */
	@RequestMapping("/getUnionID")
	@ResponseBody
	public Response<String> getUnionID(String code) {
		Response<String> response=new Response<String>();
		// 定义访问网站的路径,通过code获取微信的唯一标识
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx3b1d31427f9d2518&secret=f02987db881ac0cfb5fca930eb22d13a&code="
				+ code + "&grant_type=authorization_code";
		// 发送请求,获取微信服务器返回的数据
		String json = GetPostUtil.weiLink(url);
		
		// 进行数据封装
		QrcodeDto qrcodeDto = JsonUtil.getObjFromJson(json, QrcodeDto.class);
		
		//获取微信的UnionID,多平台时用户的唯一标识
		String unionid = qrcodeDto.getUnionid();
		response.setData(unionid);
		
		return response;
	}

	/**
	 * in：微信安全模式信息接收与发送入口
	 * @param request
	 * @param response
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年9月1日 下午6:10:54
	 */
	@RequestMapping("/in")
	public void in(HttpServletRequest request, HttpServletResponse response) {
		try {

			// 设置输入和输出编码
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			/** 读取接收到的xml消息 */
			StringBuffer sb = new StringBuffer();
			InputStream is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			br.close();
			String xml = sb.toString(); // 次即为接收到微信端发送过来的xml数据
			String result = "";
			/** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */
			String echostr = request.getParameter("echostr");
			if (echostr != null && echostr.length() > 1) {
				result = echostr;
			} else {
				//解密请求消息体  
				String nXmlString = AuthProcess.decryptMsg(request, xml);
				/** 解析xml数据 */
				ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess()
						.getMsgEntity(nXmlString);

				//获取用户的UnionID
				String access_tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx91db5a306c4e34a9&secret=80265fe67cb14fa8c66932c86729867b";
				String access_tokenJson = GetPostUtil.weiLink(access_tokenUrl);
				JSONObject access_tokenObj = JSONObject
						.parseObject(access_tokenJson);
				String access_token = access_tokenObj.getString("access_token");
				System.out.println("access_tokenJson:" + access_tokenJson);
				System.out.println("access_token:" + access_token);
				String unionidUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
						+ access_token
						+ "&openid="
						+ xmlEntity.getFromUserName() + "&lang=zh_CN";
				String unionidJson = GetPostUtil.weiLink(unionidUrl);
				JSONObject unionIDObj = JSONObject.parseObject(unionidJson);
				String unionid = unionIDObj.getString("unionid");
				System.out.println("unionidJson:" + unionidJson);
				System.out.println("unionid:" + unionid);
				System.out.println("收到的信息" + nXmlString);
				//判断是否为事件信息
				if (xmlEntity.getMsgType().equals("event")) {

					//判断是否为关注事件
					if (xmlEntity.getEvent().equals("subscribe")) {

						// 将配置文件信息转换为字符串
						result = new ReceiveXmlProcess().getOutXml(xmlEntity,
								"weixin.xml");

						//判断是否为点击事件
					} else if (xmlEntity.getEvent().equals("CLICK")) {

						//判断是否为报名事件
						if (xmlEntity.getEventKey().equals("SignUp")) {

							// 将配置文件信息转换为字符串
							result = new ReceiveXmlProcess().getOutXml(
									xmlEntity, "SignUp.xml");

							//进行URL替换,对Url添加参数
							if (result != null) {
								String str = JsonXmlUtil.xml2json(result);
								JSONObject json = JSONObject.parseObject(str);
								String url = json.getJSONObject("xml")
										.getJSONObject("Articles")
										.getJSONObject("item").getString("Url")
										.replaceAll("\"", "");
								if (url != null && url.length() > 0) {
									result = result.replace(url, url
											+ "?openId=" + unionid);
								}
							}
						} else if (xmlEntity.getEventKey().equals("MyOrder")) {
							// 将配置文件信息转换为字符串
							result = new ReceiveXmlProcess().getOutXml(
									xmlEntity, "MyOrder.xml");

							//进行URL替换,对Url添加参数
							if (result != null) {
								String str = JsonXmlUtil.xml2json(result);
								JSONObject json = JSONObject.parseObject(str);
								String url = json.getJSONObject("xml")
										.getJSONObject("Articles")
										.getJSONObject("item").getString("Url")
										.replaceAll("\"", "");
								if (url != null && url.length() > 0) {
									result = result.replace(url, url
											+ "?openId=" + unionid);
								}
							}
						}

					}
					System.out.println("回复的内容" + result);
					//加密信息
					if (result != null && result.length() > 0) {
						result = AuthProcess.encryptMsg(request, result);
					}
				}

			}
			if (result != null && result.length() > 0) {

				// 输出配置信息
				OutputStream os = response.getOutputStream();
				os.write(result.getBytes("UTF-8"));
				os.flush();
				os.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * clearIn：微信明文模式信息接收与发送入口
	 * @param request
	 * @param response
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年9月1日 下午6:10:54
	 */
	@RequestMapping("/clearIn")
	public void clearIn(HttpServletRequest request, HttpServletResponse response) {
		System.out.println(111);
		try {

			// 设置输入和输出编码
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			/** 读取接收到的xml消息 */
			StringBuffer sb = new StringBuffer();
			InputStream is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			br.close();
			String xml = sb.toString(); // 次即为接收到微信端发送过来的xml数据
			String result = "";
			/** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */
			String echostr = request.getParameter("echostr");
			if (echostr != null && echostr.length() > 1) {
				result = echostr;
			} else {
				/** 解析xml数据 */
				ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess()
						.getMsgEntity(xml);

				//判断是否为事件信息
				if (xmlEntity.getMsgType().equals("event")) {

					//判断是否为关注事件
					if (xmlEntity.getEvent().equals("subscribe")) {

						// 将配置文件信息转换为字符串
						result = new ReceiveXmlProcess().getOutXml(xmlEntity,
								"weixin.xml");

						//判断是否为点击事件
					} else if (xmlEntity.getEvent().equals("CLICK")) {

						//判断是否为报名事件
						if (xmlEntity.getEventKey().equals("SignUp")) {

							// 将配置文件信息转换为字符串
							result = new ReceiveXmlProcess().getOutXml(
									xmlEntity, "SignUp.xml");

							//进行URL替换,对Url添加参数
							if (result != null) {
								String str = JsonXmlUtil.xml2json(result);
								System.out.println(str + "!!!");
								JSONObject json = JSONObject.parseObject(str);
								String url = json.getJSONObject("xml")
										.getJSONObject("Articles")
										.getJSONObject("item").getString("Url")
										.replaceAll("\"", "");
								if (url != null && url.length() > 0) {
									result = result.replace(url, url
											+ "?需要添加的参数");
								}
							}
						}
					}
				}

				System.out.println("回复的内容" + result);

			}
			if (result != null && result.length() > 0) {

				// 输出配置信息
				OutputStream os = response.getOutputStream();
				os.write(result.getBytes("UTF-8"));
				os.flush();
				os.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * outUrl：跳转到指定链接
	 * @param url 连接地址
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年9月4日 下午3:52:51
	 */
	@RequestMapping("/outUrl")
	public String outUrl(String url) {
		return "redirect:" + url;
	}

	/**
	 * getUrl：根据参数类型返回对应的网站,并获取用户的OpenId,微信订阅号不可使用,微信服务号可以使用
	 * @param json (格式:{type:选择类型}),微信菜单请求例示(https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx91db5a306c4e34a9&redirect_uri=http://www.kaixinfd.com/game-app/weixin/getUrl.do?json={'type':'1'}&response_type=code&scope=snsapi_base&state=1#wechat_redirect)
	 * @param request
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月24日 上午10:15:58
	 */
	@RequestMapping("/getUrl")
	public String getUrl(String json, HttpServletRequest request) {

		System.out.println(json);

		WeixinCode config = JSON.parseObject(json, WeixinCode.class);

		//获取请求的type类型
		String type = config.getType();

		//通过code获取用户的openid
		String code = request.getParameter("code");

		//获取用户的openId
		String appJson = HttpClientUtil
				.sendGetRequest(
						"https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx91db5a306c4e34a9&secret=80265fe67cb14fa8c66932c86729867b&code="
								+ code + "&grant_type=authorization_code", null);
		WeixinCode weixinCode = JSON.parseObject(appJson, WeixinCode.class);
		String openId = weixinCode.getOpenid();
		System.out.println(openId);

		//根据type类型返回对应的网页
		if (type.equals("1")) {
			return "redirect:/weixin/menu.html?userId=" + openId;
		} else if (type.equals("2")) {
			return "redirect:/weixin/comment.html?userId=" + openId;
		}

		//如果请求类型不匹配返回null
		return null;
	}
	

}
