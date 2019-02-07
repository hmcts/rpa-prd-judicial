package rd.judicial

class JudicialRole {

    UUID judicialRoleId = UUID.randomUUID()
    String roleName
    String roleDescription

    static belongsTo = [
            JudicialUser
    ]

    static hasMany = [
            judicialUsers: JudicialUser
    ]

    static constraints = {
        judicialRoleId nullable: false, unique: true
        roleName nullable: false, unique: true
    }
}
