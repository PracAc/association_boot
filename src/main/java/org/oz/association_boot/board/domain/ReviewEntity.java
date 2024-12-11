package org.oz.association_boot.board.domain;

import jakarta.persistence.*;
import lombok.*;
import org.oz.association_boot.common.domain.BasicEntity;

@Entity
@Table(name = "tbl_review")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"board"})
public class ReviewEntity extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String reviewer;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardEntity board;

    public void changeContent(String content) {this.content = content;}
}
