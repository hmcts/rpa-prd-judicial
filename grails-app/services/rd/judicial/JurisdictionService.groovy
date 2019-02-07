package rd.judicial

import grails.gorm.transactions.Transactional

@Transactional
class JurisdictionService {

    def getByUuid(UUID uuid) {
        Jurisdiction.where {
            id == uuid
        }.find()
    }
}
