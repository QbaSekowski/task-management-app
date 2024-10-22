package mate.academy.taskmanagementapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE attachments SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    @Column(name = "dropbox_id", nullable = false)
    private String dropboxId;
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @CreationTimestamp
    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
