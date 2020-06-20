package org.wx.test;

import java.util.List;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl = { "http://www.akxs5.com/meizi/hot/page/{pageIndex}",
        "http://www.akxs5.com/meizi/hot/" }, pipelines = "consolePipeline")
public class Hot implements HtmlBean {

    // 热门组
    @Href(click = true)
    @HtmlField(cssPath = "#pics > li:nth-child(n+1) > a")
    private List<String> hotGroups;

    // 下一页url，取下面倒数第二个链接
    @Href(click = true)
    @HtmlField(cssPath = "body > section.main.clearfix > div.main-pic > div:nth-child(3) > ul > li:nth-last-child(2) > a")
    private String nextPageUrl;

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public List<String> getHotGroups() {
        return hotGroups;
    }

    public void setHotGroups(List<String> hotGroups) {
        this.hotGroups = hotGroups;
    }

}
