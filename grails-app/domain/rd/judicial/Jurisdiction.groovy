package rd.judicial

class Jurisdiction {

    UUID jurisdictionId = UUID.randomUUID()
    String jurisdictionName
    String jurisdictionDesc

    static belongsTo = [
            JudicialUser
    ]

    static hasMany = [
            judicialUsers: JudicialUser
    ]

    static constraints = {
        jurisdictionName nullable: false, unique: true
    }
}
