package hu.robnn.rss_analyzer.dao.model.dto

import java.util.*

class UserDTO {
    var uuid: UUID = UUID.randomUUID()

    var realName: String? = null

    var userName: String? = null

    var emailAddress: String? = null

    var role: String? = null

    var password: String? = null

    var rootFolderUuid: UUID? = null
}