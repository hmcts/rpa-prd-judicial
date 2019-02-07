package rd.judicial

class UrlMappings {

    static mappings = {
        "500"(view: '/error')
        "404"(view: '/notFound')

        get "/health" (controller: 'health', action: 'index')

        // Swagger UI
        get "/apidoc/$action?/$id?"(controller: "apiDoc", action: "getDocuments")

        get "/judicialuser/"(controller: "judicialUser", action: "index")
        get "/judicialuser/${juId}"(controller: "judicialUser", action: "show")
        put "/judicialuser/${juId}"(controller: "judicialUser", action: "update")
        delete "/judicialuser/${juId}"(controller: "judicialUser", action: "delete")
        post "/judicialuser"(controller: "judicialUser", action: "save")

        get "/judicialuser/roles"(controller: "judicialRoles", action: "index")
        get "/judicialuser/roles/${roleId}"(controller: "judicialRoles", action: "show")
        put "/judicialuser/roles/${roleId}"(controller: "judicialRoles", action: "update")
        delete "/judicialuser/roles/${roleId}"(controller: "judicialRoles", action: "delete")
        post "/judicialuser/roles"(controller: "judicialRoles", action: "save")

        get "/judicialuser/jurisdictions"(controller: "jurisdictions", action: "index")
        get "/judicialuser/${judicialUserId}/jurisdictions/${jurisdictionId}"(controller: "jurisdictions", action: "show")
        post "/judicialuser/jurisdictions/${jurisdictionId}"(controller: "jurisdictions", action: "save")
        put "/judicialuser/jurisdictions/${jurisdictionId}"(controller: "jurisdictions", action: "update")
        delete "/judicialuser/jurisdictions/${jurisdictionId}"(controller: "jurisdictions", action: "delete")
    }
}
