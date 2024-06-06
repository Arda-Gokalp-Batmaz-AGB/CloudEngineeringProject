package com.arda.core_api.domain.enums

enum class RoleEnum {
    user,
    admin,
    officer
//    electrician,
//    cleaner
}

enum class OfficierSubRoleEnum {
    electrician,
    gardener,
    cleaner,
}
fun getAllRolesExcludingOfficer(): List<String> {
    val result = RoleEnum.values().filter { it != RoleEnum.officer } + OfficierSubRoleEnum.values()
    return result.map { x-> x.toString() }
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