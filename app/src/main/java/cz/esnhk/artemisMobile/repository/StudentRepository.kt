package cz.esnhk.artemisMobile.repository

import cz.esnhk.artemisMobile.InternationalStudent


object StudentRepository {

    // TODO: Nahradit za reálná data z API
    private val cryptoList = listOf(
        InternationalStudent(
            id = 1,
            lastLogin = "2024-01-15T10:00:00Z",
            isSuperuser = false,
            objectClass = "InternationalStudent",
            email = "john.doe@example.com",
            firstName = "John",
            lastName = "Doe",
            number = "123456",
            sex = "Male",
            dateJoined = "2023-08-20T14:30:00Z",
            dateOfBirth = "1995-05-10",
            emailSubscription = true,
            jsonState = "{}",
            isActive = true,
            staff = false,
            profilePicture = "https://example.com/john_doe.jpg",
            country = "USA",
            homeUniversity = "University of California, Los Angeles",
            description = "Exchange student from UCLA studying Computer Science.",
            arrivalDate = "2023-09-01",
            arrivalNote = "Arriving at Prague Airport.",
            arrivalMates = "",
            faculty = 1,
            assignedBuddy = 2,
            arrivalPlace = 3,
            accommodation = 4,
            studentGroup = 5,
            groups = listOf(6, 7),
            userPermissions = listOf(8, 9),
            semesters = listOf(10, 11)
        ),
        InternationalStudent(
            id = 2,
            lastLogin = "2024-02-20T12:15:00Z",
            isSuperuser = false,
            objectClass = "InternationalStudent",
            email = "jane.smith@example.com",
            firstName = "Jane",
            lastName = "Smith",
            number = "654321",
            sex = "Female",
            dateJoined = "2023-07-10T09:00:00Z",
            dateOfBirth = "1998-12-05",
            emailSubscription = false,
            jsonState = "{}",
            isActive = true,
            staff = false,
            profilePicture = "https://example.com/jane_smith.jpg",
            country = "Canada",
            homeUniversity = "University of Toronto",
            description = "Exchange student from U of T studying Environmental Science.",
            arrivalDate = "2023-08-25",
            arrivalNote = "Arriving at Vaclav Havel Airport Prague.",
            arrivalMates = "",
            faculty = 2,
            assignedBuddy = 1,
            arrivalPlace = 1,
            accommodation = 2,
            studentGroup = 3,
            groups = listOf(1, 2),
            userPermissions = listOf(3, 4),
            semesters = listOf(1, 2)
        ),
        InternationalStudent(
            id = 3,
            lastLogin = "2024-03-10T15:30:00Z",
            isSuperuser = false,
            objectClass = "InternationalStudent",
            email = "mike.johnson@example.com",
            firstName = "Mike",
            lastName = "Johnson",
            number = "987654",
            sex = "Male",
            dateJoined = "2023-09-15T11:45:00Z",
            dateOfBirth = "1997-07-22",
            emailSubscription = true,
            jsonState = "{}",
            isActive = true,
            staff = false,
            profilePicture = "https://example.com/mike_johnson.jpg",
            country = "Australia",
            homeUniversity = "University of Sydney",
            description = "Exchange student from USYD studying Mechanical Engineering.",
            arrivalDate = "2023-10-05",
            arrivalNote = "Arriving at Prague Ruzyne Airport.",
            arrivalMates = "",
            faculty = 3,
            assignedBuddy = 3,
            arrivalPlace = 2,
            accommodation = 1,
            studentGroup = 2,
            groups = listOf(3, 4),
            userPermissions = listOf(5, 6),
            semesters = listOf(2, 3)
        ),
        InternationalStudent(
            id = 4,
            lastLogin = "2024-04-05T08:00:00Z",
            isSuperuser = false,
            objectClass = "InternationalStudent",
            email = "sara.lee@example.com",
            firstName = "Sara",
            lastName = "Lee",
            number = "456789",
            sex = "Female",
            dateJoined = "2023-10-20T16:20:00Z",
            dateOfBirth = "1996-03-18",
            emailSubscription = false,
            jsonState = "{}",
            isActive = true,
            staff = false,
            profilePicture = "https://example.com/sara_lee.jpg",
            country = "South Korea",
            homeUniversity = "Seoul National University",
            description = "Exchange student from SNU studying Economics.",
            arrivalDate = "2023-11-15",
            arrivalNote = "Arriving at Prague International Airport.",
            arrivalMates = "",
            faculty = 1,
            assignedBuddy = 4,
            arrivalPlace = 3,
            accommodation = 4,
            studentGroup = 1,
            groups = listOf(5, 6),
            userPermissions = listOf(7, 8),
            semesters = listOf(1, 3)
        )
    )

    fun getStudentList(): List<InternationalStudent> {
        return cryptoList
    }

    fun getStudentByBuddyId(id: Int): List<InternationalStudent> {
        return cryptoList.filter { it.assignedBuddy == id }
    }


    //todo add, remove, isIn favorites
}