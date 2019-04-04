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
		// ������Чʱ�䣬��λΪ���룬��ѡ
		message.setOfflineExpireTime(24 * 3600 * 1000);
//		message.setData(transmissionTemplateDemo(content));
		message.setData(transmissionTemplateDemo(content, "", ""));
		// ��ѡ��1Ϊwifi��0Ϊ���������绷���������ֻ����ڵ���������������Ƿ��·�
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
			System.out.println("��������Ӧ�쳣");
			return "1";
		}
	}

	public LinkTemplate linkTemplateDemo(String content) {
		LinkTemplate template = new LinkTemplate();
		// ����APPID��APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);
		// ����֪ͨ������������
		template.setTitle("������֪ͨ������");
		template.setText(content);
		// ����֪ͨ��ͼ��
		template.setLogo("icon.png");
		// ����֪ͨ������ͼ�꣬��дͼ��URL��ַ
		template.setLogoUrl("");
		// ����֪ͨ�Ƿ����壬�𶯣����߿����
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		// ���ô򿪵���ַ��ַ
		template.setUrl("http://www.baidu.com");
		return template;
	}

	public TransmissionTemplate transmissionTemplateDemo(String content) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		// ͸����Ϣ���ã�1Ϊǿ������Ӧ�ã��ͻ��˽��յ���Ϣ��ͻ���������Ӧ�ã�2Ϊ�ȴ�Ӧ������
		template.setTransmissionType(2);
		template.setTransmissionContent(content);
		// ���ö�ʱչʾʱ��
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
		// ͸����Ϣ���ã�1Ϊǿ������Ӧ�ã��ͻ��˽��յ���Ϣ��ͻ���������Ӧ�ã�2Ϊ�ȴ�Ӧ������
		template.setTransmissionType(2);
		template.setTransmissionContent(content);
		// ���ö�ʱչʾʱ��
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		// ����App͸����Ϣ IOSʹ��
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
		// ������Чʱ�䣬��λΪ���룬��ѡ
		message.setOfflineExpireTime(24 * 1000 * 3600);
		// ���͸�App��Ŀ���û���Ҫ���������
		AppConditions cdt = new AppConditions();
		List<String> appIdList = new ArrayList<String>();
		appIdList.add(appId);
		message.setAppIdList(appIdList);
		// �ֻ�����
		List<String> phoneTypeList = new ArrayList<String>();
		// ʡ��
		List<String> provinceList = new ArrayList<String>();
		// �Զ���tag
		List<String> tagList = new ArrayList<String>();

		cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
		cdt.addCondition(AppConditions.REGION, provinceList);
		cdt.addCondition(AppConditions.TAG, tagList);
		message.setConditions(cdt);

		IPushResult ret = push.pushMessageToApp(message, "�������_toApp");
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
		log.info("������" + alias + "  ���ݱ�����ȡ��CID��" + queryClient.getClientIdList());
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
				log.info("����Alisa" + alisas[i] + "��ȡ����cid�ж����δʶ��׼ȷ�ͻ���cid���Ϳ����޷�����");
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
	 * �趨IOS�����Ͳ��� By Cookies Added 20170120
	 * 
	 * @param info
	 * @return
	 */
	private APNPayload ApnsInfo(String title, String body) {
		APNPayload payload = new APNPayload();
		// payload.setAutoBadge("+1"); //���ýǱ꣬������ʵ����ʾ���ֵ��Զ�����
		// ����Ϊ-1ʱ�������������ϼ�1��ʾ������Ϊ����ʱ����ʾָ������
		payload.setContentAvailable(1); // ����ֱ�Ӵ���͸������
		payload.setSound("default"); // ֪ͨ�����ļ���
		// payload.setCategory("$�ɿͻ��˶���"); //�ڿͻ���֪ͨ�������ض���action��button��ʾ
		// ��ģʽAPNPayload.SimpleMsg
		// payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));

		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		body = StringUtils.isEmpty(body) ? "�յ�һ����Ϣ" : body;
		alertMsg.setBody(body);// ֪ͨ�ı���Ϣ�ַ���
		title = StringUtils.isEmpty(title) ? "�������ƽ̨" : title;
		alertMsg.setTitle(title); // ֪ͨ����
		
		//// alertMsg.setActionLocKey("ActionLockey");
		//// //(���ڶ�����֧�֣�ָ��ִ�а�ť��ʹ�õ�Localizable.strings
		//// alertMsg.setLocKey("LocKey");
		//// //(���ڶ�����֧�֣�ָ��Localizable.strings�ļ�����Ӧ��key
		//// alertMsg.addLocArg("loc-args"); //���loc-key��ʹ�õ�ռλ��������loc-args��ָ��������
		//// alertMsg.setLaunchImage("launch-image"); //ָ����������ͼƬ��
		// // iOS8.2���ϰ汾֧��
		
		//// alertMsg.setTitleLocKey("TitleLocKey");
		//// //(���ڶ�����֧�֣����ڱ���ָ��ִ�а�ť��ʹ�õ�Localizable.strings,
		//// alertMsg.addTitleLocArg("TitleLocArg"); //���ڱ���,
		//// ���loc-key��ʹ�õ�ռλ��������loc-args��ָ��������,
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
		// ������Чʱ�䣬��λΪ���룬��ѡ
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(transmissionTemplateDemo(content,apnsTitle,apnsBody));
		// ��ѡ��1Ϊwifi��0Ϊ���������绷���������ֻ����ڵ���������������Ƿ��·�
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
			System.out.println("��������Ӧ�쳣");
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
				log.info("����Alisa" + alisas[i] + "��ȡ����cid�ж����δʶ��׼ȷ�ͻ���cid���Ϳ����޷�����");
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
