package rd.judicial.web.dto

import rd.judicial.JudicialUser

import java.time.LocalDateTime

class JudicialUserDto {

    UUID id
    UUID userProfileId
    String firstName
    String lastName
    LocalDateTime dateCreated

    JudicialUserDto(JudicialUser user) {
        this.id = user.judicialUserId
        this.userProfileId = user.userProfileId
        this.firstName = user.firstName
        this.lastName = user.lastName
        this.dateCreated = user.dateCreated
    }
}
