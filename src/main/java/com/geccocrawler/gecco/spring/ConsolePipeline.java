package com.geccocrawler.gecco.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.spider.SpiderBean;

public class ConsolePipeline implements Pipeline<SpiderBean> {
    private static Log log = LogFactory.getLog(ConsolePipeline.class);
    
	@Override
	public void process(SpiderBean bean) {	    
		log.info(JSON.toJSONString(bean));
	}

}
