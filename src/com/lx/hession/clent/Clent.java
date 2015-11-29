package com.lx.hession.clent;

import java.net.MalformedURLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.caucho.hessian.client.HessianProxyFactory;
import com.lx.hession.service.IHelloService;

public class Clent {
	
	/** 服务URL(默认): */
	private static String SERVICE_URL = "http://localhost:8080/hessian_1_server_spring/remote/helloWebService";

	/** 连接服务超时间(默认): */
	private static int TIME_OUT = 10000;
	
	/** 服务实例 */
	private IHelloService helloService;

	/**
	 * 构造函数,初始化远程连接
	 * 
	 * @throws Exception
	 *             连接异常
	 */
	public Clent() throws Exception{
		try {
			
			
		 	//方式一：客户端不使用spring时，调用模式如下：
			/*HessianProxyFactory factory = new HessianProxyFactory();
			factory.setReadTimeout(TIME_OUT);
			helloService = (IHelloService) factory.create(IHelloService.class, SERVICE_URL);*/
			
			//方式二：客户端使用spring时，调用默认如下：
			ApplicationContext context = new ClassPathXmlApplicationContext(  
	                "config/applicationContext-hessian.xml"); 
			helloService = (IHelloService) context.getBean("helloService");
			
			
			if(helloService == null) {
				throw new Exception("远程接口初始化失败");
			} else {
				System.out.println("远程接口IHelloService 初始化成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		Clent clent = new Clent();
		IHelloService service = clent.getService();
		String result = service.sayHello("lixin");
		System.out.println(result);
	}
	
	/*public static void initApplicationContext() {  
        System.out.println("初始化context....开始");  
        ApplicationContext context = new ClassPathXmlApplicationContext(  
                "classpath:config/applicationContext-bean.xml");  
        ContextUtil.setContext(context);  
        System.out.println("初始化context....结束");  
    }*/

	public IHelloService getService(){
		return helloService;
	}
}
