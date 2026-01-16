package controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

// 샘플데이터 삽입용 리스너
// 생명주기 : 서버 시작 -> 서버 종료
@WebListener
public class InitListener implements ServletContextListener {

    public InitListener() {
        // TODO Auto-generated constructor stub
    }
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	// 서버 작동 시 application.attribute에 [샘플데이터] 추가
    	
    	// TO-DO : application.attribute 에 값 넣기
    	// sce.getServletContext().setAttribute(속성 이름 ㄴ허기, datas);
    }
	
}
