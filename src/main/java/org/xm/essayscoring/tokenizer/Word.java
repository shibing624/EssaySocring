package org.xm.essayscoring.tokenizer;

import java.util.Objects;

/**
 * @author xuming
 */
public class Word implements Comparable {
    // 词名
    public String name;
    // 词性
    public String pos;
    // 权重，用于词向量分析
    public Float weight;
    public int frequency;

    public Word(String name) {
        this.name = name;
    }

    public Word(String name, String pos) {
        this.name = name;
        this.pos = pos;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Word other = (Word) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        if (name != null) {
            str.append(name);
        }
        if (pos != null) {
            str.append("/").append(pos);
        }
        if (frequency > 0) {
            str.append("/").append(frequency);
        }
        return str.toString();
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) {
            return 0;
        }
        if (this.name == null) {
            return -1;
        }
        if (o == null) {
            return 1;
        }
        if (!(o instanceof Word)) {
            return 1;
        }
        String t = ((Word) o).name;
        if (t == null) {
            return 1;
        }
        return this.name.compareTo(t);
    }
}
