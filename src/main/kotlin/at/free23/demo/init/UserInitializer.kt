package at.free23.demo.init

import org.camunda.bpm.engine.AuthorizationService
import org.camunda.bpm.engine.IdentityService
import org.camunda.bpm.engine.authorization.Authorization
import org.camunda.bpm.engine.authorization.Groups
import org.camunda.bpm.engine.authorization.Permission
import org.camunda.bpm.engine.authorization.Permissions
import org.camunda.bpm.engine.authorization.Resource
import org.camunda.bpm.engine.authorization.Resources
import org.camunda.bpm.engine.identity.Group
import org.camunda.bpm.engine.identity.User
import org.camunda.bpm.engine.impl.persistence.entity.AuthorizationEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserInitializer : Initializer {
	
	val identityService: IdentityService;
	val authorizationService: AuthorizationService;

	@Autowired
	constructor(identityService: IdentityService, authorizationService: AuthorizationService) {
		this.identityService = identityService;
		this.authorizationService = authorizationService;
	}
	
	override fun init() {
		if(!this.adminUserExists()) {
			val users = arrayOf(
				this.createAdminUser("admin", "Administrator", "", "admin", "admin@nomail.com"),
				this.createUser("max.power", "Max", "Power", "demo", "max.power@nomail.com")
			);
			val groups = arrayOf(
				this.createGroup("AT_ACC_LC01", "Accounting AT Logistik Center 01", "ACC"),
				this.createGroup("AT_ACC_LC02", "Accounting AT Logistik Center 02", "ACC"),
				this.createGroup("CZ_ACC_LC01", "Accounting CZ Logistik Center 01", "ACC"),
				this.createGroup("CZ_ACC_LC02", "Accounting CZ Logistik Center 02", "ACC"),
				this.createGroup("AT_MGMT_LC01", "Management AT Logistik Center 01", "MGMT"),
				this.createGroup("AT_MGMT_LC02", "Management AT Logistik Center 02", "MGMT"),
				this.createGroup("CZ_MGMT_LC01", "Management CZ Logistik Center 01", "MGMT"),
				this.createGroup("CZ_MGMT_LC02", "Management CZ Logistik Center 02", "MGMT"),
				this.createGroup("AT_OP_LC01", "Operation AT Logistik Center 01", "OP"),
				this.createGroup("AT_OP_LC02", "Operation AT Logistik Center 02", "OP"),
				this.createGroup("CZ_OP_LC01", "Operation CZ Logistik Center 01", "OP"),
				this.createGroup("CZ_OP_LC02", "Operation CZ Logistik Center 02", "OP")
			);
			groups.forEach({ group ->
				users.forEach({user -> this.createMembership(user, group)});
			});
		}
	}

	private fun adminUserExists(): Boolean {
		return this.identityService.createUserQuery().userId("admin").singleResult() != null;
	}
	
	private fun createUser(userId: String, firstName: String, lastName: String, password: String, email: String): User {
		val user: User = this.identityService.newUser(userId);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setEmail(email);
		this.identityService.saveUser(user);
		return user;
	}

	private fun createAdminUser(userId: String, firstName: String, lastName: String, password: String, email: String): User {
		val user = this.createUser(userId, firstName, lastName, password, email);
		val group = this.createGroup(Groups.CAMUNDA_ADMIN, "camunda BPM Administrators", Groups.GROUP_TYPE_SYSTEM);
		enumValues<Resources>().forEach({			
			this.createAuthorizationGroup(Authorization.AUTH_TYPE_GRANT, group, it, Authorization.ANY, arrayOf(Permissions.ALL));
		});
		this.createMembership(user, group);
		return user;
	}

	private fun createGroup(groupId: String, description: String, type: String): Group {
		val group = this.identityService.newGroup(groupId);
		group.setName(description);
		group.setType(type);

		this.identityService.saveGroup(group);
		return group;
	}

	private fun createAuthorizationGroup(type: Int, group: Group, resource: Resource, resourceId: String,
			permissions: Array<Permission>): Authorization {
		val authEntity = AuthorizationEntity(type);
		authEntity.setGroupId(group.getId());
		authEntity.setResource(resource);
		authEntity.setResourceId(resourceId);
		permissions.forEach(authEntity::addPermission);
		this.authorizationService.saveAuthorization(authEntity);
		return authEntity;
	}
	
	private fun createMembership(user: User, group: Group) {
		this.identityService.createMembership(user.getId(), group.getId());
	}
}

