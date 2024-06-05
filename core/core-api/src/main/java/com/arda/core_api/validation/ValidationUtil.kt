package com.arda.core_api.validation

import java.lang.Exception
import kotlin.reflect.KFunction1

class ValidationUtil {
    companion object {
        private val exceptionMessageTemplate: String = "%s=%s."//ENUM, ERROR MESSAGE

        @JvmInline
        value class Validator(val value: String) : CharSequence by value {

            fun validityRuleEmail() =
                require(value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()) && !value.isNullOrEmpty()) { "Email Error" }

            fun longerThan8RulePassword() =
                require(value.length >= 8) { "Password Length must be longer than 8 characters" }

            fun withoutWhitespaceRulePassword() =
                require(value.none { it.isWhitespace() }) { "Password can't contain whitespace" }

            fun atLeastOneDigitRulePassword() =
                require(value.any { it.isDigit() }) { "Password must include at least one digit" }

            fun longerThan3RuleUsername() =
                require(value.length >= 3) { "Username length must be longer than 3 characters" }

            fun withoutWhitespaceRuleUsername() =
                require(value.none { it.isWhitespace() }) { "Username can not contain whitespace" }

            infix fun checkWith(rules: List<KFunction1<Validator, Unit>>) =
                runCatching { rules.forEach { it(this) } }

            infix fun validateWith(rules: List<KFunction1<Validator, Unit>>) = runCatching {
                val message =
                    rules.mapNotNull { runCatching { it(this) }.exceptionOrNull()?.message }
                        .joinToString(separator = "\n")
                require(message.isEmpty()) { message }
            }
        }

        fun validateEmail(email: String): ValidationResult {
            val rules = listOf(
                Validator::validityRuleEmail,
            )
            val validation = (Validator(email) validateWith rules).apply {}
            val enum =
                if (validation.isSuccess) "Sucess" else "Error Email"

            val err = listOf("Error Email").joinToString(separator = "\n")
            val message = exceptionMessageTemplate.format(enum, err)
            return ValidationResult(
                validation.isSuccess,
                Exception(message)
            )
        }

        fun validatePassword(password: String): ValidationResult {
            val rules = listOf(
                Validator::atLeastOneDigitRulePassword,
                Validator::longerThan8RulePassword,
                Validator::withoutWhitespaceRulePassword,
            )
            val result = (Validator(password) validateWith rules).apply {}
            val enum =
                if (result.isSuccess) "Success" else "Password Error"

            if (result.isSuccess) {
                return ValidationResult(result.isSuccess, Exception())
            }
            val err = result.exceptionOrNull()?.message//?.split("\n")?.toList()
            val message = exceptionMessageTemplate.format(enum, err)

            return ValidationResult(result.isSuccess, Exception(message))
        }


        fun validateUserName(name : String) : ValidationResult
        {
            val rules = listOf(
                Validator::longerThan3RuleUsername,
                Validator::withoutWhitespaceRuleUsername,
            )
            val result = (Validator(name) validateWith rules).apply {}
            val enum =
                if (result.isSuccess) "Sucess" else "Username Error"

            if (result.isSuccess) {
                return ValidationResult(result.isSuccess, Exception())
            }
            val err = result.exceptionOrNull()?.message
            val message = exceptionMessageTemplate.format(enum, err)

            return ValidationResult(result.isSuccess, Exception(message))
        }
//        fun validateGender(genderEnum: GenderEnum?) : ValidationResult
//        {
//            val isValid = genderEnum != null
//            val enum =
//                if (isValid)  ResourceProvider(StringResourceEnum.GENDER_SUCCESS) else  ResourceProvider(
//                    StringResourceEnum.ERROR_GENDER
//                )
//            val err =
//                listOf( ResourceProvider(StringResourceEnum.ERROR_GENDER_EMPTY)).joinToString(separator = "\n")
//            val message = exceptionMessageTemplate.format(enum, err)
//
//            return ValidationResult(
//                isValid,
//                Exception(message),
//            )
//        }
    }

}