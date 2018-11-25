package hu.robnn.rss_analyzer.mapper

import hu.robnn.rss_analyzer.dao.model.User
import hu.robnn.rss_analyzer.dao.model.dto.UserDTO
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserMapper {
    fun map(source: User) : UserDTO {
        val target = UserDTO()
        target.uuid = UUID.fromString(source.uuid)
        target.emailAddress = source.emailAddress
        target.realName = source.realName
        target.role = source.role
        target.userName = source.userName
        return target
    }
}