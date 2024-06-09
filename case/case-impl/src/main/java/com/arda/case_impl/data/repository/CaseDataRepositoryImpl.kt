package com.arda.case_impl.data.repository

import android.util.Log
import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.model.CaseLocation
import com.arda.case_api.domain.model.CaseProcessEnum
import com.arda.case_api.domain.model.CategoryEnum
import com.arda.case_api.domain.model.Comment
import com.arda.core_api.domain.enums.OfficierSubRoleEnum
import com.arda.core_api.domain.enums.findRole
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.core_api.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CaseDataRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
) : CaseDataRepository {
    private val TAG = DebugTagsEnumUtils.DataTag.tag

    private val imageRef = "https://cloudengr.s3.eu-north-1.amazonaws.com/"
    private val ticketsRef = "https://dvm2s3n3nh.execute-api.eu-north-1.amazonaws.com/dev/tickets"
    override suspend fun getAllCases(): Resource<List<Case>> =
        withContext(
            dispatcher
        ) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(ticketsRef)
                .build()
            val response = client.newCall(request).execute()
            val cases =
                Gson().fromJson(response.body?.string(), Array<Ticket>::class.java).map { x ->
                    val commentSec = x.resolution_details.split("/")
                        .map { x -> x.split(";").filter { y-> y != ""}  }.filter { z-> z.size !=0 }///yazankişi_ID;yazankişi;yorum/yazankişi;yorum;resim(opsiyonel)
                    //dıştaki list her bir yorumun bilgisi, icte ise kulanıcı adı, yorum vb
                    val resulationSec = x.resolution_image_key.split(";").filter { y-> y != ""} //image;image;image
                    val comments = commentSec.mapIndexed { i, y ->
                        if(y.size < 3)
                        {
                            Comment(
                                userID = y[0],
                                userName = y[0],
                                caseID = x.ticket_id,
                                text = y[0],
                                image = null
                            )
                        }
                        else{
                            if (i >= resulationSec.size)
                                Comment(
                                    userID = y[0],
                                    userName = y[1],
                                    caseID = x.ticket_id,
                                    text = y[2],
                                    image = null
                                )
                            else
                                Comment(
                                    userID = y[0],
                                    userName = y[1],
                                    caseID = x.ticket_id,
                                    text = y[2],
                                    image = imageRef + resulationSec[i]
                                )
                        }

                    }
                    Case(
                        id = x.ticket_id,
                        userID = x.user_id,
                        userName = x.username,
                        assignedOfficerSubRole = findRole(x.assigned_role)
                            ?: OfficierSubRoleEnum.empty,
                        currentProcess = if (x.status == false) CaseProcessEnum.waiting_for_response else CaseProcessEnum.completed,
                        image = imageRef + "${x.problem_image_key}",
                        header = x.problem_header,
                        time = LocalDate.parse(x.creation_date, formatter),
                        description = x.problem_details,
                        location = CaseLocation(
                            address = x.address,
                            place = x.place,
                            building = x.building,
                            floor = x.floor
                        ),
                        comments = comments
                    )
                }

            Log.v(TAG, "Responsed:${cases}")

            return@withContext Resource.Sucess(cases)
        }

    override suspend fun addCase(case: Case): String =
        withContext(
            dispatcher
        ) {
            //imagei base64 yap todo
            Log.v(TAG,"ADD CASE TRIGGERED")
            return@withContext try {
                val client = OkHttpClient()
                val mediaType = "application/json".toMediaType()
//                val body =
//                    "{\n  \"details\": \"${case.description}\",\n  \"problem_header\":\"${case.header}\",\n  \"user_id\": \"${case.userID}\",\n  \"username\": \"${case.userName}\",\n  \"address\": \"${case.location.address}\",\n  \"place\": \"${case.location.place}\",\n  \"building\": \"${case.location.building}\",\n  \"floor\": \"${case.location.floor}\",\n  \"image_base64\": \"${case.image}\"\n}".toRequestBody(
//                        mediaType
//                    )
                val body =
                    "{\n  \"details\": \"${case.description}\",\n  \"problem_header\":\"${case.header}\",\n  \"user_id\": \"${case.userID}\",\n  \"username\": \"${case.userName}\",\n  \"address\": \"${case.location.address}\",\n  \"place\": \"${case.location.place}\",\n  \"building\": \"${case.location.building}\",\n  \"floor\": \"${case.location.floor}\",\n  \"image_base64\": \"${case.image}\"\n}".toRequestBody(
                        mediaType
                    )//iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAApgAAAKYB3X3/OAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAANCSURBVEiJtZZPbBtFFMZ/M7ubXdtdb1xSFyeilBapySVU8h8OoFaooFSqiihIVIpQBKci6KEg9Q6H9kovIHoCIVQJJCKE1ENFjnAgcaSGC6rEnxBwA04Tx43t2FnvDAfjkNibxgHxnWb2e/u992bee7tCa00YFsffekFY+nUzFtjW0LrvjRXrCDIAaPLlW0nHL0SsZtVoaF98mLrx3pdhOqLtYPHChahZcYYO7KvPFxvRl5XPp1sN3adWiD1ZAqD6XYK1b/dvE5IWryTt2udLFedwc1+9kLp+vbbpoDh+6TklxBeAi9TL0taeWpdmZzQDry0AcO+jQ12RyohqqoYoo8RDwJrU+qXkjWtfi8Xxt58BdQuwQs9qC/afLwCw8tnQbqYAPsgxE1S6F3EAIXux2oQFKm0ihMsOF71dHYx+f3NND68ghCu1YIoePPQN1pGRABkJ6Bus96CutRZMydTl+TvuiRW1m3n0eDl0vRPcEysqdXn+jsQPsrHMquGeXEaY4Yk4wxWcY5V/9scqOMOVUFthatyTy8QyqwZ+kDURKoMWxNKr2EeqVKcTNOajqKoBgOE28U4tdQl5p5bwCw7BWquaZSzAPlwjlithJtp3pTImSqQRrb2Z8PHGigD4RZuNX6JYj6wj7O4TFLbCO/Mn/m8R+h6rYSUb3ekokRY6f/YukArN979jcW+V/S8g0eT/N3VN3kTqWbQ428m9/8k0P/1aIhF36PccEl6EhOcAUCrXKZXXWS3XKd2vc/TRBG9O5ELC17MmWubD2nKhUKZa26Ba2+D3P+4/MNCFwg59oWVeYhkzgN/JDR8deKBoD7Y+ljEjGZ0sosXVTvbc6RHirr2reNy1OXd6pJsQ+gqjk8VWFYmHrwBzW/n+uMPFiRwHB2I7ih8ciHFxIkd/3Omk5tCDV1t+2nNu5sxxpDFNx+huNhVT3/zMDz8usXC3ddaHBj1GHj/As08fwTS7Kt1HBTmyN29vdwAw+/wbwLVOJ3uAD1wi/dUH7Qei66PfyuRj4Ik9is+hglfbkbfR3cnZm7chlUWLdwmprtCohX4HUtlOcQjLYCu+fzGJH2QRKvP3UNz8bWk1qMxjGTOMThZ3kvgLI5AzFfo379UAAAAASUVORK5CYII=

                val request = Request.Builder()
                    .url("https://dvm2s3n3nh.execute-api.eu-north-1.amazonaws.com/dev/tickets/create")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build()
                val response = client.newCall(request).execute()
                Log.v(TAG,"Case with ID ${case.id} added sucessfully! : $response")
                "Case with ID ${case.id} added sucessfully! : $response"
            } catch (e: Exception) {
                Log.v(TAG,"Case add error! $e")
                "Case add error! $e"
            }
        }

    override suspend fun addCaseComment(comment: Comment): Resource<Comment> =
        withContext(
            dispatcher
        ) {
            return@withContext try {
                val client = OkHttpClient()
                val mediaType = "application/json".toMediaType()
                val body =
                    "{\n  \"ticket_id\": \"${comment.caseID}\",\n  \"resolution_details\": \"/${comment.userID};${comment.userName};${comment.text}\",\n  \"image_base64\": \"${comment.image}\"\n}".toRequestBody(
                        mediaType
                    )
                val request = Request.Builder()
                    .url("https://dvm2s3n3nh.execute-api.eu-north-1.amazonaws.com/dev/tickets/resolve")
                    .patch(body)
                    .addHeader("Content-Type", "application/json")
                    .build()
                val response = client.newCall(request).execute()
                Log.v(TAG, "Comment add sucess! $response")
                val case = getCaseByCaseID(comment.caseID)
                val commentResult=when(case){
                    is Resource.Failure<*> -> TODO()
                    Resource.Loading -> TODO()
                    is Resource.Sucess -> case.result.comments[0]//.copy(image = )
                }
                Resource.Sucess(commentResult)
            } catch (e: Exception) {
                Log.v(TAG, "Comment add error! $e")
                Resource.Failure<Exception>(Exception("Case add error! $e"))
            }
        }

    override suspend fun resolveCase(caseID: String): String {
        TODO("Not yet implemented")
    }

    data class Ticket(
        val ticket_id: String,
        val username: String,
        val assigned_role: String,
        val problem_header : String,
        val problem_details: String,
        val resolution_details: String,
        val status: Boolean,
        val address: String,
        val building: String,
        val creation_date: String,
        val floor: String,
        val user_id: String,
        val resolution_image_key: String,
        val solved_date: String,
        val problem_image_key: String,
        val place: String,
    )

    override suspend fun getCaseListByAssignedOfficerSubRole(assignedSubRole: String): Resource<List<Case>> =
        withContext(
            dispatcher
        ) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(ticketsRef)
                .build()
            val response = client.newCall(request).execute()
            val cases =
                Gson().fromJson(response.body?.string(), Array<Ticket>::class.java).map { x ->
                    val commentSec = x.resolution_details.split("/")
                        .map { x -> x.split(";").filter { y-> y != ""}  }.filter { z-> z.size !=0 }///yazankişi_ID;yazankişi;yorum/yazankişi;yorum;resim(opsiyonel)
                    //dıştaki list her bir yorumun bilgisi, icte ise kulanıcı adı, yorum vb
                    val resulationSec = x.resolution_image_key.split(";").filter { y-> y != ""} //image;image;image
                    val comments = commentSec.mapIndexed { i, y ->
                        if(y.size < 3)
                        {
                            Comment(
                                userID = y[0],
                                userName = y[0],
                                caseID = x.ticket_id,
                                text = y[0],
                                image = null
                            )
                        }
                        else{
                            if (i >= resulationSec.size)
                                Comment(
                                    userID = y[0],
                                    userName = y[1],
                                    caseID = x.ticket_id,
                                    text = y[2],
                                    image = null
                                )
                            else
                                Comment(
                                    userID = y[0],
                                    userName = y[1],
                                    caseID = x.ticket_id,
                                    text = y[2],
                                    image = imageRef + resulationSec[i]
                                )
                        }
                    }
                    Case(
                        id = x.ticket_id,
                        userID = x.user_id,
                        userName = x.username,
                        assignedOfficerSubRole = findRole(x.assigned_role)
                            ?: OfficierSubRoleEnum.empty,
                        currentProcess = if (x.status == false) CaseProcessEnum.waiting_for_response else CaseProcessEnum.completed,
                        image = imageRef + "${x.problem_image_key}",
                        header = x.problem_header,
                        time = LocalDate.parse(x.creation_date, formatter),
                        description = x.problem_details,
                        location = CaseLocation(
                            address = x.address,
                            place = x.place,
                            building = x.building,
                            floor = x.floor
                        ),
                        comments = comments
                    )
                }
            val result = cases.filter { x -> x.assignedOfficerSubRole?.role == assignedSubRole }
            Log.v(TAG, "Responset:${result}")

            return@withContext Resource.Sucess(result)
        }

    override suspend fun getCaseListByUserID(userID: String): Resource<List<Case>> =
        withContext(
            dispatcher
        ) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(ticketsRef)
                .build()
            val response = client.newCall(request).execute()
            val cases =
                Gson().fromJson(response.body?.string(), Array<Ticket>::class.java).map { x ->
                    val commentSec = x.resolution_details.split("/")
                        .map { x -> x.split(";").filter { y-> y != ""}  }.filter { z-> z.size !=0 }///yazankişi_ID;yazankişi;yorum/yazankişi;yorum;resim(opsiyonel)
                    //dıştaki list her bir yorumun bilgisi, icte ise kulanıcı adı, yorum vb
                    val resulationSec = x.resolution_image_key.split(";").filter { y-> y != ""} //image;image;image
                    val comments = commentSec.mapIndexed { i, y ->
                        if(y.size < 3)
                        {
                            Comment(
                                userID = y[0],
                                userName = y[0],
                                caseID = x.ticket_id,
                                text = y[0],
                                image = null
                            )
                        }
                        else{
                            if (i >= resulationSec.size)
                                Comment(
                                    userID = y[0],
                                    userName = y[1],
                                    caseID = x.ticket_id,
                                    text = y[2],
                                    image = null
                                )
                            else
                                Comment(
                                    userID = y[0],
                                    userName = y[1],
                                    caseID = x.ticket_id,
                                    text = y[2],
                                    image = imageRef + resulationSec[i]
                                )
                        }
                    }
                    Case(
                        id = x.ticket_id,
                        userID = x.user_id,
                        userName = x.username,
                        assignedOfficerSubRole = findRole(x.assigned_role)
                            ?: OfficierSubRoleEnum.empty,
                        currentProcess = if (x.status == false) CaseProcessEnum.waiting_for_response else CaseProcessEnum.completed,
                        image = imageRef + "${x.problem_image_key}",
                        header = x.problem_header,
                        time = LocalDate.parse(x.creation_date, formatter),
                        description = x.problem_details,
                        location = CaseLocation(
                            address = x.address,
                            place = x.place,
                            building = x.building,
                            floor = x.floor
                        ),
                        comments = comments
                    )
                }
            val result = cases.filter { x -> x.userID == userID }
            Log.v(TAG, "Responsef:${result}")

            return@withContext Resource.Sucess(result)
        }

    override suspend fun getCaseByCaseID(caseID: String): Resource<Case> =
        withContext(
            dispatcher
        ) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")

            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://dvm2s3n3nh.execute-api.eu-north-1.amazonaws.com/dev/tickets/$caseID/ticket-id")
                .build()
            val response = client.newCall(request).execute()

            val case =
                Gson().fromJson(response.body?.string(), Ticket::class.java).let { x ->
                    val commentSec = x.resolution_details.split("/")
                        .map { x -> x.split(";").filter { y-> y != ""}  }.filter { z-> z.size !=0 } ///yazankişi_ID;yazankişi;yorum/yazankişi;yorum;resim(opsiyonel)
                    //dıştaki list her bir yorumun bilgisi, icte ise kulanıcı adı, yorum vb
                    val resulationSec = x.resolution_image_key.split(";").filter { y-> y != ""}//image;image;image
                    val comments = commentSec.mapIndexed { i, y ->
                        if (i >= resulationSec.size)
                            Comment(
                                userID = y[0],
                                userName = y[1],
                                caseID = x.ticket_id,
                                text = y[2],
                                image = null
                            )
                        else
                            Comment(
                                userID = y[0],
                                userName = y[1],
                                caseID = x.ticket_id,
                                text = y[2],
                                image = imageRef + resulationSec[i]
                            )
                    }
                    Case(
                        id = x.ticket_id,
                        userID = x.user_id,
                        userName = x.username,
                        assignedOfficerSubRole = findRole(x.assigned_role)
                            ?: OfficierSubRoleEnum.empty,
                        currentProcess = if (x.status == false) CaseProcessEnum.waiting_for_response else CaseProcessEnum.completed,
                        image = imageRef + "${x.problem_image_key}",
                        header = x.problem_header,
                        time = LocalDate.parse(x.creation_date, formatter),
                        description = x.problem_details,
                        location = CaseLocation(
                            address = x.address,
                            place = x.place,
                            building = x.building,
                            floor = x.floor
                        ),
                        comments = comments
                    )
                }
            return@withContext Resource.Sucess(case)
        }

    override suspend fun assignRoleToCase(caseID: String, role: String): Resource<String> =
        withContext(
            dispatcher
        ) {
            return@withContext try {
                val client = OkHttpClient()
                val mediaType = "application/json".toMediaType()
                val body = "{\n  \"ticket_id\": \"$caseID\",\n  \"assigned_role\": \"$role\"\n}".toRequestBody(mediaType)
                val request = Request.Builder()
                    .url("https://dvm2s3n3nh.execute-api.eu-north-1.amazonaws.com/dev/tickets/assign-role")
                    .patch(body)
                    .addHeader("Content-Type", "application/json")
                    .build()
                val response = client.newCall(request).execute()
                Log.v(TAG, "Role assigned sucess ${response.body?.string()}")
                Resource.Sucess("Role assigned sucess")
            } catch (e: Exception) {
                Log.v(TAG, "Comment add error! $e")
                Resource.Failure<Exception>(Exception("Case add error! $e"))
            }
    }

    override suspend fun removeCase(caseID: String): String =
        withContext(
            dispatcher
        ) {
            return@withContext try {
                val client = OkHttpClient()
                val mediaType = "application/json".toMediaType()
                val body = "{\n  \"ticket_id\": \"c55a23ab-5747-4f7d-9a43-d637372ee51f\",\n  \"assigned_role\": \"Temizlikci\"\n}".toRequestBody(mediaType)
                val request = Request.Builder()
                    .url("https://dvm2s3n3nh.execute-api.eu-north-1.amazonaws.com/dev/tickets/12c556d0-177c-46a5-a2f5-6bea9e99052c/ticket-id")
                    .method("DELETE", body)
                    .addHeader("Content-Type", "application/json")
                    .build()
                val response = client.newCall(request).execute()
                return@withContext "Case removed sucessfuly!"
            } catch (e: Exception) {
                Log.v(TAG, "case remove error! $e")
                "Case remove error! $e"
            }
        }

