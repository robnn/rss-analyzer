package hu.robnn.rss_analyzer.dao.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "rss_user_token")
open class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rss_user_token_seq")
    @SequenceGenerator(name = "rss_user_token_seq", sequenceName = "rss_user_token_seq", allocationSize = 1)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "uuid")
    private var uuid: String = UUID.randomUUID().toString()

    @Column(name = "token")
    var token: String? = null

    @Column(name = "valid_to")
    var validTo: LocalDateTime? = null

    @ManyToOne(targetEntity = User::class)
    @JoinColumn(name = "user_id")
    var user: User? = null



}