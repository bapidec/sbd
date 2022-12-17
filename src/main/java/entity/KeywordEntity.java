package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "keyword", schema = "sbd", catalog = "")
public class KeywordEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "keyword_id")
    private int keywordId;
    @Basic
    @Column(name = "word")
    private String word;

    public int getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(int keywordId) {
        this.keywordId = keywordId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeywordEntity that = (KeywordEntity) o;

        if (keywordId != that.keywordId) return false;
        if (word != null ? !word.equals(that.word) : that.word != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = keywordId;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        return result;
    }
}
