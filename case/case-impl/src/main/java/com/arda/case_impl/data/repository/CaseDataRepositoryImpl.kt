package com.arda.case_impl.data.repository

import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.model.CaseLocation
import com.arda.case_api.domain.model.CaseProcessEnum
import com.arda.case_api.domain.model.CategoryEnum
import com.arda.case_api.domain.model.Comment
import com.arda.core_api.domain.enums.OfficierSubRoleEnum
import com.arda.core_api.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CaseDataRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
) : CaseDataRepository {
    override suspend fun getAllCases(): Resource<List<Case>> =
        withContext(
            dispatcher
        ) {
            TODO("Not yet implemented")
        }

    override suspend fun addCase(case: Case): String {
        TODO("Not yet implemented")
    }

    override suspend fun addCaseComment(comment: Comment): String {
        TODO("Not yet implemented")
    }

    override suspend fun resolveCase(caseID: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun getCaseListByAssignedOfficerSubRole(assignedSubRole: OfficierSubRoleEnum): Resource<List<Case>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCaseListByID(caseID: String): Resource<List<Case>> {
        val cases = listOf(
            Case(
                id = "C001",
                userName = "john_doe",
                assignedOfficerSubRole = OfficierSubRoleEnum.electrician,
                currentProcess = CaseProcessEnum.on_process,
                image = "url_to_case_image_1",
                header = CategoryEnum.electric.categoryName,
                time = System.currentTimeMillis(),
                description = "There is a recurring issue with the main electrical panel.",
                location = CaseLocation(
                    address = "123 Apple Street",
                    place = "Community Hall",
                    building = "Building A",
                    floor = "Ground"
                ),
                comments = listOf(
                    Comment(
                        username = "officer_jane",
                        caseID = "C001",
                        text = "Inspection scheduled for tomorrow."
                    )
                )
            ),
            Case(
                id = "C002",
                userName = "alice_smith",
                assignedOfficerSubRole = OfficierSubRoleEnum.cleaner,
                currentProcess = CaseProcessEnum.waiting_for_response,
                image = "url_to_case_image_2",
                header = CategoryEnum.cleaning.categoryName,
                time = System.currentTimeMillis(),
                description = "Chemical spill in Lab 3 needs urgent attention.",
                location = CaseLocation(
                    address = "456 Orange Lane",
                    place = "Science Lab",
                    building = "Building C",
                    floor = "2"
                )
            ),
            Case(
                id = "C003",
                userName = "mike_brown",
                assignedOfficerSubRole = null,
                currentProcess = CaseProcessEnum.completed,
                image = "url_to_case_image_3",
                header = CategoryEnum.office_suplies.categoryName,
                time = System.currentTimeMillis(),
                description = "All gardening tools in the greenhouse replaced.",
                location = CaseLocation(
                    address = "789 Banana Boulevard",
                    place = "Greenhouse",
                    building = "Garden Complex",
                    floor = "N/A"
                )
            ),
            Case(
                id = "C004",
                userName = "emily_white",
                assignedOfficerSubRole = OfficierSubRoleEnum.gardener,
                currentProcess = CaseProcessEnum.failed,
                image = "url_to_case_image_4",
                header = CategoryEnum.gardening.categoryName,
                time = System.currentTimeMillis(),
                description = "Attempted to fix the central park fountain but the issue persists.",
                location = CaseLocation(
                    address = "1024 Cherry Circle",
                    place = "Central Park",
                    building = "Outdoor",
                    floor = "N/A"
                )
            ),
            Case(
                id = "C005",
                userName = "lucas_green",
                assignedOfficerSubRole = OfficierSubRoleEnum.electrician,
                currentProcess = CaseProcessEnum.on_process,
                image = "url_to_case_image_5",
                header = CategoryEnum.lighting.categoryName,
                time = System.currentTimeMillis(),
                description = "Multiple reports of lighting outages on several floors.",
                location = CaseLocation(
                    address = "321 Grape Road",
                    place = "Office Building",
                    building = "Building B",
                    floor = "Multiple Floors"
                ),
                comments = listOf(
                    Comment(
                        username = "tech_sam",
                        caseID = "C005",
                        text = "Need access to electrical panels on all affected floors."
                    )
                )
            )
        )

        return Resource.Sucess(cases)
    }

}