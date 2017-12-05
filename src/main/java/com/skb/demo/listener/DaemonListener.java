package com.skb.demo.listener;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.discovery.EurekaClient;


@WebListener
public class DaemonListener implements ServletContextListener
{
    /** context */
    private ServletContext sc;

    @Autowired
	private EurekaClient discoveryClient;
    
    /** 컨텍스트 초기화 시 데몬 스레드를 작동한다 */
    public void contextInitialized (ServletContextEvent event) {
        System.out.println ("===================DaemonListener.contextInitialized has been called.===================");
        sc = event.getServletContext();
    }

    /** 컨텍스트 종료 시 thread를 종료시킨다 */
    public void contextDestroyed (ServletContextEvent event) {
        System.out.println ("===================DaemonListener.contextDestroyed has been called.===================");
        discoveryClient.shutdown();
    }
}