package rd.judicial

import java.time.LocalDateTime

class JudicialUser {

    UUID judicialUserId = UUID.randomUUID()
    UUID userProfileId
    String firstName
    String lastName
    LocalDateTime dateCreated
    Jurisdiction jurisdiction

    static hasMany = [
            judicialRoles: JudicialRole,
    ]

    static constraints = {
    }
}
