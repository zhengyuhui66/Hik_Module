package com.hik.service.messpush;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.hik.dao.BaseService;
import com.hik.dao.MessPushDao;
import com.hik.framework.utils.Page;

@Service
@Transactional(readOnly = true)
public class MessPushForServicesImpl extends BaseService implements MessPushForServices {

	@Autowired
	private MessPushDao messPushDao;

	@Value("${getappId}")
	private String appId;

	@Value("${getappsecret}")
	private String appSecret;

	@Value("${getappkey}")
	private String appKey;

	@Value("${getmastersecret}")
	private String masterSecret;

	@Value("${gethost}")
	private String host;

	@Override
	public String pushByone(String clientid, String content) {
		// TODO Auto-generated method stub
		IGtPush push = new IGtPush(host, appKey, masterSecret);
		// LinkTemplate template = linkTemplateDemo(content);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
//		message.setData(transmissionTemplateDemo(content));
		message.setData(transmissionTemplateDemo(content, "", ""));
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		Target target = new Target();
		target.setAppId(appId);
		clientid = "d585db155b7f8095a4e446167dc4f0c9";
		target.setClientId(clientid);
		// target.setAlias(Alias);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}

		if (ret != null) {
			System.out.println(ret.getResponse().toString());
			return ret.getResponse().toString();
		} else {
			System.out.println("服务器响应异常");
			return "1";
		}
	}

	public LinkTemplate linkTemplateDemo(String content) {
		LinkTemplate template = new LinkTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 设置通知栏标题与内容
		template.setTitle("请输入通知栏标题");
		template.setText(content);
		// 配置通知栏图标
		template.setLogo("icon.png");
		// 配置通知栏网络图标，填写图标URL地址
		template.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		// 设置打开的网址地址
		template.setUrl("http://www.baidu.com");
		return template;
	}