//    val cases = listOf(
//        Case(
//            id = "1",
//            userID = "1",
//            userName = "john_doe",
//            assignedOfficerSubRole = OfficierSubRoleEnum.electrician,
//            currentProcess = CaseProcessEnum.on_process,
//            image = "url_to_case_image_1",
//            header = CategoryEnum.electric.categoryName,
//            time = LocalDate.now(),
//            description = "There is a recurring issue with the main electrical panel.",
//            location = CaseLocation(
//                address = "123 Apple Street",
//                place = "Community Hall",
//                building = "Building A",
//                floor = "Ground"
//            ),
//            comments = listOf(
//                Comment(
//                    userID = "asfsaf",
//                    userName = "officer_jane",
//                    caseID = "C001",
//                    text = "Inspection scheduled for tomorrow."
//                )
//            )
//        ),
//        Case(
//            id = "1",
//            userID = "JEVjD8jofMY8UmsPozfhTKE4Mey1",
//            userName = "alice_smith",
//            assignedOfficerSubRole = OfficierSubRoleEnum.cleaner,
//            currentProcess = CaseProcessEnum.waiting_for_response,
//            image = "url_to_case_image_2",
//            header = CategoryEnum.cleaning.categoryName,
//            time = LocalDate.now(),
//            description = "Chemical spill in Lab 3 needs urgent attention.",
//            location = CaseLocation(
//                address = "456 Orange Lane",
//                place = "Science Lab",
//                building = "Building C",
//                floor = "2"
//            )
//        ),
//        Case(
//            id = "2",
//            userID = "JEVjD8jofMY8UmsPozfhTKE4Mey1",
//            userName = "mike_brown",
//            assignedOfficerSubRole = null,
//            currentProcess = CaseProcessEnum.completed,
//            image = "url_to_case_image_3",
//            header = CategoryEnum.office_suplies.categoryName,
//            time = LocalDate.now(),
//            description = "All gardening tools in the greenhouse replaced.",
//            location = CaseLocation(
//                address = "789 Banana Boulevard",
//                place = "Greenhouse",
//                building = "Garden Complex",
//                floor = "N/A"
//            )
//        ),
//        Case(
//            id = "3",
//            userID = "JEVjD8jofMY8UmsPozfhTKE4Mey1",
//            userName = "emily_white",
//            assignedOfficerSubRole = OfficierSubRoleEnum.gardener,
//            currentProcess = CaseProcessEnum.failed,
//            image = "url_to_case_image_4",
//            header = CategoryEnum.gardening.categoryName,
//            time = LocalDate.now(),
//            description = "Attempted to fix the central park fountain but the issue persists.",
//            location = CaseLocation(
//                address = "1024 Cherry Circle",
//                place = "Central Park",
//                building = "Outdoor",
//                floor = "N/A"
//            )
//        ),
//        Case(
//            id = "4",
//            userID = "34",
//            userName = "lucas_green",
//            assignedOfficerSubRole = OfficierSubRoleEnum.electrician,
//            currentProcess = CaseProcessEnum.on_process,
//            image = "url_to_case_image_5",
//            header = CategoryEnum.lighting.categoryName,
//            time = LocalDate.now(),
//            description = "Multiple reports of lighting outages on several floors.",
//            location = CaseLocation(
//                address = "321 Grape Road",
//                place = "Office Building",
//                building = "Building B",
//                floor = "Multiple Floors"
//            ),
//            comments = listOf(
//                Comment(
//                    userID = "asfsaf",
//                    userName = "tech_sam",
//                    caseID = "C005",
//                    text = "Need access to electrical panels on all affected floors."
//                )
//            )
//        ),
//        Case(
//            id = "4",
//            userID = "34",
//            userName = "lucasasf_green",
//            assignedOfficerSubRole = OfficierSubRoleEnum.cleaner,
//            currentProcess = CaseProcessEnum.on_process,
//            image = "url_to_case_image_5",
//            header = CategoryEnum.lighting.categoryName,
//            time = LocalDate.now(),
//            description = "adfdafdafdafda.",
//            location = CaseLocation(
//                address = "321 Grape Road",
//                place = "Office Building",
//                building = "Building B",
//                floor = "Multiple Floors"
//            ),
//            comments = listOf(
//                Comment(
//                    userID = "asfssafsafaf",
//                    userName = "tesafsafch_sam",
//                    caseID = "C005",
//                    text = "Need access to electrical panels on all affected floors."
//                )
//            )
//        )
//    )
}