package rd.judicial.web

import grails.gorm.transactions.Transactional
import io.swagger.annotations.*
import rd.judicial.Jurisdiction
import rd.judicial.web.command.AddJurisdictionCommand
import rd.judicial.web.dto.JurisdictionDto

import static org.springframework.http.HttpStatus.CREATED

@Api(
        value = "judicialuser/jurisdictions/",
        description = "Jurisdiction APIs"
)
class JurisdictionsController extends AbstractDtoRenderingController<Jurisdiction, JurisdictionDto> {
    static responseFormats = ['json']

    JurisdictionsController() {
        super(Jurisdiction, JurisdictionDto)
    }

    @ApiOperation(
            value = "List all jurisdictions",
            nickname = "/",
            produces = "application/json",
            httpMethod = 'GET',
            response = JurisdictionDto,
            responseContainer = "Set"
    )
    @ApiResponses([
            @ApiResponse(code = 405, message = "Method not allowed")
    ])
    def index() {
        super.index()
    }

    @ApiOperation(
            value = "Delete a jurisdiction",
            nickname = "/{id}",
            httpMethod = 'DELETE'
    )
    @ApiResponses([
            @ApiResponse(code = 404, message = "Jurisdiction not found"),
            @ApiResponse(code = 405, message = "Method not allowed")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(
                    name = "id",
                    paramType = "path",
                    required = true,
                    value = "Jurisdiction ID",
                    dataType = "string"
            )
    ])
    @Transactional
    def delete() {
        super.delete()
    }

    @ApiOperation(
            value = "Add a new jurisdiction",
            nickname = "/",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = 'POST'
    )
    @ApiResponses([
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 405, message = "Method not allowed"),
            @ApiResponse(code = 409, message = "Jurisdiction already exists")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(
                    name = "body",
                    paramType = "body",
                    required = true,
                    value = "Jurisdiction details",
                    dataType = "rd.judicial.web.command.AddJurisdictionCommand"
            )
    ])
    @Transactional
    def save() {
        def cmd = new AddJurisdictionCommand(request.getJSON())
        def jurisdiction = new Jurisdiction(
                jurisdictionName: cmd.name,
                jurisdictionDesc: cmd.description
        )

        jurisdiction.validate()
        if (jurisdiction.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond jurisdiction.errors, status: 400
            return
        }

        saveResource jurisdiction

        respond new JurisdictionDto(jurisdiction), [status: CREATED]
    }

    @ApiOperation(
            value = "Update a jurisdiction",
            nickname = "/{id}",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = 'PUT'
    )
    @ApiResponses([
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Jurisdiction not found"),
            @ApiResponse(code = 405, message = "Method not allowed")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(
                    name = "id",
                    paramType = "path",
                    required = true,
                    value = "Jurisdiction ID",
                    dataType = "string"
            ),
            @ApiImplicitParam(
                    name = "body",
                    paramType = "body",
                    required = true,
                    value = "Jurisdiction update details",
                    dataType = "rd.judicial.web.command.AddJurisdictionCommand"
            )
    ])
    @Transactional
    def update() {
        super.update()
    }

    protected Jurisdiction queryForResource(Serializable id) {
        Jurisdiction.where {
            jurisdictionId == id
        }.find()
    }
}
