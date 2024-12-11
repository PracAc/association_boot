package org.oz.association_boot.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.oz.association_boot.common.domain.AttachFile;
import org.oz.association_boot.common.domain.BasicEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_board")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardEntity extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;
    private String content;
    private String writer;
    private Integer btype;

    @ElementCollection
    @Builder.Default
    @CollectionTable(
            name = "tbl_board_files",
            joinColumns = @JoinColumn(name = "bno"))
    private Set<AttachFile> attachFiles = new HashSet<>();

    public void changeTitle(String title) {this.title = title;}
    public void changeContent(String content) {this.content = content;}
    public void addFile(String fileName) {
        if (fileName == null){
            attachFiles = new HashSet<>();
        }
        attachFiles.add(new AttachFile(attachFiles.size(), fileName));
    }
    public void removeFiles() {
        attachFiles.clear();
    }
}
