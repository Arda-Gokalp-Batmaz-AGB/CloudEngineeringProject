package com.arda.case_impl.di

import com.arda.case_api.domain.usecase.AddCaseUserUseCase
import com.arda.case_api.domain.usecase.AddCommentCaseUseCase
import com.arda.case_api.domain.usecase.AssignRoleToCaseUseCase
import com.arda.case_api.domain.usecase.GetAllCaseListUseCase
import com.arda.case_api.domain.usecase.GetCaseByCaseIDUseCase
import com.arda.case_api.domain.usecase.GetCaseListByAssignedOfficerSubRoleUseCase
import com.arda.case_api.domain.usecase.GetCaseListByUserIDUseCase
import com.arda.case_api.domain.usecase.RemoveCaseUseCase
import com.arda.case_api.domain.usecase.ResolveCaseOfficerUseCase
import com.arda.case_impl.data.repository.CaseDataRepository
import com.arda.case_impl.data.repository.CaseDataRepositoryImpl
import com.arda.case_impl.usecase.AddCaseUserUseCaseImpl
import com.arda.case_impl.usecase.AddAddCommentCaseUseCaseImpl
import com.arda.case_impl.usecase.AssignRoleToCaseUseCaseImpl
import com.arda.case_impl.usecase.GetAllCaseListUseCaseImpl
import com.arda.case_impl.usecase.GetCaseByCaseIDUseCaseImpl
import com.arda.case_impl.usecase.GetCaseListByAssignedOfficerSubRoleUseCaseImpl
import com.arda.case_impl.usecase.GetCaseListByUserIDUseCaseImpl
import com.arda.case_impl.usecase.RemoveCaseUseCaseImpl
import com.arda.case_impl.usecase.ResolveCaseOfficerUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
//    @Provides
//    @Singleton
//    fun auth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun CaseDataRepository(
//        dispatcher: CoroutineDispatcher,
    ): CaseDataRepository = CaseDataRepositoryImpl(providesIoDispatcher())

    @Provides
    @Singleton
    fun addCaseUseCase(
        caseDataRepository: CaseDataRepository,
    ): AddCaseUserUseCase = AddCaseUserUseCaseImpl(caseDataRepository)

    @Provides
    @Singleton
    fun commentCaseUseCase(
        caseDataRepository: CaseDataRepository,
    ): AddCommentCaseUseCase = AddAddCommentCaseUseCaseImpl(caseDataRepository)

    @Provides
    @Singleton
    fun getAllCaseListUseCase(
        caseDataRepository: CaseDataRepository,
    ): GetAllCaseListUseCase = GetAllCaseListUseCaseImpl(caseDataRepository)

    @Provides
    @Singleton
    fun getCaseListByAssignedOfficerSubRoleUseCase(
        caseDataRepository: CaseDataRepository,
    ): GetCaseListByAssignedOfficerSubRoleUseCase =
        GetCaseListByAssignedOfficerSubRoleUseCaseImpl(caseDataRepository)

    @Provides
    @Singleton
    fun getCaseListByUserIDUseCase(
        caseDataRepository: CaseDataRepository,
    ): GetCaseListByUserIDUseCase = GetCaseListByUserIDUseCaseImpl(caseDataRepository)

    @Provides
    @Singleton
    fun resolveCaseOfficerUseCase(
        caseDataRepository: CaseDataRepository,
    ): ResolveCaseOfficerUseCase = ResolveCaseOfficerUseCaseImpl(caseDataRepository)

    @Provides
    @Singleton
    fun getCaseByCaseIDUseCase(
        caseDataRepository: CaseDataRepository,
    ): GetCaseByCaseIDUseCase = GetCaseByCaseIDUseCaseImpl(caseDataRepository)


    @Provides
    @Singleton
    fun assignRoleToCaseUseCase(
        caseDataRepository: CaseDataRepository,
    ): AssignRoleToCaseUseCase = AssignRoleToCaseUseCaseImpl(caseDataRepository)

    @Provides
    @Singleton
    fun RemoveCaseUseCase(
        caseDataRepository: CaseDataRepository,
    ): RemoveCaseUseCase = RemoveCaseUseCaseImpl(caseDataRepository)

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher