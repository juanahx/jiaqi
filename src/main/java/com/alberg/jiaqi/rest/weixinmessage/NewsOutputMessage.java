package com.alberg.jiaqi.rest.weixinmessage;

import java.util.List;

public class NewsOutputMessage extends OutputMessage {

	   /**
	 * 
	 */
	private static final long serialVersionUID = 2369035818746721850L;
	/**
     * 消息类型:文本消息
     */
    private String MsgType = "news";

    private int ArticleCount;
    private List<Item> Articles;

    /**
     * 创建一个新的 Output Message.并且MsgType的值为text.
     */
    public NewsOutputMessage() {
    }

    /**
     * 获取 消息类型
     *
     * @return 消息类型
     */
    @Override
    public String getMsgType() {
        return MsgType;
    }

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Item> getArticles() {
		return Articles;
	}

	public void setArticles(List<Item> articles) {
		Articles = articles;
	}

}
