package com.arda.core_api.domain.enums

enum class RoleEnum(val role: String) {
    user("User"),
    admin("Admin"),
    officer("Officer"),
    empty("Select a role")
//    electrician,
//    cleaner
}

enum class OfficierSubRoleEnum(val role: String) {
    electrician("Electrician"),
    gardener("Gardener"),
    cleaner("Cleaner"),
    empty("Select a role")
}

fun getAllRolesExcludingOfficer(): List<String> {
    val result = RoleEnum.values().filter { it.role != RoleEnum.officer.role }
        .map { x -> x.role } + OfficierSubRoleEnum.values().map { x -> x.role }
    return result.map { x -> x.toString() }
}

fun findRole(roleString: String):  OfficierSubRoleEnum? {
//    RoleEnum.values().find { it.role.equals(roleString, ignoreCase = true) }?.let {
//        return it
//    }
    OfficierSubRoleEnum.values().find { it.role.equals(roleString, ignoreCase = true) }?.let {
        return it
    }
    return null
}



sealed class Role {
    abstract val userName: String
    abstract val role: RoleEnum

    data class UserRole(
        override val userName: String,
        override val role: RoleEnum = RoleEnum.user,
    ) : Role()

    data class AdminRole(
        override val userName: String,
        override val role: RoleEnum = RoleEnum.admin,
    ) : Role()

    sealed class OfficerRole(
        override val role: RoleEnum = RoleEnum.officer,
        val officierSubRoleEnum: OfficierSubRoleEnum,
    ) : Role()
//    sealed class OfficerRole(
//    ) : Role() {
//        override val role: RoleEnum = RoleEnum.officer
//        abstract val officierSubRoleEnum : OfficierSubRoleEnum
////        data class ElectricianRole(
////            override val userName: String,
////            override val officierSubRoleEnum: OfficierSubRoleEnum = OfficierSubRoleEnum.electrician
////        ) : OfficerRole()
////        data class GardenerRole(
////            override val userName: String,
////            override val officierSubRoleEnum: OfficierSubRoleEnum = OfficierSubRoleEnum.gardener
////            ) : OfficerRole()
////        data class CleanerRole(
////            override val userName: String) : OfficerRole()
//    }
}