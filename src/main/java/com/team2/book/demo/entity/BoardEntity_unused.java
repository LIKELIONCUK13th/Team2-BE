//엔티티는 단 하나도 쓰이지 않았습니다. 혹시 몰라서 남겨둔 것입니다.




package com.team2.book.demo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "board")

public class BoardEntity_unused {
    @Id
    //id가 자동적으로 생성된다고 함. 하지만 이것때문에 절대로 내가 수동으로 id를 설정 불가
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "purpose", length = 500)
    private String purpose;
    @ElementCollection
    @Column(name = "style", length = 500)
    private List<String> style;
    @Column(name = "question", length = 50000)
    private String question;


    public BoardEntity_unused(String purpose, List<String> style, String question) {
        this.purpose = purpose;
        this.style = style;
        this.question = question;
    }

    public BoardEntity_unused() {

    }

    public Integer getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getPurpose() { return purpose; }

    public void setPurpose(String purpose) { this.purpose = purpose; }

    public List<String> getStyle() { return style; }

    public void setStyle(List<String> style) { this.style = style; }

    public String getQuestion() { return question; }

    public void setQuestion(String question) { this.question = question; }
}
