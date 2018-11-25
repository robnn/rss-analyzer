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
    open var id: Long? = null

    @Column(name = "uuid")
    open var uuid: String = UUID.randomUUID().toString()

    @Column(name = "real_name")
    open var realName: String? = null

    @Column(name = "user_name")
    open var userName: String? = null

    @Column(name = "email_address")
    open var emailAddress: String? = null

    @Column(name = "password_hash")
    open var passwordHash: String? = null

    @Column(name = "role")
    open var role: String? = null
}