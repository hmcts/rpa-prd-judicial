package rd.judicial.web.dto

import rd.judicial.JudicialRole

class JudicialRoleDto {

    UUID id
    String name
    String description

    JudicialRoleDto(JudicialRole role) {
        this.id = role.judicialRoleId
        this.name = role.roleName
        this.description = role.roleDescription
    }
}
