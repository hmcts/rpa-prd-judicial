package rd.judicial.web

import grails.gorm.transactions.Transactional
import io.swagger.annotations.*
import rd.judicial.JudicialRole
import rd.judicial.web.command.AddRoleCommand
import rd.judicial.web.dto.JudicialRoleDto

import static org.springframework.http.HttpStatus.CREATED

@Api(
        value = "judicialuser/roles/",
        description = "Judicial User Role APIs"
)
class JudicialRolesController extends AbstractDtoRenderingController<JudicialRole, JudicialRoleDto> {
    static responseFormats = ['json']

    JudicialRolesController() {
        super(JudicialRole, JudicialRoleDto)
    }

    @ApiOperation(
            value = "List all judicial roles",
            nickname = "/",
            produces = "application/json",
            httpMethod = 'GET',
            response = JudicialRoleDto
    )
    @ApiResponses([
            @ApiResponse(code = 405, message = "Method not allowed")
    ])
    def index() {
        super.index()
    }

    @ApiOperation(
            value = "Delete a role",
            nickname = "/{id}",
            httpMethod = 'DELETE'
    )
    @ApiResponses([
            @ApiResponse(code = 404, message = "Role not found"),
            @ApiResponse(code = 405, message = "Method not allowed")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(
                    name = "id",
                    paramType = "path",
                    required = true,
                    value = "Role ID",
                    dataType = "string"
            )
    ])
    @Transactional
    def delete() {
        super.delete()
    }

    @ApiOperation(
            value = "Add a new role",
            nickname = "/",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = 'POST'
    )
    @ApiResponses([
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 405, message = "Method not allowed"),
            @ApiResponse(code = 409, message = "Role already exists")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(
                    name = "body",
                    paramType = "body",
                    required = true,
                    value = "Role details",
                    dataType = "rd.judicial.web.command.AddRoleCommand"
            )
    ])
    @Transactional
    def save() {
        def cmd = new AddRoleCommand(request.getJSON())
        def role = new JudicialRole(
                roleName: cmd.roleName,
                roleDescription: cmd.roleDescription,
        )

        role.validate()
        if (role.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond role.errors, status: 400
            return
        }

        saveResource role

        respond new JudicialRoleDto(role), [status: CREATED]
    }

    @ApiOperation(
            value = "Update a role",
            nickname = "/{id}",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = 'PUT'
    )
    @ApiResponses([
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Role not found"),
            @ApiResponse(code = 405, message = "Method not allowed")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(
                    name = "id",
                    paramType = "path",
                    required = true,
                    value = "User ID",
                    dataType = "string"
            ),
            @ApiImplicitParam(
                    name = "body",
                    paramType = "body",
                    required = true,
                    value = "User update details",
                    dataType = "rd.judicial.web.command.AddRoleCommand"
            )
    ])
    @Transactional
    def update() {
        super.update()
    }

    protected JudicialRole queryForResource(Serializable id) {
        JudicialRole.where {
            judicialRoleId == id
        }.find()
    }

    protected List<JudicialRole> listAllResources(Map params) {
        JudicialRole.findAll()
    }
}
