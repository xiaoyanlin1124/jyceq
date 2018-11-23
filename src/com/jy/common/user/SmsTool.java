package com.jy.common.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.jy.common.device.PushI18n;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.email.MailUtil;
import com.jy.entity.system.tool.Email;

public class SmsTool {
	
	 //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";
    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    /**
     * 帐号：拉麻
     * verificationaInterval 验证码间隔
     * verificationTime 验证码有效时间
     */
    public static final int  authorizedCodeDay  = 30 ; //受权码到期时间 天
    public static final int verificationaInterval = 60000 ; //验证码间隔 毫秒
    public static final int verificationTime = 1200000 ; //验证码有效时间10分钟 毫秒
    public static final int verificationDigits = 6 ; //验证码位数
    
    
    static final String accessKeyId = "LTAIMgSy8wSKTsYw";//阿里短信接口KEY
    static final String accessKeySecret = "4i3EhntVW6b6hX5A3xJIwuAMKzYuA3";
    
    public static String verifierCode(int x){
    	int y = 1;
    	for(int z = 1 ; z < x ; z++ ){
    		y = y * 10;
    	}
		Integer rd = (int)((Math.random()*9+1)*y) ;
		return rd.toString();
	}
    
    public static String verifierCode(){
    	return verifierCode(verificationDigits);
    }


    public static SendSmsResponse sendSms( String number , Locale locale , String code6  ) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "43200");
        System.setProperty("sun.net.client.defaultReadTimeout", "43200");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(number);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("创尔樵安防");
        //必填:短信模板-可在短信控制台中找到
        
        if(locale.equals(new Locale("en", "US" ))){
        	request.setTemplateCode("SMS_141190009");
        }else
        	request.setTemplateCode("SMS_140727007");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为//"{\"name\":\"Tom\", \"code\":\"123\"}"
        request.setTemplateParam("{\"code\":\""+ code6 +"\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }


    public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("15000000000");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }
    
    
    
    public static boolean  snedMial( String emial , Locale locale , String code6  ){
    	
    	Email mail = new Email();
    	boolean re ;
    	String msg = PushI18n.getI18nString( "mailSms" ,locale) + code6 + PushI18n.getI18nString( "mailSms2" ,locale );
    	mail.setSubject(PushI18n.getI18nString( "ceqCompany" ,locale));
    	mail.setToList(emial);
    	mail.setBody(msg);
//    	System.out.println(mail.getToList()+mail.getSubject()+mail.getBody());
    	if(  MailUtil.send(MailUtil.setConfig(Const.EMAIL_CONFIG),mail.getToList(),mail.getSubject(),mail.getBody()) )
    	{
    		re = true;
    	}else{
    		re = MailUtil.send(MailUtil.setConfig(Const.EMAIL_CONFIG2),mail.getToList(),mail.getSubject(),mail.getBody());
    	}
		return re;
    }
    
  public static void main(String[] args) throws ClientException, InterruptedException {
//	  sendSms( "13336459903" ,  new Locale("en", "US" ) , "123456"  );
	  String s="zh_CN";
			  System.out.println(s.subSequence(0, 2));
			  System.out.println(s.subSequence(3, 5));
  }

//    public static void main(String[] args) throws ClientException, InterruptedException {

//        //发短信
//        SendSmsResponse response = sendSms();
//        System.out.println("短信接口返回的数据----------------");
//        System.out.println("Code=" + response.getCode());
//        System.out.println("Message=" + response.getMessage());
//        System.out.println("RequestId=" + response.getRequestId());
//        System.out.println("BizId=" + response.getBizId());
//
//        Thread.sleep(3000L);
//
//        //查明细
//        if(response.getCode() != null && response.getCode().equals("OK")) {
//            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
//            System.out.println("短信明细查询接口返回数据----------------");
//            System.out.println("Code=" + querySendDetailsResponse.getCode());
//            System.out.println("Message=" + querySendDetailsResponse.getMessage());
//            int i = 0;
//            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
//            {
//                System.out.println("SmsSendDetailDTO["+i+"]:");
//                System.out.println("Content=" + smsSendDetailDTO.getContent());
//                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
//                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
//                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
//                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
//                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
//                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
//                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
//            }
//            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
//            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
//        }
//    	test2();
//    }
//    public static void test2(){
//    	Locale locale = new Locale("en", "US" );//默认
//    	
////    	System.out.println(locale2.getDisplayCountry());
////    	System.out.println(locale2.getDisplayName());
////    	System.out.println(locale2.getDisplayLanguage());
////    	System.out.println(locale2.getVariant());
//    	
//        if(locale.equals(new Locale("en", "US" ))){
//        	System.out.println(locale.getDisplayCountry());
//        }
//    	
//    }

}
