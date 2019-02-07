package rd.judicial.web

import grails.gorm.transactions.Transactional
import io.swagger.annotations.*
import rd.judicial.JudicialUser
import rd.judicial.Jurisdiction
import rd.judicial.JurisdictionService
import rd.judicial.web.command.UserRegistrationCommand
import rd.judicial.web.dto.JudicialUserDto

import static org.springframework.http.HttpStatus.CREATED

@Api(
        value = "judicialuser/",
        description = "Judicial User APIs"
)
class JudicialUserController extends AbstractDtoRenderingController<JudicialUser, JudicialUserDto> {
    static responseFormats = ['json']

    JurisdictionService jurisdictionService

    JudicialUserController() {
        super(JudicialUser, JudicialUserDto)
    }

    @ApiOperation(
            value = "List all judicial users",
            nickname = "/",
            produces = "application/json",
            httpMethod = "GET",
            response = JudicialUser,
            responseContainer = "Set"
    )
    @ApiResponses([
            @ApiResponse(code = 404, message = "No judicial users found"),
            @ApiResponse(code = 405, message = "Method not allowed")
    ])
    def index(Integer max) {
        super.index(max)
    }

    @ApiOperation(
            value = "Get the details of a user",
            nickname = "/{id}",
            produces = "application/json",
            httpMethod = 'GET',
            response = JudicialUser
    )
    @ApiResponses([
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 405, message = "Method not allowed")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(
                    name = "id",
                    paramType = "path",
                    required = true,
                    value = "User ID",
                    dataType = "string"
            )
    ])
    def show() {
        super.show()
    }

    @ApiOperation(
            value = "Delete a user",
            nickname = "/{id}",
            httpMethod = 'DELETE'
    )
    @ApiResponses([
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 405, message = "Method not allowed")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(
                    name = "id",
                    paramType = "path",
                    required = true,
                    value = "User ID",
                    dataType = "string"
            )
    ])
    @Transactional
    def delete() {
        super.delete()
    }

    @ApiOperation(
            value = "Register a new user",
            nickname = "/",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = 'POST'
    )
    @ApiResponses([
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 405, message = "Method not allowed"),
            @ApiResponse(code = 409, message = "User already exists")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(
                    name = "body",
                    paramType = "body",
                    required = true,
                    value = "User registration details",
                    dataType = "rd.professional.web.command.UserRegistrationCommand"
            )
    ])
    @Transactional
    def save() {
        def cmd = new UserRegistrationCommand(request.getJSON())
        def user = new JudicialUser(
                firstName: cmd.firstName,
                lastName: cmd.lastName,
                emailId: cmd.email
        )
        if (cmd.jurisdictionId) {
            Jurisdiction jurisdiction = jurisdictionService.getByUuid(cmd.jurisdictionId)
            if (jurisdiction) {
                user.jurisdiction = jurisdiction
            }
        }

        user.validate()
        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors, status: 400
            return
        }

        saveResource user

        respond new JudicialUserDto(user), [status: CREATED]
    }

    @ApiOperation(
            value = "Update a user",
            nickname = "/{orgId}/users/{id}",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = 'PUT'
    )
    @ApiResponses([
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 405, message = "Method not allowed")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(
                    name = "orgId",
                    paramType = "path",
                    required = true,
                    value = "Organisation ID",
                    dataType = "string"
            ),
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
                    dataType = "rd.professional.web.command.UserUpdateCommand"
            )
    ])
    @Transactional
    def update() {
        super.update()
    }

    protected JudicialUser queryForResource(Serializable id) {
        JudicialUser.where {
            judicialUserId == id
        }.find()
    }
}
