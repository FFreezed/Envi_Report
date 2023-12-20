package id.ac.umn.fadhil.envi_report_2_test.util_database

import android.net.Uri
import java.util.Date

data class ReportData(
    var reportId: Int = 0,
    var reportTitle: String = "",
    var reportDesc: String = "",
    var reportLocation: String = "",
    var reportImageUrl: String? = null,
    var reportDate: Date = Date(),
    var reportStatus: String = "",
    var reportUsername: String = "",
    var imageName: String = ""
)
