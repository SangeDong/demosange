package cn.sange.spring;

import cn.sange.spring.factory.BeanFactory;
import cn.sange.spring.pojo.ControllerHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.util.Map;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("start to handle spring");
        ServletContext servletContext = servletContextEvent.getServletContext();
        String basePackage = servletContext.getInitParameter("base_package");
        BeanFactory.getInstance().refresh(basePackage);

        Map<String, Object> webObjects = BeanFactory.getWebObjects();
        for (Map.Entry<String, Object> entry : webObjects.entrySet()) {
            ControllerHolder controllerHolder = (ControllerHolder) entry.getValue();
            ServletRegistration.Dynamic servletRegistration = servletContext.addServlet(entry.getKey(),
                    (HttpServlet) controllerHolder.getHttpServlet());
            servletRegistration.addMapping(controllerHolder.getUrl());
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        BeanFactory.getInstance().destroy();
    }
}
