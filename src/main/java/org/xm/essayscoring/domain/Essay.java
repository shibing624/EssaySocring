package org.xm.essayscoring.domain;

/**
 * @author xuming
 */
public class Essay {
    /**
     * 标题
     */
    public String title;
    /**
     * 写作者
     */
    public Writer writer;
    /**
     * 文章正文
     */
    public String text;

    /**
     * 得分
     */
    public int score;

    /**
     * 长度
     */
    public int length() {
        return text.length();
    }


    /**
     * 构造一个作文
     */
    public Essay(String title, Writer writer, String text) {
        this.title = title;
        this.writer = writer;
        this.text = text;
    }

    @Override
    public String toString() {
        return title + "/" + writer.name + "/" + text;
    }

    public String toString(String split) {
        return title + split + writer.name + split + text;
    }

}
