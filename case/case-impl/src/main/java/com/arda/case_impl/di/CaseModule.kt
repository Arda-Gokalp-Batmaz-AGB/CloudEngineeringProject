package com.arda.case_impl.di

import com.arda.case_api.domain.usecase.AddCaseUser
import com.arda.case_api.domain.usecase.CommentCase
import com.arda.case_api.domain.usecase.GetAllCaseList
import com.arda.case_api.domain.usecase.GetCaseListByAssignedOfficerSubRole
import com.arda.case_api.domain.usecase.GetCaseListByUserID
import com.arda.case_api.domain.usecase.ResolveCaseOfficer
import com.arda.case_impl.data.repository.CaseDataRepository
import com.arda.case_impl.data.repository.CaseDataRepositoryImpl
import com.arda.case_impl.usecase.AddCaseUserImpl
import com.arda.case_impl.usecase.AddCommentCaseImpl
import com.arda.case_impl.usecase.GetAllCaseListImpl
import com.arda.case_impl.usecase.GetCaseListByAssignedOfficerSubRoleImpl
import com.arda.case_impl.usecase.GetCaseListByUserIDImpl
import com.arda.case_impl.usecase.ResolveCaseOfficerImpl
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
    ): AddCaseUser = AddCaseUserImpl(caseDataRepository)

    @Provides
    @Singleton
    fun commentCaseUseCase(
        caseDataRepository: CaseDataRepository,
    ): CommentCase = AddCommentCaseImpl(caseDataRepository)

    @Provides
    @Singleton
    fun getAllCaseListUseCase(
        caseDataRepository: CaseDataRepository,
    ): GetAllCaseList = GetAllCaseListImpl(caseDataRepository)

    @Provides
    @Singleton
    fun getCaseListByAssignedOfficerSubRoleUseCase(
        caseDataRepository: CaseDataRepository,
    ): GetCaseListByAssignedOfficerSubRole =
        GetCaseListByAssignedOfficerSubRoleImpl(caseDataRepository)

    @Provides
    @Singleton
    fun getCaseListByUserIDUseCase(
        caseDataRepository: CaseDataRepository,
    ): GetCaseListByUserID = GetCaseListByUserIDImpl(caseDataRepository)

    @Provides
    @Singleton
    fun resolveCaseOfficerUseCase(
        caseDataRepository: CaseDataRepository,
    ): ResolveCaseOfficer = ResolveCaseOfficerImpl(caseDataRepository)

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher