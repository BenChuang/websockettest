package filter;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalEncodingFilter implements Filter {

    private String encode = null;
    public void destroy(){

    }

    public void doFilter(ServletRequest req,ServletResponse resp,FilterChain chain)
            throws IOException,ServletException{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        request.setCharacterEncoding(this.encode);
        response.setCharacterEncoding(this.encode);
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException{
        this.encode = config.getInitParameter("encode");
        if (StringUtils.isBlank(this.encode)) {
            this.encode = "UTF-8";
        }
    }


}