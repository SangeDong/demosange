package cn.sange.spring.pojo;

public class ControllerHolder {
    private String url;

    public ControllerHolder(String url, Object httpServlet) {
        this.url = url;
        this.httpServlet = httpServlet;
    }

    private Object httpServlet;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getHttpServlet() {
        return httpServlet;
    }

    public void setHttpServlet(Object httpServlet) {
        this.httpServlet = httpServlet;
    }
}
