package hu.robnn.rss_analyzer.dao.model

import java.util.*
import javax.persistence.*


@Entity
@Table(name = "rss_user")
open class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rss_user_seq")
    @SequenceGenerator(name = "rss_user_seq", sequenceName = "rss_user_seq", allocationSize = 1)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "uuid")
    var uuid: String = UUID.randomUUID().toString()

    @Column(name = "real_name")
    var realName: String? = null

    @Column(name = "user_name")
    var userName: String? = null

    @Column(name = "email_address")
    var emailAddress: String? = null

    @Column(name = "password_hash")
    var passwordHash: String? = null

    @Column(name = "role")
    var role: String? = null
}