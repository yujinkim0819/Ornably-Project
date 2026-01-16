package controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

// 인코딩을 위한 필터
@WebFilter({"*.jsp","*.do"})
public class EncFilter extends HttpFilter implements Filter {
    
	// 인코딩 타입 설정
	private String encodingType;
	
    public EncFilter() {
        super();
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(this.encodingType);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// 인코딩 방식 설정
		this.encodingType="UTF-8";
	}

}
