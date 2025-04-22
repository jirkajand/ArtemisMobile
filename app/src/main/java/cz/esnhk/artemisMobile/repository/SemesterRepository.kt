package cz.esnhk.artemisMobile.repository

import cz.esnhk.artemisMobile.entities.Semester
import cz.esnhk.artemisMobile.entities.SemesterInfo

object SemesterRepository {

    // TODO: Replace with actual data from API
    private val semesterList = listOf(
        Semester(
            id = 4,
            semesterInfo = emptyList(), // Assuming empty list for now
            isCurrent = false,
            label = "2025/2026 Summer semester",
            year = 2025,
            semester = "summer"
        ),
        Semester(
            id = 5,
            semesterInfo = emptyList(),
            isCurrent = false,
            label = "2025/2026 Winter semester",
            year = 2025,
            semester = "winter"
        ),
        Semester(
            id = 3,
            semesterInfo = emptyList(),
            isCurrent = false,
            label = "2024/2025 Summer semester",
            year = 2024,
            semester = "summer"
        ),
        Semester(
            id = 2,
            semesterInfo = emptyList(),
            isCurrent = false,
            label = "2024/2025 Winter semester",
            year = 2024,
            semester = "winter"
        ),
        Semester(
            id = 1,
            semesterInfo = listOf(
                SemesterInfo(
                    id = 1,
                    image = "https://demo.artemis.esnhk.cz/media/semester_info_images/fake-qr.jpg",
                    description = "<p>This is just a fake QR code.</p>"
                ),
                SemesterInfo(
                    id = 2,
                    image = "https://demo.artemis.esnhk.cz/media/semester_info_images/fake-qr_cunVY8t.jpg",
                    description = "<p>Another <b>good</b> information that can be showed up to everyone.</p>"
                )
            ),
            isCurrent = true,
            label = "2023/2024 Winter semester",
            year = 2023,
            semester = "winter"
        )
    )

    fun getSemesterList(): List<Semester> {
        return semesterList
    }

    // You can add more functions here to filter or search semesters if needed.
    // For example:

    fun getCurrentSemester(): Semester? {
        return semesterList.find { it.isCurrent }
    }

    fun getSemesterById(id: Int): Semester? {
        return semesterList.find { it.id == id }
    }
}