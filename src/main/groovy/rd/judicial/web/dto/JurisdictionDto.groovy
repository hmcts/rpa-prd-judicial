package rd.judicial.web.dto

import rd.judicial.Jurisdiction

class JurisdictionDto {

    JurisdictionDto(Jurisdiction jurisdiction) {
        this.id = jurisdiction.jurisdictionId
        this.name = jurisdiction.jurisdictionName
        this.description = jurisdiction.jurisdictionDesc
    }

    UUID id
    String name
    String description
}