	public TransmissionTemplate transmissionTemplateDemo(String content) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(2);
		template.setTransmissionContent(content);
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		return template;
	}

	/**
	 * 20170122 add by cookies
	 * 
	 * @param content
	 * @param title
	 * @param body
	 * @return
	 */
	public TransmissionTemplate transmissionTemplateDemo(String content, String title, String body) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(2);
		template.setTransmissionContent(content);
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		// 设置App透传信息 IOS使用
		template.setAPNInfo(ApnsInfo(title, body));
		return template;
	}

	@Override
	public String pushByGroup(String content) {
		// TODO Auto-generated method stub
		IGtPush push = new IGtPush(host, appKey, masterSecret);

		// LinkTemplate templates = transmissionTemplateDemo();

		// LinkTemplate template = linkTemplateDemo(content);
		AppMessage message = new AppMessage();
		message.setData(transmissionTemplateDemo(content));

		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 1000 * 3600);
		// 推送给App的目标用户需要满足的条件
		AppConditions cdt = new AppConditions();
		List<String> appIdList = new ArrayList<String>();
		appIdList.add(appId);
		message.setAppIdList(appIdList);
		// 手机类型
		List<String> phoneTypeList = new ArrayList<String>();
		// 省份
		List<String> provinceList = new ArrayList<String>();
		// 自定义tag
		List<String> tagList = new ArrayList<String>();

		cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
		cdt.addCondition(AppConditions.REGION, provinceList);
		cdt.addCondition(AppConditions.TAG, tagList);
		message.setConditions(cdt);

		IPushResult ret = push.pushMessageToApp(message, "任务别名_toApp");
		System.out.println(ret.getResponse().toString());
		return ret.getResponse().toString();
	}

	@Override
	public Page queryForPage(String UserId, int start, int limit, String searchStr) {
		// TODO Auto-generated method stu
		return messPushDao.queryForPage(UserId, start, limit, searchStr);
	}

	public List getClientId(String alias) {
		IGtPush push = new IGtPush(host, appKey, masterSecret);
		IAliasResult queryClient = push.queryClientId(appId, alias);
		log.info("别名：" + alias + "  根据别名获取的CID：" + queryClient.getClientIdList());
		return queryClient.getClientIdList();
	}

	@Override
	public int savePushMessage(String[] alisas, String content, String username, String descr) {
		// TODO Auto-generated method stub
		// List alisparam = new ArrayList();
		Map map = new HashMap();
		for (int i = 0; i < alisas.length; i++) {
			List clientList = getClientId(alisas[i]);
			if (clientList != null && clientList.size() > 1) {
				log.info("根据Alisa" + alisas[i] + "获取到的cid有多个。未识别准确客户端cid推送可能无法到达");
			}

			String result = pushByone(clientList.get(0).toString(), content);
			if (!"1".equals(result)) {
				map.put(alisas[i], clientList.get(0).toString());
			}
		}
		int result = messPushDao.savePushMess(map, content, username, descr);
		return result;
	}

	/**
	 * 设定IOS的推送参数 By Cookies Added 20170120
	 * 
	 * @param info
	 * @return
	 */
	private APNPayload ApnsInfo(String title, String body) {
		APNPayload payload = new APNPayload();
		// payload.setAutoBadge("+1"); //设置角标，还可以实现显示数字的自动增减
		// 设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
		payload.setContentAvailable(1); // 推送直接带有透传数据
		payload.setSound("default"); // 通知铃声文件名
		// payload.setCategory("$由客户端定义"); //在客户端通知栏触发特定的action和button显示
		// 简单模式APNPayload.SimpleMsg
		// payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));

		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		body = StringUtils.isEmpty(body) ? "收到一条消息" : body;
		alertMsg.setBody(body);// 通知文本消息字符串
		title = StringUtils.isEmpty(title) ? "广告自助平台" : title;
		alertMsg.setTitle(title); // 通知标题
		
		//// alertMsg.setActionLocKey("ActionLockey");
		//// //(用于多语言支持）指定执行按钮所使用的Localizable.strings
		//// alertMsg.setLocKey("LocKey");
		//// //(用于多语言支持）指定Localizable.strings文件中相应的key
		//// alertMsg.addLocArg("loc-args"); //如果loc-key中使用的占位符，则在loc-args中指定各参数
		//// alertMsg.setLaunchImage("launch-image"); //指定启动界面图片名
		// // iOS8.2以上版本支持
		
		//// alertMsg.setTitleLocKey("TitleLocKey");
		//// //(用于多语言支持）对于标题指定执行按钮所使用的Localizable.strings,
		//// alertMsg.addTitleLocArg("TitleLocArg"); //对于标题,
		//// 如果loc-key中使用的占位符，则在loc-args中指定各参数,
		//
		payload.setAlertMsg(alertMsg);
		return payload;

	}

	@Override
	public String pushByoneCompatWithIOS(String clientid, String content, String apnsTitle, String apnsBody) {
		IGtPush push = new IGtPush(host, appKey, masterSecret);
		// LinkTemplate template = linkTemplateDemo(content);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(transmissionTemplateDemo(content,apnsTitle,apnsBody));
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		Target target = new Target();
		target.setAppId(appId);
		clientid = "7974f6d1028477d3cc90b77eff69086c";
		target.setClientId(clientid);
		// target.setAlias(Alias);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}

		if (ret != null) {
			System.out.println(ret.getResponse().toString());
			return ret.getResponse().toString();
		} else {
			System.out.println("服务器响应异常");
			return "1";
		}
	}

	@Override
	public int savePushMessage(String[] alisas, String content, String username, String descr, String apnsTitle,
			String apnsBody) {
		Map map = new HashMap();
		for (int i = 0; i < alisas.length; i++) {
			List clientList = getClientId(alisas[i]);
			if (clientList != null && clientList.size() > 1) {
				log.info("根据Alisa" + alisas[i] + "获取到的cid有多个。未识别准确客户端cid推送可能无法到达");
			}

//			String result = pushByone(clientList.get(0).toString(), content);
			String result = pushByoneCompatWithIOS(clientList.get(0).toString(), content, apnsTitle, apnsBody);
			if (!"1".equals(result)) {
				map.put(alisas[i], clientList.get(0).toString());
			}
		}
		int result = messPushDao.savePushMess(map, content, username, descr);
		return result;
	}

}
