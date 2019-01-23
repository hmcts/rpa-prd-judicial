package rpa.rhubarb

class UrlMappings {

    static mappings = {
        "500"(view: '/error')
        "404"(view: '/notFound')

        get "/health" (controller: 'health', action: 'index')

        // Swagger UI
        get "/apidoc/$action?/$id?"(controller: "apiDoc", action: "getDocuments")
    }
}
