package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestLoginFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (null != ((HttpServletRequest) servletRequest).getSession().getAttribute("name")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        ((HttpServletResponse) servletResponse).sendRedirect("login.jsp");
    }

    public void destroy() {

    }
}
