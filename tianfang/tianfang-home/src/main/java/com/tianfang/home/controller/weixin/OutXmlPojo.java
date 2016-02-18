package com.tianfang.home.controller.weixin;

public class OutXmlPojo {
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private String Content;
    private String ArticleCount;
    private String Articles;
    private String FuncFlag;

    public String getToUserName() {
	return ToUserName;
    }

    public void setToUserName(String toUserName) {
	ToUserName = toUserName;
    }

    public String getFromUserName() {
	return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
	FromUserName = fromUserName;
    }

    public String getCreateTime() {
	return CreateTime;
    }

    public void setCreateTime(String createTime) {
	CreateTime = createTime;
    }

    public String getMsgType() {
	return MsgType;
    }

    public void setMsgType(String msgType) {
	MsgType = msgType;
    }

    public String getContent() {
	return Content;
    }

    public void setContent(String content) {
	Content = content;
    }

    public String getArticleCount() {
	return ArticleCount;
    }

    public void setArticleCount(String articleCount) {
	ArticleCount = articleCount;
    }

    public String getArticles() {
	return Articles;
    }

    public void setArticles(String articles) {
	Articles = articles;
    }

    public String getFuncFlag() {
	return FuncFlag;
    }

    public void setFuncFlag(String funcFlag) {
	FuncFlag = funcFlag;
    }

}
