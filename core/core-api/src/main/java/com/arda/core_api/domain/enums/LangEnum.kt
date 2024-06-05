package com.arda.core_api.domain.enums

import kotlinx.serialization.Serializable

@Serializable
enum class LangEnum(val code : String) {
    EMPTY("EMPTY"),
    English("EN"),
    Turkish("TR"),
    German("DE"),
    Chinese("CN"),
    Chinese_2("ZH");
    companion object {
        fun from(code: String): LangEnum? = LangEnum.values().firstOrNull { it.code == code }
    }
}