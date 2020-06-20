package org.wx.test;

import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Image;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.downloader.DownloaderContext;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.HtmlBean;
import com.geccocrawler.gecco.utils.DownloadImage;

@Gecco(matchUrl = { "http://www.akxs5.com/meizi/{group}/{index}", "http://www.akxs5.com/meizi/{group}" }, pipelines = {
        "consolePipeline", "imagePipeline" })
// @PipelineName("imagePipeline")//对于spring无效
@Service("imagePipeline") // spring要这样设置
public class Meizitu implements HtmlBean, Pipeline<Meizitu> {
    private static Log log = LogFactory.getLog(Meizitu.class);

    @Request
    private HttpRequest request;

    @RequestParameter("group")
    private String group;

    @RequestParameter("index")
    private String index;

    @Text
    @HtmlField(cssPath = "body > section > article > header > h2")
    private String groupName;

    // 下一页url，取下面倒数第1个链接
    @Href(click = true)
    @HtmlField(cssPath = "body > section > article > div:nth-child(3) > ul > li:nth-last-child(1) > a")
    private String nextPageUrl;

    // @Image(download = "d:/meizitu")
    @Image
    @HtmlField(cssPath = "body > section > article > div.main-image > p > a > img")
    public String imageUrl;

    @Override
    public void process(Meizitu meizitu) {
        log.debug(meizitu.getImageUrl());
        String dir = StringUtils.substringBefore(meizitu.getGroupName(), "（");
        // DownloadImage.download("d:/meizi1/" + dir,
        // meizitu.getImageUrl());//不好用
        HttpResponse subReponse = null;
        String imgUrl = meizitu.getImageUrl();
        HttpRequest request = meizitu.getRequest();
        String parentPath = "d:/meizi2/" + dir;
        try {
            String before = StringUtils.substringBefore(imgUrl, "?");
            String last = StringUtils.substringAfter(imgUrl, "?");
            String fileName = StringUtils.substringAfterLast(before, "/");
            if (StringUtils.isNotEmpty(last)) {
                last = URLEncoder.encode(last, "UTF-8");
                imgUrl = before + "?" + last;
            }
            HttpRequest subRequest = request.subRequest(imgUrl);
            subReponse = DownloaderContext.defaultDownload(subRequest);
            DownloadImage.download(parentPath, fileName, subReponse.getRaw());
        } catch (Exception ex) {
            // throw new FieldRenderException(field, ex.getMessage(), ex);
            // FieldRenderException.log(field, "download image error : " +
            // imgUrl, ex);
            // return imgUrl;
            log.error(meizitu.getImageUrl(), ex);
        } finally {
            if (subReponse != null) {
                subReponse.close();
            }
        }
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Log getLog() {
        return log;
    }

    public static void setLog(Log log) {
        Meizitu.log = log;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

}
