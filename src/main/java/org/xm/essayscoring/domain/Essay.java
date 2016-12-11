package org.xm.essayscoring.domain;

/**
 * @author xuming
 */
public class Essay {
    /**
     * 标题
     */
    private String title;
    /**
     * 写作者
     */
    private Writer writer;
    private String text;

    private int score;

    public int length() {
        return text.length();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
        return title + "/" + writer.getName() + "/" + text;
    }

    public String toString(String split) {
        return title + split + writer.getName() + split + text;
    }

}
